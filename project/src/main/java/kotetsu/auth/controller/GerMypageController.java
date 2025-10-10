package kotetsu.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GerMypageController {
    @GetMapping("/mypage")
    public String handle() {
        return "mypage";
    }
}
