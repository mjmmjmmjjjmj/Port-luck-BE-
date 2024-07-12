package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String content;
	
	@ManyToOne
	@JoinColumn(name="username")
    @JsonBackReference("member-questions")
	private Member author;
	
	@Column(name = "question_date")
	@Builder.Default
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime questionDate = LocalDateTime.now();
	
	@OneToMany(mappedBy = "question")
	@JsonManagedReference("question-answers")
	private List<Answer> answers;
	
	private boolean answered;

    @JsonProperty("authorUsername")
    public String getAuthorUsername() {
    return author != null ? author.getUsername() : null;
   }
	
	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", questionDate=" + questionDate +
                ", answered=" + answered +
                '}';
	}
}
