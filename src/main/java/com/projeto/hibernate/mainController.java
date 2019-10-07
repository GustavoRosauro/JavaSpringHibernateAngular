package com.projeto.hibernate;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projeto.model.Contato;
import com.projeto.repository.ContatoRepository;

@Controller
@CrossOrigin(origins="http://localhost:4200")
@EntityScan("com.projeto.model")
@ComponentScan("com.projeto.model")
@EnableJpaRepositories("com.projeto.repository")
public class mainController {
	@Autowired
	private ContatoRepository repository;
	
	mainController(ContatoRepository contatoRepository) {
		this.repository = contatoRepository;
	}
	@PostMapping("/contato")
	public @ResponseBody String adicionar(@RequestBody Contato contato) {
		try {
		repository.save(contato);
		return  "Inserido com sucesso";
		}catch(Exception ex) {
			return ex.getMessage();
		}
	}
	@GetMapping("/contato")
	public @ResponseBody List<Contato> contatos(){
		return repository.findAll();
	}
	@GetMapping("contato/{id}")
	public ResponseEntity findById(@PathVariable long id)
	{
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	@PutMapping("contato/{id}")
	public ResponseEntity Update(@PathVariable("id") long id,
							@RequestBody Contato contato) {
		return repository.findById(id)
				.map(record -> {
					record.setNome(contato.getNome());
					record.setEmail(contato.getEmail());
					record.setIdade(contato.getIdade());
					Contato updated = repository.save(record);
					return ResponseEntity.ok().body(updated);
				}).orElse(ResponseEntity.notFound().build());
	}
	@DeleteMapping("contato/{id}")
	public ResponseEntity delete(@PathVariable long id) {
		return repository.findById(id)
				.map(record ->{
					repository.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
}
