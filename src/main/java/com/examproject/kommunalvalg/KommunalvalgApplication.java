package com.examproject.kommunalvalg;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KommunalvalgApplication {
    
    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(KommunalvalgApplication.class, args);
    }

}
