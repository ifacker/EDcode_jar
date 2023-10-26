package plugins.EDcode.Util.ED;

import javax.xml.bind.DatatypeConverter;

public class Hex {
    public static String encode(String code) {
        return DatatypeConverter.printHexBinary(code.getBytes());
    }

    public static String decode(String code){
        byte[] result = DatatypeConverter.parseHexBinary(code);
        return new String(result);
    }
}
