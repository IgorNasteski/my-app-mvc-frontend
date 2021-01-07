package io.igornasteski.mvcfrontendapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private String[] PUBLIC_MATCHERS = {
            "/authenticate",
            "/webjars/**",	//OVE FOLDERE(webjars,css,js) ZA SAD NEMAM U resources/static/ ALI NIJE PROBLEM, BITAN MI JE SAMO
            "/css/**",		//BIO FOLDER img DA BIH DOZVOLIO SLIKAMA DA SE PRIKAZUJU NA LOGIN STRANICI fancy-login
            "/js/**",
            "/img/**",
            "/signUp/**",
            "/processNewSignUpUser/**"
    };

    //enable connection with mysql db
    /*@Autowired
    DataSource dataSource;

    //Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }*/



    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
                //.userDetailsService(userDetailsService)
                //.passwordEncoder(passwordEncoder());
        /* configure user-service, password-encoder etc ... */

    //}*/



    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }*/

    /*@Autowired
 private UserDetailsService userDetailsService;

 @Bean
 public UserDetailsService userDetailsService() {
     return super.userDetailsService();
 }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()   //pusti sve usere da budu autentikovani za ovaj endpoint
                    .anyRequest().authenticated()
                .and()//za sve druge endpointe trazi autentikaciju
                    .formLogin()
                        .loginPage("/showMyLoginPage") //KAZEMO DA UMESTO DA KORISTI DEFAULTNU-NE STILIZOVANU VERZIJU LOGINA KOJI
                        //NAM SPRING PO DEFAULTU OMOGUCAVA, MI KAZEMO IDI NA OVU PUTANJU
                        //SERVLETA(LoginController) PA U NJOJ NA JSP STRANICU KOJU VRACAMO(NASA STILIZOVANA FORMA ZA LOGIN)
                        .loginProcessingUrl("/authenticateTheUser")	//OVDE NAS FORMA DALJE SALJE KADA NAKON UNOSA USERNAMEA, AUTOMATSKI U POZADINI ODRADJUJE PROVERU
                        //NE MORAM NISTA DA ODRADIM, SAM CE MI SPRING BOOT SECURITY ODRADITI AUTENTIKACIJU(DA LI JE USERNAME I PASSWORD OK)
                        .permitAll()
                        //.defaultSuccessUrl("/")
                        //.usernameParameter("username").passwordParameter("password")
                .and()
                    .logout().permitAll()	//DODAJEM MOGUCNOST/OPCIJU ZA LOGOUT !!!!!
                .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");

    }




}
