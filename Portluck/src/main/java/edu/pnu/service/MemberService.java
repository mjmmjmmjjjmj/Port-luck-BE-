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
	
//	@Autowired
//	private EmailService emailService;
	
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

//    public boolean sendTemporaryPassword(String email) {
//        email = email.trim().toLowerCase();
//        Optional<Member> memberOpt = memberRepository.findByEmail(email);
//        if (memberOpt.isPresent()) {
//            Member member = memberOpt.get();
//            String tempPassword = generateRandomPassword();
//            member.setPassword(passwordEncoder.encode(tempPassword));
//            memberRepository.save(member);
//            emailService.sendSimpleMessage(email, "임시 비밀번호 발송", "임시 비밀번호는: " + tempPassword + "입니다.");
//            return true;
//        } else {
//            System.out.println("해당 이메일(" + email + ")을 찾을 수 없습니다.");
//            return false;
//        }
//    }

    public String generateTemporaryPassword(String email) {
        email = email.trim().toLowerCase();
        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            String tempPassword = generateRandomPassword();
            member.setPassword(passwordEncoder.encode(tempPassword));
            memberRepository.save(member);
            // 로그에 출력
            System.out.println("임시 비밀번호: " + tempPassword);
            return tempPassword;
        } else {
            System.out.println("해당 이메일(" + email + ")을 찾을 수 없습니다.");
            return null;
        }
    }
	private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
	}

	public boolean changePassword(String username, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		Optional<Member> memberOpt = memberRepository.findById(username);
		if(memberOpt.isPresent()) {
			Member member = memberOpt.get();
			if (passwordEncoder.matches(oldPassword, member.getPassword())) {
				member.setPassword(passwordEncoder.encode(newPassword));
				memberRepository.save(member);
				return true;
			}
		}
		return false;
	}
}
