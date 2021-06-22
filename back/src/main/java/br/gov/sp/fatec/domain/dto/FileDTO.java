package br.gov.sp.fatec.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.view.View;

public class FileDTO {
	
	@JsonView(value = {
		View.File.Detail.class, 
		View.Post.Info.class
	})
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@JsonView(value = {
		View.File.Create.class, 
		View.Post.Info.class, 
		View.Post.CreateWithFile.class
	})
    private String url;
	
	@NotBlank
	@Size(min = 3, max = 50)
	@JsonView(value = {
		View.File.Create.class, 
		View.Post.Info.class, 
		View.Post.CreateWithFile.class
	})
    private String name;
	
	@JsonView(value = View.File.Detail.class)
	private Long post;
	
	public FileDTO() {}
	
	public FileDTO(Long id, String url, String name, Long post) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.post = post;
	}
	
	public FileDTO(String url, String name, Long post) {
		this.url = url;
		this.name = name;
		this.post = post;
	}
	
	public FileDTO(File file) {
		this.id = file.getId();
		this.url = file.getUrl();
		this.name = file.getName();
		this.post = file.getPost().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		FileDTO other = (FileDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileDTO [id=" + id + ", url=" + url + ", name=" + name + "]";
	}
}
