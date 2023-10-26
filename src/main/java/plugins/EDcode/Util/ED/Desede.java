package plugins.EDcode.Util.ED;

import java.security.NoSuchAlgorithmException;

public class Desede {
    public static String encode(String data, String key, String mode, String padding, String outputType) throws Exception {
        return encode(data, key, mode, padding, outputType, null);
    }

    public static String encode(String data, String key, String mode, String padding, String outputType, String iv) throws Exception {
        return Aes2.encode(data, key, "DESede", mode, padding, outputType, iv);
    }

    public static String decode(String data, String key, String mode, String padding, String outputType) throws Exception {
        return decode(data, key, mode, padding, outputType, null);
    }

    public static String decode(String data, String key, String mode, String padding, String outputType, String iv) throws Exception {
        return Aes2.decode(data, key, "DESede", mode, padding, outputType, iv);
    }

    // 获取随机 key 80/128
    public static String getRandomKey(int keySize) {
        try {
            return Aes2.keyToString(Aes2.generateAESKey(keySize));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取随机 IV 偏移量
    public static byte[] getRandomIV() {
        return Aes2.generateRandomIV(8);
    }
}
