package com.projectsky.auth_microservice.security.jwt;

import com.projectsky.auth_microservice.dto.JwtAunthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Генерация JWT + RefreshToken
    public JwtAunthenticationDto generateAuthToken(String email) {
        return new JwtAunthenticationDto(
                generateJwtToken(email),
                generateRefreshToken(email)
        );
    }

    // Обновляет JWT токен
    public JwtAunthenticationDto refreshBaseToken(String email, String refreshToken) {
        return new JwtAunthenticationDto(
                generateJwtToken(email),
                refreshToken
        );
    }

    // Берет из Payload JWT email пользователя
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public Date getExpiration(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration();
    }

    // Валидация JWT токена
    // Если во время получения Payload не будет Exception - JWT валиден, иначе - нет
    public boolean validateJwtToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e){
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token");
        } catch (MalformedJwtException e){
            log.error("Malformed JWT token");
        } catch (SecurityException e){
            log.error("Security exception");
        } catch (Exception e){
            log.error("Invalid token");
        }
        return false;
    }

    // Генерирует JWT токен с помощью SecretKey
    private String generateJwtToken(String email) {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .expiration(expirationDate)
                .signWith(getSignKey())
                .compact();
    }

    // Генерирует RefreshToken с помощью SecretKey
    private String generateRefreshToken(String email) {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(14).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .expiration(expirationDate)
                .signWith(getSignKey())
                .compact();
    }

    // Получение SecretKey с помощью декодированного секрета JWT
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
