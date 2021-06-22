package br.gov.sp.fatec.domain.service.file;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.domain.exception.FileNotFoundException;
import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.repository.FileRepository;

@Service
public class FileServiceImplement implements FileService {

	@Autowired
	private FileRepository fileRepository;
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADM')")
	public List<File> findAll() {
		return fileRepository.findAll();
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public File findById(Long id) {
		Optional<File> file = fileRepository.findById(id);
		
		if(file.isPresent()) {
			return file.get();
		}
		
		throw new FileNotFoundException(
			MessageFormat.format("Não foi encontrado arquivo com id {0}", id)
		);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Set<File> findByPostId(Long postId) {
		Optional<Set<File>> files = fileRepository.findByPostId(postId);
		
		if(files.isPresent()) {
			return files.get();
		}
		
		throw new FileNotFoundException(
			MessageFormat.format("Não foi encontrado arquivo em um post com id {0}", postId)
		);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public File save(File file) {
		return fileRepository.save(file);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Set<File> saveAll(Set<File> files) {
		return new HashSet<File>(fileRepository.saveAll(files));
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public void delete(File file) {
		fileRepository.delete(file);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public File update(File file, Long id) {
		File foundFile = findById(id);
		File newFile = setValues(foundFile, file);
		newFile = save(newFile);
		return newFile;
	}
	
	private File setValues(File oldFile, File newFile) {
		oldFile.setName(newFile.getName());
		oldFile.setUrl(newFile.getUrl());
    	return oldFile;
    }

}
