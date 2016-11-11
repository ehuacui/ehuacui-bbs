package org.ehuacui.bbs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * Created by jianwei.zhou on 2016/10/27.
 */
public class MD5Util {

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};

    private static String encryptPwd(String password, String algorithm) throws RuntimeException {
        try {
            MessageDigest ssha = MessageDigest.getInstance(algorithm);
            ssha.update(password.getBytes());
            byte[] bytes = ssha.digest();
            int j = bytes.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = bytes[i];
                str[k++] = hexDigits[(byte0 >>> 4) & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("encryptPwd failed");
        }
    }

    public static String crypt(String password) throws RuntimeException {
        return encryptPwd(password, "MD5");
    }

    public static void main(String[] args) throws Exception {
        System.err.print(crypt("123456"));
    }
}
