package com.zq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file_upload_dev.properties")
@Data
public class FileUpload {

    private String imageUserFace;

    private String imageServerUrl;

}

