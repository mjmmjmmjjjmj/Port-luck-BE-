package edu.pnu.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EmailService emailService;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public Member createMember(Member member) {
		System.out.println(member);
		//보안->비번 ** 처리(해시 처리)
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setRole(Role.ROLE_USER);
		member.setEmail(member.getEmail());
		return memberRepository.save(member);
	}
	
	public Member getMemberById(String username) {
		return memberRepository.findById(username).orElse(null);
	}

	public boolean authenticate(String username, String password) {
		Member member = memberRepository.findById(username).orElse(null);
		if (member != null && passwordEncoder.matches(password, member.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sendPasswordResetLink(String email) {
		Optional<Member> memberOpt = memberRepository.findByEmail(email);
		if (memberOpt.isPresent()) {
			Member member = memberOpt.get();
			String resetToken = UUID.randomUUID().toString();
			String resetLink = "http://your-application.com/reset-password?token="+resetToken;
			emailService.sendSimpleMessage(email, "비밀번호 재설정 요청", "비밀번호 재설정은 링크를 눌러주세요"+resetLink);
			member.setResetToken(resetToken);
			memberRepository.save(member);
			return true;
		}
		return false;
	}
	
	public boolean resetPassword(String token, String newPassword) {
		Optional<Member> memberOpt = memberRepository.findByResetToken(token);
		if(memberOpt.isPresent()) {
			Member member = memberOpt.get();
			member.setPassword(passwordEncoder.encode(newPassword));
			member.setResetToken(null); //토큰 쓰고 나면 없앰
			memberRepository.save(member);
			return true;
		}
		return false;
	}
}
