package com.pessoal.compras.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.pessoal.compras.model.Produto;
import com.pessoal.compras.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> pesquisar() {
		return produtoRepository.findAll();
	}

	public Produto criar(@Valid Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto atualizar(Long id, @Valid Produto produto) {
		Produto produtoBuscadoPeloId = buscarProdutoPeloId(id);
		BeanUtils.copyProperties(produto, produtoBuscadoPeloId, "id");
		produtoRepository.save(produtoBuscadoPeloId);
		return produtoBuscadoPeloId;
	}
	
	public Produto buscarProdutoPeloId(@PathVariable Long id) {
		Optional<Produto> produtoBuscadoPeloId = produtoRepository.findById(id);
		if (!produtoBuscadoPeloId.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoBuscadoPeloId.get();
	}

	public void deletar(Long id) {
		Produto produto = buscarProdutoPeloId(id);		
		produtoRepository.delete(produto);		
	}
}
