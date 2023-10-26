package plugins.EDcode.Util.ED;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class Unicode {
    /**
     * 字符串转 Unicode 编码
     * @param code 原字符串
     * @param halfWith 是否转换半角字符
     * @return 编码后的字符串
     */
    public static String encode(String code, Boolean halfWith) {
        if (code == null || code.isEmpty()) {
            // 传入字符串为空返回原内容
            return code;
        }

        StringBuilder value = new StringBuilder(code.length() << 3);
        String prefix = "\\u", zerofix = "0", unicode;
        char c;
        for (int i = 0, j; i < code.length(); i++) {
            c = code.charAt(i);
            if (!halfWith && c > 31 && c < 127) {
                // 不转换半角字符
                value.append(c);
                continue;
            }
            value.append(prefix);

            // 高 8 位
            j = c >>> 8;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);

            // 低 8 位
            j = c & 0xFF;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);
        }

        return value.toString();
    }

    /**
     * 编码转字符串
     * @param code 支持 Unicode 编码和普通字符混合的字符串
     * @return 解码后的字符串
     */
    public static String decode(String code) {
        String prefix = "\\u";
        if (code == null || code.indexOf(prefix) < 0) {
            // 传入字符串为空或不包含 Unicode 编码返回原内容
            return code;
        }

        StringBuilder value = new StringBuilder(code.length() >> 2);
        String[] strings = code.split("\\\\u");
        String hex, mix;
        char hexChar;
        int ascii, n;

        if (strings[0].length() > 0) {
            // 处理开头的普通字符串
            value.append(strings[0]);
        }

        try {
            for (int i = 1; i < strings.length; i++) {
                hex = strings[i];
                if (hex.length() > 3) {
                    mix = "";
                    if (hex.length() > 4) {
                        // 处理 Unicode 编码符号后面的普通字符串
                        mix = hex.substring(4, hex.length());
                    }
                    hex = hex.substring(0, 4);

                    try {
                        Integer.parseInt(hex, 16);
                    } catch (Exception e) {
                        // 不能将当前 16 进制字符串正常转换为 10 进制数字，拼接原内容后跳出
                        value.append(prefix).append(strings[i]);
                        continue;
                    }

                    ascii = 0;
                    for (int j = 0; j < hex.length(); j++) {
                        hexChar = hex.charAt(j);
                        // 将 Unicode 编码中的 16 进制数字逐个转为 10 进制
                        n = Integer.parseInt(String.valueOf(hexChar), 16);
                        // 转换为 ASCII 码
                        ascii += n * ((int) Math.pow(16, (hex.length() - j - 1)));
                    }

                    // 拼接解码内容
                    value.append((char) ascii).append(mix);
                } else {
                    // 不转换特殊长度的 Unicode 编码
                    value.append(prefix).append(hex);
                }
            }
        } catch (Exception e) {
            // Unicode 编码格式有误，解码失败
            return null;
        }

        return value.toString();
    }
}
