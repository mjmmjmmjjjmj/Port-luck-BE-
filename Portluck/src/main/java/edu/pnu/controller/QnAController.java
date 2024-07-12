package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Answer;
import edu.pnu.domain.Question;
import edu.pnu.service.QnaService;

@RestController
@RequestMapping("/qna")
public class QnAController {
	
	@Autowired
	private QnaService qnaService;

	//user, admin 둘 다 됨
	@GetMapping("")
	public List<Question> getAllQuestions() {
		return qnaService.getAllQuestions();
	}
	
//	//user, admin 둘 다 됨
//	@GetMapping("/questions/{username}")
//	public Question getQuestionByUsername(@PathVariable String username) {
//		return qnaService.getQuestionByUsername(username);		
//	}
	
	//user, admin 둘 다 됨
	@PostMapping("/questions")
	public Question createQuestion(@RequestBody Question question) {
		return qnaService.createQuestion(question);
	}
	
	////admin만 됨
	@PostMapping("/answers")
	public Answer createAnswer(@RequestBody Answer answer) {
		Answer createdAnswer = qnaService.createAnswer(answer);
		return qnaService.createAnswer(answer);
	}
	
	//user, admin 둘 다 됨
	@GetMapping("/answers")
	public List<Answer> getAllAnswers() {
		return qnaService.getAllAnswers();
	}
	
	@GetMapping("/answers/{username}")
	public Answer getAnswerByUsername(@PathVariable String username) {
		return qnaService.getAnswerByUsername(username);
	}
	
// admin만 됨	
	@DeleteMapping("/questions/{username}")
	public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
		Question deletedQuestion = qnaService.deleteQuestion(id);
		return ResponseEntity.ok("질문이 성공적으로 삭제되었습니다.");
	}

	@GetMapping("/myQuestions")
	public List<Question> getMyQuestions() {
		return qnaService.getMyQuestions();
	}
//	@GetMapping("/qna/{question_id}")
//	public Question
	
//	//admin만 됨
//	@PutMapping("/qna/answers")
//	public Answer fixAnswer(@RequestBody Answer answer) {
//		return qnaService.fixAnswer(answer);
//	}

}
