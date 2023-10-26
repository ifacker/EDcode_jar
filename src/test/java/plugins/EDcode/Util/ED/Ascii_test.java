package plugins.EDcode.Util.ED;

import java.util.ArrayList;

public class Ascii_test {
    public static void main(String[] args) {
        String a = Ascii.encodeHex("hello", "");
        System.out.printf(a);

//        String c = "2c68656c6c6f2e2c2e3b2c5b5d40233132334a534f4456425245";
//        String d = Ascii.decodeHex(c);
//        System.out.println(d);
    }
}
