package com.notes.web.filter;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

@Component
@Order(1)
public class ApiKeyFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
        System.out.println("=== API KEY FILTER ===");
        System.out.println("---->    INIT    <----");
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String vpsApiKey = request.getHeader("X-API-KEY");

        // TODO: Actually Check API Keys
//        if (StringUtils.isBlank(vpsApiKey) ||
//            !vpsApiKey.equalsIgnoreCase("3^_X-sRaE_Ii6I02O^z1JizgP}zezvq-.D/`N!GwuEY@;+7$-F-Q2 ,}CpdSV*.U") ) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("===   API KEY FILTER  ===");
        System.out.println("---->    DESTROY    <----");
    }
}
