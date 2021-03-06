package com.casic.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class TokenUtils {

    //Secret密钥
    private final String SECRET = "auth_chm222";

    //token有效期（分钟）
    private static final long VALIDATE_MINUTE = 10080;

    //加密算法
    private static  Algorithm algorithm;

    public TokenUtils() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(SECRET);
    }

    /**
     * 根据用户信息生成token
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        Date now = Date.from(Instant.now());
        Date expiration = Date.from(ZonedDateTime.now().plusMinutes(VALIDATE_MINUTE).toInstant());

        //create jwt
        String jwt = JWT.create()
                .withClaim("authorities", authorities)
                .withSubject(authentication.getName())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
        return jwt;
    }

    /**
     * 根据用户信息生成token
     * @param user
     * @return
     */
    public static String generateToken(UserDetails user) {
        Date now = Date.from(Instant.now());
        Date expiration = Date.from(ZonedDateTime.now().plusMinutes(VALIDATE_MINUTE).toInstant());

        //create jwt
        String jwt = JWT.create()
                .withIssuer("auth0")
                .withSubject(user.getUsername())
                .withClaim("username", user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
        return jwt;
    }

    /**
     * 认证token有效性
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 从token中解析中用户信息
     * @param token
     * @return
     */
    /*public Authentication getAuthentication(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);
        String authorityString = decodedJWT.getClaim("authorities").asString();

        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        if(!StringUtils.isEmpty(authorityString)){
            authorities = Arrays.asList(authorityString.split(","))
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        }

        User principal = new User(decodedJWT.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    */

    /**
     * 根据 token 获取 用户名称信息
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String authorityString = decodedJWT.getClaim("authorities").asString();

        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        if(!StringUtils.isEmpty(authorityString)){
            authorities = Arrays.asList(authorityString.split(","))
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        }

        User principal = new User(decodedJWT.getSubject(), "", authorities);

        return principal.getUsername();
    }

}