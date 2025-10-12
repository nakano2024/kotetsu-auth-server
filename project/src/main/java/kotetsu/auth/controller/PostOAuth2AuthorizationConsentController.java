package kotetsu.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.ClientNotPermittedScopesContainedException;
import kotetsu.auth.application.exception.InvalidScopeNameListTokenException;
import kotetsu.auth.application.exception.RedirectUriDoseNotMatchException;
import kotetsu.auth.application.exception.RequesterClientNotFoundRuntimeException;
import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;
import kotetsu.auth.constant.ResponseTypeConstant;
import kotetsu.auth.dto.requestparam.PostOAuth2AuthorizationRequestConsentParam;
import kotetsu.auth.dto.security.MyUserDetails;
import kotetsu.auth.exception.ResponseTypeInvalidRuntimeException;


@Controller
public class PostOAuth2AuthorizationConsentController {

    private final GetAuthorizationCodeUsecase usecase;

    public PostOAuth2AuthorizationConsentController(final GetAuthorizationCodeUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/oauth2/authorization/consent/code")
    public String handle(
        @Valid PostOAuth2AuthorizationRequestConsentParam param,
        @AuthenticationPrincipal MyUserDetails loginUser
    )
    {
        if (param.getResponseType().equals(ResponseTypeConstant.CODE)) {
            return handleResponseTypeCode(
                loginUser.getKey(),
                param.getClientId(),
                param.getRedirectUri(),
                param.getScope(),
                param.getCodeChallenge(),
                param.getNonce(),
                param.getAccessType(),
                param.getState()
            );
        }

        throw new ResponseTypeInvalidRuntimeException();
    }

    private String handleResponseTypeCode(
        final String loginUserKey,
        final String clientId,
        final String redirectUri,
        final String scope,
        final String codeChallenge,
        final String nonce,
        final String accessType,
        final String state
    ) {
        try {
            final AuthorizationCodeOutput output = usecase.execute(GetAuthorizationCodeInput.of(
                loginUserKey,
                clientId,
                redirectUri,
                scope,
                codeChallenge,
                nonce,
                accessType
            ));

            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(redirectUri);
            stringBuilder.append("?code=" + output.getCode());
            stringBuilder.append("&state=" + state);
            return "redirect:" + stringBuilder.toString();
        }
        catch(ClientNotPermittedScopesContainedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch(RedirectUriDoseNotMatchException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
        catch(InvalidScopeNameListTokenException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch(RequesterClientNotFoundRuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
