package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.Answer;
import edu.pnu.domain.Member;
import edu.pnu.domain.Question;
import edu.pnu.persistence.AnswerRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class QnaService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
    private MemberRepository memberRepository;

	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	public Question getQuestionByUsername(String username) {
		 Member author = memberRepository.findByUsername(username).orElse(null);
	     //return questionRepository.findByAuthor(author);
		 return questionRepository
				 .findByAuthor(author)
				 .stream()
				 .findFirst().orElse(null); // 단일 질문 반환
	     
	};
	
	private String getCurrentUsername() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof UserDetails) {
	        return ((UserDetails) principal).getUsername();
	    } else {
	        return principal.toString();
	    }
    }

	@Transactional
    public Question createQuestion(Question question) {
        // 현재 인증된 사용자 정보 가져오기
        String username = getCurrentUsername();
        Member author = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        question.setAuthor(author);
        return questionRepository.save(question);
    }

	public List<Answer> getAllAnswers() {
		return answerRepository.findAll();
	}
	
	public Answer getAnswerByUsername(String username) {
		Member author = memberRepository.findByUsername(username).orElse(null);
        return answerRepository.findByAuthor(author);

	}

	 @Transactional
	    public Answer createAnswer(Answer answer) {
	        String username = getCurrentUsername();
	        Member author = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	        Question question = questionRepository.findById(answer.getQuestion().getId()).orElseThrow(() -> new RuntimeException("Question not found"));
	        answer.setAuthor(author);
	        answer.setQuestion(question);

	        // 질문에 답변이 등록되면 answered 필드를 true로 설정
	        question.setAnswered(true);
	        questionRepository.save(question);

	        return answerRepository.save(answer);
	    }
    
    @Transactional
    public Question deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Question not found"));
        questionRepository.delete(question);
        return question;
    }
    
    public List<Question> getMyQuestions() {
    	String username = getCurrentUsername();
    	Member author = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    	return questionRepository.findByAuthor(author);
    }
    
//    @Transactional
//    public void markQuestionAsAnswered(String username) {
//        Member author = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
//        Question question = questionRepository.findByAuthor(author);
//        if (question != null) {
//            question.setAnswered(true);
//            questionRepository.save(question);
//        } else {
//            throw new EntityNotFoundException("Question not found");
//        }
//    }
//	public Answer fixAnswer(Answer answer) {
//		Answer existingAnswer = answerRepository
//				.findByAuthorUsername(answer.getAuthor().getUsername());
//		
//		if(existingAnswer != null) {
//			existingAnswer.setContent(answer.getContent());
//			existingAnswer.setUpdated(LocalDateTime.now());
//			
//			return AnswerRepository.save(existingAnswer);
//		} else {
//			throw new EntityNotFoundException("답변을 찾을 수 없습니다.");
//		}
//	}
}
