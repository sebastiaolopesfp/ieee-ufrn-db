package br.ufrn.ieee.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Libera o CORS para todas as rotas do seu backend
        registry.addMapping("/**")
                // Permite requisições vindas especificamente do seu frontend React (Vite)
                .allowedOrigins("http://localhost:5173") 
                // Métodos HTTP permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Permite todos os cabeçalhos (headers) nas requisições
                .allowedHeaders("*")
                // Permite o envio de cookies/tokens de autenticação
                .allowCredentials(true);
    }
}
