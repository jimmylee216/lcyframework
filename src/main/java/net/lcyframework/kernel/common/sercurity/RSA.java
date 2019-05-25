/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lcyframework.kernel.common.sercurity;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import net.lcyframework.kernel.common.util.Base64Util;
import net.lcyframework.kernel.core.consts.SysErrorConsts;
import net.lcyframework.kernel.core.exception.SysException;

/**
 * <pre>
 * RSA加密
 * </pre>
 * 慎用： RSA加解密容易导致性能问题和线程安全问题，请阅读：http://blog.51cto.com/nxlhero/1832127
 * 1、RSA解密的本质就是幂模运算，也就是x = a ^ b  mod  n ，其中a是明文，b是私钥，n是两个大质数(p-1)(q-1)的积，在做这个运算的时候，
 * 为了防范被破解，需要加入随机因素，寻找一个blinding parameter，让破解者无法破解私钥，就是在寻找这个blinding parameter的时候线程出现了堵塞
 * 2、Cipher存在线程安全问题
 * @author Jimmy Li
 * @since 1.0.0
 */
public final class RSA {
    /**
     * 加密算法RSA:RSA
     */
    //public static final String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法:MD5withRSA
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key:RSAPublicKey
     */
    public static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key:RSAPrivateKey
     */
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小:117
     */
    public static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小:128
     */
    public static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA秘钥长度: 必须是64的倍数,其范围在512位到1024位之间
     */
    public static final int KEY_LENGTH = 512;

    private RSA() { }

