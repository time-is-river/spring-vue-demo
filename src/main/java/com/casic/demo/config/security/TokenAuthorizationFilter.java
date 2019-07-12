/*
package com.casic.demo.config.security;

import com.casic.demo.utils.TokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

*/
/**
 * @author
 * @date 2019/6/21 16:59
 *//*

@Component
public class TokenAuthorizationFilter extends GenericFilterBean {

    private final String HEAN_NAME = "Authorization";
    private final TokenUtils tokenUtils;

    public TokenAuthorizationFilter(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String token = resolveToken((HttpServletRequest) request);

        if(tokenUtils.validateToken(token)){
            Authentication authentication = tokenUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);

        cleanAuthentication();
    }

    */
/**
     * 从请求头解析出token
     * @param request
     * @return token
     *//*

    private String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        logger.info("获取 token:"+token);
        if (token==null||!token.startsWith("Bearer ")) {
            return null;
        } else {
            return token.substring(7);
        }
    }
    private void cleanAuthentication(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
*/
