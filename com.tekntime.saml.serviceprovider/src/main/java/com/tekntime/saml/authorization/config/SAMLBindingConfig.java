package com.tekntime.saml.authorization.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.saml2.metadata.provider.HTTPMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.processor.HTTPArtifactBinding;
import org.springframework.security.saml.processor.HTTPPAOS11Binding;
import org.springframework.security.saml.processor.HTTPPostBinding;
import org.springframework.security.saml.processor.HTTPRedirectDeflateBinding;
import org.springframework.security.saml.processor.HTTPSOAP11Binding;
import org.springframework.security.saml.processor.SAMLBinding;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.websso.ArtifactResolutionProfile;
import org.springframework.security.saml.websso.ArtifactResolutionProfileImpl;

public class SAMLBindingConfig {
	
	    @Autowired   
	    StaticBasicParserPool parserPool;
	    
	    @Autowired
	    VelocityEngine velocityEngine;
	    

	    @Autowired
	    ExtendedMetadata extendedMetadata;
	    
	    @Autowired 
	    HttpClient httpClient;
		
	    // Bindings
	    private ArtifactResolutionProfile artifactResolutionProfile() {
	        final ArtifactResolutionProfileImpl artifactResolutionProfile = 
	        		new ArtifactResolutionProfileImpl(httpClient);
	        artifactResolutionProfile.setProcessor(new SAMLProcessorImpl(soapBinding()));
	        return artifactResolutionProfile;
	    }
	
	   @Bean
	    public HTTPArtifactBinding artifactBinding(ParserPool parserPool, VelocityEngine velocityEngine) {
	        return new HTTPArtifactBinding(parserPool, velocityEngine, artifactResolutionProfile());
	    }
	 
	    @Bean
	    public HTTPSOAP11Binding soapBinding() {
	        return new HTTPSOAP11Binding(parserPool);
	    }
	    
	    @Bean
	    public HTTPPostBinding httpPostBinding() {
	    		return new HTTPPostBinding(parserPool, velocityEngine);
	    }
	    
	    @Bean
	    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
	    		return new HTTPRedirectDeflateBinding(parserPool);
	    }
	    
	    @Bean
	    public HTTPSOAP11Binding httpSOAP11Binding() {
	    	return new HTTPSOAP11Binding(parserPool);
	    }
	    
	    @Bean
	    public HTTPPAOS11Binding httpPAOS11Binding() {
	    		return new HTTPPAOS11Binding(parserPool);
	    }
	    
	    // Processor
		@Bean
		public SAMLProcessorImpl processor() {
			Collection<SAMLBinding> bindings = new ArrayList<SAMLBinding>();
			bindings.add(httpRedirectDeflateBinding());
			bindings.add(httpPostBinding());
			bindings.add(artifactBinding(parserPool, velocityEngine));
			bindings.add(httpSOAP11Binding());
			bindings.add(httpPAOS11Binding());
			return new SAMLProcessorImpl(bindings);
		}
		
		@Bean
		@Qualifier("idp-ssocircle")
		public ExtendedMetadataDelegate ssoCircleExtendedMetadataProvider()
				throws MetadataProviderException {
			String idpSSOCircleMetadataURL = "https://idp.ssocircle.com/meta-idp.xml";
			HTTPMetadataProvider httpMetadataProvider = new HTTPMetadataProvider(
					new Timer(true), httpClient, idpSSOCircleMetadataURL);
			httpMetadataProvider.setParserPool(parserPool);
			ExtendedMetadataDelegate extendedMetadataDelegate = 
					new ExtendedMetadataDelegate(httpMetadataProvider, extendedMetadata);
			extendedMetadataDelegate.setMetadataTrustCheck(true);
			extendedMetadataDelegate.setMetadataRequireSignature(false);
			new Timer(true).purge();
			return extendedMetadataDelegate;
		}

	    // IDP Metadata configuration - paths to metadata of IDPs in circle of trust
	    // is here
	    // Do no forget to call iniitalize method on providers
	    @Bean
	    @Qualifier("metadata")
	    public CachingMetadataManager metadata() throws MetadataProviderException {
	        List<MetadataProvider> providers = new ArrayList<MetadataProvider>();
	        providers.add(ssoCircleExtendedMetadataProvider());
	        return new CachingMetadataManager(providers);
	    }

}
