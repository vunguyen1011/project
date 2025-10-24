package com.thanglong.project.infrastructure.Security;
import com.thanglong.project.domain.model.UserModel;
import com.thanglong.project.usecase.service.MyUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final   MyUserDetailService myUserDetailService;


    @Value("${jwt.signerKey}")
    private String secretKey;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshExpiration;

    @Value("${jwt.accessTokenExpiration}")
    private long jwtExpiration;

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserModel userModel, long time) {
        MyUserDetail user = MyUserDetail.from(userModel);

        List<String> roles = user.getAuthorities().stream()
                .map(auth -> auth.getAuthority()) // Ví dụ: "ROLE_USER"
                .toList();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("NguyenVu")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(generateTokenId().toString())
                .claim("roles", roles)
                .signWith(getKey())
                .compact();
    }

    public String generateAccessToken(UserModel userModel) {

        return generateToken(userModel, jwtExpiration);
    }

    public String generateRefreshToken(UserModel userModel) {
        return generateToken(userModel, refreshExpiration);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                System.out.println("Token expired");
                return false;
            }
            String username = claims.getSubject();
            return myUserDetailService.loadUserByUsername(username) != null;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class); // Trả về List<String>
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        return extractRoles(token).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public long getRefreshExpiration() {
        return getJwtExpiration();
    }

    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    public Long getRemainTime(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        long currentTime = System.currentTimeMillis();
        return (expirationDate.getTime() - currentTime) / 1000; // Trả về thời gian còn lại tính bằng giây
    }

    public UUID generateTokenId() {
        return UUID.randomUUID();

    }

    public String extractTokenId(String token) {
        return extractClaim(token, Claims::getId);
    }
}
