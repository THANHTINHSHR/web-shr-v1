package cdw.cdwproject.security;


import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@Configuration
//@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//@Autowired
//private CustomLoginSuccessHandler  customLoginSuccessHandler;
//    @Autowired
//    private CustomOAuth2UserService oAuth2UserService;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//
//    }
//
//    @Override
//
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/lib/**", "/img/**", "/bootstrap/**","/plugins/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors();// ajax
//        // Chỉ cho phép user đã đăng nhập mới được truy cập đường dẫn /admin/**
//        http.authorizeRequests().antMatchers("/admin/**", "/cart/**").authenticated();
//
//        // Cấu hình remember me, thời gian là 1296000 giây
//        http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000);
//
//        // Cấu hình cho Login Form.
//        http.authorizeRequests().and().formLogin()//
//                .loginProcessingUrl("/login")//
//                .loginPage("/login")//
//                .defaultSuccessUrl("/admin")//
//                .usernameParameter("email")//
//                .passwordParameter("password");
//
//
//
////
////        http.authorizeRequests().antMatchers("/my-account", "/cart", "/checkout").authenticated();
////        http.authorizeRequests().anyRequest().permitAll()
////        .and()
////        .formLogin().loginPage("/login").usernameParameter("email")
////        .and()
////        .oauth2Login()
////        .loginPage("/login")
////        .userInfoEndpoint().userService(oAuth2UserService)
////      . and().successHandler(customLoginSuccessHandler)
////        .and()
////        .logout().permitAll()
////        ;
//
//
//
//            http.csrf().disable().cors();// ajax
    // Chỉ cho phép user đã đăng nhập mới được truy cập đường dẫn /admin/**
//        http.authorizeRequests().antMatchers("/admin/**", "/cart/**").authenticated();

    // Cấu hình remember me, thời gian là 1296000 giây
//        http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000);
//        http.rememberMe().tokenRepository(persistentTokenRepository());
//
//
//                http.authorizeRequests().anyRequest().permitAll()
//        .and()
//        .formLogin().loginPage("/login").usernameParameter("email")
//        .and()
//        .oauth2Login()
//        .loginPage("/login")
//        .userInfoEndpoint().userService(oAuth2UserService)
//      . and().successHandler(customLoginSuccessHandler)
//        .and()
//        .logout().permitAll()
//            ;


//    }
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        return new UserDetailsServiceImp();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(userDetailsService());
//
//        return authenticationProvider;
//    }


}
