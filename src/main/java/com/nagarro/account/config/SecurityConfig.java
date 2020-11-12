package com.nagarro.account.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
			http.authorizeRequests().antMatchers("/home").permitAll()
			.antMatchers("/account").hasAnyRole("USER", "ADMIN")
			.antMatchers("/statement").hasAnyRole("USER", "ADMIN")
			.antMatchers("/getStatement").hasAnyRole("USER", "ADMIN")
			.anyRequest().authenticated()
			.and().formLogin().permitAll()
			.and().logout().deleteCookies().invalidateHttpSession(true).permitAll()
			.and().sessionManagement().maximumSessions(1).sessionRegistry(getSessionRegistry()).maxSessionsPreventsLogin(false).expiredUrl("/login");
			http.csrf().disable();
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication()
		.withUser("testadmin").password("{noop}adminpassword")
		.authorities("ROLE_ADMIN")
		.and().withUser("testUser").password("{noop}userpassword")
		.authorities("ROLE_USER");
	}
	
	@Bean
	public SessionRegistry getSessionRegistry(){
	    SessionRegistry sessionRegistry=new SessionRegistryImpl();
	    return sessionRegistry;
	}


}
