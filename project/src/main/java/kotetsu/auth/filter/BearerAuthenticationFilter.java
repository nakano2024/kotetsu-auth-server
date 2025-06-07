package kotetsu.auth.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotetsu.auth.dto.principal.AuthUserPrincipal;
import kotetsu.auth.exception.BearerTokenInvalidException;
import kotetsu.auth.util.JwtClaimsGetter;

public class BearerAuthenticationFilter extends OncePerRequestFilter {
    private final JwtClaimsGetter jwtClaimsGetter;

    public BearerAuthenticationFilter(final JwtClaimsGetter jwtClaimsGetter) {
        this.jwtClaimsGetter = jwtClaimsGetter;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    )
        throws ServletException, IOException 
    {
        final String prefix = "Bearer ";
        try {
            final String bearerToken = request.getHeader("Authorization");

            // ホワイトリスト方式で認証を行うために、やや複雑な条件式となっている。
            if (
                (bearerToken != null && !bearerToken.isEmpty()) &&
                (bearerToken.startsWith(prefix))
            ) {
                Claims claims = jwtClaimsGetter.getClaims(bearerToken.replace(prefix, ""));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    new AuthUserPrincipal(claims.getSubject()), 
                    bearerToken,
                    Collections.emptyList()
                ));
                filterChain.doFilter(request, response);
                return;
            }

            filterChain.doFilter(request, response);
        }
        catch(BearerTokenInvalidException e) {
            filterChain.doFilter(request, response);
        }
    }
}
