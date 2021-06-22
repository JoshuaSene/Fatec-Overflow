package br.gov.sp.fatec.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.sp.fatec.domain.dto.FileDTO;


@Entity
@Table(name = "File")
public class File {

	@Id
    @Column(name = "id_file")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(length = 100)
    private String url;
    
    @Column(length = 50)
    private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_file_post")
	private Post post;
	
	public File() {}
	
	public File(String url, String name, Post post) {
		this.url = url;
		this.name = name;
		this.post = post;
	}
	
	public File(FileDTO dto, Post post) {
		this.setUrl(dto.getUrl());
		this.setName(dto.getName());
		this.setPost(post);
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", url=" + url + ", name=" + name + "]";
	}

}
