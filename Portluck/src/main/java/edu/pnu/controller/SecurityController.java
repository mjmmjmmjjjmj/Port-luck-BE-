package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
public class SecurityController {
    @Autowired
    private MemberService memberService;
    
	@GetMapping("/login")	public String login()	{	return "login";		} //로그인 페이지
	@PostMapping("/signUp")	public String signUp(@RequestBody Member member)	{	
		memberService.createMember(member);
		return "회원가입 성공";	}// 회원가입\
	
	@PostMapping("/searchPW")	public String searchPassword(@RequestBody String email)	{
		boolean result = memberService.sendPasswordResetLink(email);
		if (result) {
			return "비밀번호 재설정 링크 전송 성공";
		} else {
			return "이메일을 찾을 수 없습니다.";	
		}
	}
	@GetMapping("/waitingTime")	public String waitingTime()	{	return "waitingTime";	}

}
