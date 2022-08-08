package com.nkrasnovoronka.todoapp.security.jwt;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = jwtUtils.getJwtFromHeader(req);
      if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {
        String emailFromJwt = jwtUtils.getUserEmailFromJwt(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromJwt);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Cannot set authentication", e);
    }
    filterChain.doFilter(req, resp);
  }
}
