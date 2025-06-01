package kotetsu.auth.controller;

import java.util.Map;

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
import kotetsu.auth.application.dto.output.UserProfileOutput;
import kotetsu.auth.application.exception.UserProfileNotFoundException;
import kotetsu.auth.application.usecase.GetUserProfileByEmailUsecase;
import kotetsu.auth.request.PasswordAuthRequest;
import kotetsu.auth.util.InternalAuthIdTokenGenerator;

@RestController
@RequestMapping(path = "/api")
public class PostPasswordAuthController {

    private final AuthenticationManager authenticationManager;

    private final InternalAuthIdTokenGenerator idTokenGenerator;

    private final GetUserProfileByEmailUsecase getUserProfileByEmailUsecase;

    public PostPasswordAuthController(
        final AuthenticationManager authenticationManager,
        final InternalAuthIdTokenGenerator idTokenGenerator,
        final GetUserProfileByEmailUsecase getUserProfileByEmailUsecase
    ) {
        this.authenticationManager = authenticationManager;
        this.idTokenGenerator = idTokenGenerator;
        this.getUserProfileByEmailUsecase = getUserProfileByEmailUsecase;
    }

    @PostMapping("/auth/token")
    @PermitAll
    public ResponseEntity<?> handle(@RequestBody PasswordAuthRequest request) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            UserProfileOutput output = getUserProfileByEmailUsecase.getUserProfile(GetUserProfileEmailInput.of(authentication.getName()));

            String jwt = idTokenGenerator.generate(output.getCode().toString(), Map.of(
                "name", output.getName(),
                "email", output.getEmail(),
                "image_url", output.getImageUrl()
            ));

            return ResponseEntity.ok(jwt);
        }
        catch (UserProfileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e) {
            throw e;
        }
    }
}
