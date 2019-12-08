package com.msh.mrfix.security;

import com.msh.mrfix.helpers.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {

        //Si no contiene security token o no empieza por Bearer lo filtra
        if (!existJWT(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        //Desencripta el token para obtener la informacion en el
        Claims claims = this.validateJWT(request);

        if (claims.get("rol") != null) {
            this.setUpSpringAuthentication(claims);
            filterChain.doFilter(request, response);
        } else {
            SecurityContextHolder.clearContext();
        }
    }

    private boolean existJWT(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION_KEY);
        if (authenticationHeader == null || !authenticationHeader.startsWith(SecurityConstants.TOKEN_BEARER_PREFIX))
            return false;
        return true;
    }

    private Claims validateJWT(HttpServletRequest req) {
        String jwToken = req.getHeader("Authorization").replace(SecurityConstants.TOKEN_BEARER_PREFIX, "");
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SIGNING_KEY)
                .parseClaimsJws(jwToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        List<String> authorities = (List<String>) claims.get("rol");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null
                , authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
