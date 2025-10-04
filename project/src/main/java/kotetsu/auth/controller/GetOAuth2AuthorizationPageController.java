package kotetsu.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;
import kotetsu.auth.dto.requestparam.GetOAuth2AuthorizationRequestParam;
import kotetsu.auth.dto.security.MyUserDetails;


@Controller
public class GetOAuth2AuthorizationPageController {
    @GetMapping("/oauth2/authorization")
    public String handle(
        @Valid GetOAuth2AuthorizationRequestParam param,
        @AuthenticationPrincipal MyUserDetails loginUser,
        Model model
    ) {
        
        model.addAttribute("client_id", param.getClientId());
        model.addAttribute("redirect_uri", param.getRedirectUri());
        model.addAttribute("nonce", param.getNonce());
        model.addAttribute("code_challenge", param.getCodeChallenge());
        model.addAttribute("state", param.getState());
        model.addAttribute("scope", param.getScope());
        model.addAttribute("access_type", param.getAccessType());
        model.addAttribute("username", loginUser.getName());

        return "oauth2-authorization";
    }
}
