package com.pessoal.compras.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.compras.event.RecursoCriadoEvent;
import com.pessoal.compras.model.Pessoa;
import com.pessoal.compras.repository.PessoaRepository;
import com.pessoal.compras.repository.filter.PessoaFilter;
import com.pessoal.compras.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	// Publicador de evento de aplicação lança um evento
	@Autowired
	private ApplicationEventPublisher publisher;

	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA) and #oauth2.hasScope('read')")
	public Page<Pessoa> pesquisar(PessoaFilter pessoaFilter, Pageable pageable) {		
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	}	

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.criar(pessoa);

		// Lança o evento para adicionar header Location ou seja a localização do recurso criado
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaService.atualizar(id, pessoa);
		
		return ResponseEntity.ok(pessoaAtualizada);
	}
	
	@PutMapping("/{id}/ativo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> atualizarCampoAtivo(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaService.atualizarCampoAtivo(id, pessoa.getAtivo());
		
		return ResponseEntity.ok(pessoaAtualizada);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//retorna 204 deletado mas sem conteúdo
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void deletar(@PathVariable Long id) {
		pessoaService.deletar(id);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA) and #oauth2.hasScope('read')")
	public Pessoa buscarPeloId(@PathVariable Long id) {
		Pessoa pessoaBuscadaPeloId = pessoaService.buscarPessoaPeloId(id);
		return pessoaBuscadaPeloId;
	}

}
