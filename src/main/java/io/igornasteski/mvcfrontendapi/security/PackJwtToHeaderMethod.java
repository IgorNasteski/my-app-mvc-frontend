package io.igornasteski.mvcfrontendapi.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class PackJwtToHeaderMethod {

    public HttpEntity<String> packJwtToHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println("TOKEN " + token);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return entity;
    }



}
