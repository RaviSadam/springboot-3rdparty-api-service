//package onlinetestapi.SecurityConfiguration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests((request)-> {
////                    request
////                            .requestMatchers(HttpMethod.POST, "/login", "/registration").permitAll()
////                            .anyRequest().authenticated();
////                });
////
////        return http.build();
////    }
//}
