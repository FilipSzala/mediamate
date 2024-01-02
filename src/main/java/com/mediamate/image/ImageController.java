package com.mediamate.image;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")

public class ImageController {
    @Autowired
    private ImageService imageService;



    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws SQLException, IOException {
        Image image = imageService.getImageById(id).get();
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }

    @GetMapping()
    public ResponseEntity<String> getAllImages() {
        List<Image> images = imageService.getImages();
        String html = images.stream()
                .map(image -> "<img src='/image/" + image.getId() + "'/>")
                .collect(Collectors.joining("<br>"));

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    //TODO: Remember to change default value for maximum picture size. (current 2 MB)
    @PostMapping()
    public ResponseEntity<?> createImage(@RequestParam("image") MultipartFile file) throws IOException, SQLException {
        imageService.createImage(file);
        return ResponseEntity
                .ok()
                .body("Image added");
}}