package io.igornasteski.mvcfrontendapi.controller;

import io.igornasteski.mvcfrontendapi.model.UserEntity;
import io.igornasteski.mvcfrontendapi.msg.AuthenticationRequest;
import io.igornasteski.mvcfrontendapi.msg.AuthenticationResponse;
import io.igornasteski.mvcfrontendapi.msg.UserPasswordResponse;
import io.igornasteski.mvcfrontendapi.msg.UserSignUpRequest;
import io.igornasteski.mvcfrontendapi.security.PackJwtToHeaderMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class BasicController {

    @Autowired
    private RestTemplate restTemplate;

    private final static Logger log = LoggerFactory.getLogger(BasicController.class);

    String token = "";

    //ODRADJUJEM SIGNUP PRICU, U config klasi SecurityConfiguration.java sam dodao u private String[] PUBLIC_MATCHERS = {...,"/signUp/**"} i tako dozvolio svima da odrade SIGNUP
    @GetMapping("/signUp")
    public String signUp(Model theModel){
        UserEntity theUser = new UserEntity();
        theModel.addAttribute("user", theUser);
        return "signUpForma";
    }

    @PostMapping("/processNewSignUpUser")
    public String processNewSignUpUser(@Valid @ModelAttribute("user")UserSignUpRequest userSignUpRequest, BindingResult bindingResult, RedirectAttributes ra){

        if(bindingResult.hasErrors()) {
            return "signUpForma";
        }

        String url = "http://localhost:8086/signUp";

        System.out.println("IME I PREZIME I MAIL " + userSignUpRequest.getUsername() + " " + userSignUpRequest.getPassword() + " " + userSignUpRequest.getEmail());

        //AuthenticationRequest request1 = new AuthenticationRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        //Ocekujem AuthenticationResponse(String jwt) a saljem AuthenticationRequest(String username, String password)
        String response = restTemplate.postForObject(url, userSignUpRequest, String.class);

        if(response.equalsIgnoreCase("username-already-taken")){
            bindingResult.addError(new FieldError("user", "username", "Username is already in use."));
            ra.addFlashAttribute("message2", "Username is already taken!");
            return "redirect:/signUp";
        }
        if(response.equalsIgnoreCase("email-already-taken")){
            ra.addFlashAttribute("message3", "Email is already taken!");
            return "redirect:/signUp";
        }

        ra.addFlashAttribute("message", "Success! Your registration is now complete.");

        System.out.println("SIGN UP RESPONSE STRING " + response);

        //success-registration(link to login) or error-registration(link to login)
        if(response.equalsIgnoreCase("success-registration")){
            return "redirect:/showMyLoginPage";
        }
        else return response;
        //return response;
    }

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

    @PostMapping("/checkUserAuthenticationInRestApi")
    public String checkUserWithRest(@ModelAttribute("authRequest")AuthenticationRequest authenticationRequest, BindingResult bindingResult){
        System.out.println("USAO U /checkUserAuthenticationInRestApi");
        System.out.println("USERNAME " + authenticationRequest.getUsername() + " PASSWORD " + authenticationRequest.getPassword());

        String url = "http://localhost:8086/authenticateLogin";
        //saljem AuthenticationRequest koji ima username password koji je user uneo i idem do resta da mi da za njega jwt ako je login uspesan
        //taj jwt cu nadalje u svakom requestu koji budem slao ka restu morati da pakujem u heder
        //AuthenticationRequest request1 = new AuthenticationRequest("foo", "foo");
        AuthenticationRequest request1 = new AuthenticationRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        //Ocekujem AuthenticationResponse(String jwt) a saljem AuthenticationRequest(String username, String passwor)
        AuthenticationResponse response = restTemplate.postForObject(url, request1, AuthenticationResponse.class);
        System.out.println("RESPONSE AND REQUEST, response jwt: " + response.getJwt());

        if(response.getJwt().equalsIgnoreCase("Incorrect username or password")){
            return "access-denied-send-me-to-login-page";
            //return "fancy-login";
        }

        token = response.getJwt();

        return "home";
        //return "redirect:/";
    }

    //NI NE KORISTIM ZA SAD
    @GetMapping("/")
    public String showHome(Principal principal1, Authentication authentication, @ModelAttribute("authRequest")AuthenticationRequest authenticationRequest){
        String url = "http://localhost:8086/authenticateLogin";
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

//token = response.getJwt();
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

    @Autowired
    private PackJwtToHeaderMethod packJwtToHeaderMethod;

    @GetMapping("/helloWorld")
    public String probaBiloKojeStranice2(){
        System.out.println("USAO U HELLO WORLD");
        String url = "http://localhost:8086/obicanString";

        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println("TOKEN " + token);

        HttpEntity<String> entity = new HttpEntity<String>(headers);*/

        //napravio metodu u klasi PackJwtToHeaderMethod koja pakuje jwt u heder, jer svaki endpoint koji kontaktiram ka restu ocekuje u hederu token
        String response = restTemplate.postForObject(url, packJwtToHeaderMethod.packJwtToHeader(token), String.class);//USPEO TAKO STO SAM NEKAKO DOHVATIO TOKEN IZ LOGIN METODE KOJA MI
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

    @GetMapping("/probaZaSlanjeRequestObjektaIHedera")
    public String proba(){
        String url = "http://localhost:8086/probaZaSlanjeRequestObjektaIHedera";


        // Create the request body as a MultiValueMap
        //MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("s","s");

        //body.add("field", "value");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer " + token);
        //HttpEntity<String> entity = new HttpEntity<String>(requestHeaders);

        // Note the body object as first parameter!
        HttpEntity<?> httpEntity = new HttpEntity<Object>(authenticationRequest, requestHeaders);

        //ResponseEntity<MyModel> response = restTemplate.exchange("/api/url", HttpMethod.POST, httpEntity, MyModel.class);
        //AuthenticationResponse authenticationResponse = restTemplate.postForObject(url, httpEntity, AuthenticationResponse.class);
        //objekat(body sa vrednostima)mora da se pakuje u ResponseEntity jer se salje preko exchange(url, GetIliPost, hederPrica, responseKlasa)
        //ResponseEntity<AuthenticationResponse> authenticationResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AuthenticationResponse.class);
        AuthenticationResponse authenticationResponse1 = restTemplate.postForObject(url, httpEntity, AuthenticationResponse.class);

        System.out.println("USPESNO POSLAO KA RESTU I OBJEKAT(BODY) I HEADER PARAMETAR KOJI SAM UPAKOVAO, DOBIO RESPONSE: " + authenticationResponse1.getJwt());

        //return authenticationResponse.getBody().getJwt();
        return authenticationResponse1.getJwt();

    }



}
