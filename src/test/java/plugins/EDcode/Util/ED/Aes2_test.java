package plugins.EDcode.Util.ED;

public class Aes2_test {
    public static void main(String[] args) throws Exception {
        String code = "hello world";
        String key = "Rw9fxVvWVBK6cLW4a21JSQ==";
        String mode = "CBC"; // ECB, CBC, CTR, OFB, or CFB
        String padding = "PKCS5Padding"; // PKCS5Padding, PKCS7Padding, NoPadding, ISO10126Padding, ISO9797ALG3Padding, ANSI_X923Padding, or ZeroPadding
        String outputType = "Hex"; // Base64 or Hex
        String iv = "0123456789abcdef";

        String a = Aes2.encode(code, key, mode, padding, outputType, iv);
        System.out.println(a);

        String b = Aes2.decode(a, key, mode, padding, outputType, iv );
        System.out.println(b);
    }
}
