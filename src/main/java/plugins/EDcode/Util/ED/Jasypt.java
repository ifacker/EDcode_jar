package plugins.EDcode.Util.ED;

import org.jasypt.util.text.BasicTextEncryptor;

public class Jasypt {
    public static String encode(String code, String key) {
        // 创建一个基本的文本加密器
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        return basicTextEncryptor.encrypt(code);
    }

    public static String decode(String code, String key) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        return basicTextEncryptor.decrypt(code);
    }
}
