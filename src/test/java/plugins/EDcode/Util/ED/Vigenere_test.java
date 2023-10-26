package plugins.EDcode.Util.ED;

public class Vigenere_test {
    public static void main(String[] args) {
        String a = Vigenere.encode("fuckyou", "c");
        System.out.println(a);
        String b = Vigenere.decode(a, "c");
        System.out.println(b);

    }
}
