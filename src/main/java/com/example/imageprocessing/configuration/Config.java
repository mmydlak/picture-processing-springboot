package com.example.imageprocessing.configuration;

import com.example.imageprocessing.utils.CallCounter;
import com.example.imageprocessing.utils.PictureUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

@Configuration
public class Config {

    @Bean
    public CallCounter callCounter(){
        return new CallCounter();
    }

    @Bean
    public PictureUtils mathUtil(){
        return new PictureUtils(new LinkedHashMap<String, BufferedImage>());
    }
}
