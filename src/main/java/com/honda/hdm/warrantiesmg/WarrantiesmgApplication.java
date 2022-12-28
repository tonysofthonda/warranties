package com.honda.hdm.warrantiesmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import com.honda.hdm.hdmbase.app.aspect.HdmUtils;
import com.honda.hdm.security.common.aspect.EnableSecurity;

@SpringBootApplication
@HdmUtils
@EnableSecurity
public class WarrantiesmgApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WarrantiesmgApplication.class, args);
    }
    
}
