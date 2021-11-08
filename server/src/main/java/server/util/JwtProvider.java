package server.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import server.domain.entity.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwt_key;

    @Value("${jwt.exp}")
    private long jwt_exp;

    public String createAcessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return createToken(user);
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("alg", "HS512");
//        headers.put("typ", "JWT");
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwt_exp);
//
//        return Jwts.builder()
//                .setHeader(headers)
//                .setSubject((user.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwt_key)
//                .compact();
    }

    public String createRefreshToken(User user) {
        return createToken(user);
    }

    public String createToken(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS512");
        headers.put("typ", "JWT");

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwt_exp);

        return Jwts.builder()
                .setHeader(headers)
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwt_key)
                .compact();
    }

    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwt_key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String headerToken) {
        try {
            Jwts.parser().setSigningKey(jwt_key).parseClaimsJws(headerToken);
            return true;
        } catch (SignatureException exception) {
            System.out.println("서명실패 ㅋ");
        } catch (MalformedJwtException exception) {
            System.out.println("내용이 이상 ㅋ");
        } catch (ExpiredJwtException exception) {
            System.out.println("시간초과입니다");
        } catch (IllegalArgumentException exception) {
            System.out.println("문법오류 ㅋ");
        }
        return false;
    }

}
