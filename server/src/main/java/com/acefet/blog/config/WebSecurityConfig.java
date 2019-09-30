package com.acefet.blog.config;

public class WebSecurityConfig{}
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    UserDetailsService customUserService(){
//        return new UserServiceImpl();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(customUserService())
//            .passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                //.antMatchers("/","/css/**","/js/**").permitAll()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/article/list/**", "/article/p/**").permitAll()
//                .antMatchers("/comment/**","/article/like/**").permitAll()
//                .antMatchers("/class/list/**").permitAll()
//
//                .antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/dashboard").hasRole("USER")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/dashboard")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//
//}
