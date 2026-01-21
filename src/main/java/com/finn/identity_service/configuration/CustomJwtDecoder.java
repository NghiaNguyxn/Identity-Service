package com.finn.identity_service.configuration;

import com.finn.identity_service.repository.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    private final JwtDecoder delegate;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public CustomJwtDecoder(
            @Qualifier("nimbusJwtDecoder") JwtDecoder delegate,
            InvalidatedTokenRepository invalidatedTokenRepository
    ){
        this.delegate = delegate;
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        // Verify signature, exp, ...
        Jwt jwt = delegate.decode(token);

        if(jwt.getId() != null
                && invalidatedTokenRepository.existsById(jwt.getId())) {
            throw new JwtException("Token has been invalidated");
        }

        return jwt;
    }
}
