package com.icptechno.admincore.image;

import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping(path = "/image")
public class ImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseHolder<String>> uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
        String fileName = imageService.storeImage(file);

        return ResponseBuilder.okResponseBuilder(fileName).build();
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename, HttpServletRequest request) throws FileNotFoundException {
        Resource resource = imageService.loadImageAsResource(filename);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }

}
