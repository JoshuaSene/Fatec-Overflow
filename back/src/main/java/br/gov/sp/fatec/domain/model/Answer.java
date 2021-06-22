package br.gov.sp.fatec.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.helpers.MainFormatter;

@Entity
@Table(name = "Answer")
public class Answer {

	@Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "ans_date")
	private LocalDate date;
	
	@Column(length = 200)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ans_author")
	private UserEntity author;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ans_post")
	private Post post;
	
	public Answer() {}
	
	public Answer(AnswerDTO dto, UserEntity author, Post post) {
		if(dto.getDate() != null) {			
			this.date = MainFormatter.parseDateString(dto.getDate());
		} else {
			this.date = LocalDate.now();
		}
		this.content = dto.getContent();
		this.author = author;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", date=" + date + ", content=" + content + "]";
	}
	
}
