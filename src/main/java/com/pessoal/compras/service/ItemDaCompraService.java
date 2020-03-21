package com.pessoal.compras.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.pessoal.compras.model.ItemDaCompra;
import com.pessoal.compras.repository.ItemDaCompraRepository;

@Service
public class ItemDaCompraService {
	
	@Autowired
	private ItemDaCompraRepository itemRepository;
	
	@Autowired
	private CompraService compraService;
	
	public List<ItemDaCompra> pesquisar() {
		return itemRepository.findAll();
	}

	public ItemDaCompra criar(@Valid ItemDaCompra item) {
		compraService.atualizarValorTotalDaCompra(item);
		
		return itemRepository.save(item);
	}

	public ItemDaCompra atualizar(Long id, @Valid ItemDaCompra item) {
		ItemDaCompra itemBuscadoPeloId = buscarItemPeloId(id);
		BeanUtils.copyProperties(item, itemBuscadoPeloId, "id");
		itemRepository.save(itemBuscadoPeloId);
		return itemBuscadoPeloId;
	}
	
	public ItemDaCompra buscarItemPeloId(@PathVariable Long id) {
		Optional<ItemDaCompra> ItemDaCompraBuscadoPeloId = itemRepository.findById(id);
		if (!ItemDaCompraBuscadoPeloId.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return ItemDaCompraBuscadoPeloId.get();
	}

	public void deletar(Long id) {
		ItemDaCompra item = buscarItemPeloId(id);		
		itemRepository.delete(item);		
	}
}
