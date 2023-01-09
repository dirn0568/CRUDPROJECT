package com.example.springcrud2.config;

import com.example.springcrud2.jwt.JwtAuthFilter;
import com.example.springcrud2.jwt.JwtUtil;
// import com.example.springcrud2.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class SecurityConfig {
    // 참고: UserDetails와 UserDetailsService 커스텀해서 사용하지 않으면 시큐리티 디폴트 패스워드가 저절로 주어진다
    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 암호화 해줌 controller bean에 저장된 BCryptPasswordEncoder 저장시켜줌???
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Spring Security는 Servlet FilterChain을 자동으로 구성한 후 거치게 한다, Spring Security는 기본적으로 제공되는 Filter chain들이 존재하며, debug=true 옵션을 통해 chain들을 확인할 수 있다.
        http.csrf().disable(); // @EnableWebSecurity을 통해 자동으로 csrf를 방어하는 기능을 가졌지만 disable을 통해 꺼버렸다 -> why? 우리는 restapi로 설계했기 때문에 애시당초 csrf 공격을 받을 이유가 없었기 떄문에 꺼놨다?

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // SessionCreationPolicy.STATELESS = Spring Security는 HttpSession을 생성하지 않으며 SecurityContext를 가져오는 데 사용하지 않습니다 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음 -> JWT 같은토큰방식을 쓸때 사용하는 설정
        System.out.println("시큐리티 콘피그 언제 실행되는거지????");
        http.authorizeRequests().antMatchers("/api/register/**").permitAll()
                .anyRequest().permitAll()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
                //.and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//                .antMatchers("/api/user/**").permitAll() // 요청에 대한 권한 지정. Security 처리에 HttpServletRequest를 이용한다는 것을 의미한다.
//                .antMatchers("/api/search").permitAll()
//                .antMatchers("/api/shop").permitAll()
//                .anyRequest().authenticated();
                // JWT 인증/인가를 사용하기 위한 설정
                //.and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter.class를 실행하기 전에 new JwtAuthFilter(jwtUtil) 이거를 실행해라

        // .permitAll() 인증 없이 권한을 가짐
        // .authenticated() 인증된 사용자만 접근 가능
        // .antMatchers 경로 알려주기
        // .anyRequest 그 외 나머지 리소스를 가리킴, 혼자 쓰일경우 모든 리소스를 뜻하기도함
        //http.formLogin().loginPage("/api/register").permitAll(); // formLogin() = form 기반의 로그인을 할 수 있습니다., loginPage() = 로그인 페이지의 URL을 설정합니다.
        http.formLogin().loginPage("/register").permitAll(); // formLogin() = form 기반의 로그인을 할 수 있습니다., loginPage() = 로그인 페이지의 URL을 설정합니다.

        //http.addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().accessDeniedPage("/api/user/forbidden"); // exceptionHandling() = 예외사항을 설정한다, accessDeniedPage() = url 이동

        return http.build();
    }
}
