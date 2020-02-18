package com.tenquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @ClassName ParseJwt 解析
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/7
 **/
public class ParseJwt {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1ODEwNzY0MDIsImV4cCI6MTU4MTA3NjQ2Mn0.OVCBxFcMG-u9d6Sek-9am3KhOidmDxLlkk5oC0nThco";
        Claims claims = Jwts.parser()
                .setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("用户id:"+claims.getId());
        System.out.println("用户名:"+claims.getSubject());
        System.out.println("登录时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色:"+claims.get("role"));
    }
}
