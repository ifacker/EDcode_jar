package plugins.EDcode.Util.ED;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import plugins.EDcode.Util.MsgBox;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.spec.IvParameterSpec;

public class Aes {
//    static List<Integer> ivLen = Arrays.stream(new int[]{16, 24, 32}).boxed().collect(Collectors.toList());
//    static List<Integer> keyLen = Arrays.stream(new int[]{24, 32, 44}).boxed().collect(Collectors.toList());

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encode(String plainText, String key, String mode, String padding, String outputType, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
        byte[] ivb = veriftIV(key, iv);
        if (ivb == null) {
            MsgBox.sendSystemInfo("错误", "IV 异常");
            return "";
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivb); // 使用自定义的 IV
        cipher.init(Cipher.ENCRYPT_MODE, stringToKey(key), ivSpec);

        return enSub(plainText, cipher, outputType);
    }

    public static String encode(String plainText, String key, String mode, String padding, String outputType) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
        cipher.init(Cipher.ENCRYPT_MODE, stringToKey(key));

        return enSub(plainText, cipher, outputType);
    }

    private static String enSub(String code, Cipher cipher, String outputType) throws Exception {
//        byte[] encryptedBytes = cipher.doFinal(code.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedBytes = cipher.doFinal(code.getBytes());
        if (outputType.equalsIgnoreCase("Base64")) {
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } else if (outputType.equalsIgnoreCase("Hex")) {
            return bytesToHex(encryptedBytes);
        } else {
            throw new IllegalArgumentException("Invalid output type: " + outputType);
        }
    }

    public static String decode(String encryptedText, String key, String mode, String padding, String outputType, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
        byte[] ivb = veriftIV(key, iv);
        if (ivb == null) {
            MsgBox.sendSystemInfo("错误", "IV 异常");
            return "";
        }
        IvParameterSpec ivSpec = new IvParameterSpec(ivb); // 使用相同的 IV
        cipher.init(Cipher.DECRYPT_MODE, stringToKey(key), ivSpec);

        return deSub(encryptedText, cipher, outputType);
    }


    public static String decode(String encryptedText, String key, String mode, String padding, String outputType) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
        cipher.init(Cipher.DECRYPT_MODE, stringToKey(key));

        return deSub(encryptedText, cipher, outputType);
    }

    private static String deSub(String code, Cipher cipher, String outputType) throws Exception {
        if (outputType.equalsIgnoreCase("Base64")) {
            byte[] encryptedBytes = Base64.getDecoder().decode(code);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
//            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } else if (outputType.equalsIgnoreCase("Hex")) {
            byte[] encryptedBytes = hexToBytes(code);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
//            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Invalid output type: " + outputType);
        }
    }

    // 验证 iv
    private static byte[] veriftIV(String key, String iv) {
        byte[] ivb;
        iv = iv.trim();
        if (iv.contains(",")) {
            ivb = veriftIVSub(iv, ",", key);
        } else if (iv.contains(" ")) {
            ivb = veriftIVSub(iv, " ", key);
        } else if (iv.contains("\n")) {
            ivb = veriftIVSub(iv, "\n", key);
//        } else if (!ivLen.contains(iv.length()) || !keyLen.contains(key.length())) {
//            MsgBox.sendSystemInfo("错误", "key 与 iv 位数不同（128位key对应16位iv、192对24位、256对32位）");
//            return null;
        } else {
            ivb = iv.getBytes();
        }
        return ivb;
    }

    private static byte[] veriftIVSub(String iv, String sp, String key) {
        String[] ivs = iv.split(sp);

//        if (!ivLen.contains(ivs.length) && !keyLen.contains(key.length())) {
//            MsgBox.sendSystemInfo("错误", "key 与 iv 位数不同（128位key对应16位iv、192对24位、256对32位）");
//            return null;
//        }
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
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 将 String 类型的 key 转换为 正儿八经的 Key
     *
     * @param stringKey String 类型的 key
     * @return 返回正儿八经的 key
     */
    public static SecretKey stringToKey(String stringKey) {
        byte[] decodedKey = Base64.getDecoder().decode(stringKey);
        return new SecretKeySpec(decodedKey, "AES");
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
}
