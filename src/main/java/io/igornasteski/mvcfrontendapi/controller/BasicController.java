package io.igornasteski.mvcfrontendapi.controller;

import io.igornasteski.mvcfrontendapi.msg.AuthenticationRequest;
import io.igornasteski.mvcfrontendapi.msg.AuthenticationResponse;
import io.igornasteski.mvcfrontendapi.msg.UserPasswordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Controller
public class BasicController {

    @Autowired
    private RestTemplate restTemplate;

    private final static Logger log = LoggerFactory.getLogger(BasicController.class);

    //SPRING SECURITY NAS PRVO SALJE NA OVU PUTANJU /showMyLoginPage A TO SMO DEFINISALI U config klasi SecurityConfiguration.java, DA NAS PRVO SALJE TAMO PRI LOGIN-U
    @GetMapping("/showMyLoginPage")
    public String showLoginPage(Model theModel){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        theModel.addAttribute("authRequest", authenticationRequest);
        //System.out.println("usao");
        System.out.println("FANCY LOGIN USAO");
        return "fancy-login";
    }

    /*@PostMapping("/authenticateTheUser")
    public String saveFeedback(@ModelAttribute("authRequest")AuthenticationRequest authenticationRequest){
        String urlUsernamePassword = "http://localhost:8086/retrievePasswordAndSendItToMvc";
        System.out.println("USAO, AUTH USERNAME " + authenticationRequest.getUsername() + " AUTH PASSWORD " + authenticationRequest.getPassword());
        //feedbackRepository.save(theFeedback);
				/* REDIREKTUJEM NA HomeController koji ima @RequestMapping("/") pa odma gadjam na endpoint metode
				 * A DA SAM REDIREKTOVAO NA NEKI ENDPOINT OVOG KONTROLERA MORAO BIH DA DODAM NPR "/customers/list";*/
    /*    return "redirect:/";
    }*/

String token = "";
    @GetMapping("/")
    public String showHome(Principal principal1, Authentication authentication, @ModelAttribute("authRequest")AuthenticationRequest authenticationRequest){
        String url = "http://localhost:8086/authenticate";
        String urlUsernamePassword = "http://localhost:8086/retrievePasswordAndSendItToMvc";


        String authUsername = authenticationRequest.getUsername();
        String authPassword = authenticationRequest.getPassword();
        System.out.println("!!!!   !!!!   !!!! " + authUsername + " " + authPassword);

        /*Object credentials5 = authentication.getCredentials();
        if(!(credentials5 instanceof String)) {
            return null;
        }

        String username5 = authentication.getName();
        String password5 = credentials5.toString(); //password isn't null here

        System.out.println("UsErNAME I pasSWORD " + username5 + " " + password5);*/




        //String requestUsernameToSendToRest = authentication.getName();
        //UserPasswordResponse passwordResponse = restTemplate.postForObject(urlUsernamePassword, requestUsernameToSendToRest, UserPasswordResponse.class);
        //System.out.println("SA RESTA PASSWORD " + passwordResponse);

            log.info("LOGGER - authentication.getName() = " + authentication.getName());
        log.info("LOGGER - authentication.getAuthorities() = " + authentication.getAuthorities());
        log.info("LOGGER - authentication.isAuthenticated() = " + authentication.isAuthenticated());
        log.info("LOGGER - authentication.getCredentials() = " + authentication.getCredentials());
        log.info("LOGGER - authentication.getDetails() = " + authentication.getDetails());
            log.info("LOGGER - authentication.getPrincipal() = " + authentication.getPrincipal());
        log.info("LOGGER - authentication.getClass() = " + authentication.getClass());

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        String credentials = (String) loggedInUser.getCredentials();
        String password =  loggedInUser.getPrincipal().toString();

        System.out.println("USERN " + username + " credentials " + credentials + " password " + password);

        //saljem AuthenticationRequest koji ima username"foo" i password"foo"(u rest api-ju je zakucan na "foo" pa da bi prosao jwt)
        //AuthenticationRequest request1 = new AuthenticationRequest("foo", "foo");
        AuthenticationRequest request1 = new AuthenticationRequest(authentication.getName(), authentication.getName());
        //Ocekujem AuthenticationResponse(String jwt) a saljem AuthenticationRequest(String username, String passwor)
        AuthenticationResponse response = restTemplate.postForObject(url, request1, AuthenticationResponse.class);

token = response.getJwt();
        //STIGAO JE STRING JWT, NESIGURAN SAM DA LI AKO GADJAM OPET NEKI ENDPOINT REST API-JA(DA LI U HEDERU MORAM IMATI OVAJ JWT STRING???
        //DA LI PRAVIM NPR FILTER OVDE PRI SVAKOM REQUESTU PREMA REST API-JU, DA MORAM DA MU SALJEM I DA PRIMAM TAJ ISTI JWT STRING??)
        System.out.println("RESPONSE AND REQUEST, response jwt: " + response.getJwt());

        //UnlockedRewardsListResponse response=
        //        restTemplateBuilder.build().postForObject(gamificationUrl + "/checkAllUnlockedRewards?reportDate="+reportDate,
        //                                                                              null, UnlockedRewardsListResponse.class);

        return "home";
    }

    @GetMapping("/helloWorld1")
    public String probaBiloKojeStranice1(){
        String url = "http://localhost:8086/obicanString";
        String response = restTemplate.getForObject(url, String.class);
        //System.out.println("IZ RESTA STRING: " + response);
        return response;
    }

    @GetMapping("/helloWorld")
    public String probaBiloKojeStranic1(){
        String url = "http://localhost:8086/obicanString";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println("TOKEN " + token);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String response = restTemplate.postForObject(url, entity, String.class);//USPEO TAKO STO SAM NEKAKO DOHVATIO TOKEN IZ LOGIN METODE KOJA MI
        //JE STIGLA IZ REST API-JA, PA SAM GA OVDE SPAKOVAO U AUTHORIZATION HEDER I POSLAO OPET REST-U(JER REST SVAKI PUT OCEKUJE SAD U HEDERU TAJ
        //JWT TOKEN KOJI NAM JE POSLAO PRI PRVOM LOGINU... NISAM PROVALIO KAKO PREKO GET FOR OBJECT PA SAM ODRATIO PREKO POST-A
        //KAKO RESITI OVO DA NE BIH U SVAKOJ METODI MORAO DA NEKAKO DOHVATAM TOKEN PA DA GA PAKUJEM U HEDER I SALJEM DA BIH DOBIO NEKI RESPONSE OD
        //REST API-JA AKO MI TREBA? STA AKO NPR NAPRAVIM DRUGI KONTROLER I ODATLE HOCU TO DA RADIM, NECU MOCI JE NECU MOCI DA DOHVATIM TOKEN
        System.out.println("RESPONSE STRING: " + response);

        return response;
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }



}
