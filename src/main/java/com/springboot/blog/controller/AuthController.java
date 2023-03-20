package com.springboot.blog.controller;

import com.springboot.blog.dto.JWTAuthResponseDTO;
import com.springboot.blog.dto.LoginDTO;
import com.springboot.blog.dto.RegisterDTO;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@Tag(name = "CRUD REST APIs for Authorization Resource")
public class AuthController {
     private final AuthService authService;

     public AuthController(AuthService authService) {
          this.authService = authService;
     }
     @Operation(
             summary = "Login REST API",
             description = "Login to the application using valid credentials and obtain a Bearer token"
     )
     @ApiResponse(
             responseCode = "200",
             description = "HTTP Status 200 SUCCESS"
     )
     @PostMapping({"login", "signin"})
     public ResponseEntity<JWTAuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
          String token = authService.login(loginDTO);
          JWTAuthResponseDTO jwtAuthResponseDTO = new JWTAuthResponseDTO(token);
          return new ResponseEntity<>(jwtAuthResponseDTO, HttpStatus.OK);

     }
     @Operation(
             summary = "Register REST API",
             description = "Registers a new User and returns a Bearer token"
     )
     @ApiResponse(
             responseCode = "201",
             description = "HTTP Status 201 CREATED"
     )
     @PostMapping("register")
     public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
          return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
     }
}
