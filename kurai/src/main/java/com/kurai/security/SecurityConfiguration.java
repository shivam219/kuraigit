package com.kurai.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.kurai.filter.JWTAuthenticationEntryPoint;
import com.kurai.filter.JsonWebTokenGenerationFilter;

@Configuration
@EnableWebSecurity
@SuppressWarnings("all")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityConfiguration {

	@Autowired
	private JWTAuthenticationEntryPoint unAuthorizedHandler;

	@Autowired
	private JsonWebTokenGenerationFilter jsonWebTokenGenerationFilter;

	@Value("${freeurl}")
	private String freeurl;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		String[] freeUrls = freeurl.split(",");

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOriginPatterns(Collections.singletonList("*"));
					config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setExposedHeaders(Collections.singletonList("Authorization"));
					config.setAllowCredentials(true);
					config.setMaxAge(3600L);
					return config;
				}))
				.authorizeHttpRequests(auth -> auth.requestMatchers(freeUrls).permitAll().anyRequest().authenticated())
				.exceptionHandling(handler -> handler.authenticationEntryPoint(unAuthorizedHandler))
				.addFilterBefore(jsonWebTokenGenerationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public Optional<String> auditorProvider() {
//		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getName);
//	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImp();
	}

}