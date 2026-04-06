package com.faceshield.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "scans")
public class ScanRecord {

    @Id
    private String id;
    private String imageUrl;
    private String verdict;
    private LocalDateTime timestamp;

    // Constructors
    public ScanRecord(String imageUrl, String verdict) {
        this.imageUrl = imageUrl;
        this.verdict = verdict;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getImageUrl() { return imageUrl; }
    public String getVerdict() { return verdict; }
    public LocalDateTime getTimestamp() { return timestamp; }
}