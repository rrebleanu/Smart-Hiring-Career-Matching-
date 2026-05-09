package com.project.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Change "C:/uploads/" to a path that actually exists on your computer
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/uploads/");
    }
}