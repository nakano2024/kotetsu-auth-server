package kotetsu.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kotetsu.auth.dto.principal.AuthUserPrincipal;

@RestController
@RequestMapping(path = "/api")
public class AuthRequiredController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth/test")
    public ResponseEntity<?> authTest(@AuthenticationPrincipal AuthUserPrincipal principal) {

        return ResponseEntity.ok("authorized: " + principal.getSubject());
    }
}
