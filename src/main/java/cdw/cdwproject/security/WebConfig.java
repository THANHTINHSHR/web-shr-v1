package cdw.cdwproject.security;

import cdw.cdwproject.handler.CustomAuthenticationFailure;
import cdw.cdwproject.handler.CustomBasicLoginSuccessHandler;
import cdw.cdwproject.handler.CustomOAuth2LoginSuccessHandler;
import cdw.cdwproject.oauth.CustomOAuth2UserService;
import cdw.cdwproject.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler;
    @Autowired
    private CustomBasicLoginSuccessHandler customBasicLoginSuccessHandler;
    @Autowired
    private CustomAuthenticationFailure customAuthenticationFailure;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userDetailsService());

    }


    @Override

    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/lib/**", "/img/**", "/bootstrap/**", "/plugins/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors(); // ajax, logout
        // pages only for admin
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
       // pages only for customer or admin
        http.authorizeRequests().antMatchers("/my-account/**", "/checkout/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER");
//        http.authorizeRequests().antMatchers("/cart/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER");


        http.rememberMe().userDetailsService(userDetailsService()).tokenRepository(persistentTokenRepository());
//        http.rememberMe().tokenValiditySeconds(60*60*24).userDetailsService(userDetailsService());

        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/login")//
                .loginPage("/login")//
                .usernameParameter("email")
                .failureHandler(customAuthenticationFailure)
                .defaultSuccessUrl("/home")

                .successHandler(customBasicLoginSuccessHandler)
//                .failureForwardUrl("fail-login")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(customOAuth2LoginSuccessHandler)
                .userInfoEndpoint().userService(oAuth2UserService);
//                .and()
//                .successHandler(customOAuth2LoginSuccessHandler);
        // logout
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .permitAll()
        ;

    }

    //
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        return new UserDetailsServiceImp();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
        tokenRepo.setDataSource(dataSource);
        return tokenRepo;
    }

    //
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }




}
