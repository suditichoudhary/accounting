package com.nagarro.account.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${application.user.username:defaultValue}")
	private String userName;
	@Value("${application.user.userpassword:defaultValue}")
	private String userPassword;
	@Value("${application.user.adminname:defaultValue}")
	private String adminUserName;
	@Value("${application.user.adminpassword:defaultValue}")
	private String adminPassword;


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	/*
	 * Authentication and Authorization of users on given modules
	 * Home Controller : Home Screen redirects to account.
	 * Account Controller : Responsible of displaying filter screen based upon user.
	 * Statement Controller : Responsible to display the table with account statement.
	 * */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/").hasAnyRole("USER", "ADMIN")
		.antMatchers("/home").hasAnyRole("USER", "ADMIN")
		.antMatchers("/account").hasAnyRole("USER", "ADMIN")
		.antMatchers("/getStatement").hasAnyRole("USER", "ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and()
		.sessionManagement().maximumSessions(1)
		.sessionRegistry(getSessionRegistry())
		.maxSessionsPreventsLogin(true).expiredUrl("/login");		
		http.csrf().disable();
	}

	/*
	 * Admin and User credentials saved in memory for authentication
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication()
		.withUser(adminUserName).password("{noop}"+adminPassword)
		.authorities("ROLE_ADMIN")
		.and().withUser(userName).password("{noop}"+userPassword)
		.authorities("ROLE_USER");
	}

	@Bean
	public SessionRegistry getSessionRegistry(){
		SessionRegistry sessionRegistry=new SessionRegistryImpl();
		return sessionRegistry;
	}

	/*
	 * To invalidate user session after logout
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}


}
