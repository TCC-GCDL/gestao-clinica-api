package com.tcc.gestaoclinica.api.dto;

import com.tcc.gestaoclinica.domain.models.Role;

import java.util.List;

public record RecoveryUserDto(Long id, String email, List<Role> roles) {
}
