package com.a14.emart.backendsp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dcf91ipuo",
                "api_key", "596658713131867",
                "api_secret", "Q55ZHrDO4WLNjFUx8e3Ayz2BbkI"
        ));
    }
}
