package com.yufan.config;

import org.springframework.beans.factory.annotation.Value;

public class GlobalProperties {

    @Value("${image.url}")
    private String url;

    @Value("${image.path}")
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
