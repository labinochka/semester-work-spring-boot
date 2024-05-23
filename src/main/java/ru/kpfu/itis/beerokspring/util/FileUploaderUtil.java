package ru.kpfu.itis.beerokspring.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@UtilityClass
@Slf4j
public class FileUploaderUtil {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "src" + File.separator +
            "main" + File.separator + "resources" + File.separator + "uploads";


    public static String uploadFile(MultipartFile multipartFile, String fileName, String url) {
        try {
            String extension = Objects.requireNonNull(multipartFile.getOriginalFilename())
                    .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String storageFileName = fileName + extension;
            Files.createDirectories(Paths.get(UPLOAD_DIRECTORY, url));
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIRECTORY, url, storageFileName));
            return File.separator + "uploads" + url + File.separator + storageFileName;
        } catch (IOException e) {
            log.error("Error:", e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteFile(String path) {
        try {
            String[] names = path.split("\\\\");
            System.out.println(names[names.length - 2]);
            String newPath = File.separator + names[names.length - 2] + File.separator + names[names.length - 1];
            Files.delete(Path.of(UPLOAD_DIRECTORY + newPath));
        } catch (IOException e) {
            log.error("Error:", e);
            throw new RuntimeException(e);
        }
    }
}
