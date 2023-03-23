package ar.com.llegolaslutz.atencionpsicologica.auth;


import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter { // se encarga de dar acceso a los recursos de la app si el token es valido

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/pacientes").permitAll()
        .antMatchers(HttpMethod.POST, "/api/user").permitAll().antMatchers(HttpMethod.POST, "/api/forgottenPassword").permitAll()
        .antMatchers(HttpMethod.POST, "/api/restorePassword").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/pacientes").hasRole("ADMIN")        // partir dando permisos desde lo ma especifico alo mas generico
//		.antMatchers("/api/pacientes/**").hasRole("ADMIN")    //this commented code will be replace for anottation on each method in controller class
		.anyRequest().authenticated()   //todas  
		.and().cors().configurationSource(corsConfigurationSource());
	}  // the same method goes at SpringSecurityConfiguratio class
	
	

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT", "OPTIONS")); //OPTIONS algunos navegadores mandan el request con este metodo al solicitar el token
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;				//todas las rutas
    }
    
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
    	FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
    	bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  // se le da al filtro una prioridad alta
    	return bean;
    }

	
}
  