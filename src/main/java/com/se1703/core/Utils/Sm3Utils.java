package com.se1703.core.Utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import java.nio.charset.StandardCharsets;

/**
 * @author leekejin
 * @date 2020-9-10 16:05:14
 */
public class Sm3Utils {

    private static final String defaultKey = "hust软工1703第二十一组";
    /**
     * 加密
     *
     * @param plainText 明文
     * @return 密文
     */
    public static String encryption(String plainText) {
        SM3Digest digest = new SM3Digest();
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return ByteUtils.toHexString(hash);
    }

    /**
     * 通过密钥进行加密
     * @explain 指定密钥进行加密
     * @param keyStr
     *            密钥
     * @param srcStr
     *            被加密的byte数组
     * @return 十六进制加密字符串
     */
    public static String hmac(String keyStr, String srcStr) {
        byte[] srcData = srcStr.getBytes(StandardCharsets.UTF_8);
        byte[] key = keyStr.getBytes(StandardCharsets.UTF_8);
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return ByteUtils.toHexString(result);
    }

    /**
     * 通过默认密钥进行加密
     * @explain 指定密钥进行加密
     * @param srcStr
     *            被加密的byte数组
     * @return 十六进制加密字符串
     */
    public static String hmac(String srcStr){
        return hmac(defaultKey,srcStr);
    }


    /**
     * 校验
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @return 校验结果
     */
    public static boolean verification(String plainText, String cipherText) {
        String encryptionText = encryption(plainText);
        return encryptionText.equals(cipherText);
    }

    /**
     * 校验
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @param key 密钥
     * @return 校验结果
     */
    public static boolean verificationWithKey(String plainText, String cipherText, String key){
        String encryptionText = hmac(key,plainText);
        return encryptionText.equals(cipherText);
    }

    /**
     * 默认密钥校验
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @return 校验结果
     */
    public static boolean verificationWithKey(String plainText, String cipherText){
        return verificationWithKey(plainText,cipherText,defaultKey);
    }

}
