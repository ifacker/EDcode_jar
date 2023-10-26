package plugins.EDcode.Util.ED;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class Aes2 {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    public static String encode(String data, String key, String mode, String padding, String outputType) throws Exception {
        return encode(data, key, mode, padding, outputType, null);
    }

    public static String encode(String data, String key, String mode, String padding, String outputType, String iv) throws Exception {
        return encode(data, key, "AES", mode, padding, outputType, iv);
    }


    /**
     * @param data 明文
     * @param key  密钥，长度16
     * @param iv   偏移量，长度16
     * @return 密文
     * @author miracle.qu
     * @Description AES算法加密明文
     */
    public static String encode(String data, String key, String type, String mode, String padding, String outputType, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", type, mode, padding));
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = data.getBytes();
        int plaintextLength = dataBytes.length;

        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), type);

        // 判断 IV
        if (iv != null) {
            byte[] ivb = veriftIV(key, iv);
            IvParameterSpec ivspec = new IvParameterSpec(ivb);  // CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        }

        byte[] encrypted = cipher.doFinal(plaintext);

        // 判断输出类型
        switch (outputType) {
            case "Base64":
                return base64Encode(encrypted).trim(); // BASE64做转码。
            case "Hex":
                return bytesToHex(encrypted).trim();    // Hex 做转码
            default:
                return null;
        }

    }

    public static String decode(String data, String key, String mode, String padding, String outputType) throws Exception {
        return decode(data, key, mode, padding, outputType, null);
    }

    public static String decode(String data, String key, String mode, String padding, String outputType, String iv) throws Exception {
        return decode(data, key, "AES", mode, padding, outputType, iv);
    }

    /**
     * @param data 密文
     * @param key  密钥，长度16
     * @param iv   偏移量，长度16
     * @return 明文
     * @author miracle.qu
     * @Description AES算法解密密文
     */
    public static String decode(String data, String key, String type, String mode, String padding, String outputType, String iv) throws Exception {

        Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", type, mode, padding));
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), type);

        // 判断 iv
        if (iv != null) {
            byte[] ivb = veriftIV(key, iv);
            IvParameterSpec ivspec = new IvParameterSpec(ivb);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
        }

        byte[] encrypted1 = new byte[0];
        // 判断输出类型
        switch (outputType) {
            case "Base64":
                encrypted1 = base64Decode(data);//先用base64解密
                break;
            case "Hex":
                encrypted1 = hexToBytes(data);
                break;
        }

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString.trim();

    }

    /**
     * 编码
     *
     * @param byteArray
     * @return
     */
    public static String base64Encode(byte[] byteArray) {
        return new String(new org.apache.commons.codec.binary.Base64().encode(byteArray));
    }

    /**
     * 解码
     *
     * @param base64EncodedString
     * @return
     */
    public static byte[] base64Decode(String base64EncodedString) {
        return new Base64().decode(base64EncodedString);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        return bytes;
    }

    private static byte[] veriftIV(String key, String iv) {
        byte[] ivb;
        iv = iv.trim();
        if (iv.contains(",")) {
            ivb = veriftIVSub(iv, ",", key);
        } else if (iv.contains(" ")) {
            ivb = veriftIVSub(iv, " ", key);
        } else if (iv.contains("\n")) {
            ivb = veriftIVSub(iv, "\n", key);
        } else {
            ivb = iv.getBytes();
        }
        return ivb;
    }

    private static byte[] veriftIVSub(String iv, String sp, String key) {
        String[] ivs = iv.split(sp);

        byte[] ivb = new byte[ivs.length];
        for (int i = 0; i < ivs.length; i++) {
            ivb[i] = (byte) Integer.parseInt(ivs[i].trim());
        }
        return ivb;
    }

    /**
     * 生成 key
     *
     * @param keySize 设置密钥的长度，分别可以设置：128位、192位、256位
     * @return 返回 SecretKey，也可以 keyToString() 转换位字符串类型
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateAESKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize); // 设置密钥长度，可以是128、192或256
        return keyGen.generateKey();
    }

    /**
     * 生成偏移量
     *
     * @param ivSize 生成偏移量位数，128位的 key 对应 16位的偏移量；192对24位；256对32位。
     * @return 返回 iv 偏移量 byte[] 数组
     */
    public static byte[] generateRandomIV(int ivSize) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[ivSize]; // 128-bit IV
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    /**
     * 将 SecretKey 转换为 String 类型的 key
     *
     * @param secretKey key
     * @return String 类型的 key
     */
    public static String keyToString(SecretKey secretKey) {
        return java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 将 String 类型的 key 转换为 正儿八经的 Key
     *
     * @param stringKey String 类型的 key
     * @return 返回正儿八经的 key
     */
    public static SecretKey stringToKey(String stringKey) {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(stringKey);
        return new SecretKeySpec(decodedKey, "AES");
    }
}
