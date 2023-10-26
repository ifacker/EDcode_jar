package plugins.EDcode.Util.ED;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
public class Rsa2_test {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = Rsa2.generateKeyPair(2048);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String originalText = "Hello, RSA!";
        String encryptedWithPublicKey = Rsa2.encryptWithPublicKey(originalText, publicKey);
        System.out.println("Encrypted with Public Key: " + encryptedWithPublicKey);

        String decryptedWithPrivateKey = Rsa2.decryptWithPrivateKey(encryptedWithPublicKey, privateKey);
        System.out.println("Decrypted with Private Key: " + decryptedWithPrivateKey);

        String encryptedWithPrivateKey = Rsa2.encryptWithPrivateKey(originalText, privateKey);
        System.out.println("Encrypted with Private Key: " + encryptedWithPrivateKey);

        String decryptedWithPublicKey = Rsa2.decryptWithPublicKey(encryptedWithPrivateKey, publicKey);
        System.out.println("Decrypted with Public Key: " + decryptedWithPublicKey);
    }
}
