package com.tenquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @ClassName CreateJwt 加密
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/7
 **/
public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .setExpiration(new Date(new Date().getTime()+60000))
                .claim("role","admin");
        System.out.println(builder.compact());
    }
}
