package com.faceshield.service;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.ZooModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class DetectionService {

    @Autowired
    private ZooModel<Image, Classifications> faceShieldModel;

    public String predict(MultipartFile file) throws Exception {
        // Reads the image uploaded from the browser
        BufferedImage bufferedImg = ImageIO.read(file.getInputStream());
        
        // Convert BufferedImage to DJL Image
        Image img = ImageFactory.getInstance().fromImage(bufferedImg);

        // Uses the model we loaded in DjlConfig
        try (Predictor<Image, Classifications> predictor = faceShieldModel.newPredictor()) {
            Classifications result = predictor.predict(img);
            return result.best().getClassName(); 
        }
    }
}