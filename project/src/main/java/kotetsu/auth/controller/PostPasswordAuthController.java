package kotetsu.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import kotetsu.auth.application.dto.input.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.UserProfileNotFoundException;
import kotetsu.auth.application.usecase.GetIdTokenByEmailUsecase;
import kotetsu.auth.dto.request.PasswordAuthRequest;
import kotetsu.auth.dto.resource.IdTokenResource;
import kotetsu.auth.dto.response.PasswordAuthResponse;

@RestController
@RequestMapping(path = "/api")
public class PostPasswordAuthController {

    private final AuthenticationManager authenticationManager;

    private final GetIdTokenByEmailUsecase getUserProfileByEmailUsecase;

    public PostPasswordAuthController(
        final AuthenticationManager authenticationManager,
        final GetIdTokenByEmailUsecase getUserProfileByEmailUsecase
    ) {
        this.authenticationManager = authenticationManager;
        this.getUserProfileByEmailUsecase = getUserProfileByEmailUsecase;
    }

    @PostMapping("/auth/token")
    @PermitAll
    public ResponseEntity<PasswordAuthResponse> handle(@RequestBody PasswordAuthRequest request) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            IdTokenOutput output = getUserProfileByEmailUsecase.getUserProfile(GetUserProfileEmailInput.of(authentication.getName()));

            return ResponseEntity.ok(new PasswordAuthResponse(new IdTokenResource(
                output.getIdToken(),
                output.getTokenType(),
                output.getExpiresIn()
            )));
        }
        catch (UserProfileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e) {
            throw e;
        }
    }
}
