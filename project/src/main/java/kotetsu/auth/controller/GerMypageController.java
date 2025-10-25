package kotetsu.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kotetsu.auth.dto.security.MyUserDetails;

@Controller
public class GerMypageController {
    @GetMapping("/")
    public String handle(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getName());
        return "pages/mypage";
    }
}
