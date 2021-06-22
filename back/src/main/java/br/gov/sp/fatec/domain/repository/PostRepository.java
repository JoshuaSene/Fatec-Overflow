package br.gov.sp.fatec.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.domain.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	public Optional<Post> findByTitleContainsAndAuthorId(String title, Long authorId);

	/**
	 * @apiNote findById 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param id (Long)
	 * 
	 * @return Optional<Post>
	 */
	public Optional<Post> findById(Long id);
	
	/**
	 * @apiNote buscarPorId(Long) 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param id (Long)
	 * 
	 * @return Post
	 */
	@Query("select p from Post p where p.id = ?1")
	public Post buscarPorId(Long id);
	
	/**
	 * @apiNote findByTitle 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param title (String)
	 * 
	 * @return Set<Post>
	 */
	public Set<Post> findByTitle(String title);
	
	/**
	 * @apiNote findByTitle 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param title (String)
	 * 
	 * @return Set<Post>
	 */
	public Set<Post> findByTitleContainsIgnoreCase(String title);
	
	/**
	 * @apiNote countForUser 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param username (String)
	 * 
	 * @return Long
	 */
	@Query("select count(p) from Post p inner join p.author u where u.username = ?1 order by p.date")
    public Long countForUsername(String username);
	
	/**
	 * @apiNote buscarPostsPorUsername 
	 * @author Marcos Vinicio Pereira
	 * 
	 * @param username (String)
	 * 
	 * @return Set<Post>
	 */
	@Query("select p from Post p inner join p.author u where u.username = ?1 order by p.date")
    public List<Post> listaPostsPorUsername(String username);
}
