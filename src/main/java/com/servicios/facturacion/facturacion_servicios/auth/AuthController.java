package com.servicios.facturacion.facturacion_servicios.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        LoginRequest entity = new LoginRequest();
        entity.setUsername(username);
        entity.setPassword(password);
        System.out.println(entity + "\nLogin");
        return ResponseEntity.ok(authService.login(entity));
    }
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role) {
        RegisterRequest entity = new RegisterRequest();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setRole(role);
        return ResponseEntity.ok(authService.register(entity));
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
        
        return ResponseEntity.ok(authService.deleteUser(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Boolean> updateUser(@PathVariable String username,  @RequestParam("password") String password) {
        
        return ResponseEntity.ok(authService.changePasswordUser(username, password));
    }
    
}
