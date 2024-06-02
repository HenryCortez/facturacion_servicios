package com.servicios.facturacion.facturacion_servicios.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicios.facturacion.facturacion_servicios.user.User;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest entity) {
        
        return ResponseEntity.ok(authService.login(entity));
    }
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest entity) {
  
        
        return ResponseEntity.ok(authService.register(entity));
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
        
        return ResponseEntity.ok(authService.deleteUser(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Boolean> updateUser(@PathVariable String username, @RequestBody User entity) {
        
        return ResponseEntity.ok(authService.changePasswordUser(username, entity));
    }
    
}
