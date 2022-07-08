package com.icptechno.admincore.image;

import com.icptechno.admincore.common.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ImageService {

    @Value("${app.address}")
    private String baseAddress;

    private final String baseImagePath = String.format("%s/data/images/", System.getProperty("user.dir"));

    public String storeImage(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String[] fileParts = file.getOriginalFilename().split("\\.");
            String extension = fileParts[fileParts.length - 1];
            if (Stream.of("jpeg", "png", "jpg").noneMatch(p -> Objects.equals(p, extension.toLowerCase()))) {
                throw new Exception("Invalid Type!");
            }

            String writeFileName = String.format("%s.%s", CryptoUtil.sha1Hash(String.format("%s%d", fileName, System.currentTimeMillis())), extension);

            // Copy file to the target location (Replacing existing file with the same name)
            Files.copy(file.getInputStream(), Path.of(baseImagePath, writeFileName), StandardCopyOption.REPLACE_EXISTING);

            return String.format("%s/image/%s", baseAddress, writeFileName);
        } catch (Exception ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadImageAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = Path.of(baseImagePath, fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

}
