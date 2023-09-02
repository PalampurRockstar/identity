package com.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.model.category.ClaimType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.var;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static com.common.Constants.*;

public class Jwt {
    public static <T> String createToken(int timeout, T claim, ClaimType type) {
        var now = OffsetDateTime.now(ZoneOffset.ofHours(APP_ZONE_OFFSET));
        Claims claims = Jwts.claims();
        Map<String, Object> claimMap=convertObjectToHashMap(claim);
        claims.putAll(claimMap);
        return Jwts.builder()
                .claim("info", claims)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +timeout))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    public static String decodeJwt(String jwtToken){
        return Optional.of(jwtToken)
                .map(jwt->jwt.split("\\."))
                .filter(parts->parts.length==3)
                .map(parts-> Base64.getDecoder()
                        .decode(parts[1]))
                .map(bs->new String(bs, StandardCharsets.UTF_8))
                .orElseGet(()->null);
    }
    public static boolean isJwtExpired(Long expirationTimeMillis) {
        if (expirationTimeMillis == null) return false;
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis > expirationTimeMillis * 1000;
    }
    public static boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .setAllowedClockSkewSeconds(JWT_SKEWING)
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static  <T> T readClaim(String claims,TypeReference<T> ref) {
        System.out.println(claims);
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        try {
            return om.readValue(claims,ref);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, Object> convertObjectToHashMap(Object obj)  {
        Map<String, Object> hashMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        ObjectNode objectNode = om.valueToTree(obj);
        objectNode.fields().forEachRemaining(entry -> hashMap.put(entry.getKey(), entry.getValue()));
        return hashMap;
    }
}
