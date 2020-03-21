package com.pessoal.compras.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.pessoal.compras.exception.PessoaInexistenteOuInativaException;
import com.pessoal.compras.model.Compra;
import com.pessoal.compras.model.ItemDaCompra;
import com.pessoal.compras.model.Pessoa;
import com.pessoal.compras.repository.CompraRepository;
import com.pessoal.compras.repository.PessoaRepository;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Compra> pesquisar() {
		return compraRepository.findAll();
	}

	public Compra criar(@Valid Compra compra) {
		validarPessoa(compra); 
			
		return compraRepository.save(compra);
	}

	public Compra atualizar(Long id, @Valid Compra compras) {
		Compra comprasBuscadaPeloId = buscarComprasPeloId(id);
		BeanUtils.copyProperties(compras, comprasBuscadaPeloId, "id");
		compraRepository.save(comprasBuscadaPeloId);
		return comprasBuscadaPeloId;
	}
	
	public Compra buscarComprasPeloId(@PathVariable Long id) {
		Optional<Compra> comprasBuscadaPeloId = compraRepository.findById(id);
		if (!comprasBuscadaPeloId.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return comprasBuscadaPeloId.get();
	}

	public void deletar(Long id) {
		Compra compras = buscarComprasPeloId(id);		
		compraRepository.delete(compras);		
	}
	
	public Compra atualizarValorTotalDaCompra(@Valid ItemDaCompra item) {
		Compra compra = buscarComprasPeloId(item.getCompra().getId());
		
		compra.setTotal(item.getValor().multiply(item.getQuantidade()).add(compra.getTotal()));
		
		return compraRepository.save(compra);
}
	
	private void validarPessoa(Compra compra) {
		Pessoa pessoa = null;
		pessoa = pessoaRepository.getOne(compra.getPessoa().getId());
		
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
}
