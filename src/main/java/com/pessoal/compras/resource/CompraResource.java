package com.pessoal.compras.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.pessoal.compras.model.Compra;
import com.pessoal.compras.service.CompraService;


@RestController
@RequestMapping("/compras")
public class CompraResource {
	
	@Autowired
	private CompraService compraService;

	// Publicador de evento de aplicação lança um evento
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Compra> pesquisar() {		
		return compraService.pesquisar();
	}

	@PostMapping
	public ResponseEntity<Compra> criar(@Valid @RequestBody Compra compras, HttpServletResponse response) {
		Compra compraSalva = compraService.criar(compras);

		// Lança o evento para adicionar header Location ou seja a localização do recurso criado
		publisher.publishEvent(new RecursoCriadoEvent(this, response, compraSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Compra> atualizar(@PathVariable Long id, @Valid @RequestBody Compra compras) {
		Compra comprasAtualizada = compraService.atualizar(id, compras);
		
		return ResponseEntity.ok(comprasAtualizada);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//retorna 204 deletado mas sem conteúdo
	public void deletar(@PathVariable Long id) {
		compraService.deletar(id);
	}

	@GetMapping("/{id}")
	public Compra buscarPeloId(@PathVariable Long id) {
		Compra comprasBuscadaPeloId = compraService.buscarComprasPeloId(id);
		return comprasBuscadaPeloId;
	}

}
