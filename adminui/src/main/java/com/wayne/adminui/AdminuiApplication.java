package com.wayne.adminui;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class AdminuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminuiApplication.class, args);
    }

}
