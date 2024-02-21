package com.tcc.gestaoclinica.core.security;

import com.tcc.gestaoclinica.domain.models.User;
import com.tcc.gestaoclinica.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    O método loadUserByUsername() é um método da interface UserDetailsService, e é usado para carregar os detalhes do
//    usuário com base no nome de usuário fornecido. Esse método é chamado automaticamente pelo Spring durante o
//    processo de autenticação, e é responsável por retornar um UserDetails com base no nome de usuário fornecido.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserDetailsImpl(user);
    }
}
