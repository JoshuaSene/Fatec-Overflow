package br.gov.sp.fatec.domain.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.model.Answer;
import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.view.View;
import br.gov.sp.fatec.helpers.MainFormatter;

public class PostDTO {
	
	@JsonView(value = View.Post.Detail.class)
	private Long id;

	@NotBlank
    @Size(min = 3, max = 30)
    @JsonView(value = {
		View.Post.Create.class, 
		View.Post.Update.class
    })
    private String title;
    
	@JsonView(value = View.Post.Create.class)
    private Long author;
    
	@JsonView(value = View.Post.Create.class)
    private String date;
    
    @NotBlank
    @Size(min = 3, max = 200)
    @JsonView(value = {
		View.Post.Create.class, 
		View.Post.Update.class
	})
    private String content;
    
    @JsonView(value = {
		View.Post.Detail.class, 
		View.Post.CreateWithFile.class
	})
    private Set<FileDTO> files;
    
    @JsonView(value = View.Post.Detail.class)
    private Set<AnswerDTO> answers;

    public PostDTO() {}
    
	public PostDTO(Long id, String title, Long author, 
			String date, String content) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.content = content;
	}
	
	public PostDTO(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.author = post.getAuthor().getId();
		this.content = post.getContent();
		
		if(post.getDate() != null) {			
			this.date = MainFormatter.parseStringDate(post.getDate());
		}		
		
		if(post.getFiles() != null &&
				post.getFiles().size() > 0) {
			this.filFiles(post.getFiles());
		} else {
			this.files = new HashSet<FileDTO>();
		}
		
		if(post.getAnswers() != null &&
				post.getAnswers().size() > 0) {
			this.fillAnswers(post.getAnswers());
		} else {
			this.answers = new HashSet<AnswerDTO>();
		}
	}
	
	private void filFiles(Set<File> files) {
		this.files = new HashSet<FileDTO>();
		files.forEach(file -> {
			FileDTO dto = new FileDTO(file);
			this.files.add(dto);
		});
	}
	
	private void fillAnswers(Set<Answer> answers) {
		this.answers = new HashSet<AnswerDTO>();
		answers.forEach(answer -> {
			AnswerDTO dto = new AnswerDTO(answer);
			this.answers.add(dto);
		});
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
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
	
	public Set<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(Set<FileDTO> files) {
		this.files = files;
	}

	public Set<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<AnswerDTO> answers) {
		this.answers = answers;
	}

	public static List<PostDTO> convertToList(List<Post> posts) {
		return posts.stream().map(PostDTO::new).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		PostDTO other = (PostDTO) obj;
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
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", content="
				+ content + "]";
	}
}
