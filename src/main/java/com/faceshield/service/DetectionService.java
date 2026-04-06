package com.faceshield.service;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.ZooModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
// NEW IMPORTS FOR DATABASE
import com.faceshield.model.ScanRecord;
import com.faceshield.repository.ScanRepository;

@Service
public class DetectionService {

    @Autowired
    private ZooModel<Image, Classifications> faceShieldModel;

    @Autowired
    private CloudinaryService cloudinaryService;

    // NEW: Inject the Database Connector
    @Autowired
    private ScanRepository scanRepository;

    public String predict(MultipartFile file) throws Exception {
        // 1. Upload to Cloud
        String uploadedUrl = cloudinaryService.upload(file);

        // 2. Read Image for AI
        Image img = ImageFactory.getInstance().fromInputStream(file.getInputStream());

        // 3. Run AI Prediction
        try (Predictor<Image, Classifications> predictor = faceShieldModel.newPredictor()) {
            Classifications result = predictor.predict(img);
            String verdict = result.best().getClassName();

            // 4. NEW: Save the digital receipt to MongoDB!
            ScanRecord record = new ScanRecord(uploadedUrl, verdict);
            scanRepository.save(record);

            return String.format("%s (Image stored securely and saved to database)", verdict);
        }
    }
}