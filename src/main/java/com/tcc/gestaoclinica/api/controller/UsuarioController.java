package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.UserDto;
import com.tcc.gestaoclinica.domain.exceptions.EntityNotFoundException;
import com.tcc.gestaoclinica.domain.models.Status;
import com.tcc.gestaoclinica.domain.models.Usuario;
import com.tcc.gestaoclinica.domain.repositories.UsuarioRepository;
import com.tcc.gestaoclinica.domain.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<UserDto> getUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return new ArrayList<>();
        }
        return usuarios.stream().map(this::userToUserDto).toList();
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UserDto> getUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        UserDto userDto = userToUserDto(usuario);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long usuarioId, @RequestBody CreateUserDto createUserDto) {
        usuarioService.updateUser(usuarioId, createUserDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/{usuarioId}/inactivate")
    public void deleteUser(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        usuarioService.inativarUsuario(usuario);
    }

    @PostMapping("/{usuarioId}/activate")
    public void activeUser(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        usuarioService.ativarUsuario(usuario);
    }



    private UserDto userToUserDto(Usuario usuario) {
        UserDto userDto = new UserDto();
        userDto.setId(usuario.getId());
        userDto.setEmail(usuario.getEmail());
        userDto.setNome(usuario.getNome());
        userDto.setSobrenome(usuario.getSobrenome());
        userDto.setTelefone(usuario.getTelefone());
        userDto.setCpf(usuario.getCpf());
        userDto.setGenero(usuario.getGenero());
        userDto.setDataDeNascimento(usuario.getDataDeNascimento());
        if (usuario.getEndereco() != null) {
            userDto.setRua(usuario.getEndereco().getRua());
            userDto.setNumero(usuario.getEndereco().getNumero());
            userDto.setComplemento(usuario.getEndereco().getComplemento());
            userDto.setBairro(usuario.getEndereco().getBairro());
            userDto.setCidade(usuario.getEndereco().getCidade());
            userDto.setEstado(usuario.getEndereco().getEstado());
            userDto.setCep(usuario.getEndereco().getCep());
        }

        userDto.setRole(usuario.getRoles().stream().toList().get(0).getNome());
        userDto.setStatus(usuario.getStatus());
        userDto.setPassword(usuario.getPassword());

        return userDto;
    }







}
