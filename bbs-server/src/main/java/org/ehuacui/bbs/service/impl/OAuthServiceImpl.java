package org.ehuacui.bbs.service.impl;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.ehuacui.bbs.model.OAuthUserInfo;
import org.ehuacui.bbs.service.OAuthService;
import org.ehuacui.bbs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * GitHubOAuthService
 * Created by jianwei.zhou on 2016/9/29.
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.callback}")
    private String callback;

    private OAuth20Service buildOAuthService(String secretState) {
        return new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .state(secretState)
                .callback(callback)
                .build(GitHubApi.instance());
    }

    @Override
    public String getAuthorizationUrl(String secretState) {
        return buildOAuthService(secretState).getAuthorizationUrl();
    }

    @Override
    public OAuthUserInfo getOAuthUserInfo(String code, String secretState) {
        String resourceUrl = "https://api.github.com/user";
        try {
            OAuth20Service oAuth20Service = buildOAuthService(secretState);
            OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
            OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl, oAuth20Service);
            oAuth20Service.signRequest(accessToken, request);
            Response response = request.send();
            /*
            int responseCode = response.getCode();
            if (responseCode == 200) {
            }
            */
            String responseBody = response.getBody();
            Map data = JsonUtil.nonDefaultMapper().fromJson2Map(responseBody);
            String githubId = data.get("id").toString();
            String login = data.get("login").toString();
            String avatar_url = data.get("avatar_url").toString();
            String email = data.get("email").toString();
            String html_url = data.get("html_url").toString();
            OAuthUserInfo oAuthUserInfo = new OAuthUserInfo();
            oAuthUserInfo.setOauthUserId(githubId);
            oAuthUserInfo.setLoginName(login);
            oAuthUserInfo.setAvatarUrl(avatar_url);
            oAuthUserInfo.setUserEmail(email);
            oAuthUserInfo.setHomeUrl(html_url);
            return oAuthUserInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
