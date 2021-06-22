package br.gov.sp.fatec.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.model.Answer;
import br.gov.sp.fatec.domain.view.View;
import br.gov.sp.fatec.helpers.MainFormatter;

public class AnswerDTO {

	@JsonView(value = {
		View.Answer.Detail.class, 
		View.Post.Info.class
	})
    private Long id;
	
	@JsonView(value = {
		View.Answer.Create.class, 
		View.Post.Info.class
	})
	private String date;
	
	@NotBlank
    @Size(min = 3, max = 200)
	@JsonView(value = {
			View.Answer.Create.class, 
			View.Answer.Update.class,
			View.Post.Info.class
	})
	private String content;
	
	@NotBlank
	@NotNull
	@JsonView(value = View.Answer.Create.class)
	private Long author;
	
	@NotBlank
	@NotNull
	@JsonView(value = View.Answer.Create.class)
	private Long post;
	
	public AnswerDTO() {}
	
	public AnswerDTO(Long id, String date, 
			String content, Long author, Long post) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.author = author;
		this.post = post;
	}
	
	public AnswerDTO(Answer answer) {
		this.id = answer.getId();
		if(answer.getDate() != null) {			
			this.date = MainFormatter.parseStringDate(answer.getDate());
		}
		this.content = answer.getContent();
		this.author = answer.getAuthor().getId();
		this.post = answer.getPost().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnswerDTO other = (AnswerDTO) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnswerDTO [id=" + id + ", date=" + date + ", content=" + content + ", author=" + author + ", post="
				+ post + "]";
	}
}
