package esi.project.ils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override 
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }
    
    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    } 
        
    @Override 
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth").authenticated()
                .antMatchers(HttpMethod.POST, "/request/loan").hasAuthority("BORROWER")
                .antMatchers(HttpMethod.PUT, "/request/loan/**").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/request/loan/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/request/loan/**}").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/loan").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/loan/borrower/**").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/loan/material/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/loan/status/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/request/extension").hasAuthority("BORROWER")
                .antMatchers(HttpMethod.PUT, "/request/extension/**").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/request/extension/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/request/extension/**}").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/extension").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/extension/borrower/**").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/extension/material/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/requests/extension/status/**").hasAuthority("LIBRARIAN")
                .antMatchers("/user/**").hasAnyAuthority("BORROWER", "LIBRARIAN")
                .antMatchers("/users/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/material/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.PUT, "/material/**").hasAuthority("LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/material/**").hasAuthority("LIBRARIAN")
                .antMatchers("/reservation/**").hasAuthority("LIBRARIAN")
                .antMatchers("/reservations/**").hasAuthority("LIBRARIAN")
                .antMatchers("/").permitAll()
        .and().formLogin().disable();
    }

}
