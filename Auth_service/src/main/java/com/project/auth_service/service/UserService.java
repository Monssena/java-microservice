package com.project.auth_service.service;

import com.project.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.project.auth_service.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean register(String username, String password) {
        if (repo.findByUsername(username).isPresent()) return false;
        repo.save(User.builder()
                .username(username)
                .password(encoder.encode(password))
                .build());
        return true;
    }

    public boolean validate(String username, String rawPassword) {
        return repo.findByUsername(username)
                .map(user -> encoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
