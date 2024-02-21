package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.api.dto.AuthUserDto;

public record RecoveryJwtTokenDto(AuthUserDto usuario, String token) {
}
