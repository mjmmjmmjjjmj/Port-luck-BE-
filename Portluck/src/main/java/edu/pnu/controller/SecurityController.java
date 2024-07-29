package edu.pnu.controller;

import java.util.Map;

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
    
	@GetMapping("/login")	
	public String login()	{	
		return "login";		
		} //로그인 페이지
	
	@PostMapping("/signUp")	public String signUp(@RequestBody Member member)	{	
		memberService.createMember(member);
		return "회원가입 성공";	}// 회원가입\
	
//	@PostMapping("/searchPW")	
//	public String searchPassword(@RequestBody Map<String, String> payload)	{
//        String email = payload.get("email").trim().toLowerCase();
//        System.out.println("요청받은 이메일:"+ email);
//		boolean result = memberService.generateTemporaryPassword(email);
//		if (result) {
//			return "새 비밀번호가 이메일로 전송되었습니다.";
//		} else {
//			return "이메일을 찾을 수 없습니다.";	
//		}
//	}
	@PostMapping("/temPW")
	public String generateTemporaryPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email").trim().toLowerCase();
        System.out.println("요청 받은 이메일: " + email);
        String tempPassword = memberService.generateTemporaryPassword(email);
        if (tempPassword != null) {
            return tempPassword;
        } else {
            return "이메일을 찾을 수 없습니다.";
        }
    }
    @PostMapping("/changePW")
    public String changePassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String oldPassword = payload.get("oldPassword");
        String newPassword = payload.get("newPassword");

        boolean result = memberService.changePassword(username, oldPassword, newPassword);
        if (result) {
            return "비밀번호 변경 성공";
        } else {
            return "비밀번호 변경 실패";
        }
    }
    
	@GetMapping("/waitingTime")	
	public String waitingTime()	{	
		return "waitingTime";	
		}

}
