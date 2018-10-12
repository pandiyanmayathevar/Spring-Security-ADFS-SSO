package com.techprimers.security.springsecurityclient.controller;


import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;



@RestController
@Slf4j
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/Authenticated")
    public boolean  Authenticate(Principal principal) {
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            return oAuth2Authentication.isAuthenticated();
        }
        return false;
    }

    @RequestMapping("/UserInfo")
    public Map<String, String> user(Principal principal) {
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            Map<String, String> details = new LinkedHashMap<>();
            details = (Map<String, String>) authentication.getDetails();
            log.info("details = " + details);  // id, email, name, link etc.

            Map<String, String> map = new LinkedHashMap<>();
            map.put("name", details.get("given_name"));
            map.put("id", details.get("id"));
            map.put("email", details.get("email"));
            map.put("email", details.get("name"));
            return map;
        }
        return null;
    }

}
