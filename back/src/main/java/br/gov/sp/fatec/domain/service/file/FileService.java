package br.gov.sp.fatec.domain.service.file;

import java.util.List;
import java.util.Set;

import br.gov.sp.fatec.domain.model.File;

public interface FileService {
	public List<File> findAll();
	public File findById(Long id);
	public Set<File> findByPostId(Long postId);
	public File save(File file);
	public Set<File> saveAll(Set<File> files);
	public void delete(File file);
	public File update(File file, Long id);
}
