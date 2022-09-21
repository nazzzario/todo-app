package com.nkrasnovoronka.todoapp.security.jwt;

import com.nkrasnovoronka.todoapp.dto.auth.ResponseJwt;
import com.nkrasnovoronka.todoapp.model.RefreshToken;
import com.nkrasnovoronka.todoapp.security.AppUserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtUtils {

  @Value("${todoapp.app.jwt-secret}")
  private String jwtSecret;

  @Value("${todoapp.app.jwt-expiration-ms}")
  private int expirationInMs;

  public String generateJwtToken(UserDetails userDetails) {
    return generateJwtFromUsername(userDetails.getUsername());
  }

  public String generateTokenFromEmail(String email) {
    return Jwts.builder().setSubject(email).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + expirationInMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserEmailFromJwt(String jwt) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException eje) {
      log.error("{} is expired", token);
    } catch (SignatureException se) {
      log.error("{} has invalid signature", token);
    } catch (MalformedJwtException mje) {
      log.error("{} is not Jwt token", token);
    } catch (UnsupportedJwtException uje) {
      log.error("{} token not representing a Claims JWS", token);
    } catch (IllegalArgumentException iae) {
      log.error("Invalid argument as token");
    }
    return false;
  }

  private String generateJwtFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + expirationInMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getJwtFromHeader(HttpServletRequest req) {
    var authHeader = req.getHeader("Authorization");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }

  public ResponseJwt buildJwtResponse(AppUserDetailsImpl userDetails, RefreshToken refreshToken) {
    var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return new ResponseJwt(userDetails.getId(), generateJwtToken(userDetails), refreshToken.getToken(),
        userDetails.getUsername(), roles);
  }
}
