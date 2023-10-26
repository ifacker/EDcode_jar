package plugins.EDcode.Util.ED;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;
public class Rsa2 {
    public static String publicKeyHome = "-----BEGIN PUBLIC KEY-----";
    public static String publicKeyEnd = "-----END PUBLIC KEY-----";
    public static String privateKeyHome = "-----BEGIN RSA PRIVATE KEY-----";
    public static String privateKeyEnd = "-----END RSA PRIVATE KEY-----";
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize); // 使用2048位密钥，可以根据需要调整
        return keyPairGenerator.generateKeyPair();
    }

    public static String encryptWithPublicKey(String plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptWithPrivateKey(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static String encryptWithPrivateKey(String plaintext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptWithPublicKey(String encryptedText, PublicKey publicKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String privateKeyToString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static String fuckHomeEnd(String code){
        return code
                .replaceAll(publicKeyHome, "")
                .replaceAll(publicKeyEnd, "")
                .replaceAll(privateKeyHome, "")
                .replaceAll(privateKeyEnd, "")
                .trim();
    }
}
