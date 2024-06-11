package com.nichoscode.sportyshoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nichoscode.sportyshoes.exceptions.CustomAuthenticationFailureHandler;
import com.nichoscode.sportyshoes.service.CustomUserDetailService;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	 @Autowired
	    private CustomUserDetailService customUserDetailService;
	 
	 @Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
				.authorizeHttpRequests((requests) -> requests
					.requestMatchers("/admin/**").hasRole("ADMIN")
	                .requestMatchers("/cart", "shop/addToCart").authenticated()
	                .anyRequest().permitAll()
				)
				.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/shop")
					.usernameParameter("email")
					.passwordParameter("password")
					.failureHandler(customAuthenticationFailureHandler)	
				)
				.logout((logout) -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.permitAll());

			return http.build();
		}
	 
	 
	 
	 @Bean
	  public UserDetailsService userDetailsService() {
	    return customUserDetailService;
	  }

	  @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	  @Bean
	  public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(customUserDetailService);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	  }
//	 @Bean
//		public UserDetailsService userDetailsService() {
//			UserDetails user =
//				 User.withDefaultPasswordEncoder()
//					.username("admin@mail.com")
//					.password("admin")
//					.roles("ADMIN")
//					.build();
//
//			return new InMemoryUserDetailsManager(user);
//		}
	 
//	 @Bean                                                             
//		public UserDetailsService userDetailsService() throws Exception {
//			// ensure the passwords are encoded properly
//			UserBuilder users = User.withDefaultPasswordEncoder();
//			InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//			manager.createUser(users.username("user@mail.com").password("password").roles("USER").build());
//			manager.createUser(users.username("admin@mail.com").password("password").roles("USER","ADMIN").build());
//			return manager;
//		}

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	http
//        .authorizeHttpRequests(authorize -> authorize
//            .antMatchers("/","shop","/login").permitAll()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//        )
//        .formLogin(form -> form
//            .loginPage("/login")
//            .defaultSuccessUrl("/", true)
//            .permitAll()
//        )
//        .logout(logout -> logout
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login?logout")
//            .permitAll()
//        ); 
//        return http.build();
//    }
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorize -> authorize
//                .anyRequest().permitAll()
//            );
//        
//        return http.build();
//    }
    
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
//    }
    
    
}

