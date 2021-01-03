package com.tekntime.saml.authorization.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.MetadataGenerator;

public class SAMLMetaDataConfig {
	
	@Value("${saml.keystore}")
	private String SAMLKEYSTORE;
	
	@Value("${saml.keystore.password}")
	private String SAMLKEYSTOREPASSWORD;
	
	@Value("${saml.keystore.user}")
	private String SAMLKEYSTOREUSER;
    
	@Value("${saml.entity.id}")
	private String SAMLENTITYID;
	
	@Value("${saml.discovery}")
	private String SAMLDISCOVERY;
	
    @Bean
    public KeyManager keyManager() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource storeFile = loader.getResource(SAMLKEYSTORE);
        Map<String, String> passwords = new HashMap<String, String>();
        passwords.put(SAMLKEYSTOREUSER, SAMLKEYSTOREPASSWORD);
        return new JKSKeyManager(storeFile, SAMLKEYSTOREPASSWORD, passwords, SAMLKEYSTOREUSER);
    }
	 
    @Bean
    public ExtendedMetadata extendedMetadata() {
	    ExtendedMetadata extendedMetadata = new ExtendedMetadata();
	    extendedMetadata.setIdpDiscoveryEnabled(true);
	    extendedMetadata.setSigningAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
	    extendedMetadata.setSignMetadata(true);
	    extendedMetadata.setEcpEnabled(true);
	    return extendedMetadata;
    }
    
    @Bean
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        metadataGenerator.setEntityId(SAMLENTITYID);
        metadataGenerator.setExtendedMetadata(extendedMetadata());
        metadataGenerator.setIncludeDiscoveryExtension(false);
        metadataGenerator.setKeyManager(keyManager()); 
        return metadataGenerator;
    }
    
    @Bean
    public SAMLDiscovery samlIDPDiscovery() {
        SAMLDiscovery idpDiscovery = new SAMLDiscovery();
        idpDiscovery.setIdpSelectionPath(SAMLDISCOVERY);
        return idpDiscovery;
    }

}
