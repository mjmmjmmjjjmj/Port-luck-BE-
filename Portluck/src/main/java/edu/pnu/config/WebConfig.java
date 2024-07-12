package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000","http://10.125.121.216:3000", "http://localhost:8080")
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					//get : 보는 거
					//post : 무에서 유 상태로
					//put : 수정
					//delete : 삭제
					//options : 이건 뭘까 
//					서버 지원 메서드 확인:
//						특정 URL에 대해 서버가 어떤 HTTP 메서드를 지원하는지 확인할 수 있습니다.
//						클라이언트는 OPTIONS 요청을 보내고 서버로부터 허용된 메서드 목록을 응답으로 받을 수 있습니다.
//						CORS (Cross-Origin Resource Sharing) 사전 확인 요청:
//
//						다른 도메인 간의 요청을 할 때, 특히 POST, PUT, DELETE 등의 메서드를 사용할 때 브라우저는 보안을 위해 사전 확인(preflight) 요청을 보냅니다.
//						이 사전 확인 요청은 OPTIONS 메서드를 사용하여 실제 요청 전에 서버가 요청을 허용하는지 확인합니다.
					.allowedHeaders("*")
					.allowCredentials(true)
					.maxAge(3600)
					.exposedHeaders("Authorization");
				//클라이언트에 노출할 헤더에 토큰 담기
			}
		};
	}

}
