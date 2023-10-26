package plugins.EDcode.Util.ED;

import java.util.HashMap;
import java.util.Map;

public class Morse {
    private static final Map<Character, String> toMorse = new HashMap<>();
    private static final Map<String, Character> toChar = new HashMap<>();

    static {
        // 字母的摩斯密码映射
        toMorse.put('A', ".-");
        toMorse.put('B', "-...");
        toMorse.put('C', "-.-.");
        toMorse.put('D', "-..");
        toMorse.put('E', ".");
        toMorse.put('F', "..-.");
        toMorse.put('G', "--.");
        toMorse.put('H', "....");
        toMorse.put('I', "..");
        toMorse.put('J', ".---");
        toMorse.put('K', "-.-");
        toMorse.put('L', ".-..");
        toMorse.put('M', "--");
        toMorse.put('N', "-.");
        toMorse.put('O', "---");
        toMorse.put('P', ".--.");
        toMorse.put('Q', "--.-");
        toMorse.put('R', ".-.");
        toMorse.put('S', "...");
        toMorse.put('T', "-");
        toMorse.put('U', "..-");
        toMorse.put('V', "...-");
        toMorse.put('W', ".--");
        toMorse.put('X', "-..-");
        toMorse.put('Y', "-.--");
        toMorse.put('Z', "--..");

        /* 数字电码0-9 */
        toMorse.put('0', "-----");
        toMorse.put('1', ".----");
        toMorse.put('2', "..---");
        toMorse.put('3', "...--");
        toMorse.put('4', "....-");
        toMorse.put('5', ".....");
        toMorse.put('6', "-....");
        toMorse.put('7', "--...");
        toMorse.put('8', "---..");
        toMorse.put('9', "----.");

        /* 标点符号，可自增删 */
        toMorse.put(',', "--..--"); // ,逗号
        toMorse.put('.', ".-.-.-"); // .句号
        toMorse.put('?', "..--.."); // ?问号
        toMorse.put('!', "-.-.--"); // !感叹号
        toMorse.put('\'', ".----.");// '单引号
        toMorse.put('\"', ".-..-.");// "引号
        toMorse.put('=', "-...-");  // =等号
        toMorse.put(':', "---..."); // :冒号
        toMorse.put(';', "-.-.-."); // ;分号
        toMorse.put('(', "-.--.");  // (前括号
        toMorse.put(')', "-.--.-"); // )后括号
        toMorse.put(' ', " ");    // 留空格，这里的星号是自定义的

        // 摩斯密码到字符的映射（需要手动建立）
        for (Map.Entry<Character, String> entry : toMorse.entrySet()) {
            toChar.put(entry.getValue(), entry.getKey());
        }
    }

    // 加密
    public static String encode(String code) {
        StringBuilder morseCode = new StringBuilder();
        for (char c : code.toUpperCase().toCharArray()) {
            if (toMorse.containsKey(c)) {
                morseCode.append(toMorse.get(c)).append(" ");
            }
        }
        return morseCode.toString();
    }

    // 解密
    public static String decode(String code) {
        StringBuilder text = new StringBuilder();
        String[] words = code.split(" ");
        for (String word : words) {
            if (toChar.containsKey(word)) {
                text.append(toChar.get(word));
            } else if (word.isEmpty()) {
                text.append(" ");
            }
        }
        return text.toString();
    }
}
