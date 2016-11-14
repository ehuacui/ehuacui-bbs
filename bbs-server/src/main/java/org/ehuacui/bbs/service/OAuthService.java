package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.OAuthUserInfo;

/**
 * OAuth Service Interface
 * Created by jianwei.zhou on 2016/9/30.
 */
public interface OAuthService {

    /**
     * Get AuthorizationUrl
     *
     * @return AuthorizationUrl
     */
    String getAuthorizationUrl(String secretState);

    OAuthUserInfo getOAuthUserInfo(String code, String secretState);
}
