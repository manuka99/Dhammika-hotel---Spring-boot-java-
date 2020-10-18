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

import com.hotel.management.util.CommonConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().sameOrigin().and().authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/assets/**", "/Design/**").permitAll().antMatchers("/")
				.permitAll()
				.antMatchers("/booking/**", "/menu/**", "/reviewPaypalPayment/**", "/executePaypalPayemnt", "/newFood", "/login/**", "/register/**", "/product/**", "/confirm-email/**" , "/public/**",
						"/forgot-password", "/contact", "/saveContactUs")
				.permitAll().antMatchers().permitAll()
				.antMatchers("/user/**", "/payment/**").hasAuthority(CommonConstants.ROLE_MEMBER)
				//public panel
				.antMatchers("/panel", "/panel/about", "/panel/notifications/**", "/panel/navigation/**", "/panel/user/**").hasAnyAuthority(CommonConstants.ROLE_ADMIN, CommonConstants.ROLE_FOOD_MANAGER, CommonConstants.ROLE_ORDER_MANAGER, CommonConstants.ROLE_COMPLAIN_MANAGER)
				
				//panel admin
				.antMatchers("/panel/accounts/**", "/panel/roles/**").hasAuthority(CommonConstants.ROLE_ADMIN)
				
				//panel complain manager
				.antMatchers("/panel/complains/**", "/panel/contacts/**", "/panel/mails/**").hasAnyAuthority(CommonConstants.ROLE_ADMIN, CommonConstants.ROLE_COMPLAIN_MANAGER)
				
				//panel order manager
				.antMatchers("/panel/currency/**", "/panel/delivery/**", "/panel/feedbacks/**", "/panel/orders/**", "/panel/product-analysis/**", "/panel/purchase-analysis/**", "/panel/orders-analysis/**").hasAnyAuthority(CommonConstants.ROLE_ADMIN, CommonConstants.ROLE_ORDER_MANAGER)
				
				//panel food manager
				.antMatchers("/panel/products/**", "/panel/feedbacks/**").hasAnyAuthority(CommonConstants.ROLE_ADMIN, CommonConstants.ROLE_FOOD_MANAGER)
				
				.anyRequest().hasAuthority(CommonConstants.ROLE_ADMIN)
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
