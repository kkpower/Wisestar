package com.tensquare.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName WebFilter
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/12
 **/
@Component
public class WebFilter extends ZuulFilter {

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
        //拿到request上下文
        RequestContext context = RequestContext.getCurrentContext();
        //拿到request作用域
        HttpServletRequest request = context.getRequest();
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)){
            context.addZuulRequestHeader("Authorization",header);
        }
        return null;
    }
}
