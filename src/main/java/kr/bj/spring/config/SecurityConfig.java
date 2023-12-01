package kr.bj.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//http.csrf().disable();
		http.formLogin()		
			.loginPage("/member/login") 
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			//.passwordParameter("password")
			.failureUrl("/member/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/");
		
		 http.authorizeRequests()
	         
	         .mvcMatchers("/", "/member/**", "/item/**", "/images/**").permitAll()
	         .mvcMatchers("/admin/**").hasRole("ADMIN")
	         .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
	         .mvcMatchers("/fragments/**").permitAll()
	         .anyRequest().authenticated();


		 http.exceptionHandling()
         .authenticationEntryPoint(new CustomEntryPoint())
         ;

		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
