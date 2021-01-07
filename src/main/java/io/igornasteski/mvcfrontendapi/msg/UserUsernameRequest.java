package io.igornasteski.mvcfrontendapi.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUsernameRequest {

    @JsonProperty
    private String username;

    public UserUsernameRequest(){}

    public UserUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
