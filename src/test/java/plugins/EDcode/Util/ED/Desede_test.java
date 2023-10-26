package plugins.EDcode.Util.ED;

public class Desede_test {
    public static void main(String[] args) throws Exception {
        String code = "hello wo";
        String key = "9okeS67xiOPMsQ==";
        String mode = "CBC"; // ECB, CBC, CTR, OFB, or CFB
        String padding = "PKCS7Padding"; // PKCS5Padding, PKCS7Padding, NoPadding, ISO10126Padding, ISO9797ALG3Padding, ANSI_X923Padding, or ZeroPadding
        String outputType = "Base64"; // Base64 or Hex
        String iv = "01234567";

        String b = Aes2.keyToString(Aes2.generateAESKey(88));
        System.out.println(b);

        String a = Desede.encode(code, key, mode, padding, outputType, iv);
        System.out.println(a);
    }
}
