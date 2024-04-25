package ru.deathkiller2009.dvdspringbootproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DvdSpringBootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DvdSpringBootProjectApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
