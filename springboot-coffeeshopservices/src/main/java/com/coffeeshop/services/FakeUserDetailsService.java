package com.coffeeshop.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Navaneeth
 *
 */
@Service
public class FakeUserDetailsService implements UserDetailsService {
@Value("${app.superuser:Rob}")
private String superUser;
@Override
public UserDetails loadUserByUsername(String username) throws
UsernameNotFoundException {	
return new User(superUser, "{noop}pwd#", getGrantedAuthorities(username));
}
private Collection<? extends GrantedAuthority> getGrantedAuthorities(String
username) {
Collection<? extends GrantedAuthority> authorities;
if (username.equalsIgnoreCase(superUser)) {
authorities = Arrays.asList(() -> "ROLE_ADMIN", () -> "ROLE_BASIC");
} else {
authorities = Arrays.asList(() -> "ROLE_BASIC");
}
return authorities;
}
}
