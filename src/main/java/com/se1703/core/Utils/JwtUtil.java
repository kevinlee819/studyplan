package com.se1703.core.Utils;

import com.se1703.core.entity.TokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author leekejin
 * @Date 2020-9-10 16:04:01
 */
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String SIGNING_KEY = "033VTs";

    private static String generateToken(Map<String, Object> claims) {
        // 有效期:如1小时,60分钟*60秒*1000毫秒=3600000L;
        // 7天
        Long expiration = 25_200_000L;
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("leekejin")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    /**
     * 生成token
     */
    public static String generate(TokenEntity entity) throws Exception {
        Map<String, String> map = BeanUtils.describe(entity);
        Map<String, Object> claims = new HashMap<>(map);
        return String.format("Bearer %s", generateToken(claims));
    }

    /**
     * 解析token
     */
    public static TokenEntity validation(String token) throws Exception {
        Map<String, Object> claims = getClaimsFromToken(token.replace("Bearer ", ""));
        if (claims == null) {
            return null;
        }
        TokenEntity tokenEntity = new TokenEntity();
        BeanUtils.populate(tokenEntity, claims);
        return tokenEntity;
    }
}
