package plugins.EDcode.Util.ED;

public class Des_test {
    public static void main(String[] args) throws Exception {
        String code = "hello world";
        String key = "y1jHmueB";
        String mode = "CBC"; // ECB, CBC, CTR, OFB, or CFB
        String padding = "PKCS7Padding"; // PKCS5Padding, PKCS7Padding, NoPadding, ISO10126Padding, ISO9797ALG3Padding, ANSI_X923Padding, or ZeroPadding
        String outputType = "Base64"; // Base64 or Hex
        String iv = "01234567";

        String b = Aes2.keyToString(Aes2.generateAESKey(32));
        System.out.println(b);

        String a = Des.encode(code, key, mode, padding, outputType, iv);
        System.out.println(a);
    }
}
