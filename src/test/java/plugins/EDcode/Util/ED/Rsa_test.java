package plugins.EDcode.Util.ED;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Rsa_test {
    public static void main(String[] args) throws Exception {
        String password = "1234abcd5678";
        Rsa.KeyStore keys = Rsa.createKeys();
        byte[] publicKey = Rsa.getPublicKey(keys);
        byte[] privateKey = Rsa.getPrivateKey(keys);
        System.out.println("公钥："+ Base64.encode(Arrays.toString(publicKey)));
        System.out.println("私钥："+ Base64.encode(Arrays.toString(privateKey)));

        byte[] encryptByPublicKey = Rsa.encryptByPublicKey(password.getBytes(), publicKey);
        System.out.println("使用公钥加密后的数据："+Base64.encode(Arrays.toString(encryptByPublicKey)));

        byte[] decryptByPrivateKey = Rsa.decryptByPrivateKey(encryptByPublicKey, privateKey);
        System.out.println("使用私钥解密后的数据："+new String(decryptByPrivateKey));
    }
}
