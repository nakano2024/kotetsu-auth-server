package kotetsu.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kotetsu.auth.application.dto.output.OidcPublicKeyCertsOutput;
import kotetsu.auth.application.usecase.GetOidcPublicKeyCertsUsecase;
import kotetsu.auth.dto.resource.JwkResource;
import kotetsu.auth.dto.response.GetCertsResponse;

@RestController
public class GetCertsController {

    private final GetOidcPublicKeyCertsUsecase usecase;

    public GetCertsController(final GetOidcPublicKeyCertsUsecase usecase) {
        this.usecase = usecase;
    }

    @GetMapping("/api/oauth2/certs")
    ResponseEntity<GetCertsResponse> handle() {
        final OidcPublicKeyCertsOutput output = usecase.execute();

        final List<JwkResource> keys = output.getKeys()
            .stream()
            .map(key -> new JwkResource(
                key.getKid(),
                key.getKty(),
                key.getAlg(),
                key.getUse(),
                key.getN(),
                key.getE()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok().body(new GetCertsResponse(keys));
    }
}
