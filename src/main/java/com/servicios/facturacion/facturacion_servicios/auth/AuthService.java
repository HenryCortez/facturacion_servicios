package com.servicios.facturacion.facturacion_servicios.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.servicios.facturacion.facturacion_servicios.jwt.JwtService;
import com.servicios.facturacion.facturacion_servicios.user.Role;
import com.servicios.facturacion.facturacion_servicios.user.RoleRepository;
import com.servicios.facturacion.facturacion_servicios.user.User;
import com.servicios.facturacion.facturacion_servicios.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder  passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public AuthResponse  login(LoginRequest entity) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword()));
            User user = userRepository.findByUsername(entity.getUsername()).orElseThrow();
            if(!user.isStatus()) return null;
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }

    public AuthResponse register(RegisterRequest entity) {
        Role role = roleRepository.findByRoleName(entity.getRole())
        .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        User user = User.builder()
                .username(entity.getUsername())
                .password(passwordEncoder.encode( entity.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))

                .build();
        
    }

    public boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setStatus(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public AuthResponse changePasswordUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
        }
        return null;
    }
}
