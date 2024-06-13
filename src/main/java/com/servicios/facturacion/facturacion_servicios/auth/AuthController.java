package com.servicios.facturacion.facturacion_servicios.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest entity) {
        System.out.println(entity + "\nLogin");
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
    public ResponseEntity<Boolean> updateUser(@PathVariable String username,  @RequestBody String password) {
        
        return ResponseEntity.ok(authService.changePasswordUser(username, password));
    }
    
}
