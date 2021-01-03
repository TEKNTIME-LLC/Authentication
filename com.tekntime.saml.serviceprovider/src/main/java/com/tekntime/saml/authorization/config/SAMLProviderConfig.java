package com.tekntime.saml.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.context.SAMLContextProviderImpl;

import com.tekntime.saml.authorization.service.SAMLUserDetailsServiceImpl;

public class SAMLProviderConfig {
	
    @Autowired
    private SAMLUserDetailsServiceImpl samlUserDetailsServiceImpl;
	

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
        samlAuthenticationProvider.setUserDetails(samlUserDetailsServiceImpl);
        samlAuthenticationProvider.setForcePrincipalAsString(false);
        return samlAuthenticationProvider;
    }
 

    @Bean
    public SAMLContextProviderImpl contextProvider() {
        return new SAMLContextProviderImpl();
    }
 
 

}
