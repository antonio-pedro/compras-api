package com.pessoal.compras.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.pessoal.compras.model.ItemDaCompra;
import com.pessoal.compras.service.ItemDaCompraService;


@RestController
@RequestMapping("/itens")
public class ItemDaCompraResource {
	
	@Autowired
	private ItemDaCompraService itemService;

	@Autowired  // Publicador de evento de aplicação
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_ITEM') and #oauth2.hasScope('read')")
	public List<ItemDaCompra> pesquisar() {		
		return itemService.pesquisar();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_ITEM') and #oauth2.hasScope('write')")
	public ResponseEntity<ItemDaCompra> criar(@Valid @RequestBody ItemDaCompra item, HttpServletResponse response) {
		ItemDaCompra itemSalvo = itemService.criar(item);

		// Lança o evento para adicionar header Location ou seja a localização do recurso criado
		publisher.publishEvent(new RecursoCriadoEvent(this, response, itemSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_ITEM') and #oauth2.hasScope('write')")
	public ResponseEntity<ItemDaCompra> atualizar(@PathVariable Long id, @Valid @RequestBody ItemDaCompra item) {
		ItemDaCompra itemAtualizado = itemService.atualizar(id, item);
		
		return ResponseEntity.ok(itemAtualizado);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_ITEM') and #oauth2.hasScope('write')")
	public void deletar(@PathVariable Long id) {
		itemService.deletar(id);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_ITEM') and #oauth2.hasScope('read')")
	public ItemDaCompra buscarPeloId(@PathVariable Long id) {
		ItemDaCompra itemBuscadoPeloId = itemService.buscarItemPeloId(id);
		return itemBuscadoPeloId;
	}

}
