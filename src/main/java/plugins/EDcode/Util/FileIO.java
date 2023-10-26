package plugins.EDcode.Util;

import util.Serialize;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIO {
    public static void saveObject(String path, Object obj) {
        String content = Serialize.object2json(obj);
        writeFile(path, content);
    }

    public static Object readObject(String path, Class<?> cfile) {
        String content = readFile(path);
        return Serialize.json2object(content, cfile);
    }

    /**
     * 写入文本文件内容（覆盖式写入）
     *
     * @param filePath 要写入的文件路径
     * @param content  要写入的内容
     */
    public static void writeFile(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent()); // 创建文件夹，包括父文件夹
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path),
                    StandardCharsets.UTF_8))) {
                writer.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件内容
     *
     * @param path 输入路径
     * @return 返回读取的内容
     */
    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
