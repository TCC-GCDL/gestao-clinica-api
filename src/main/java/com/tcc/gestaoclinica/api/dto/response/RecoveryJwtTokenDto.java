package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.api.dto.AuthUserDto;

import java.time.Instant;

public record RecoveryJwtTokenDto(AuthUserDto usuario, String token, Instant expires_at) {
}
