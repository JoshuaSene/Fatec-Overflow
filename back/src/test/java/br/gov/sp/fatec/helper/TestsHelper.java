package br.gov.sp.fatec.helper;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.domain.dto.FileDTO;
import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.domain.dto.UserDTO;

public class TestsHelper {

	public static PostDTO getDTOForPostCreation() {
		PostDTO dto = new PostDTO();
		dto.setTitle("Post inicial de Testes");
		dto.setContent("Post para testes criado em um Helper");
		
		return dto;
	}
	
	public static UserDTO getDTOForUserCreation() {
		UserDTO dto = new UserDTO();
		dto.setRegister(124679);
		dto.setName("Sysadmin");
		dto.setUsername("admin");
		dto.setPassword("$enh4_F0rt3");
		dto.setRole("ROLE_ADM");
		
		return dto;
	}
	
	public static FileDTO getDTOForFileCreation() {
		FileDTO dto = new FileDTO();
		dto.setUrl("/files/image.jpg");
		dto.setName("Imagem Exemplo");
		
		return dto;
	}
	
	public static AnswerDTO getDTOForAnswerCreation() {
		AnswerDTO dto = new AnswerDTO();	
		dto.setDate("10-04-2021");
		dto.setContent("Exemplificando resposta");
		
		return dto;
	}
}
