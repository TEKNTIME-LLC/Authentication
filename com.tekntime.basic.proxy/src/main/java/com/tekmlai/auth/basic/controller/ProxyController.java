package com.tekmlai.auth.basic.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
	Logger logger = LoggerFactory.getLogger(ProxyController.class);
	
	@Value("${resource.base.auth.url}")
	private String resourceUrl;
	
	@Value("${proxy.auth.host}")
	private String PROXYAUTHHOSTNAME;
	
	@Value("${basic.auth.host}")
	private String BASICAUTHHOSTNAME;
	
	@Value("${basic.auth.app.key}")
	private String APPKEY;
	
	
	
	@GetMapping("/test")
    public Map<String, String> test() {
		logger.info("reached proxy-auth app" );
		Map<String, String> map = new HashMap<String, String>();
		map.put("app", "PROXY-AUTH");
	       
	    return map;
	}

	@GetMapping("/app")
    public Map<String, String> getApp(HttpServletRequest request) {
		logger.info("reached proxy app" );
		Map<String, String> map = new HashMap<String, String>();

		if(! PROXYAUTHHOSTNAME.equalsIgnoreCase(request.getHeader("host"))||
			!"https".equalsIgnoreCase(request.getScheme())
				) {
			map.put("app", "PROXY-AUTH is NOT accessible");
			return map;
	    }
		
		map.put("scheme", request.getScheme());
		map.put("server", request.getServerName());
		map.put("method", request.getMethod());
		map.put("contextpath", request.getContextPath());
		map.put("protocol", request.getProtocol());
		map.put("port", ""+request.getServerPort());
		map.put("LocalName", request.getLocalName());
		map.put("requestURI", request.getRequestURI());
		map.put("queryString", request.getQueryString());
		map.put("cookies", Arrays.toString(request.getCookies()));
		
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 map.put("Parameter Name - "+paramName, "Value - "+request.getParameter(paramName));
		}
		
		
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		
		logger.info("calling url: {}", resourceUrl );
		ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);
		logger.info("response: {}", response.getBody() );

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        map.put("PROXY-AUTH -->",  response.getBody());
        
		return map;
    }
	
	
	@GetMapping("/direct")
    public String  direct(HttpServletRequest request, @RequestParam String appName) throws KeyManagementException, NoSuchAlgorithmException {
		logger.info("reached proxy app" );
		Map<String, String> map = new HashMap<String, String>();

		if(! PROXYAUTHHOSTNAME.equalsIgnoreCase(request.getHeader("host"))||
			!"https".equalsIgnoreCase(request.getScheme())
				) {
			map.put("app", "PROXY-AUTH is NOT accessible");
			return "PROXY-AUTH is NOT accessible";
	    }
		
			
		RestTemplate tlsRestTemplate=restTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("appkey", APPKEY); 
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		String resourceUrlDirect="https://"+BASICAUTHHOSTNAME+"/auth/"+appName;
		
		logger.info("calling url: {}", resourceUrlDirect );
		ResponseEntity<String> response = tlsRestTemplate.exchange(resourceUrlDirect, HttpMethod.POST, requestEntity, String.class);
		logger.info("response: {}", response.getBody() );

               
		return response.getBody();
    }
	
   
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };  
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom()); 
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();   
        HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
        customRequestFactory.setHttpClient(httpClient);
        RestTemplateBuilder builder=new RestTemplateBuilder();
        return builder.requestFactory(() -> customRequestFactory).build();  
    }

}
