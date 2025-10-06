package kotetsu.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.CheckAuthorizationRequestInput;
import kotetsu.auth.application.dto.output.AuthorizationRequestCheckOutput;
import kotetsu.auth.application.usecase.CheckAuthorizationRequestUsecase;
import kotetsu.auth.dto.requestparam.GetOAuth2AuthorizationRequestPageParam;
import kotetsu.auth.dto.security.MyUserDetails;


@Controller
public class GetOAuth2AuthorizationPageController {

    private final CheckAuthorizationRequestUsecase usecase;

    public GetOAuth2AuthorizationPageController(final CheckAuthorizationRequestUsecase usecase) {
        this.usecase = usecase;
    }

    @GetMapping("/oauth2/authorization")
    public String handle(
        @Valid GetOAuth2AuthorizationRequestPageParam param,
        @AuthenticationPrincipal MyUserDetails loginUser,
        Model model
    ) {
        final AuthorizationRequestCheckOutput output = usecase.execute(CheckAuthorizationRequestInput.of(
            param.getClientId(),
            param.getRedirectUri(),
            param.getScope()
        ));

        if (output.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_CLIENT_NOT_FOUND)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (output.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (output.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_INVALID_SCOPE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
        model.addAttribute("client_id", param.getClientId());
        model.addAttribute("redirect_uri", param.getRedirectUri());
        model.addAttribute("nonce", param.getNonce());
        model.addAttribute("code_challenge", param.getCodeChallenge());
        model.addAttribute("state", param.getState());
        model.addAttribute("scope", param.getScope());
        model.addAttribute("access_type", param.getAccessType());
        model.addAttribute("response_type", param.getResponseType());
        model.addAttribute("username", loginUser.getName());

        return "oauth2-authorization";
    }
}
