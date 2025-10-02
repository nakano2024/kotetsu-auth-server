package kotetsu.auth.dto.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

    private final String key;

    private final String name;

    private final String imageUrl;

    private final String email;

    private final String hashedPassword;

    private final boolean isActive;

    public MyUserDetails(
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword,
        final boolean isActive
    ) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
            new SimpleGrantedAuthority("ROLE_GENERAL")
        );
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEmail() {
        return email;
    }
}
