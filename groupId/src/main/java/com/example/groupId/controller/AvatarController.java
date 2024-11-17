package com.example.groupId.controller;

import com.example.groupId.model.Avatar;
import com.example.groupId.repository.AvatarRepository;
import com.example.groupId.service.impl.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    private AvatarRepository avatarRepository;

    @GetMapping
    public Page<Avatar> getAllAvatars(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return avatarRepository.findAll(PageRequest.of(page, size));
    }


    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(path = "/upload/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@PathVariable String studentId, @RequestParam("file") MultipartFile file) {
        try {

            avatarService.uploadImage(Long.parseLong(studentId), file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDB(@RequestParam("studentId") long studentId) {
        Avatar avatar = avatarService.getAvatarFromDB(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(path = "/get/from-local", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam("studentId") long studentId) {
        byte[] data = avatarService.getAvatarFromLocal(studentId);
        return ResponseEntity.ok(data); // Return the byte data wrapped in ResponseEntity
    }
}