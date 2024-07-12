package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private MemberService memberService;
	   /**
     * 회원가입 요청을 처리하는 메서드입니다.
     * 웹에서 회원가입 정보를 받아 DB에 저장합니다.
     * 
     * @param member 회원가입 정보를 담은 Member 객체
     * @return 회원가입 완료 메시지를 담은 ResponseEntity
     */
	
	@PostMapping("/signUp") //웹에서 회원가입하면 정보를 DB에 저장
	public ResponseEntity<String> createMember(@RequestBody Member member) {
		System.out.println(member);
		memberService.createMember(member);
		return ResponseEntity.ok("회원가입이 완료되었습니다.");
	}
	  /**
     * 주어진 ID에 해당하는 회원 정보 조회 메서드.
     * */
	@GetMapping("/login/{username}")
	public ResponseEntity<Member> getMemberById(@PathVariable String username) {
		Member member = memberService.getMemberById(username);
		 if (member != null) {
	            return ResponseEntity.ok(member);
	        } else {
	            return ResponseEntity.notFound().build();
	        }	
		 }
	
	
	/**
     * 로그인 요청을 처리하는 메서드입니다.
     * 사용자의 ID와 비밀번호를 확인합니다.
     * 
     * @param loginRequest 로그인 정보를 담은 객체
     * @return 로그인 성공 또는 실패 메시지를 담은 ResponseEntity
     */
	
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
    	
    	boolean isAuthenticated = memberService.authenticate(member.getUsername(), member.getPassword());
    	
    	if(isAuthenticated) {
    		return ResponseEntity.ok("로그인 성공");
    	}
    	else {
    		return ResponseEntity.status(401).body("로그인 실패: 잘못된 ID 또는 비밀번호");
    	}
//        Member member = memberService.getMemberById(loginRequest.getId());
//        
//        if (member != null && member.getPassword().equals(loginRequest.getPassword())) {
//            return ResponseEntity.ok("로그인 성공");
//        } else {
//            return ResponseEntity.status(401).body("로그인 실패: 잘못된 ID 또는 비밀번호");
//        }
    }
}
