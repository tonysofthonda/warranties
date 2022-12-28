package com.honda.hdm.securityapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.*;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${saml.sp}")
	String spEntityId;

	@Value("${saml.metadata.location}")
	String metadataLocation;

	@Value("${saml.idp}")
	String defaultIdp;

	@Value("${saml.keystore.location}")
	String samlKeyStoreLocation;

	@Value("${saml.keystore.alias}")
	String samlKeyStoreAlias;

	@Value("${saml.keystore.password}")
	String samlKeyStorePassword;

	@Value("${frontend.saml-view-url}")
	String frontendSamlViewUrl;

	@Value("${backend.protocol}")
	String backendProtocol;

	@Value("${backend.servername}")
	String backendServername;

	@Value("${server.port}")
	Integer backendPort;

	@Value("${backend.include-port}")
	Boolean includePort;

	@Value("${server.servlet.context-path}")
	String contextPath;
	
    @Autowired
    @Qualifier("saml")
    private SavedRequestAwareAuthenticationSuccessHandler samlAuthSuccessHandler;

    @Autowired
    @Qualifier("saml")
    private SimpleUrlAuthenticationFailureHandler samlAuthFailureHandler;

    @Lazy
    @Autowired
    private SAMLEntryPoint samlEntryPoint;

    @Autowired
    private SAMLLogoutFilter samlLogoutFilter;

    @Autowired
    private SAMLLogoutProcessingFilter samlLogoutProcessingFilter;

    @Autowired
    private SAMLAuthenticationProvider samlAuthenticationProvider;

    @Autowired
    private ExtendedMetadata extendedMetadata;

    @Autowired
    private KeyManager keyManager;

//    public MetadataGenerator metadataGenerator() {
//        MetadataGenerator metadataGenerator = new MetadataGenerator();
//        metadataGenerator.setEntityId(samlAudience);
//        metadataGenerator.setExtendedMetadata(extendedMetadata);
//        metadataGenerator.setIncludeDiscoveryExtension(false);
//        metadataGenerator.setKeyManager(keyManager);
//        return metadataGenerator;
//    }
    
    @Bean
    public SAMLDiscovery samlDiscovery() {
        SAMLDiscovery idpDiscovery = new SAMLDiscovery();
        return idpDiscovery;
    }
    
    @Bean
	public MetadataGenerator metadataGenerator() {
		MetadataGenerator metadataGenerator = new MetadataGenerator();
		metadataGenerator.setEntityId(spEntityId);
		metadataGenerator.setEntityBaseURL(getBaseUrl());
		metadataGenerator.setExtendedMetadata(extendedMetadata);
		metadataGenerator.setIncludeDiscoveryExtension(false);
		metadataGenerator.setKeyManager(keyManager);
		return metadataGenerator;
	}

    @Bean
    public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
        SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
        samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(samlAuthSuccessHandler);
        samlWebSSOProcessingFilter.setAuthenticationFailureHandler(samlAuthFailureHandler);
        return samlWebSSOProcessingFilter;
    }

    @Bean
    public FilterChainProxy samlFilter() throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<>();
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSO/**"),
            samlWebSSOProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/discovery/**"),
            samlDiscovery()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"),
            samlEntryPoint));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"),
            samlLogoutFilter));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SingleLogout/**"),
            samlLogoutProcessingFilter));
        return new FilterChainProxy(chains);
    }

    @Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataGenerator());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf()
        .disable();

        http
        .httpBasic()
        .authenticationEntryPoint(samlEntryPoint);

        http
        .addFilterBefore(metadataGeneratorFilter(), ChannelProcessingFilter.class)
        .addFilterAfter(samlFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(samlFilter(), CsrfFilter.class);

        http
        .logout()
        .logoutSuccessUrl("/");

        http
        .logout()
        .addLogoutHandler((request, response, authentication) -> {
            try {
                response.sendRedirect("/saml/logout");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(samlAuthenticationProvider);
    }
    
    private String getBaseUrl(){
		StringBuilder sb = new StringBuilder();
		if(includePort){
			sb.append(backendProtocol).append("://").append(backendServername).append(":").append(backendPort).append(contextPath);
		} else {
			sb.append(backendProtocol).append("://").append(backendServername).append(contextPath);
		}
		return sb.toString();
	}

}
