package carrot.app.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.IMAGE_JPEG) // 기본값 설정 (원하는 형식 선택)
                .mediaType("jpg", MediaType.IMAGE_JPEG)    // jpg 포맷
                .mediaType("jpeg", MediaType.IMAGE_JPEG)   // jpeg 포맷
                .mediaType("png", MediaType.IMAGE_PNG)     // png 포맷 등
                .mediaType("gif", MediaType.IMAGE_GIF);    // gif 포맷 등
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:///static/images/profile/");
    }

}
