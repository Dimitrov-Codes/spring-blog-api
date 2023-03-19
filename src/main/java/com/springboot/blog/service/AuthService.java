package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDTO;
import com.springboot.blog.dto.RegisterDTO;
import com.springboot.blog.exception.APIException;
import com.springboot.blog.models.user.Role;
import com.springboot.blog.models.user.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JWTTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
     private final AuthenticationManager authenticationManager;
     private final UserRepository userRepository;
     private final RoleRepository roleRepository;
     private final PasswordEncoder passwordEncoder;
     private final JWTTokenProvider jwtTokenProvider;
     public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                        RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
          this.authenticationManager = authenticationManager;
          this.userRepository = userRepository;
          this.roleRepository = roleRepository;
          this.passwordEncoder = passwordEncoder;
          this.jwtTokenProvider = jwtTokenProvider;
     }


     public String login(LoginDTO loginDTO) {
          Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                  loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(auth);
          String token = jwtTokenProvider.generateToken(auth);
          return token;
     }

     public String register(RegisterDTO registerDTO) {
          //add check for existing user
          if (userRepository.existsByUsername(registerDTO.getUsername()) &&
                  userRepository.existsByEmail(registerDTO.getEmail()))
               throw new APIException("User Already exists", HttpStatus.BAD_REQUEST);

          Role role = roleRepository.findByName("ROLE_USER").get();
          User user = new User(null, registerDTO.getName(), registerDTO.getEmail(), registerDTO.getUsername(),
                  passwordEncoder.encode(registerDTO.getPassword()), Set.of(role));

          userRepository.save(user);
          return "Created user successfully!";

     }
}
