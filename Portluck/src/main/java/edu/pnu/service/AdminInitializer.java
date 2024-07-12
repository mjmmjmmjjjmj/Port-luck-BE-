package edu.pnu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@Configuration
public class AdminInitializer {
//	  @Autowired
//	  private MemberRepository memberRepository;
//   
//	  @Autowired  
//	  private PasswordEncoder passwordEncoder;

	  @Value("${admin.password}")
	  private String adminPassword;

	@Bean
	CommandLineRunner init(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (memberRepository.findByUsername("99admin9999").isEmpty()) {
				Member admin = Member.builder()
						.username("99admin9999")
						.password(passwordEncoder.encode(adminPassword))
						.role(Role.ROLE_ADMIN)
						.enabled(true)
						.build();
				memberRepository.save(admin);
			}
		};
	}
//	  public void initializeAdmin() {
//	        if (memberRepository.findByUsername("99admin9999").isEmpty()) {
//	            Member admin = new Member();
//	            admin.setUsername("99admin9999");
//	            admin.setPassword(passwordEncoder.encode("admin_password")); // 암호화된 비밀번호 설정
//	            admin.setRole(Role.ROLE_ADMIN);
//	            admin.setEnabled(true);
//	            memberRepository.save(admin);
//	        }
//	    }

}
