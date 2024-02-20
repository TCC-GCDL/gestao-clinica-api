package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.api.dto.AuthUserDto;
import com.tcc.gestaoclinica.api.dto.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.LoginUserDto;
import com.tcc.gestaoclinica.api.dto.RecoveryJwtTokenDto;
import com.tcc.gestaoclinica.core.security.JwtTokenService;
import com.tcc.gestaoclinica.core.security.SecurityConfiguration;
import com.tcc.gestaoclinica.core.security.UserDetailsImpl;
import com.tcc.gestaoclinica.domain.exceptions.*;
import com.tcc.gestaoclinica.domain.models.Endereco;
import com.tcc.gestaoclinica.domain.models.Role;
import com.tcc.gestaoclinica.domain.models.Status;
import com.tcc.gestaoclinica.domain.models.Usuario;
import com.tcc.gestaoclinica.domain.repositories.UsuarioRepository;
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
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {

        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        AuthUserDto authUser = new AuthUserDto(usuario.getId(), usuario.getEmail(), usuario.getNome());

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(authUser, jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {

        if(usuarioRepository.existsByEmail(createUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if(usuarioRepository.existsByTelefone(createUserDto.telefone())) {
            throw new TelefoneExistsException();
        }

        if(usuarioRepository.existsByCpf(createUserDto.cpf())) {
            throw new CpfExistsException();
        }

        Endereco endereco = Endereco.builder()
                .cep(createUserDto.cep())
                .rua(createUserDto.rua())
                .cidade(createUserDto.cidade())
                .bairro(createUserDto.bairro())
                .estado(createUserDto.estado())
                .complemento(createUserDto.complemento())
                .numero(createUserDto.numero()).build();

        Usuario usuario = new Usuario();
        usuario.setEndereco(endereco);
        usuario.setEmail(createUserDto.email());

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setNome(createUserDto.role());
        roles.add(role);

        usuario.setRoles(roles);
        usuario.setDataDeNascimento(createUserDto.dataDeNascimento());
        usuario.setNome(createUserDto.nome());
        usuario.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        usuario.setTelefone(createUserDto.telefone());
        usuario.setSobrenome(createUserDto.sobrenome());
        usuario.setCpf(createUserDto.cpf());
        usuario.setGenero(createUserDto.genero());
        usuario.setStatus(Status.ATIVO);

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateUser(Long userId, CreateUserDto updateUserDto) {

        Usuario usuario = buscarOuFalhar(userId);

        if (!usuario.getEmail().equals(updateUserDto.email()) && usuarioRepository.existsByEmail(updateUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        if (!usuario.getTelefone().equals(updateUserDto.telefone()) && usuarioRepository.existsByTelefone(updateUserDto.telefone())) {
            throw new TelefoneExistsException();
        }

        if (!usuario.getCpf().equals(updateUserDto.cpf()) && usuarioRepository.existsByCpf(updateUserDto.cpf())) {
            throw new CpfExistsException();
        }


        usuario.setNome(updateUserDto.nome());
        usuario.setSobrenome(updateUserDto.sobrenome());
        usuario.setGenero(updateUserDto.genero());
        usuario.setDataDeNascimento(updateUserDto.dataDeNascimento());

        Endereco endereco = usuario.getEndereco();
        endereco.setCep(updateUserDto.cep());
        endereco.setRua(updateUserDto.rua());
        endereco.setCidade(updateUserDto.cidade());
        endereco.setBairro(updateUserDto.bairro());
        endereco.setEstado(updateUserDto.estado());
        endereco.setComplemento(updateUserDto.complemento());
        endereco.setNumero(updateUserDto.numero());
        usuario.setEndereco(endereco);


        if (!usuario.getEmail().equals(updateUserDto.email())) {
            usuario.setEmail(updateUserDto.email());
        }

        if (!usuario.getCpf().equals(updateUserDto.cpf())) {
            usuario.setCpf(updateUserDto.cpf());
        }

        if (!usuario.getTelefone().equals(updateUserDto.telefone())) {
            usuario.setTelefone(updateUserDto.telefone());
        }

        if (!securityConfiguration.passwordEncoder().matches(updateUserDto.password(), usuario.getPassword())) {
            usuario.setPassword(securityConfiguration.passwordEncoder().encode(updateUserDto.password()));
        }

        usuario.setStatus(updateUserDto.status());

        usuarioRepository.save(usuario);
    }

    public Usuario getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();
        return usuario;
    }


    @Transactional
    public void inativarUsuario(Usuario usuario) {
        try {
            usuario.setStatus(Status.INATIVO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void ativarUsuario(Usuario usuario) {
        try {
            usuario.setStatus(Status.ATIVO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

}
