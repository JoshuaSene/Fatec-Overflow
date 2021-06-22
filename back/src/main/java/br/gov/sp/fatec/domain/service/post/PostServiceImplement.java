package br.gov.sp.fatec.domain.service.post;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.domain.dto.FileDTO;
import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.domain.exception.PostNotFoundException;
import br.gov.sp.fatec.domain.model.Answer;
import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.FileRepository;
import br.gov.sp.fatec.domain.repository.PostRepository;
import br.gov.sp.fatec.domain.service.answer.AnswerService;
import br.gov.sp.fatec.domain.service.file.FileService;
import br.gov.sp.fatec.domain.service.user.UserService;

@Service
public class PostServiceImplement implements PostService {

	@Autowired
    UserService userService;
	
	@Autowired
    FileService fileService;

    @Autowired
    AnswerService answerService;
	
    @Autowired
    PostRepository repository;
    
    @Autowired
    FileRepository fileRepository;

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Post findById(Long id) {
    	Optional<Post> post = repository.findById(id);
    	if(post.isPresent()) {
    		return post.get();
    	}
    	
    	throw new PostNotFoundException(MessageFormat.format("Não foi encontrado um post com o id {0}", id));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Post save(PostDTO post) {
    	Post newPost = new Post(post); 
    	UserEntity author = userService.findById(post.getAuthor());
    	
    	newPost.setAuthor(author);
    	if(newPost.getDate() == null) {    			
    		newPost.setDate(LocalDate.now());
    	}
    	
    	newPost = repository.save(newPost);

    	if(post.getFiles() != null && 
    			post.getFiles().size() > 0) {
    		Set<File> files = new HashSet<File>(); 
    		
    		for (FileDTO file : post.getFiles()) {				
    			files.add(new File(file, newPost));
			}
    		
    		fileService.saveAll(files);
    	}
    	
    	return newPost;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void delete(Post post) {
        repository.delete(post);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Post update(Post post, Long id) {
    	Post foundPost = findById(id);
    	
    	Post newPost = setValues(foundPost, post);
    	newPost = repository.save(newPost);
    	return newPost;    	
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public Post findByTitleAndAuthorId(String title, Long authorId) {
    	Optional<Post> post = repository.findByTitleContainsAndAuthorId(title, authorId);
    	
    	if(post.isPresent()) {
    		return post.get();
    	}
    	
    	throw new PostNotFoundException(
    			MessageFormat.format("Não foi encontrado um post com o título {0} e autor com id {1}", 
    					title, authorId)
    	);
    }
    
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Post savePostWithFile(UserEntity author, PostDTO postDto) {
    	Post post = new Post(postDto);
    	post.setAuthor(author);
    	post = repository.save(post);
    	
    	Set<FileDTO> filesDto = postDto.getFiles();
    	Set<File> files = new HashSet<File>();
    	
    	for (FileDTO dto : filesDto) {
    		files.add(new File(dto, post));
		}
    	
    	List<File> savedFiles = fileRepository.saveAll(files);
    	post.getFiles().addAll(savedFiles);
    	post = repository.save(post);
    	
    	return post;
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public Post removeFilesFromPost(Long postId, Long fileId) {
    	Post foundPost = findById(postId);
    	File foundFile = fileService.findById(fileId);
    	Set<File> files = foundPost.getFiles();
    
        files.remove(foundFile);
        foundPost.setFiles(files);
        
        return repository.save(foundPost);
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public Post addFileOnPost(FileDTO dto, Long id) {
    	Post foundPost = findById(id);
        
        Post newPost = foundPost;
        File file = new File(dto, newPost);
        
        Set<File> files = newPost.getFiles();
        files.add(file);
        newPost.setFiles(files);
        
        return repository.save(newPost);
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public Post answerPost(AnswerDTO dto, Long id){
    	Post foundPost = findById(id);
        UserEntity user = foundPost.getAuthor();
        Answer answer = new Answer(dto, foundPost.getAuthor(), foundPost);
        
        Set<Answer> answers = foundPost.getAnswers();
        answers.add(answer);
        foundPost.setAnswers(answers);
        
        Set<Answer> userAnswers = user.getAnswers();
        userAnswers.add(answer);
        user.setAnswers(userAnswers);
        
        return repository.save(foundPost);
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public Post erasePostAnswer(Long id, Long answerId) {
    	Post foundPost = findById(id);
    	Post post = foundPost;
    	Answer answer = answerService.findById(answerId);
    	post.getAnswers().remove(answer);        
        return repository.save(post);
    }
    
    private Post setValues(Post oldPost, Post newPost) {
    	oldPost.setContent(newPost.getContent());
    	oldPost.setTitle(newPost.getTitle());
    	return oldPost;
    }
}
