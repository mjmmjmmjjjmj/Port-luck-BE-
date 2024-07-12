package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;
import edu.pnu.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	List<Question> findByAuthor(Member author);


}
