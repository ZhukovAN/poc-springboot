package local.ptdemo.appsec.poc.springboot.utils;

import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.FileInputStream;

public class FileUtils {
    @SneakyThrows
    public static byte[] getFileContents(@NonNull final String path) {
        File file = new File(path);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
        }
        return byteArray;
    }
}
