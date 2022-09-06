package org.loginmodule.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	/**
	 * This method is initializing the authenticationManagerBean.
	 */
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	/**
	 *This method is used to configure api authorities,resister and validate api will be accessible by everyone. 
	 *and all other api will require authorization.
	 */
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().and();
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/api/users/register").permitAll()
		.antMatchers("/api/users/validate").permitAll()
		.anyRequest().authenticated();
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		
		return http.build();
	}
	
	/**This method is used for password encoding
	 * @return the encoded password
	 */
	@Bean
	public PasswordEncoder passEncoder(){
		return new BCryptPasswordEncoder(12);
	}
	
	
}