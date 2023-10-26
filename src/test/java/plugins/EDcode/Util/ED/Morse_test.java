package plugins.EDcode.Util.ED;

public class Morse_test {
    public static void main(String[] args) {
        String a = Morse.encode("fuck you;");
        System.out.println(a);
        String b = Morse.decode(a);
        System.out.println(b);
    }
}
