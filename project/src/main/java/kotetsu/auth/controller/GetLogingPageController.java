package kotetsu.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetLogingPageController {
    @GetMapping("/login")
    public String handle() {
        return "pages/sign-in";
    }
}
