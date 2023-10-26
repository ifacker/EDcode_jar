package plugins.EDcode.Util.ED;


import plugins.EDcode.Util.MsgBox;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Ascii {

    public static String encodeHex(String code, String split) {
        String codeTmp = encode(code, " ").trim();
        String[] codeTmps = codeTmp.split(" ");
        String result = "";
        for (String tmp : codeTmps){
            result += String.format("%s%s", Integer.toHexString(Integer.parseInt(tmp)), split);
        }
        return result;
    }

    public static String encode(String code, String split) {
        String result = "";
        try {
            byte[] asciiBytes = code.getBytes("US-ASCII");
            for (Byte b : asciiBytes) {
                result += String.format("%d%s", b, split);
            }
        } catch (UnsupportedEncodingException e) {
            MsgBox.sendSystemInfo("错误", e.getMessage());
        }
        return result;
    }

    public static String decodeHex(String code) {
        String codeTmp = code.trim();
        String codeNew = "";

        // 判断是否是复数位
        String isTmp = codeTmp.replaceAll(" ", "").replaceAll("\n", "");
        if (isTmp.length() %2 != 0){
            MsgBox.sendSystemInfo("错误", "要解码的字符串不全 或 选择的进制错误");
            return codeNew;
        }

        // 16进制转换为10进制
        if (codeTmp.contains(" ")) {
            String[] tmps = codeTmp.split(" ");
            for (String tmp : tmps) {
                codeNew += String.format("%d ", Integer.parseInt(tmp, 16));
            }
        } else if (codeTmp.contains("\n")) {
            String[] tmps = codeTmp.split("\n");
            for (String tmp : tmps) {
                codeNew += String.format("%d ", Integer.parseInt(tmp, 16));
            }
        } else {
            while (true) {
                if (codeTmp.length() >= 2) {
                    int num = Integer.parseInt(codeTmp.substring(0, 2), 16);
                    codeNew += String.format("%d ", num);
                    codeTmp = codeTmp.substring(2);
                } else if (codeTmp.isEmpty()) {
                    break;
                } else {
                    MsgBox.sendSystemInfo("提示", "解码异常，请检查进制");
                    return "";
                }
            }
        }
        return decode(codeNew);
    }


    public static String decode(String code) {
        // 取出首尾空格
        String codeTmp = code.trim();

        // 判断输入是否是纯数字
        if (!codeTmp.matches("^[\\d\\s\\n]*$")) {
            MsgBox.sendSystemInfo("错误", "选择的进制错误");
            return "";
        }

        ArrayList<Byte> byteArrayList = new ArrayList<>();

        // 判断字符串中是否还存在空格
        if (codeTmp.contains(" ")) {
            String[] tmps = codeTmp.split(" ");
            for (String tmp : tmps) {
                byteArrayList.add((byte) Integer.parseInt(tmp));
            }
        } else if (codeTmp.contains("\n")) {
            String[] tmps = codeTmp.split("\n");
            for (String tmp : tmps) {
                byteArrayList.add((byte) Integer.parseInt(tmp));
            }
        } else {
            while (true) {
                if (codeTmp.length() >= 2) {
                    int num = Integer.parseInt(codeTmp.substring(0, 2));
                    if (32 <= num & num <= 126) {
                        byteArrayList.add((byte) num);
                        codeTmp = codeTmp.substring(2);
                    } else if (codeTmp.length() >= 3) {
                        num = Integer.parseInt(codeTmp.substring(0, 3));
                        if (32 <= num & num <= 126) {
                            byteArrayList.add((byte) num);
                            codeTmp = codeTmp.substring(3);
                        } else {
                            MsgBox.sendSystemInfo("提示", "解码异常，请检查进制");
                            return "";
                        }
                    }
                } else if (codeTmp.isEmpty()) {
                    break;
                } else {
                    MsgBox.sendSystemInfo("提示", "解码异常，请检查进制");
                    return "";
                }
            }
        }

        if (byteArrayList.isEmpty()) {
            return "";
        }

        // 转换成 String
        StringBuilder sb = new StringBuilder();
        for (Byte b : byteArrayList) {
            sb.append((char) (byte) b);
        }
        return sb.toString();
    }
}
