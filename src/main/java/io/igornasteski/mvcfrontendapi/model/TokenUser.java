package io.igornasteski.mvcfrontendapi.model;

import javax.persistence.*;

@Entity(name = "token_user")
public class TokenUser {

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Id
    private UserEntity userEntity;

    @Column(name = "token")
    private String jwt;*/

    @Id
    @Column(name = "user_id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "token")
    private String jwt;

    //CONSTRUCTOR
    public TokenUser(){}

    //BUILDER PATTERN
    public static class TokenUserBuilder{
        private int id;
        private UserEntity userEntity;
        private String jwt;

        //constructors
        public TokenUserBuilder(){
        }
        public static TokenUserBuilder anTokenUser(){
            return new TokenUserBuilder();
        }

        //methods "with"
        public TokenUserBuilder withId(int id){
            this.id = id;
            return this;
        }
        public TokenUserBuilder withUser(UserEntity userEntity){
            this.userEntity = userEntity;
            return this;
        }
        public TokenUserBuilder withJwt(String jwt){
            this.jwt = jwt;
            return this;
        }
        //constructor "build" of main class Venue
        public TokenUser build(){
            TokenUser tokenUser = new TokenUser();
            tokenUser.id = this.id;
            tokenUser.userEntity = this.userEntity;
            tokenUser.jwt = this.jwt;
            return tokenUser;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenUser tokenUser = (TokenUser) o;

        if (id != tokenUser.id) return false;
        if (userEntity != null ? !userEntity.equals(tokenUser.userEntity) : tokenUser.userEntity != null) return false;
        return jwt != null ? jwt.equals(tokenUser.jwt) : tokenUser.jwt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        result = 31 * result + (jwt != null ? jwt.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
