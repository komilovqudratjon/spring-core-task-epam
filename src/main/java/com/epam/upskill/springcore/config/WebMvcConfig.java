package com.epam.upskill.springcore.config;

import com.epam.upskill.springcore.config.loging.TransactionLoggingInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for Web MVC, including CORS settings.
 */
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final HandlerInterceptor transactionLoggingInterceptor;

    /**
     * Configures CORS mappings for the application.
     * <p>
     * This method allows cross-origin requests from any source.
     * In a production environment, consider restricting the allowed origins
     * for better security.
     *
     * @param registry the CORS registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionLoggingInterceptor);
    }

}
