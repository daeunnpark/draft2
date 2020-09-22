package org.kpax.oauth2.controller.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class PageController {

    private final Resource indexPage;

    @Autowired
    private OAuth2RestOperations restOperations;

    public PageController(@Value("classpath:/static/index.html") Resource indexPage) {
        this.indexPage = indexPage;
    }

    @GetMapping(value = {"/", "/mail"})
    public ResponseEntity<Resource> getFirstPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(indexPage, headers, HttpStatus.OK);
    }

    @RequestMapping("/index")
    public ModelAndView homee() {
        ModelAndView page = new ModelAndView();
        page.setViewName("index");
        return page;
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }

    @RequestMapping("/callback")
    public String callback() {
        System.out.println("**call back sheree");
        return "<div> Callback</div>";
    }

    /*
    @RequestMapping("/its-me")
    public String home(Principal principal) {
        return "The principal's name is: " + principal.getName();
    }

    @RequestMapping("/its-also-me")
    public String home() {
        String message = restOperations.getForObject("http://localhost:8080/auth/user/also-me", String.class);
        return "Message from auth server: " + message;
    }

    @RequestMapping("/its-me")
    public String home(Principal principal) {
        return "The principal's name is: " + principal.getName();
    }
    */

}