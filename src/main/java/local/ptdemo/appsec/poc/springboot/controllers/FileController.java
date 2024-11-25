package local.ptdemo.appsec.poc.springboot.controllers;

import local.ptdemo.appsec.poc.springboot.utils.FileUtils;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

@RestController
public class FileController {
    @GetMapping(value = "/files/v1", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFileV1(@RequestParam(name = "path") String path) throws IOException {
        File file = new File(path);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
            return ResponseEntity.ok().body(byteArray);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/files/v2", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFileV2(@RequestParam(name = "path") String path) {
        return ResponseEntity.ok().body(getFileContents(path));
    }

    @GetMapping(value = "/files/v3", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFileV3(@RequestParam(name = "path") String path) throws IOException {
        return ResponseEntity.ok().body(FileUtils.getFileContents(path));
    }

    @GetMapping(value = "/files/v4", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFileV4(@RequestParam(name = "path") String path) throws IOException {
        return ResponseEntity.ok().body(FileUtils.getFileContents("/etc/passwd"));
    }

    @SneakyThrows
    protected static byte[] getFileContents(@NonNull final String path) {
        File file = new File(path);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
        }
        return byteArray;
    }
}
