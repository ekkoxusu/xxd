package cn.su.xxd.server.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnWebApplication
//@ComponentScan(basePackages =)
@EntityScan("cn.su.xxd.xxd.base.entity")
public class OptimalManageAutoConfiguration {

	@Autowired
	private UserDetailsService myUserDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico", "/swagger-ui.html",
					"/webjars/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/configuration/security",
					"/swagger-resources/configuration/ui","/supervise/shutdown");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/login","/securityLogin","/createCode","/error/*","/supervise/**").permitAll().anyRequest().authenticated()
					.and().headers().frameOptions().sameOrigin()
					.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/", true)
					.and().logout().permitAll();
			http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/login");
		}
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
}
