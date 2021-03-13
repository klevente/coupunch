package dev.klevente.coupunch.usermanager.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.BufferedImageHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import java.awt.image.BufferedImage

@Configuration
class CustomMessageConverters {
    @Bean
    fun createImageConverter(): HttpMessageConverter<BufferedImage> = BufferedImageHttpMessageConverter()
}