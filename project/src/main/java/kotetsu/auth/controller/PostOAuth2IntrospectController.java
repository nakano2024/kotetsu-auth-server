package kotetsu.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.CheckAccessTokenInput;
import kotetsu.auth.application.dto.output.AccessTokenCheckOutput;
import kotetsu.auth.application.usecase.CheckAccessTokenUsecase;
import kotetsu.auth.dto.requestparam.PostOAuth2IntrospectRequestParam;
import kotetsu.auth.dto.response.PostOAuth2IntrospectResponse;

@RestController
public class PostOAuth2IntrospectController {

    private final CheckAccessTokenUsecase usecase;

    public PostOAuth2IntrospectController(final CheckAccessTokenUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/api/oauth2/introspect")
    public ResponseEntity<PostOAuth2IntrospectResponse> handle(@Valid PostOAuth2IntrospectRequestParam param) {
        final AccessTokenCheckOutput output = usecase.execute(CheckAccessTokenInput.of(param.getToken()));
        return ResponseEntity.ok().body(new PostOAuth2IntrospectResponse(
            output.isActive(),
            output.getScopeToken().orElse(null),
            output.getClientId().orElse(null),
            output.getIssuedAt().orElse(null),
            output.getExpiredAt().orElse(null),
            output.getSubject().orElse(null),
            output.getAudiences().orElse(null),
            output.getIssuer().orElse(null),
            output.getTokenType().orElse(null)
        ));
    }
}
