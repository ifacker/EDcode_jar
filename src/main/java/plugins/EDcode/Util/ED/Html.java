package plugins.EDcode.Util.ED;


import org.apache.commons.text.StringEscapeUtils;

public class Html {
    public static String encode(String code) {
        return StringEscapeUtils.escapeHtml4(code);
    }

    public static String encodeAll(String code) {
        return customHTMLEncode(code);
    }

    private static String customHTMLEncode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append("&#x").append(Integer.toHexString(c)).append(";");
        }
        return encodedText.toString();
    }

    public static String decode(String code) {
        return StringEscapeUtils.unescapeHtml4(code);
    }
}
