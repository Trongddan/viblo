package com.example.viablo.jwt;

import com.example.viablo.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenUtils {
    private static final long EXPIRE_DURATION=84600;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    @Value("${app.jwt.secret}")
    private String jwtSecurity;
    public String generateToken(User user){
        return Jwts.builder().setSubject(user.getId()+ ","+ user.getUsername())
                .claim("roles",user.getRoles().toString())
                .setIssuer("danken")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512,jwtSecurity)
                .compact();
    }
    public boolean ValidateToken(String Token){
        try{
            Jwts.parser().setSigningKey(jwtSecurity).parseClaimsJws(Token);
            return true;
        }catch (ExpiredJwtException e){
            logger.error("Jwt expire",e);
        }
        catch (IllegalArgumentException e){
            logger.error("Jwt not contain whiteSpace...",e);
        }
        catch (MalformedJwtException e){
            logger.error("Jwt form error",e);
        }
        catch (UnsupportedJwtException e){
            logger.error("Jwt not supported",e);
        }
        catch (SignatureException e){
            logger.error("sign validate faild",e);
        }
        return false;
}
    public String getSubject(String Token){
        return parseClaims(Token).getSubject();
    }
    public Claims parseClaims(String token){
        return Jwts.parser().setSigningKey(jwtSecurity).parseClaimsJws(token).getBody();
    }

}
