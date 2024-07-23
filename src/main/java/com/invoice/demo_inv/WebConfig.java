// package com.invoice.demo_inv;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @RequiredArgsConstructor
// @EnableWebSecurity
// public class WebConfig {
// 	@Bean
// 	public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
// 		http
// 		.cors().disable()
// 		.csrf().disable()
// 		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Authentication object is removed when the request has been processed
// 		.and()
// 		.formLogin().disable()
// 		.authorizeHttpRequests(
// 				(authz) -> authz
// 						.antMatchers("/*").permitAll()
						
// 						// .anyRequest().authenticated()
// 		).exceptionHandling();

//         return http.build();
//     } 
// }
