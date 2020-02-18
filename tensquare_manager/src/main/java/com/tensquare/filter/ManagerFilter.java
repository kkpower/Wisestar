package com.tensquare.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ManagerFilter
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/12
 **/

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre"; //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;   //优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;    //过滤器开关
    }

    @Override
    public Object run() throws ZuulException {

        //得到request上下文
        RequestContext context = RequestContext.getCurrentContext();

        //得到request作用域
        HttpServletRequest request = context.getRequest();

        if ("OPTIONS".equals(request.getMethod())){
            return null;
        }

        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login")>0){
            System.out.println("登录页面"+url);
            return null;
        }
        //拿到请求头信息
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)){
            if (header.startsWith("Bearer ")){
                String token = header.substring(7);
                //解析token 拿到角色信息
                Claims claims = null;
                try {
                    claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if ("admin".equals(roles)){
                        //把请求头转发下去并放行
                        context.addZuulRequestHeader("Authorization",header);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    context.setSendZuulResponse(false);//终止运行
                }

            }
        }
        context.setSendZuulResponse(false);//终止运行
        context.setResponseStatusCode(403);
        context.setResponseBody("权限不足！");
        context.getResponse().setContentType("application/json;charset=utf-8");
        return null;
    }
}
