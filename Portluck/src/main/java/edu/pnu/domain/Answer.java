package edu.pnu.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference("question-answers")
	private Question question;

	@ManyToOne
    @JoinColumn(name = "question_username") //, referencedColumnName = "username"
    @JsonBackReference("member-answers")
//	@JoinColumn(name = "question_username", referencedColumnName = "username")
//	@JoinColumn(name = "username", referencedColumnName = "username")
//	@JsonBackReference
	private Member author;

	private String content;

	@Column(name = "answer_date")
	@Builder.Default
	private LocalDateTime answerDate = LocalDateTime.now();
	
	@Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", question=" + (question != null ? question.getId() : "null") +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", answerDate=" + answerDate +
                '}';
    }
	
//	@JoinColumn(name = "updated")
//	private LocalDateTime updated;
}