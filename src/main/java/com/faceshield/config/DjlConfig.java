package com.faceshield.config;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DjlConfig {

    @Bean
    public ZooModel<Image, Classifications> faceShieldModel() throws Exception {
        Path modelPath = Paths.get("src/main/resources/model");

        // Define the classes (must match your Python training labels)
        List<String> classes = Arrays.asList("Real", "Spoof");

        // The Translator: Handles resizing and normalization
        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                .addTransform(new Resize(128, 128)) // Fixed to match your original Python training size 
                .optSynset(classes)
                .build();

        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelPath(modelPath)
                .optTranslator(translator) // Attach the translator here
                .optEngine("TensorFlow")
                .optOption("Tags", "serve")
                .build();

        return criteria.loadModel();
    }
}