package fivetwentysix.ware.com.securitytry.config;

import fivetwentysix.ware.com.securitytry.MyApplication;
import org.apache.catalina.filters.RequestDumperFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

//import java.util.logging.Filter;

/*
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);
    @Autowired
    private AppUserDetailService appUserDetailsService;
    @Autowired
    private AppAuthenticationEntryPoint appAuthenticationEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("In configure");
        //changing .hasAnyRole to ROLE_ADMIN results in 401
        //this curl -i --request POST  -d "@data.txt" http://localhost:8080/user/register works order matches on antMatchers
       http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);  //these 2 create a jsessionid value returned to caller
         http.csrf().disable()
                .authorizeRequests()
             //   .antMatchers("/dist/**").permitAll()
                .antMatchers("/user/**").permitAll()
              //  .antMatchers("/weather/save").permitAll()
         //       .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
         //       .antMatchers("/dist/**").hasAnyRole("ADMIN","USER")
                .anyRequest().fullyAuthenticated()
                .and().httpBasic().realmName("Spring")
                //   .and().httpBasic()
                .authenticationEntryPoint(appAuthenticationEntryPoint)
                .and()
                .logout().deleteCookies("remember-me")
                .permitAll()
                .and()
                .rememberMe();
    }
        /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();*
    }
    */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configGlobal");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public FilterRegistrationBean requestDumperFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter requestDumperFilter = new RequestDumperFilter();
        registration.setFilter(requestDumperFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }

}
