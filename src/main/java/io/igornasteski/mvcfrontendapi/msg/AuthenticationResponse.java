package io.igornasteski.mvcfrontendapi.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponse {

    @JsonProperty
    private String jwt;

    public AuthenticationResponse(){}

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
