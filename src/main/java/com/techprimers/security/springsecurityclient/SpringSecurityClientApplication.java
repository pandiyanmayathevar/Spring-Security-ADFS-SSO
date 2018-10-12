package com.techprimers.security.springsecurityclient;

import groovy.util.logging.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;


@SpringBootApplication

public class SpringSecurityClientApplication {



	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityClientApplication.class, args);


	}


}
