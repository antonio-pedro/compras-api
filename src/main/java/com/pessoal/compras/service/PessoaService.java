package com.pessoal.compras.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.pessoal.compras.model.Pessoa;
import com.pessoal.compras.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Pessoa> pesquisar() {
		return pessoaRepository.findAll();
	}

	public Pessoa criar(@Valid Pessoa pessoa) {
		
		return pessoaRepository.save(pessoa);
	}

	public Pessoa atualizar(Long id, @Valid Pessoa pessoa) {
		Pessoa pessoaBuscadaPeloId = buscarPessoaPeloId(id);
		BeanUtils.copyProperties(pessoa, pessoaBuscadaPeloId, "id");
		pessoaRepository.save(pessoaBuscadaPeloId);
		return pessoaBuscadaPeloId;
	}
	
	public Pessoa buscarPessoaPeloId(@PathVariable Long id) {
		Optional<Pessoa> pessoaBuscadaPeloId = pessoaRepository.findById(id);
		if (!pessoaBuscadaPeloId.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBuscadaPeloId.get();
	}

	public void deletar(Long id) {
		Pessoa pessoa = buscarPessoaPeloId(id);		
		pessoaRepository.delete(pessoa);		
	}

	public Pessoa atualizarCampoAtivo(Long id, Boolean ativo) {
		Pessoa pessoaBuscadaPeloId = buscarPessoaPeloId(id);
		
		pessoaBuscadaPeloId.setAtivo(ativo);
		return pessoaRepository.save(pessoaBuscadaPeloId);
	}
}
