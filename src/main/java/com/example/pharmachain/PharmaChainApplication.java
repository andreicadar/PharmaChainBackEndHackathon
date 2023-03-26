package com.example.pharmachain;

import WebConfiguration.WebSocketConfig;
import api.DataAccessService;
import exception.APIExceptionHandler;
import jwt.JwtAuthenticationEntryPoint;
import jwt.JwtAuthentificationController;
import jwt.JwtTokenUtil;
import jwt.JwtUserDetailsService;
import model.JwtRequest;
import model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

//@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan(basePackageClasses = DataAccessService.class)
//@ComponentScan(basePackageClasses = UserService.class)
@ComponentScan(basePackageClasses = APIExceptionHandler.class)
@ComponentScan(basePackageClasses = WebSocketConfig.class)
@ComponentScan(basePackageClasses = JwtAuthentificationController.class)
@ComponentScan(basePackageClasses = JwtAuthenticationEntryPoint.class)
@ComponentScan(basePackageClasses = JwtUserDetailsService.class)
@ComponentScan(basePackageClasses = JwtRequest.class)
@ComponentScan(basePackageClasses = JwtTokenUtil.class)
@ComponentScan(basePackageClasses = JwtRequest.class)
@ComponentScan(basePackageClasses = JwtResponse.class)

@SpringBootApplication
public class PharmaChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaChainApplication.class, args);
    }

}
