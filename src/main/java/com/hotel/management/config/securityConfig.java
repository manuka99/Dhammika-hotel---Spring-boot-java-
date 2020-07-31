package com.hotel.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().sameOrigin().and().authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/assets/**", "/Design/**").permitAll().antMatchers("/")
				.permitAll()
				.antMatchers("/menu/**", "/newFood", "/register/**", "/product/**", "/confirm-email" , "/public/**",
						"/forgot-password", "/contact", "/saveContactUs")
				.permitAll().antMatchers().permitAll()
				.antMatchers("/uploadFood").permitAll()
				.antMatchers("/user/**").hasAuthority("MEMBER")
				.antMatchers("/payment/**").hasAuthority("MEMBER")
				.antMatchers("/panel/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/menu").failureUrl("/login?error").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout").deleteCookies("my-remember-me-cookie").permitAll();

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
