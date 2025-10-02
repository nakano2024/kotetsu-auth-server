package kotetsu.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import kotetsu.auth.dto.security.MyUserDetails;



@Controller
public class UserController {
    @GetMapping("/login")
    public String getLoginView() {
        return "login"; 
    }

    @GetMapping("/home")
    public String currentUser(@AuthenticationPrincipal MyUserDetails user, Model model) {
        model.addAttribute("username", user.getName());
        return "home"; 
    }

    @GetMapping("/oauth2/authorization")
    public String getMethodName(@RequestParam String param) {
        return "oauth2-authorization";
    }
    
    @PostMapping("/oauth2/code")
    public String postMethodName(@RequestBody String entity) {
        
        
        return entity;
    }
    
}
