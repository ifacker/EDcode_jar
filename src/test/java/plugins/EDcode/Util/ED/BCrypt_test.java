package plugins.EDcode.Util.ED;

import org.mindrot.jbcrypt.BCrypt;

public class BCrypt_test {
    public static void main(String[] args) {
        String key = BCrypt.gensalt(30);
        System.out.println(key);
        String a = BCrypt.hashpw("fuck", key);
        System.out.printf(a);
    }
}
