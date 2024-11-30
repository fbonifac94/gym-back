package com.gym.gym.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.gym.gym.repositories.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

	private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("EstoEsUnaPruebaPruebaPrueba123123123...".getBytes());
	private final Integer TOKEN_VALIDITY_MINUTES = 60;

	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer ";

	private JwtParser jwtParser;

	public JwtUtil() {
		this.jwtParser = Jwts.parser().verifyWith(SECRET_KEY).build();
	}

	public String createToken(UserEntity user) {
		Claims claims = generateClaims(user);
		var now = Instant.now();
		return Jwts.builder().subject(user.getEmail()).claims(claims).issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(TOKEN_VALIDITY_MINUTES, ChronoUnit.MINUTES)))
				.signWith(SECRET_KEY, Jwts.SIG.HS256).compact();
	}

	private Claims generateClaims(UserEntity user) {
		Map<String, String> claimsMap = new HashMap<>();
		claimsMap.put("username", user.getEmail());
		claimsMap.put("userId", user.getId().toString());
		claimsMap.put("isFirstLogin", user.getFirstLogin().toString());
		claimsMap.put("firstName", user.getPerson().getFirstName());
		claimsMap.put("lastName", user.getPerson().getLastName());
		claimsMap.put("role", user.getRole().getName().name());
		claimsMap.put("expirationDateMembership",
				(Objects.isNull(user.getExpireDate())) ? "" : user.getExpireDate().toString());
		Claims claims = Jwts.claims().subject(user.getEmail()).add(claimsMap).build();
		return claims;
	}

	private Claims parseJwtClaims(String token) {
		return jwtParser.parseSignedClaims(token).getPayload();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public Claims resolveClaims(HttpServletRequest request) {
		try {
			String token = resolveToken(request);
			if (token != null) {
				return parseJwtClaims(token);
			}
			return null;
		} catch (ExpiredJwtException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public Boolean validateClaims(Claims claims) throws AuthenticationException {
		return claims.getExpiration().after(new Date());
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private List<String> getRoles(Claims claims) {
		return (List<String>) claims.get("roles");
	}

	public Claims decodeJWT(String jwt) {
		return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt).getPayload();
	}
}
