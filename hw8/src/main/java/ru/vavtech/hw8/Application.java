package ru.vavtech.hw8;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.shell.command.annotation.CommandScan;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
@CommandScan("ru.vavtech.hw8.commands")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
