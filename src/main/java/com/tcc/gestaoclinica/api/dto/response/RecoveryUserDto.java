package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.domain.models.Role;

import java.util.List;

public record RecoveryUserDto(Long id, String email, List<Role> roles) {
}
