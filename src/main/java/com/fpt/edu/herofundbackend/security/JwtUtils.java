package com.fpt.edu.herofundbackend.security;

import com.fpt.edu.herofundbackend.dto.auth.AuthenticateResponse;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    public static final String PATH_AUTH_LOGIN = "/api/v1/auth/authenticate/**";
    public static final String PATH_AUTH_REGISTER = "/api/v1/auth/sign-up/**";
    public static final String PATH_ADMIN = "/api/v1/admin/**";
    public static final String PATH_HEROFUND_TRANSACTION = "/api/v1/transactions/**";
    public static final String PATH_HEROFUND_ARTICLES = "/api/v1/articles/**";
    public static final String PATH_HEROFUND_CAMPAIGNS = "/api/v1/campaigns/**";
    public static final String PATH_HEROFUND_CATEGORIES = "/api/v1/categories/**";
    public static final String PATH_HEROFUND_COMMENTS = "/api/v1/comments/**";
    public static final String PATH_HEROFUND_FAQS = "/api/v1/faqs/**";
    public static final String PATH_HEROFUND_CHANNELS = "/api/v1/channels/**";
    public static final String PATH_HEROFUND_SPONSOR = "/api/v1/sponsors/**";
    public static final String PATH_HEROFUND_PAYMENTS = "/api/v1/payments/**";
    public static final String PATH_HEROFUND_UPLOAD = "/api/v1/files/upload/**";

    public static String[] WHITE_LIST = new String[]{
            PATH_AUTH_LOGIN,
            PATH_AUTH_REGISTER,
            PATH_HEROFUND_TRANSACTION,
            PATH_HEROFUND_CAMPAIGNS,
            PATH_HEROFUND_CATEGORIES,
            PATH_HEROFUND_ARTICLES,
            PATH_HEROFUND_COMMENTS,
            PATH_HEROFUND_FAQS,
            PATH_HEROFUND_CHANNELS,
            PATH_HEROFUND_SPONSOR,
            PATH_HEROFUND_PAYMENTS,
            PATH_HEROFUND_UPLOAD,
    };

    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES = "authorities";
    private static final String jwtSigningKey = "secret";

    private final AuthenticateService authenticateService;

    public String extractUsername(String token) {
        try {
            return extractClaims(token, Claims::getSubject);
        }catch (Exception e){
            return null;
        }
    }

    public String extractUsername(HttpServletRequest request) {
        return extractClaims(getTokenHeader(request), Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public AuthenticateResponse createToken(UserDetails userDetails) {
        Date iat = new Date(System.currentTimeMillis());
        Date expiredAccess = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
        Date expiredRefresh = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        Map<String, Object> map = new HashMap<>();
        map.put("profileId", 1);
        map.put("phone", "0969451691");
        String accessToken = Jwts.builder().setClaims(map)
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES, userDetails.getAuthorities())
                .setIssuedAt(iat)
                .setExpiration(expiredAccess)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();

        String refreshToken = Jwts.builder().setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES, userDetails.getAuthorities())
                .setIssuedAt(iat)
                .setExpiration(expiredRefresh)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();

        return AuthenticateResponse.builder()
                .iat(iat)
                .expired(expiredAccess)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES, userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String getTokenHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            return null;
        }
        return authHeader.substring(7);
    }
}
