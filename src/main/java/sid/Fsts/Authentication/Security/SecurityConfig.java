package sid.Fsts.Authentication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sid.Fsts.Authentication.Filter.JwtAuthenticationFilter;
import sid.Fsts.Authentication.Filter.JwtAuthorizationFilter;
import sid.Fsts.Authentication.Service.AccountService;
import sid.Fsts.Authentication.entities.User;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //il faut desactiver le csrf dans stateless
        http.csrf().disable();
        //dire a spring que je vais utiliser l authenticaton stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/produits/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveProduct/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/**").hasAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        // http.formLogin();



      //dire au spring je vais ajouter un filter
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
      http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               User user= accountService.loadUserByUsername(username);
                Collection<GrantedAuthority>authorities=new ArrayList<>();
                user.getRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getName()));
                });
                return new org.springframework.security.core.userdetails.
                        User(user.getUsername(),user.getPassword(),authorities);
            }
        });
    }



}
