package plugins.EDcode.Util.ED;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {
    public static String encode(String code) {
        return DigestUtils.md5Hex(code);
    }
}
