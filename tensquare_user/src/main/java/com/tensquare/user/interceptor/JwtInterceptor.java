package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName JwtInterceptor
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/8
 **/

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        //获取头信息
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)){
            //如果包含有Authorization头信息，就对其进行解析
            if (authHeader.startsWith("Bearer ")){
                //得到token
                String token = authHeader.substring(7);
                //对令牌进行验证
                Claims claims = null;
                try {
                    claims = jwtUtil.parseJWT(token);
                } catch (Exception e) {
                   throw new RuntimeException("令牌不正确");
                }
                String role = (String) claims.get("roles");
                if (StringUtils.isNotBlank(role) && "user".equals(role)){
                    request.setAttribute("claims_user",token);
                }
                if (StringUtils.isNotBlank(role) && "admin".equals(role)){
                    request.setAttribute("claims_admin",token);
                }
            }
        }

        return true;
    }
}
