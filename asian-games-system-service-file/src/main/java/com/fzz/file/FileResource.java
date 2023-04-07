package com.fzz.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:file-dev.properties"})
@Data
@ConfigurationProperties(prefix = "file")
public class FileResource {

    private String host;
}
