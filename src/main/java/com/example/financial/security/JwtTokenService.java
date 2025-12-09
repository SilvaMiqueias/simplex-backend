package com.example.financial.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {

    @Value("{jwt.secret}")
    private  String SECRET_KEY;

    private static final String ISSUER = "simplex-api";

    public String generateToken(UserDetailsImp user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withClaim("id", user.getUser().getId())
                    .withClaim("name", user.getUser().getName())
                    .withClaim("role", user.getUser().getRoles().stream().map( t -> t.getName().name()).collect(Collectors.toList()))
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token", exception);
        }
    }

    public String generateTempToken(UserDetailsImp user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 3)))
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar temp token", exception);
        }
    }

    public String getSubjectFromToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Token inválido ou expirado", exception);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}
