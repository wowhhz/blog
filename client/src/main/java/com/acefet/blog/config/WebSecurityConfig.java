package com.acefet.blog.config;

public class WebSecurityConfig{}
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private com.acefet.blog.service.UserService userService;
//
////    @Bean
////    UserDetailsService customUserService(){
////        return userService;
////    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userService)
//            .passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        LogoutConfigurer<HttpSecurity> httpSecurityConfigurer =
//                http.authorizeRequests()
//                    .antMatchers("/", "/assets/**").permitAll()
//                    .antMatchers("/", "/home", "/login**", "/logout", "/index/**", "/test/**", "/detail/**", "/about/**", "/contact/**").permitAll()
//
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                    .antMatchers("/").permitAll()
//                    .antMatchers("/article/list/**", "/article/p/**").permitAll()
//                    .antMatchers("/comment/**", "/article/like/**").permitAll()
//                    .antMatchers("/class/list/**").permitAll()
//
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/admin/**").hasRole("USER")
////                    .anyRequest()
////                    .authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/admin/index")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/login?logout")
////                        .and()
////                        .csrf()
////                        .disable();
//                ;
//    }
//
//
//}
