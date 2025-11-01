package com.DXCTrainingProject.NoteMakingApplication.Controller;

import com.DXCTrainingProject.NoteMakingApplication.DTO.AuthRequest;
import com.DXCTrainingProject.NoteMakingApplication.DTO.AuthResponse;
import com.DXCTrainingProject.NoteMakingApplication.DTO.RegisterRequest;
import com.DXCTrainingProject.NoteMakingApplication.Entity.User;
import com.DXCTrainingProject.NoteMakingApplication.Repository.UserRepository;
import com.DXCTrainingProject.NoteMakingApplication.Security.JwtUtil;
import org.hibernate.service.UnknownServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        if(userRepository.existsByUsername(req.getUsername())){
            return new ResponseEntity<>("User with this username already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            User user = userRepository.findByUsername(req.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found with this username"));

            String token = jwtUtil.generateToken(req.getUsername(), user.getRoles());
            AuthResponse authResponse = new AuthResponse(token, user.getRoles());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }catch(BadCredentialsException ex){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest req){
        if(userRepository.existsByUsername(req.getUsername())){
            return new ResponseEntity<>("Admin already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles("ROLE_ADMIN");
        userRepository.save(user);
        return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
    }
}
