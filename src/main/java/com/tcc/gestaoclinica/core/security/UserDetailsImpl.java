package com.tcc.gestaoclinica.core.security;

import com.tcc.gestaoclinica.domain.models.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    /*
     Este método converte a lista de papéis (roles) associados ao usuário
     em uma coleção de GrantedAuthorities, que é a forma que o Spring Security
     usa para representar papéis. Isso é feito mapeando cada papel para um
     novo SimpleGrantedAuthority, que é uma implementação simples de
     GrantedAuthority
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
