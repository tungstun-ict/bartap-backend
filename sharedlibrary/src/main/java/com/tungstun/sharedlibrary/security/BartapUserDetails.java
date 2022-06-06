package com.tungstun.sharedlibrary.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record BartapUserDetails(Long id, String username, List<Authorization> authorizations) implements UserDetails {
    public Long getId() {
        return id;
    }

    public List<Authorization> getAuthorizations() {
        return authorizations;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BartapUserDetails that = (BartapUserDetails) o;
        return Objects.equals(username, that.username) && Objects.equals(authorizations, that.authorizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authorizations);
    }
}