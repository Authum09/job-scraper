package de.wurow.jobtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class JobTrackerApplication {

    public static void main(String[] args) {

        SpringApplication.run(JobTrackerApplication.class, args);

    }


}
