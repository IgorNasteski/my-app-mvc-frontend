package io.igornasteski.mvcfrontendapi.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TokenUser tokenUser;

    //CONSTRUCTOR
    public UserEntity(){}

    //BUILDER PATTERN
    public static class UserBuilder{
        private int id;
        private String username;
        private String password;
        private String email;
        private TokenUser tokenUser;

        //constructors
        public UserBuilder(){
        }
        public static UserBuilder anUser(){
            return new UserBuilder();
        }

        //methods "with"
        public UserBuilder withId(int id){
            this.id = id;
            return this;
        }
        public UserBuilder withUsername(String username){
            this.username = username;
            return this;
        }
        public UserBuilder withPassword(String password){
            this.password = password;
            return this;
        }
        public UserBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public UserBuilder withTokenUser(TokenUser tokenUser){
            this.tokenUser = tokenUser;
            return this;
        }

        //constructor "build" of main class Venue
        public UserEntity build(){
            UserEntity userEntity = new UserEntity();
            userEntity.id = this.id;
            userEntity.username = this.username;
            userEntity.password = this.password;
            userEntity.email = this.email;
            userEntity.tokenUser = this.tokenUser;
            return userEntity;
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity userEntity = (UserEntity) o;

        if (id != userEntity.id) return false;
        if (username != null ? !username.equals(userEntity.username) : userEntity.username != null) return false;
        if (password != null ? !password.equals(userEntity.password) : userEntity.password != null) return false;
        if (email != null ? !email.equals(userEntity.email) : userEntity.email != null) return false;
        return tokenUser != null ? tokenUser.equals(userEntity.tokenUser) : userEntity.tokenUser == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (tokenUser != null ? tokenUser.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public TokenUser getTokenUser() {
        return tokenUser;
    }
    public void setTokenUser(TokenUser tokenUser) {
        this.tokenUser = tokenUser;
    }

}
