package kotetsu.auth.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.ClientNotPermittedScopesContainedException;
import kotetsu.auth.application.exception.InvalidScopeNameListTokenException;
import kotetsu.auth.application.exception.RedirectUriDoseNotMatchException;
import kotetsu.auth.application.exception.RequesterClientNotFoundRuntimeException;
import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;
import kotetsu.auth.dto.requestparam.PostOAuth2AuthorizationRequestParam;
import kotetsu.auth.dto.security.MyUserDetails;


@Controller
public class PostOAuth2AuthorizationCodeController {

    private final GetAuthorizationCodeUsecase usecase;

    public PostOAuth2AuthorizationCodeController(final GetAuthorizationCodeUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/oauth2/authorization/code")
    public String handle(
        @Valid PostOAuth2AuthorizationRequestParam param,
        @AuthenticationPrincipal MyUserDetails loginUser
    ) throws BadRequestException
    {

        try {
            final AuthorizationCodeOutput output = usecase.execute(GetAuthorizationCodeInput.of(
                loginUser.getKey(),
                param.getClientId(),
                param.getRedirectUri(),
                param.getScope(),
                param.getCodeChallenge(),
                param.getNonce(),
                param.getAccessType()
            ));

            final String redirectPath = generateRedirectPath(
                param.getRedirectUri(), output.getCode(), param.getState()
            );
            return "redirect:" + redirectPath;
        }
        catch(ClientNotPermittedScopesContainedException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch(RedirectUriDoseNotMatchException exception) {
            throw new BadRequestException(exception.getMessage());

        }
        catch(InvalidScopeNameListTokenException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch(RequesterClientNotFoundRuntimeException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    private String generateRedirectPath(
        final String redirectUri,
        final String code,
        final String state
    ) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(redirectUri);
        stringBuilder.append("?code=" + code);
        stringBuilder.append("&state=" + state);
        return stringBuilder.toString();
    }
    
}
