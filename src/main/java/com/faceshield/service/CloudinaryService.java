package com.faceshield.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        // This is exactly as you wrote it, which is perfect for a quick start
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "drygdhbcb",
            "api_key", "555194432978383",
            "api_secret", "KEotI8_gC7dMlJWxsuNTZp4uuNw"
        ));
    }
    // ... rest of your upload method ...
    public String upload(MultipartFile file) throws Exception {
        // Uploads the file and gets the data back
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        
        // Returns the permanent URL of the uploaded image
        Object url = uploadResult.get("secure_url");
        if (url == null) {
            url = uploadResult.get("url");
        }
        return url != null ? url.toString() : "";
    }
}