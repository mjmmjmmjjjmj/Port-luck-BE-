package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Answer;
import edu.pnu.domain.Member;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

	Answer findByAuthor(Member author);

}
