package com.wayne.usermanage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SessionFilter implements Filter {

    protected Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    // 不登录也可以访问的资源
    private static Set<String> GreenUrlSet = new HashSet<>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        GreenUrlSet.add("/toRegister"); //去注册
        GreenUrlSet.add("/toLogin"); // 去登录
        GreenUrlSet.add("/login"); // 登录
        GreenUrlSet.add("/loginout"); // 登出
        GreenUrlSet.add("/register"); // 注册
        GreenUrlSet.add("/verified"); // 验证
    }

    @Override
    public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sRequest;
        String uri = request.getRequestURI();
        sResponse.setCharacterEncoding("UTF-8"); // 设置响应编码格式
        sResponse.setContentType("text/html;charset=UTF-8"); // 设置响应编码格式
        if(uri.endsWith(".js")
                || uri.endsWith(".css")
                || uri.endsWith(".jpg")
                || uri.endsWith(".gif")
                || uri.endsWith(".png")
                || uri.endsWith(".ico")) {
            logger.debug("security filter, pass, " + request.getRequestURI());
            filterChain.doFilter(sRequest, sResponse);
            return;
        }

        System.out.println("request uri is : "+ uri);
        //不处理指定的action, jsp
        if(GreenUrlSet.contains(uri) || uri.contains("/verified/")) {
            logger.debug("security filter, pass, " + request.getRequestURI());
            filterChain.doFilter(sRequest, sResponse);
            return;
        }

        String id = (String)request.getSession().getAttribute(WebConfiguration.LOGIN_KEY);
        if(StringUtils.isEmpty(id)) {
            String html = "<script type=\"text/javascript\">window.location.href=\"/toLogin\"</script>";
            sResponse.getWriter().write(html);
        } else {
            filterChain.doFilter(sRequest,sResponse);
        }
    }

    @Override
    public void destroy() { }
}
