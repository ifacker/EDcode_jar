package plugins.EDcode.Util.ED;

import com.github.tasubo.jurl.URLEncode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URL {
    public static String encodeSpace(String code){
        try {
            return URLEncoder.encode(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String code){
        return URLEncode.encode(code);
    }

    public static String encodeAll(String code){
        return customFullURLEncode(code);
    }

    public static String decode(String code){
        try {
            return URLDecoder.decode(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String customFullURLEncode(String original) {
        StringBuilder encoded = new StringBuilder();
        for (char c : original.toCharArray()) {
            // 将每个字符都编码成百分号编码形式
            encoded.append('%').append(Integer.toHexString(c));
        }
        return encoded.toString();
    }
}
