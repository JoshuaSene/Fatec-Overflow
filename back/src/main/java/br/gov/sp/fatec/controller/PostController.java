package br.gov.sp.fatec.controller;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.domain.dto.FileDTO;
import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.service.file.FileService;
import br.gov.sp.fatec.domain.service.post.PostService;
import br.gov.sp.fatec.domain.service.user.UserService;
import br.gov.sp.fatec.domain.view.View;


@RestController
@RequestMapping(value = "post")
@CrossOrigin
public class PostController {

    @Autowired
    PostService service;
    
    @Autowired
    UserService userService;
    
    @Autowired
    FileService fileService;
    

    @GetMapping
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<Post> posts = service.findAll();
        return ResponseEntity.ok(PostDTO.convertToList(posts));
    }

    @GetMapping(value = "/{id}")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> getPostDetails(@PathVariable Long id) {
        Post post = service.findById(id);
        return ResponseEntity.ok(new PostDTO(post));
    }

    @PostMapping
    @JsonView(value = View.Post.Detail.class)
    public ResponseEntity<PostDTO> savePost(
		@Valid @RequestBody @JsonView(value = View.Post.Create.class) PostDTO post, 
		UriComponentsBuilder builder
    ) {
    	Post savedPost = service.save(post);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(
    		builder.path(MessageFormat.format("/post/{0}", savedPost.getId())).build().toUri()
    	);
    	
    	return new ResponseEntity<PostDTO>(new PostDTO(savedPost), headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
    	Post post = service.findById(id);
    	service.delete(post);
    	return ResponseEntity.ok("Post excluído com sucesso!");
    }

    @PutMapping(value = "/{id}")
    @JsonView(value = View.Post.Detail.class)
    public ResponseEntity<PostDTO> updatePost(
		@PathVariable Long id, 
		@RequestBody @Valid @JsonView(value = View.Post.Update.class) PostDTO post
    ) {
        Post updatedPost = service.update(new Post(post), id);
        return ResponseEntity.ok(new PostDTO(updatedPost));
    }
    
    @DeleteMapping(value = "/{id}/remove-file/{fileId}")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> removeFileFromPost(
		@PathVariable Long id, 
		@PathVariable Long fileId
	) {
        return ResponseEntity.ok(new PostDTO(service.removeFilesFromPost(id, fileId)));
    }
    
    @PostMapping(value = "/{id}/add-file")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> addFileOnPost(
		@PathVariable Long id, 
		@Valid @RequestBody @JsonView(value = View.File.Create.class) FileDTO dto
    ) {
        return ResponseEntity.ok(new PostDTO(service.addFileOnPost(dto, id)));
    }
    
    @PostMapping(value = "/{id}/answer")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> answerPost(
		@PathVariable Long id, 
		@Valid @RequestBody @JsonView(value = View.Answer.Create.class) AnswerDTO dto
    ) {
        return ResponseEntity.ok(new PostDTO(service.answerPost(dto, id)));
    }
    
    @DeleteMapping(value = "/{id}/answer/{answerId}")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> erasePostAnswer(
    	@PathVariable Long id, 
    	@PathVariable Long answerId
    ) {
        return ResponseEntity.ok(new PostDTO(service.erasePostAnswer(id, answerId)));
    }
    
    @PostMapping(value = "/with-file")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> createPostWithFile(
		@Valid @RequestBody @JsonView(value = View.Post.CreateWithFile.class) PostDTO postDto, 
		UriComponentsBuilder builder
    ) {
    	UserEntity author = userService.findById(postDto.getAuthor());    	
    	Post responsePost = service.savePostWithFile(author, postDto);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(
    		builder.path(MessageFormat.format("/post/{0}", responsePost.getId())).build().toUri()
    	);
    	
    	return new ResponseEntity<PostDTO>(new PostDTO(responsePost), headers, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/get-by-title")
    @JsonView(value = View.Post.Info.class)
    public ResponseEntity<PostDTO> getPostByTitleAndAuthorId(
		@RequestParam String title, 
		@RequestParam Long authorId
    ) {
    	Post post = service.findByTitleAndAuthorId(title, authorId);
    	return ResponseEntity.ok(new PostDTO(post));
    }

    // Esta requisição está sendo feita apenas para testar o funcionamento do Pipeline
	@GetMapping(value = "/teste")
	public String sayHello() {
		return "Testanto Pipeline";
	}
}
