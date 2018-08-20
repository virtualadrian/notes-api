package com.notes.security.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String PROD_PROFILE = "prod";

    @Autowired
    private Environment environment;

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Value("${spring.security.enabled}")
    private Boolean securityEnabled;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        boolean prodProfileActive = Arrays.asList(environment.getActiveProfiles())
            .contains(PROD_PROFILE);
        if (securityEnabled || prodProfileActive) {
            http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/account/register/**", "/account/password/**", "/note/shared/**").permitAll()
                .anyRequest().authenticated();
        } else {
            http.authorizeRequests().anyRequest().permitAll();
        }
    }
}
