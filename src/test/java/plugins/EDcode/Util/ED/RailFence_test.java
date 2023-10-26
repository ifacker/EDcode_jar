package plugins.EDcode.Util.ED;

public class RailFence_test {
    public static void main(String[] args) {
        String a = RailFence.encode("fuck every one;", 3);
        System.out.println(a);
        String b = RailFence.decode(a, 3);
        System.out.println(b);
    }
}
