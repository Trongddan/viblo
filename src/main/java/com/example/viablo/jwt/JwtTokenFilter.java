package com.example.viablo.jwt;

import com.example.viablo.entity.Role;
import com.example.viablo.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired private JwtTokenUtils jwtTokenUtils;

    private boolean hasAuthorization(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }
    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }
    private UserDetails getUserDetail(String token){
        User userDetail = new User();

        Claims claims= jwtTokenUtils.parseClaims(token);
        String claimsRole = (String) claims.get("roles");
        claimsRole=claimsRole.replace("[","").replace("]","");
        String[] roleList = claimsRole.split(",");

        for(String arole:roleList){
            userDetail.addRole(new Role(arole));
        }

        String Subject = (String) claims.get(Claims.SUBJECT);
        String[] subArray = Subject.split(",");
        userDetail.setId(Integer.parseInt(subArray[0]));
        userDetail.setUsername(subArray[1]);
        return userDetail;
    }
    private void setAuthenticationContex(String token, HttpServletRequest request){
        UserDetails userDetails = getUserDetail(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,"",userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!hasAuthorization(request)){
            filterChain.doFilter(request,response);
            return;
        }
        String token = getAccessToken(request);
        if(!jwtTokenUtils.ValidateToken(token)){
            filterChain.doFilter(request,response);
            return;
        }
        setAuthenticationContex(token,request);
        filterChain.doFilter(request,response);
    }
}
