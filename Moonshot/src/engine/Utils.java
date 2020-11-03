package engine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static String readFile(String fileName) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        Path path = Path.of(fileName);
        String content = Files.readString(path);
        return content;
    }

    public static List<String> readLines(String fileName) throws Exception {
        String fileContents = readFile(fileName);
        return Arrays.asList(fileContents.split("\\r?\\n"));
    }
}