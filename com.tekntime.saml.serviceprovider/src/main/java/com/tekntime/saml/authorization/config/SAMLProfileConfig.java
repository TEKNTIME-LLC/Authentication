package com.tekntime.saml.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.websso.SingleLogoutProfile;
import org.springframework.security.saml.websso.SingleLogoutProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfile;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.security.saml.websso.WebSSOProfileECPImpl;
import org.springframework.security.saml.websso.WebSSOProfileImpl;

public class SAMLProfileConfig {
	
	   // Initialization of OpenSAML library
    @Bean
    public static SAMLBootstrap samlBootstrap() {
        return new SAMLBootstrap();
    }
 
    // Logger for SAML messages and events
    @Bean
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }
 
    // SAML 2.0 WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }
 
    // SAML 2.0 Holder-of-Key WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }
 
    // SAML 2.0 Web SSO profile
    @Bean
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }
 
    // SAML 2.0 Holder-of-Key Web SSO profile
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }
 
    // SAML 2.0 ECP profile
    @Bean
    public WebSSOProfileECPImpl ecpprofile() {
        return new WebSSOProfileECPImpl();
    }
 
    @Bean
    public SingleLogoutProfile logoutprofile() {
        return new SingleLogoutProfileImpl();
    }

}
