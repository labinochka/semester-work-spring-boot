package ru.kpfu.itis.beerokspring.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@UtilityClass
public class FileUploaderUtil {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";

    public static String uploadAvatar(MultipartFile multipartFile, String fileName, String url) {
        try {
            String extension = Objects.requireNonNull(multipartFile.getOriginalFilename())
                    .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String storageFileName = fileName + extension;
            Files.createDirectories(Paths.get(UPLOAD_DIRECTORY, url));
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIRECTORY, url, storageFileName));
            return File.separator + "uploads" + url + File.separator + storageFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
