package org.loginmodule.springboot.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.loginmodule.springboot.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
    /**
     * This method is used to verify the request sent, by validating token.
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request);
		try {
			if(token!=null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				System.out.println(jwtTokenProvider.getUsername(token));
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println(auth);
			}
		}
		catch(CustomException ex) {
			SecurityContextHolder.clearContext();
			response.sendError(ex.getHttpStatus().value(),ex.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}

}

