package plugins.EDcode.Util.ED;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Aes_test {
    public static void main(String[] args) throws Exception {

//        demo();

        String code = "hello world";
        int keySize = 128; // 128, 192, or 256
        SecretKey secretKey = Aes.generateAESKey(keySize);
        String b = Aes.keyToString(secretKey);
        String key = "Rw9fxVvWVBK6cLW4a21JSQ==";
        System.out.println(b);
        String mode = "CBC"; // ECB, CBC, CTR, OFB, or CFB
        String padding = "PKCS5Padding"; // PKCS5Padding, PKCS7Padding, NoPadding, ISO10126Padding, ISO9797ALG3Padding, ANSI_X923Padding, or ZeroPadding
        String outputType = "Base64"; // Base64 or Hex

//        byte[] ivb = Aes.generateRandomIV(32); // 对应分别是 16、24、32 位的偏移量
//        String iv = "";
//        for (byte i : ivb){
//            iv += i + " ";
//        }
        String iv = "0123456789abcdef";

        System.out.println(iv);
        String a = Aes.encode(code, key, mode, padding, outputType, iv);
        System.out.println(a);

        // 解密
        String c = Aes.decode("D72Ud44z0bpCrye8PHiy0Q==", key, mode, padding, outputType, iv);
        System.out.println(c);
    }

    private static void demo() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {

        String key = "Rw9fxVvWVBK6cLW4a21JSQ=="; // 256-bit AES key
        String iv = "0123456789abcdef";  // 256-bit IV

        String plaintext = "This is a secret message.";

        // Convert key and IV to byte arrays
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] ivBytes = iv.getBytes("UTF-8");

        // Create AES cipher and initialize with key and IV
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

        // Encrypt the plaintext
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));

        // Encode the encrypted data as a Base64 string
        String encryptedText = java.util.Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted Text: " + encryptedText);

        // Decryption (use the same key and IV)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(java.util.Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes, "UTF-8");

        System.out.println("Decrypted Text: " + decryptedText);
    }



}
