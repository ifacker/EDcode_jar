package plugins.EDcode.Util.ED;

public class BCrypt {
    public static String encode(String code, String key) {
        if ("".equals(key)){
            return org.mindrot.jbcrypt.BCrypt.hashpw(code, org.mindrot.jbcrypt.BCrypt.gensalt());
        }else {
            return org.mindrot.jbcrypt.BCrypt.hashpw(code, org.mindrot.jbcrypt.BCrypt.gensalt(Integer.parseInt(key)));
        }
    }
}
