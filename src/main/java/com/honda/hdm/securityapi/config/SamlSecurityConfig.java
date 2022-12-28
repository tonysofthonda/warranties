package com.honda.hdm.securityapi.config;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ResourceBackedMetadataProvider;
import org.opensaml.util.resource.ClasspathResource;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.saml.*;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.context.SAMLContextProviderLB;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.processor.*;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
//import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.*;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.honda.hdm.securityapi.service.SamlUserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@Configuration
public class SamlSecurityConfig {

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


    @Bean(initMethod = "initialize")
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }
    
    @Bean
	public SAMLUserDetailsService samlUserDetailsService(){
		return new SamlUserDetailsService();
	}

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
    	SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
		samlAuthenticationProvider.setUserDetails(samlUserDetailsService());
		samlAuthenticationProvider.setForcePrincipalAsString(false);
		return samlAuthenticationProvider;
    }

//    @Bean
//    public SAMLContextProviderImpl contextProvider() {
//        return new SAMLContextProviderImpl();
//    }
    
    @Bean
	public SAMLContextProviderImpl contextProvider() {
		SAMLContextProviderLB samlContextProviderLB = new SAMLContextProviderLB();
		samlContextProviderLB.setScheme(backendProtocol);
		samlContextProviderLB.setServerName(backendServername);
		if(includePort){
			samlContextProviderLB.setServerPort(backendPort);
		}
		samlContextProviderLB.setIncludeServerPortInRequestURL(includePort);
		samlContextProviderLB.setContextPath(contextPath);
		return samlContextProviderLB;
	}

    @Bean
    public static SAMLBootstrap samlBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    @Bean
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }

    @Bean
    @Qualifier("hokWebSSOprofileConsumer")
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }

    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean
    public WebSSOProfileECPImpl ecpProfile() {
        return new WebSSOProfileECPImpl();
    }

    @Bean
    public SingleLogoutProfile logoutProfile() {
        return new SingleLogoutProfileImpl();
    }

    @Bean
    public KeyManager keyManager() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource storeFile = loader.getResource(samlKeyStoreLocation);
        Map<String, String> passwords = new HashMap<>();
        passwords.put(samlKeyStoreAlias, samlKeyStorePassword);
        return new JKSKeyManager(storeFile, samlKeyStorePassword, passwords, samlKeyStoreAlias);
    }

    @Bean
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {
        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
        webSSOProfileOptions.setIncludeScoping(false);
        return webSSOProfileOptions;
    }

    @Bean
    public SAMLEntryPoint samlEntryPoint() {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
        return samlEntryPoint;
    }
    
//    @Bean
//	public SAMLEntryPoint samlEntryPoint() {
//		SAMLEntryPoint samlEntryPoint = new SamlWithRelayStateEntryPoint(frontendSamlViewUrl);
//		samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
//		return samlEntryPoint;
//	}

    @Bean
    public ExtendedMetadata extendedMetadata() {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setIdpDiscoveryEnabled(false);
        extendedMetadata.setSignMetadata(false);
        return extendedMetadata;
    }
       
    @Bean
	public ExtendedMetadataDelegate idpMetadata()
			throws MetadataProviderException, ResourceException {

		Timer backgroundTaskTimer = new Timer(true);

		ResourceBackedMetadataProvider resourceBackedMetadataProvider =
				new ResourceBackedMetadataProvider(backgroundTaskTimer, new ClasspathResource(metadataLocation));

		resourceBackedMetadataProvider.setParserPool(parserPool());

		ExtendedMetadataDelegate extendedMetadataDelegate =
				new ExtendedMetadataDelegate(resourceBackedMetadataProvider , extendedMetadata());
		extendedMetadataDelegate.setMetadataTrustCheck(true);
		extendedMetadataDelegate.setMetadataRequireSignature(false);
		return extendedMetadataDelegate;
	}
    
    @Bean
    @Qualifier("metadata")
    public CachingMetadataManager metadata() throws MetadataProviderException, ResourceException {
        List<MetadataProvider> providers = new ArrayList<>(); 
        providers.add(idpMetadata());
        CachingMetadataManager metadataManager = new CachingMetadataManager(providers);
        metadataManager.setDefaultIDP(defaultIdp);
        return metadataManager;
    }

    @Bean
    @Qualifier("saml")
    public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successRedirectHandler.setDefaultTargetUrl("/home");
        return successRedirectHandler;
    }
    
//    @Bean
//    @Qualifier("saml")
//	public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
//		SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler =
//				new CustomSamlRelayStateSuccessHandler();
//		return successRedirectHandler;
//	}

    @Bean
    @Qualifier("saml")
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setUseForward(true);
        failureHandler.setDefaultFailureUrl("/error");
        return failureHandler;
    }

    @Bean
    public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
        SimpleUrlLogoutSuccessHandler successLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        successLogoutHandler.setDefaultTargetUrl("/");
        return successLogoutHandler;
    }

    @Bean
    public SecurityContextLogoutHandler logoutHandler() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.setClearAuthentication(true);
        return logoutHandler;
    }

    @Bean
    public SAMLLogoutProcessingFilter samlLogoutProcessingFilter() {
        return new SAMLLogoutProcessingFilter(successLogoutHandler(), logoutHandler());
    }

    @Bean
    public SAMLLogoutFilter samlLogoutFilter() {
        return new SAMLLogoutFilter(successLogoutHandler(),
            new LogoutHandler[] { logoutHandler() },
            new LogoutHandler[] { logoutHandler() });
    }

    @Bean
    public HTTPPostBinding httpPostBinding() {
        return new HTTPPostBinding(parserPool(), VelocityFactory.getEngine());
    }
    
    @Bean
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
        return new HTTPRedirectDeflateBinding(parserPool());
    }
    
    @Bean
    public SAMLProcessorImpl processor() {
        ArrayList<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(httpRedirectDeflateBinding());
        bindings.add(httpPostBinding());
        return new SAMLProcessorImpl(bindings);
    }
    
}
