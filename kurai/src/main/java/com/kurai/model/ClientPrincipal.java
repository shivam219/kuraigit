package com.kurai.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kurai.entity.Client;

public class ClientPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;
	private Client client;

    public ClientPrincipal(Client client) {
        this.client = client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("CLIENT"));
    }

    @Override
    public String getPassword() {
        return client.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return client.getEmail();
    }

    public Long getClientId() {
    	return client.getId();
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

}
