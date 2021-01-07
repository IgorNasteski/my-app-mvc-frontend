package io.igornasteski.mvcfrontendapi.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    private String password;

    public AuthenticationRequest(){}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
