//package me.choi.blog.config;
//
//import lombok.RequiredArgsConstructor;
//import me.choi.blog.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@Configuration // 이 클래스가 스프링 설정 클래스임을 나타냄
//@EnableWebSecurity // 스프링 시큐리티를 활성화
//@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성
//public class WebSecurityConfig {
//
//    private final UserDetailService userService; // 사용자 세부 정보를 제공하는 서비스
//
//    //스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console()) // H2 콘솔에 대한 요청을 무시
//                .requestMatchers(new AntPathRequestMatcher("/static/**")); // 정적 리소스에 대한 요청을 무시
//    }
//
//    // 스프링 시큐리티 필터 연결
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests(auth -> auth // 요청에 대한 인증, 인가 설정
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/login"), // 로그인 페이지에 대한 요청을 허용
//                                new AntPathRequestMatcher("/signup"), // 회원가입 페이지에 대한 요청을 허용
//                                new AntPathRequestMatcher("/user") // 사용자 페이지에 대한 요청을 허용
//                        ).permitAll() // 위의 요청들을 모두 허용
//                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증 필요
//                .formLogin(formLogin -> formLogin // 폼 로그인 설정
//                        .loginPage("/login") // 로그인 페이지 설정
//                        .defaultSuccessUrl("/articles") // 로그인 성공 시 리다이렉트할 URL 설정/**/
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 리다이렉트할 URL 설정
//                        .invalidateHttpSession(true) // 세션 무효화
//                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService); // 사용자 세부 정보 서비스 설정
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호 인코더 설정
//        return new ProviderManager(authProvider); // 인증 관리자 반환
//    }
//
//    //패스워드 암호화
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(); // BCrypt 비밀번호 인코더 빈 생성
//    }
//}