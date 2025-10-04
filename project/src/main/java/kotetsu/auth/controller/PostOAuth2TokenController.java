package kotetsu.auth.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kotetsu.auth.application.dto.input.GetTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundException;
import kotetsu.auth.application.exception.InputAuthorizationCodeNullException;
import kotetsu.auth.application.exception.InputCodeVerifierNullException;
import kotetsu.auth.application.exception.InputRefreshTokenNullException;
import kotetsu.auth.application.exception.InvalidCodeVerifierException;
import kotetsu.auth.application.exception.InvalidGrantTypeException;
import kotetsu.auth.application.exception.RefreshTokenExpiredException;
import kotetsu.auth.application.exception.RefreshTokenNotFoundException;
import kotetsu.auth.application.exception.TokenGrantTypeDoseNotMatchException;
import kotetsu.auth.application.usecase.GetTokenUsecase;
import kotetsu.auth.dto.requestparam.OAuth2PostTokenRequestParam;
import kotetsu.auth.dto.response.PostOAuth2TokenResponse;

@RestController
public class PostOAuth2TokenController {

    final GetTokenUsecase usecase;

    public PostOAuth2TokenController(final GetTokenUsecase usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/api/oauth2/token")
    public ResponseEntity<PostOAuth2TokenResponse> handle(@Valid OAuth2PostTokenRequestParam param) throws BadRequestException
    {
        try {
            final TokenOutput output = usecase.execute(GetTokenInput.of(
                param.getGrantType(),
                param.getCode(),
                param.getCodeVerifier(),
                param.getRefreshToken()
            ));

            return ResponseEntity.ok().body(new PostOAuth2TokenResponse(
                output.getAccessToken(),
                output.getTokenType(),
                output.getExpiresIn(),
                output.getScopeToken(),
                output.getRefreshToken().orElse(null),
                output.getIdToken().orElse(null)
            ));
        } 
        catch (AuthorizationCodeNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (AuthorizationCodeExpiredException e) {
            throw new BadRequestException(e.getMessage());
        } 
        catch (InvalidGrantTypeException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (InvalidCodeVerifierException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (InputAuthorizationCodeNullException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (InputCodeVerifierNullException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (InputRefreshTokenNullException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (RefreshTokenNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (TokenGrantTypeDoseNotMatchException e) {
            throw new BadRequestException(e.getMessage());
        }
        catch (RefreshTokenExpiredException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
