package kotetsu.auth.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.CheckClientInput;
import kotetsu.auth.application.dto.output.ClientCheckOutput;
import kotetsu.auth.application.usecase.CheckClientUsecase;
import kotetsu.auth.dto.requestparam.PostOAuth2AuthorizationDenyRequesParam;

@Controller
public class PostOAuth2AuthorizationDenyController {

    private final CheckClientUsecase usecase;

    public PostOAuth2AuthorizationDenyController(final CheckClientUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/oauth2/authorization/deny")
    public String handle(@Valid PostOAuth2AuthorizationDenyRequesParam param) throws BadRequestException
    {
            final ClientCheckOutput output = usecase.execute(CheckClientInput.of(
                param.getClientId(),
                param.getRedirectUri()
            ));

            if (output.getStatus().equals(ClientCheckOutput.STATUS_CLIENT_NOT_FOUND)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            if (output.getStatus().equals(ClientCheckOutput.STATUS_INVALID_REDIRECT_URI)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            final String redirectPath = generateRedirectPath(
                param.getRedirectUri(), param.getState()
            );
            return "redirect:" + redirectPath;
    }

    private String generateRedirectPath(
        final String redirectUri,
        final String state
    ) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(redirectUri);
        stringBuilder.append("?error=" + "access_denied");
        stringBuilder.append("&state=" + state);
        return stringBuilder.toString();
    }
}
