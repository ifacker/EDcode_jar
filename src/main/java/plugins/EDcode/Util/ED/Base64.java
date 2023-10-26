package plugins.EDcode.Util.ED;

public class Base64 {
    public static String encode(String code){
        return new String(java.util.Base64.getEncoder().encode(code.getBytes()));
    }

    public static String decode(String code) {
        return new String(java.util.Base64.getDecoder().decode(code));
    }
}
