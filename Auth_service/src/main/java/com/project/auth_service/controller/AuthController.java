package com.project.auth_service.controller;

import com.project.auth_service.config.JwtUtil;
import com.project.auth_service.model.AuthRequest;
import com.project.auth_service.model.AuthResponse;
import com.project.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest req) {
        boolean created = userService.register(req.getUsername(), req.getPassword());
        return created
                ? ResponseEntity.ok("✅ Пользователь зарегистрирован")
                : ResponseEntity.status(400).body("⚠ Пользователь уже существует");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        if (userService.validate(req.getUsername(), req.getPassword())) {
            String token = jwtUtil.generateToken(req.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Неверные учетные данные");
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("✅ Доступ к защищённому ресурсу получен");
    }
}
