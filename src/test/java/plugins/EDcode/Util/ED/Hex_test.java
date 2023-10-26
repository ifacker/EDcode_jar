package plugins.EDcode.Util.ED;

public class Hex_test {
    public static void main(String[] args) {
        String a = Hex.encode("hello");
        System.out.println(a);

        String b = Hex.decode(a);
        System.out.println(b);
    }
}
