package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	@Autowired	
	private MemberRepository memberRepository;
	
	@Override
	@Transactional
	// loadUserByUsername : AuthenticationManager의 authenticate 메소드가 호출되면 실행
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Member> memberOpt = memberRepository.findById(username);
//		if (!memberOpt.isPresent()) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		Member member = memberOpt.get();
//		return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(),
//				member.getAuthorities());
//		}

	Member member = memberRepository.findByUsername(username) //memRepo에서 사용자 정보 검색
								.orElseThrow(()->new UsernameNotFoundException(username+"찾을 수 없습니다."));
		System.out.println(member);

		return new User(member.getUsername(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
}
