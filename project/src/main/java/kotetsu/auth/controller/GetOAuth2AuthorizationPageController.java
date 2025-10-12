package kotetsu.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.CheckAuthorizationRequestInput;
import kotetsu.auth.application.dto.input.GetClientInformationInput;
import kotetsu.auth.application.dto.input.GetScopeDescriptionsInput;
import kotetsu.auth.application.dto.output.AuthorizationRequestCheckOutput;
import kotetsu.auth.application.dto.output.ClientInformationOutput;
import kotetsu.auth.application.dto.output.ScopeDescriptionsOutput;
import kotetsu.auth.application.exception.InvalidScopeNameListTokenException;
import kotetsu.auth.application.usecase.CheckAuthorizationRequestUsecase;
import kotetsu.auth.application.usecase.GetClientInformationUsecase;
import kotetsu.auth.application.usecase.GetScopeDescriptionsUsecase;
import kotetsu.auth.dto.requestparam.GetOAuth2AuthorizationRequestPageParam;
import kotetsu.auth.dto.security.MyUserDetails;


@Controller
public class GetOAuth2AuthorizationPageController {

    private final CheckAuthorizationRequestUsecase checkAuthorizationRequestUsecase;
    private final GetScopeDescriptionsUsecase getScopeDescriptionsUsecase;
    private final GetClientInformationUsecase getClientInformationUsecase;

    public GetOAuth2AuthorizationPageController(
        final CheckAuthorizationRequestUsecase checkAuthorizationRequestUsecase,
        final GetScopeDescriptionsUsecase getScopeDescriptionsUsecase,
        final GetClientInformationUsecase getClientInformationUsecase
    ) {
        this.checkAuthorizationRequestUsecase = checkAuthorizationRequestUsecase;
        this.getScopeDescriptionsUsecase = getScopeDescriptionsUsecase;
        this.getClientInformationUsecase = getClientInformationUsecase;
    }

    @GetMapping("/oauth2/authorization")
    public String handle(
        @Valid GetOAuth2AuthorizationRequestPageParam param,
        @AuthenticationPrincipal MyUserDetails loginUser,
        Model model
    ) {
        try {
            final AuthorizationRequestCheckOutput authorizationRequestCheckOutput = checkAuthorizationRequestUsecase.execute(CheckAuthorizationRequestInput.of(
                param.getClientId(),
                param.getRedirectUri(),
                param.getScope()
            ));
            
            if (authorizationRequestCheckOutput.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_CLIENT_NOT_FOUND)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        
            if (authorizationRequestCheckOutput.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        
            if (authorizationRequestCheckOutput.getStatus().equals(AuthorizationRequestCheckOutput.STATUS_INVALID_SCOPE)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        
            final ClientInformationOutput clientInformationOutput = getClientInformationUsecase.execute(GetClientInformationInput.of(param.getClientId()));
            final ScopeDescriptionsOutput scopeDescriptionsOutput = getScopeDescriptionsUsecase.execute(GetScopeDescriptionsInput.of(param.getScope()));
            model.addAttribute("client_id", param.getClientId());
            model.addAttribute("redirect_uri", param.getRedirectUri());
            model.addAttribute("nonce", param.getNonce());
            model.addAttribute("code_challenge", param.getCodeChallenge());
            model.addAttribute("state", param.getState());
            model.addAttribute("scope", param.getScope());
            model.addAttribute("access_type", param.getAccessType());
            model.addAttribute("response_type", param.getResponseType());
            model.addAttribute("username", loginUser.getName());
            model.addAttribute("scopeDescriptions", scopeDescriptionsOutput.getScopeDescriptions());
            model.addAttribute("client_name", clientInformationOutput.getName());
            return "oauth2-authorization";
        }
        catch(InvalidScopeNameListTokenException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
