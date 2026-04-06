package com.faceshield.controller;

import com.faceshield.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "*") 
public class FaceController {

    @Autowired
    private DetectionService detectionService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyFace(@RequestParam("image") MultipartFile image) {
        try {
            // This calls the "Brain" (Service) to check the image
            String result = detectionService.predict(image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}