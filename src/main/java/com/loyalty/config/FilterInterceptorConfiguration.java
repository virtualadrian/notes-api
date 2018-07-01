package com.notes.config;

import com.notes.web.interceptor.PerformanceInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterInterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new PerformanceInterceptor()).addPathPatterns("/**");
    }

    // FILTERS
//    @Bean
//    public FilterRegistrationBean apiKeyFilter() {
//        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
//
//        filterRegBean.setFilter(new ApiKeyFilter());
//
//        filterRegBean.addUrlPatterns("/*");
//        filterRegBean.setEnabled(Boolean.TRUE);
//        filterRegBean.setName("API KEY FILTER");
//        filterRegBean.setAsyncSupported(Boolean.TRUE);
//
//        return filterRegBean;
//    }
}
