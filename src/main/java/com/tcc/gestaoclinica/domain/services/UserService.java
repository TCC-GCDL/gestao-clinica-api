package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.api.dto.AuthUserDto;
import com.tcc.gestaoclinica.api.dto.request.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.request.LoginUserDto;
import com.tcc.gestaoclinica.api.dto.response.RecoveryJwtTokenDto;
import com.tcc.gestaoclinica.core.security.JwtTokenService;
import com.tcc.gestaoclinica.core.security.SecurityConfiguration;
import com.tcc.gestaoclinica.core.security.UserDetailsImpl;
import com.tcc.gestaoclinica.domain.exceptions.*;
import com.tcc.gestaoclinica.domain.models.Address;
import com.tcc.gestaoclinica.domain.models.Role;
import com.tcc.gestaoclinica.domain.models.Status;
import com.tcc.gestaoclinica.domain.models.User;
import com.tcc.gestaoclinica.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private UserRepository userRepository;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {

        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        AuthUserDto authUser = new AuthUserDto(user.getId(), user.getEmail(), user.getFirstName());

        Instant expirationDate = jwtTokenService.expirationDate();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(authUser, jwtTokenService.generateToken(userDetails), expirationDate);
    }

    public void createUser(CreateUserDto createUserDto) {

        if(userRepository.existsByEmail(createUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if(userRepository.existsByPhone(createUserDto.phone())) {
            throw new TelefoneExistsException();
        }

        if(userRepository.existsByCpf(createUserDto.cpf())) {
            throw new CpfExistsException();
        }

        Address address = Address.builder()
                .zipCode(createUserDto.zipCode())
                .street(createUserDto.street())
                .city(createUserDto.city())
                .neighborhood(createUserDto.neighborhood())
                .state(createUserDto.state())
                .complement(createUserDto.complement())
                .number(createUserDto.number()).build();

        User user = new User();
        user.setAddress(address);
        user.setEmail(createUserDto.email());

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName(createUserDto.role());
        roles.add(role);

        user.setRoles(roles);
        user.setDateOfBirth(createUserDto.dateOfBirth());
        user.setFirstName(createUserDto.firstName());
        user.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        user.setPhone(createUserDto.phone());
        user.setLastName(createUserDto.lastName());
        user.setCpf(createUserDto.cpf());
        user.setGender(createUserDto.gender());
        user.setStatus(Status.ATIVO);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, CreateUserDto updateUserDto) {

        User user = buscarOuFalhar(userId);

        if (!user.getEmail().equals(updateUserDto.email()) && userRepository.existsByEmail(updateUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if (!user.getPhone().equals(updateUserDto.phone()) && userRepository.existsByPhone(updateUserDto.phone())) {
            throw new TelefoneExistsException();
        }

        if (!user.getCpf().equals(updateUserDto.cpf()) && userRepository.existsByCpf(updateUserDto.cpf())) {
            throw new CpfExistsException();
        }


        user.setFirstName(updateUserDto.firstName());
        user.setLastName(updateUserDto.lastName());
        user.setGender(updateUserDto.gender());
        user.setDateOfBirth(updateUserDto.dateOfBirth());

        Address address = user.getAddress();
        address.setZipCode(updateUserDto.zipCode());
        address.setStreet(updateUserDto.street());
        address.setCity(updateUserDto.city());
        address.setNeighborhood(updateUserDto.neighborhood());
        address.setState(updateUserDto.state());
        address.setComplement(updateUserDto.complement());
        address.setNumber(updateUserDto.number());
        user.setAddress(address);


        if (!user.getEmail().equals(updateUserDto.email())) {
            user.setEmail(updateUserDto.email());
        }

        if (!user.getCpf().equals(updateUserDto.cpf())) {
            user.setCpf(updateUserDto.cpf());
        }

        if (!user.getPhone().equals(updateUserDto.phone())) {
            user.setPhone(updateUserDto.phone());
        }

        if (!securityConfiguration.passwordEncoder().matches(updateUserDto.password(), user.getPassword())) {
            user.setPassword(securityConfiguration.passwordEncoder().encode(updateUserDto.password()));
        }

        user.setStatus(updateUserDto.status());

        userRepository.save(user);
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return user;
    }


    @Transactional
    public void inativarUsuario(User user) {
        try {
            user.setStatus(Status.INATIVO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void ativarUsuario(User user) {
        try {
            user.setStatus(Status.ATIVO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public User buscarOuFalhar(Long usuarioId) {
        return userRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

}
