package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.api.dto.AuthUserDto;
import com.tcc.gestaoclinica.api.dto.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.LoginUserDto;
import com.tcc.gestaoclinica.api.dto.RecoveryJwtTokenDto;
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
        AuthUserDto authUser = new AuthUserDto(user.getId(), user.getEmail(), user.getNome());

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(authUser, jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {

        if(userRepository.existsByEmail(createUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if(userRepository.existsByTelefone(createUserDto.telefone())) {
            throw new TelefoneExistsException();
        }

        if(userRepository.existsByCpf(createUserDto.cpf())) {
            throw new CpfExistsException();
        }

        Address address = Address.builder()
                .cep(createUserDto.cep())
                .rua(createUserDto.rua())
                .cidade(createUserDto.cidade())
                .bairro(createUserDto.bairro())
                .estado(createUserDto.estado())
                .complemento(createUserDto.complemento())
                .numero(createUserDto.numero()).build();

        User user = new User();
        user.setAddress(address);
        user.setEmail(createUserDto.email());

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setNome(createUserDto.role());
        roles.add(role);

        user.setRoles(roles);
        user.setDataDeNascimento(createUserDto.dataDeNascimento());
        user.setNome(createUserDto.nome());
        user.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        user.setTelefone(createUserDto.telefone());
        user.setSobrenome(createUserDto.sobrenome());
        user.setCpf(createUserDto.cpf());
        user.setGenero(createUserDto.genero());
        user.setStatus(Status.ATIVO);

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, CreateUserDto updateUserDto) {

        User user = buscarOuFalhar(userId);

        if (!user.getEmail().equals(updateUserDto.email()) && userRepository.existsByEmail(updateUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if (!user.getTelefone().equals(updateUserDto.telefone()) && userRepository.existsByTelefone(updateUserDto.telefone())) {
            throw new TelefoneExistsException();
        }

        if (!user.getCpf().equals(updateUserDto.cpf()) && userRepository.existsByCpf(updateUserDto.cpf())) {
            throw new CpfExistsException();
        }


        user.setNome(updateUserDto.nome());
        user.setSobrenome(updateUserDto.sobrenome());
        user.setGenero(updateUserDto.genero());
        user.setDataDeNascimento(updateUserDto.dataDeNascimento());

        Address address = user.getAddress();
        address.setCep(updateUserDto.cep());
        address.setRua(updateUserDto.rua());
        address.setCidade(updateUserDto.cidade());
        address.setBairro(updateUserDto.bairro());
        address.setEstado(updateUserDto.estado());
        address.setComplemento(updateUserDto.complemento());
        address.setNumero(updateUserDto.numero());
        user.setAddress(address);


        if (!user.getEmail().equals(updateUserDto.email())) {
            user.setEmail(updateUserDto.email());
        }

        if (!user.getCpf().equals(updateUserDto.cpf())) {
            user.setCpf(updateUserDto.cpf());
        }

        if (!user.getTelefone().equals(updateUserDto.telefone())) {
            user.setTelefone(updateUserDto.telefone());
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
