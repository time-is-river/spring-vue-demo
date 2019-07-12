package com.casic.demo.config.security;

import com.casic.demo.service.security.JwtSysUserDetailsServiceImpl;
import com.casic.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * @date 2019/6/25 9:06
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtSysUserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    private final String TOKEN_HEAD = "Authorization";

    private final String TOKEN_START_STR = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpServletResponse.SC_OK);
            //当判定为预检请求后，设定允许请求的方法
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
            //当判定为预检请求后，设定允许请求的头部类型
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token");
            // 预检有效保持时间
            response.addHeader("Access-Control-Max-Age", "1");
            chain.doFilter(request, response);
        } else {
            String authHeader = request.getHeader(TOKEN_HEAD);
            if (authHeader != null && authHeader.startsWith(TOKEN_START_STR)) {
                final String authToken = authHeader.substring(TOKEN_START_STR.length());
                String username = tokenUtils.getUsernameFromToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (tokenUtils.validateToken(authToken)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token");
                    }
                }
            }
            chain.doFilter(request, response);
        }
    }
}
