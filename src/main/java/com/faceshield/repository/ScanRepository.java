package com.faceshield.repository;

import com.faceshield.model.ScanRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends MongoRepository<ScanRecord, String> {
    // Spring Boot automatically writes the database queries for you behind the scenes!
}