    /**
     * 返回一个密钥对
     *
     * @return 密钥对
     * @throws Exception 异常
     */
    public static KeyPair getKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数
            keyPairGen.initialize(KEY_LENGTH);
            // 密钥对
            return keyPairGen.generateKeyPair();
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
    }

    /**
     * 获取：使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus 模
     * @param privateExponent 私钥指数
     * @return 私钥
     * @throws Exception 异常
     */
    public static RSAPrivateKey getPrivateKey(final String modulus, final String privateExponent) {
        if (modulus == null || privateExponent == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[modulus], param[privateExponent]不允许为空");
        }
        try {
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
    }

    /**
     * 获取：私钥(RSAPrivateKey)
     * @param keyPair 密钥对
     * @return 私钥(RSAPrivateKey)
     */
    public static RSAPrivateKey getPrivateKey(final KeyPair keyPair) {
        if (keyPair == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[keyPair]不允许为空");
        }
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * 获取：私钥(byte)
     * @param privateKey 私钥(RSAPrivateKey)
     * @return 私钥(byte)
     */
    public static byte[] getPrivateKeyByte(final RSAPrivateKey privateKey) {
        if (privateKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[privateKey]不允许为空");
        }
        return privateKey.getEncoded();
    }

    /**
     * 获取：私钥(BASE64编码)
     * @param privateKey 私钥(byte)
     * @return 私钥(BASE64编码)
     */
    public static String getPrivateKeyBase64(final byte[] privateKey) {
        if (privateKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[privateKey]不允许为空");
        }
        return Base64Util.encode(privateKey);
    }

    /**
     * 获取：私钥(byte)
     * @param privateKeyBase64 私钥(BASE64编码)
     * @return 私钥(byte)
     */
    public static byte[] loadPrivateKey(final String privateKeyBase64) {
        if (privateKeyBase64 == null || "".equals(privateKeyBase64)) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[privateKeyBase64]不允许为空");
        }
        return Base64Util.decode(privateKeyBase64);
    }

    /**
     * 获取创建私钥的指数值
     *
     * @param privateKey 私钥(RSAPrivateKey)
     * @return 私钥的指数
     */
    public static String getPrivateExponent(final RSAPrivateKey privateKey) {
        if (privateKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[privateKey]不允许为空");
        }
        return privateKey.getPrivateExponent().toString();
    }

    /**
     * 获取：使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus 模
     * @param publicExponent 公钥指数
     * @return 公钥
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(final String modulus, final String publicExponent) {
        if (modulus == null || publicExponent == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[modulus], param[publicExponent]不允许为空");
        }
        try {
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
    }

    /**
     * 获取：公钥(RSAPublicKey)
     * @param keyPair 密钥对
     * @return 公钥(RSAPublicKey)
     */
    public static RSAPublicKey getPublicKey(final KeyPair keyPair) {
        if (keyPair == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[keyPair]不允许为空");
        }
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 获取：公钥(byte)
     * @param publicKey 公钥(RSAPublicKey)
     * @return 公钥(byte)
     */
    public static byte[] getPublicKeyByte(final RSAPublicKey publicKey) {
        if (publicKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[publicKey]不允许为空");
        }
        return publicKey.getEncoded();
    }

    /**
     * 获取：公钥(BASE64编码)
     * @param publicKey 公钥(byte)
     * @return 公钥(BASE64编码)
     */
    public static String getPublicKeyBase64(final byte[] publicKey) {
        if (publicKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[publicKey]不允许为空");
        }
        return Base64Util.encode(publicKey);
    }

    /**
     * 获取：公钥(byte)
     * @param publicKeyBase64 公钥(BASE64编码)
     * @return 公钥(byte)
     */
    public static byte[] loadPublicKey(final String publicKeyBase64) {
        if (publicKeyBase64 == null || "".equals(publicKeyBase64)) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[publicKeyBase64]不允许为空");
        }
        return Base64Util.decode(publicKeyBase64);
    }

    /**
     * 获取创建公钥的指数值
     *
     * @param publicKey 公钥(RSAPublicKey)
     * @return 公钥的指数
     */
    public static String getPublicExponent(final RSAPublicKey publicKey) {
        if (publicKey == null) {
            throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[publicKey]不允许为空");
        }
        return publicKey.getPublicExponent().toString();
    }

   /**
    * 获取创建公钥的模值
    *
    * @param publicKey 公钥(RSAPublicKey)
    * @return 公钥的指数
    */
   public static String getModulus(final RSAPublicKey publicKey) {
       if (publicKey == null) {
           throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[publicKey]不允许为空");
       }
       return publicKey.getModulus().toString();
   }

   /**
    * 私钥加密(PKCS8EncodedKeySpec)
    *
    * @param data 数据
    * @param key 私钥
    * @return 加密数据(byte)
    */
   public static byte[] encryptByPrivateKey(final byte[] data, final  byte[] key) {
        try {
            // 取的私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成秘钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            return encryptedData;
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
   }

   /**
    * 私钥解密(PKCS8EncodedKeySpec)
    *
    * @param data 加密数据(BASE64编码)
    * @param key 私钥(BASE64编码)
    * @return 解密后的字符串
    */
   public static byte[] decryptByPrivateKey(final byte[] data, final byte[] key) {
        try {
            // 取的私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成秘钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
   }

   /**
    * 私钥加密(PKCS8EncodedKeySpec)
    *
    * @param data 数据
    * @param key 私钥(BASE64编码)
    * @return 解密后的字符串
    */
   public static String encryptByPrivateKey(final String data, final String key) {
       if (data == null || key == null) {
           throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[data],param[key]不允许为空");
       }
       return new String(encryptByPrivateKey(Base64Util.decode(data), Base64Util.decode(key)));
   }

   /**
    * 私钥解密(PKCS8EncodedKeySpec)
    *
    * @param data 数据
    * @param key 私钥(BASE64编码)
    * @return 解密后的字符串
    */
   public static String decryptByPrivateKey(final String data, final String key) {
       if (data == null || key == null) {
           throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[data],param[key]不允许为空");
       }
       return new String(decryptByPrivateKey(Base64Util.decode(data), Base64Util.decode(key)));
   }

   /**
    * 公钥加密(X509EncodedKeySpec)
    *
    * @param data  数据
    * @param key 公钥
    * @return 字节数组
    */
   public static byte[] encryptByPublicKey(final byte[] data, final byte[] key) {
        try {
            // 获得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
   }

   /**
    * 公钥解密(X509EncodedKeySpec)
    *
    * @param data  数据
    * @param key 公钥
    * @return 字节数组
    */
   public static byte[] decryptByPublicKey(final byte[] data, final byte[] key) {
        try {
            //获得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //生成公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (final Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }
   }

   /**
    * 公钥加密(X509EncodedKeySpec)
    *
    * @param data 数据
    * @param key 公钥(BASE64编码)
    * @return 解密后的字符串
    */
   public static String encryptByPublicKey(final String data, final String key) {
       if (data == null || key == null) {
           throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[data],param[key]不允许为空");
       }
       return new String(encryptByPublicKey(Base64Util.decode(data), Base64Util.decode(key)));
   }

   /**
    * 公钥解密(X509EncodedKeySpec)
    *
    * @param data 数据
    * @param key 公钥(BASE64编码)
    * @return 解密后的字符串
    */
   public static String decryptByPublicKey(final String data, final String key) {
       if (data == null || key == null) {
           throw new SysException(SysErrorConsts.MISSING_ARGUMETN_ERROR_CODE, "param[data],param[key]不允许为空");
       }
       return new String(decryptByPublicKey(Base64Util.decode(data), Base64Util.decode(key)));
   }

}
