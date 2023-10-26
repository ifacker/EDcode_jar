package plugins.EDcode.Util.ED;

import java.io.IOException;

public class Gzip_test {
    public static void main(String[] args) throws IOException {
        String a = Gzip.encode("hello world");
        System.out.println(a);

        String b = Gzip.decode(a);
        System.out.println(b);
    }
}
