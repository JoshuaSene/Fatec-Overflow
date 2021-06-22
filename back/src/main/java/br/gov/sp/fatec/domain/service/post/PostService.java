package br.gov.sp.fatec.domain.service.post;

import java.util.List;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.domain.dto.FileDTO;
import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;

public interface PostService {
	public List<Post> findAll();
	public Post findById(Long id);
	public Post save(PostDTO post);
	public void delete(Post post);
	public Post update(Post post, Long id);
	public Post findByTitleAndAuthorId(String title, Long authorId);
	public Post savePostWithFile(UserEntity author, PostDTO postDto);
	public Post removeFilesFromPost(Long postId, Long fileId);
	public Post addFileOnPost(FileDTO dto, Long id);
	public Post answerPost(AnswerDTO dto, Long id);
	public Post erasePostAnswer(Long id, Long answerId);
}
