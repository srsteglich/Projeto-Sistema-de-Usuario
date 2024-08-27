package com.fourcatsdev.siscrud_usario.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fourcatsdev.siscrud_usario.modelo.Papel;
import com.fourcatsdev.siscrud_usario.repository.PapelRepository;

@Component
public class CarregadoraDados implements CommandLineRunner {

	@Autowired
	private PapelRepository papelRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		String[] papeis = {"ADMIN", "USER", "ENFERMEIRO"};
		
		for (String papelString: papeis) {
			Papel papel = papelRepository.findByPapel(papelString);
			if (papel == null) {
				papel = new Papel(papelString);
				papelRepository.save(papel);
			}
		}				
	}

}
