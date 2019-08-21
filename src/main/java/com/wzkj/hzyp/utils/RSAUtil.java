package com.wzkj.hzyp.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.xml.crypto.Data;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA加密解密
 * 此工具类能使用指定的字符串，每次生成相同的公钥和私钥且在linux和windows密钥也相同；相同的原文和密钥生成的密文相同
 */
public class RSAUtil {
    private static final String ALGORITHM_RSA = "RSA";
    private static final String ALGORITHM_SHA1PRNG = "SHA1PRNG";
    private static final int KEY_SIZE = 1024;
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String TRANSFORMATION = "RSA/None/NoPadding";
    public static final String seed = "zsq";

    public static void main(String[] args) {
//        RSAUtil rsaUtil = new RSAUtil();
//
//        String source = "12dfefDKLJKLKL464d中文f465as43f1a3 f46e353D1F34&*^$E65F46EF43456abcd54as56f00ef";//原文
//        String seed = "zsq";//种子
//
//        System.out.println("原文：\n" + source);
//        Map<String, Object> keyMap = null;//初始化密钥
//        try {
//            keyMap = rsaUtil.initKey(seed);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String publicKey = rsaUtil.getPublicKey(keyMap);//公钥
//        String privateKey = rsaUtil.getPrivateKey(keyMap);//私钥
//        System.out.println("公钥：\n" + publicKey);
//        System.out.println("私钥：\n" + privateKey);
//        String encodedStr = null;//加密
//        try {
//            encodedStr = rsaUtil.encryptByPublicKey(source, publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("密文：\n" + encodedStr);
//        String decodedStr = null;//解密
//        try {
//            decodedStr = rsaUtil.decryptByPrivateKey(encodedStr, privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("解密后的结果：\n" + decodedStr);
        String token = getToken("ssasasa");
        System.out.println(token);
        String source = getDecryptToken(token);
        System.out.println(source);
        String id = RSAUtil.getId(source);
        System.out.println(id);
    }

    public static String getToken(String id){
        Date date = new Date();
        String dateString = date.toString();
        String source = id + ":Date" + dateString;
        Map<String,Object> keyMap = null;
        try {
            keyMap = RSAUtil.initKey(seed);
            String publicKey = RSAUtil.getPublicKey(keyMap);//公钥
            String token = RSAUtil.encryptByPublicKey(source,publicKey);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDecryptToken(String token){
        Map<String,Object> keyMap = null;
        try {
            keyMap = RSAUtil.initKey(seed);
            String privateKey = RSAUtil.getPrivateKey(keyMap);//私钥
            String decryptToken = RSAUtil.decryptByPrivateKey(token,privateKey);
            return decryptToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getId(String token){
        if(StringUtils.isNotBlank(token)){
            String id = token.substring(0,token.indexOf(":Date"));
            return id;
        }
        return null;
    }

    /**
     * 解密
     * 用私钥解密，解密字符串，返回字符串
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key) throws Exception {
        return new String(decryptByPrivateKey(BASE64Util.decodeToByte(data), key));
    }

    /**
     * 加密
     * 用公钥加密，加密字符串，返回用base64加密后的字符串
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String key) throws Exception {
        return encryptByBytePublicKey(data.getBytes(), key);
    }

    /**
     * 加密
     * 用公钥加密，加密byte数组，返回用base64加密后的字符串
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByBytePublicKey(byte[] data, String key) throws Exception {
        return BASE64Util.encodeByte(encryptByPublicKey(data, key));
    }

    /**
     * 解密
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = BASE64Util.decodeToByte(key);//对私钥解密
        /*取得私钥*/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        /*对数据解密*/
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, new BouncyCastleProvider());//相同的原文、公钥能生成相同的密文。如果使用Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());//相同的原文、公钥生成的密文不同
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = BASE64Util.decodeToByte(key);//对公钥解密
        /*取得公钥*/
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        /*对数据加密*/
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, new BouncyCastleProvider());//相同的原文、公钥能生成相同的密文。如果使用Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());//相同的原文、公钥生成的密文不同
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return BASE64Util.encodeByte(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return BASE64Util.encodeByte(key.getEncoded());
    }

    /**
     * 初始化公钥和私钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey(String seed) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        SecureRandom random = SecureRandom.getInstance(ALGORITHM_SHA1PRNG);//如果使用SecureRandom random = new SecureRandom();//windows和linux默认不同，导致两个平台生成的公钥和私钥不同
        random.setSeed(seed.getBytes());//使用种子则生成相同的公钥和私钥
        keyPairGen.initialize(KEY_SIZE, random);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();//私钥

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


}
