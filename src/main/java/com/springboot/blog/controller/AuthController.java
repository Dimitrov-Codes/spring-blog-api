package com.springboot.blog.controller;

import com.springboot.blog.dto.JWTAuthResponseDTO;
import com.springboot.blog.dto.LoginDTO;
import com.springboot.blog.dto.RegisterDTO;
import com.springboot.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
     private final AuthService authService;

     public AuthController(AuthService authService) {
          this.authService = authService;
     }

     @PostMapping({"login", "signin"})
     public ResponseEntity<JWTAuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
          String token = authService.login(loginDTO);
          JWTAuthResponseDTO jwtAuthResponseDTO = new JWTAuthResponseDTO(token);
          return new ResponseEntity<>(jwtAuthResponseDTO, HttpStatus.OK);

     }

     @PostMapping("register")
     public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
          return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
     }
}
