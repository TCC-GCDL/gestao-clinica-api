package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.request.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.request.LoginUserDto;
import com.tcc.gestaoclinica.api.dto.request.TokenDto;
import com.tcc.gestaoclinica.api.dto.response.RecoveryJwtTokenDto;
import com.tcc.gestaoclinica.api.dto.response.ValidateTokenResponse;
import com.tcc.gestaoclinica.core.security.JwtTokenService;
import com.tcc.gestaoclinica.domain.repositories.UserRepository;
import com.tcc.gestaoclinica.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

    @PostMapping("/token/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestBody TokenDto token) {
        if (jwtTokenService.isTokenExpired(token.token())) {
            ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
            validateTokenResponse.setIsValid(false);
            validateTokenResponse.setMessage("Token expirado");
            return new ResponseEntity<>(validateTokenResponse, HttpStatus.BAD_REQUEST);
        } else {
            ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
            validateTokenResponse.setIsValid(true);
            validateTokenResponse.setMessage("Token válido");
            return new ResponseEntity<>(validateTokenResponse, HttpStatus.OK);
        }
    }

}
