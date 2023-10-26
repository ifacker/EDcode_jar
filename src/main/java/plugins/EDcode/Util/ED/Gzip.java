package plugins.EDcode.Util.ED;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
public class Gzip {
    public static String encode(String code) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(baos);
        gzip.write(code.getBytes("UTF-8"));
        gzip.close();
        return new String(baos.toByteArray());
    }


    // 编码解码的过程中，会丢失细节
    public static String decode(String code) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(code.getBytes("UTF-8"));
        GZIPInputStream gzip = new GZIPInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzip.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        return new String(baos.toByteArray());
    }
}


