package edu.pnu.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
	@Id
	private String username;
	private String password;
	private String email;
	private String resetToken;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean enabled;

	@OneToMany(mappedBy = "author")
	@JsonManagedReference("member-questions")
	@ToString.Exclude
	private List<Question> questions;
	
	@OneToMany(mappedBy = "author")
    @JsonManagedReference("member-answers")
	private List<Answer> answers;

}
