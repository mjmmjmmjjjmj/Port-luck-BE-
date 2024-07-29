package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    @Query("SELECT m FROM Member m WHERE LOWER(m.email) = LOWER(:email)")
	Optional<Member> findByEmail(@Param("email")String email);
	Optional<Member> findByResetToken(String resetToken);
	Optional<Member> findByUsername(String username);
}
