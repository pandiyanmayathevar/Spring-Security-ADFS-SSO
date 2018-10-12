package com.techprimers.security.springsecurityclient.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Configuration
@ComponentScan
@Controller
@Slf4j
public class ResourceController {

    @Autowired
    private ObjectMapper objectMapper;

    private OAuth2RestTemplate oauth2RestTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired(required = false)
    private OAuth2ClientContext clientContext;

    @PostConstruct
    public void init() {
        oauth2RestTemplate = new OAuth2RestTemplate(address(), clientContext);
    }

    @Bean
    public OAuth2ProtectedResourceDetails address(){
        return new AuthorizationCodeResourceDetails();
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String token(Model model) throws Exception{
        OAuth2AccessToken accessToken = oauth2RestTemplate.getOAuth2ClientContext().getAccessToken();
        model.addAttribute("access_token",  toPrettyJsonString(parseToken(accessToken.getValue())));
        return "access_token";
    }

    @RequestMapping(value = "/accesstokenfull", method = RequestMethod.GET)
    public String accesstokenfull(Model model) throws Exception{
        OAuth2AccessToken accessToken = oauth2RestTemplate.getOAuth2ClientContext().getAccessToken();
        model.addAttribute("access_token",  toPrettyJsonString(accessToken.getValue()));
        return "access_token";
    }


    private String toPrettyJsonString(Object object) throws Exception {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private Map<String, ?> parseToken(String base64Token) throws IOException {
        String token = base64Token.split("\\.")[1];
        return objectMapper.readValue(Base64.decodeBase64(token), new TypeReference<Map<String, ?>>() {
        });
    }
}
