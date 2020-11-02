package engine;

import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    public static String readFile(String fileName) throws Exception {
        Path path = Path.of(fileName);
        String content = Files.readString(path);
        return content;
    }
}