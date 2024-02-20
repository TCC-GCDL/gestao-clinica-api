package com.tcc.gestaoclinica.core.security;

import com.tcc.gestaoclinica.domain.models.Usuario;
import com.tcc.gestaoclinica.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

//    O método loadUserByUsername() é um método da interface UserDetailsService, e é usado para carregar os detalhes do
//    usuário com base no nome de usuário fornecido. Esse método é chamado automaticamente pelo Spring durante o
//    processo de autenticação, e é responsável por retornar um UserDetails com base no nome de usuário fornecido.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserDetailsImpl(usuario);
    }
}
