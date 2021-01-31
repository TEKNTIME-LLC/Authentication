package com.tekmlai.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

@Configuration
public class RouteConfig {
	@Value("${resource.base.auth.url}")
	private String RESOURCEPATH;
	
	@Value("${proxy.auth.host}")
	private String PROXYAUTHHOSTNAME;
	
	@Value("${basic.auth.host}")
	private String BASICAUTHHOSTNAME;
	
	@Value("${basic.auth.app.key}")
	private String APPKEY;
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return  builder.routes()
			      .route("r1", r -> r.host(PROXYAUTHHOSTNAME)
	    	        .and()
	    	        .path(RESOURCEPATH)
	    	        .uri(BASICAUTHHOSTNAME + RESOURCEPATH))
	    	      .route("r2", r -> r.host(PROXYAUTHHOSTNAME)
	    	        .and()
	    	        .path("/myOtherRouting")
	    	        .filters(f -> f.prefixPath("/myPrefix"))
	    	        
	    	        .uri("http://othersite.com")
	    	        .id(APPKEY))
			    .build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

}
