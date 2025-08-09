package com.david.cv_screener_backend.service;

import com.david.cv_screener_backend.dto.*;
import com.david.cv_screener_backend.model.AppUser;
import com.david.cv_screener_backend.model.Role;
import com.david.cv_screener_backend.repository.AppUserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthService(AppUserRepository users, PasswordEncoder encoder,
                       AuthenticationManager authManager, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (users.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        AppUser u = new AppUser();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setPassword(encoder.encode(req.password()));
        u.setRole(Role.RECRUITER); // choose default role
        users.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        var user = users.findByEmail(req.email()).orElseThrow();

        String token = jwt.generateToken(user.getEmail(), Map.of(
                "role", user.getRole().name(),
                "uid", user.getId()
        ));
        return new AuthResponse(token, "Bearer", jwt.getExpirationMs());
    }
}
