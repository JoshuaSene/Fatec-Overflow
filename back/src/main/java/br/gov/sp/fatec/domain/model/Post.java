package br.gov.sp.fatec.domain.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.helpers.MainFormatter;

@Entity
@Table(name = "Post")
public class Post {

    @Id
    @Column(name = "id_post")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post_author")
    private UserEntity author;
    
    @JsonFormat(
    	shape = JsonFormat.Shape.STRING,
    	pattern = "dd-MM-yyyy"
    )
    @Column(
    	length = 40,
    	name = "post_date"
    )
    private LocalDate date;
    
    @Lob
    @Column(length = 200)
    private String content;
    
    @OneToMany(
    	mappedBy = "post", 
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<File> files = new HashSet<File>();
    
    @OneToMany(
    	mappedBy = "post",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Answer> answers = new HashSet<Answer>();
    
    public Post() {}
    
    public Post(PostDTO dto) {
    	this.title = dto.getTitle();
    	if(dto.getDate() != null) {    		
    		this.date = MainFormatter.parseDateString(dto.getDate());
    	} else {
    		this.date = LocalDate.now();
    	}
    	this.content = dto.getContent();
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

	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
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

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", date=" + date + ", content=" + content
				+ "]";
	}

}
