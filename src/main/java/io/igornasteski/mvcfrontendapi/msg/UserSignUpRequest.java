package io.igornasteski.mvcfrontendapi.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Generated("com.robohorse.robopojogenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignUpRequest {

    @JsonProperty
    @Length(min = 5, message = "Username must contains at least 4 characters")
    @NotBlank(message = "Enter your username")
    private String username;

    @JsonProperty
    @Length(min = 6, message = "Password must contains at least 6 characters")
    @NotBlank(message = "Enter your password")
    private String password;

    @JsonProperty
    @NotBlank(message = "Enter you email address")
    @Email(message = "Enter a valid email address")
    private String email;

    @JsonProperty
    private String jwtEmptyString;

    //CONSTRUCTOR
    public UserSignUpRequest(){}

    //BUILDER PATTERN
    public static class UserSignUpRequestBuilder{

        private String username;
        private String password;
        private String email;
        private String jwtEmptyString;

        //constructors
        public UserSignUpRequestBuilder(){
        }

        public static UserSignUpRequestBuilder anUserSignUpRequestBuilder(){
            return new UserSignUpRequestBuilder();
        }

        //methods "with"
        public UserSignUpRequestBuilder withUsername(String username){
            this.username = username;
            return this;
        }
        public UserSignUpRequestBuilder withPassword(String password){
            this.password = password;
            return this;
        }
        public UserSignUpRequestBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public UserSignUpRequestBuilder withTokenUser(String jwtEmptyString){
            this.jwtEmptyString = jwtEmptyString;
            return this;
        }

        //constructor "build" of main class UserSignUpRequest
        public UserSignUpRequest build(){
            UserSignUpRequest userSignUpRequest = new UserSignUpRequest();
            userSignUpRequest.username = this.username;
            userSignUpRequest.password = this.password;
            userSignUpRequest.email = this.email;
            userSignUpRequest.jwtEmptyString = this.jwtEmptyString;
            return userSignUpRequest;
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSignUpRequest that = (UserSignUpRequest) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (jwtEmptyString != null ? !jwtEmptyString.equals(that.jwtEmptyString) : that.jwtEmptyString != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (jwtEmptyString != null ? jwtEmptyString.hashCode() : 0);
        return result;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getJwtEmptyString() {
        return jwtEmptyString;
    }
    public void setJwtEmptyString(String jwtEmptyString) {
        this.jwtEmptyString = jwtEmptyString;
    }

}
