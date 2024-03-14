package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.request.CreateUserDto;
import com.tcc.gestaoclinica.api.dto.response.PatientResponse;
import com.tcc.gestaoclinica.api.dto.response.UserDto;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.models.User;
import com.tcc.gestaoclinica.domain.repositories.UserRepository;
import com.tcc.gestaoclinica.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (name != null && !name.isEmpty()) {
            users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        Page<UserDto> responses = users.map(this::userToUserDto);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UserDto> getUsuario(@PathVariable Long usuarioId) {
        User user = userService.buscarOuFalhar(usuarioId);
        UserDto userDto = userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long usuarioId, @RequestBody CreateUserDto createUserDto) {
        userService.updateUser(usuarioId, createUserDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/{usuarioId}/inactivate")
    public void deleteUser(@PathVariable Long usuarioId) {
        User user = userService.buscarOuFalhar(usuarioId);
        userService.inativarUsuario(user);
    }

    @PostMapping("/{usuarioId}/activate")
    public void activeUser(@PathVariable Long usuarioId) {
        User user = userService.buscarOuFalhar(usuarioId);
        userService.ativarUsuario(user);
    }



    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setCpf(user.getCpf());
        userDto.setGender(user.getGender());
        userDto.setDateOfBirth(user.getDateOfBirth());
        if (user.getAddress() != null) {
            userDto.setStreet(user.getAddress().getStreet());
            userDto.setNumber(user.getAddress().getNumber());
            userDto.setComplement(user.getAddress().getComplement());
            userDto.setNeighborhood(user.getAddress().getNeighborhood());
            userDto.setCity(user.getAddress().getCity());
            userDto.setState(user.getAddress().getState());
            userDto.setZipCode(user.getAddress().getZipCode());
        }

        userDto.setRole(user.getRoles().stream().toList().get(0).getName());
        userDto.setStatus(user.getStatus());


        return userDto;
    }







}
