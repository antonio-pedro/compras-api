package com.pessoal.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.compras.model.Pessoa;
import com.pessoal.compras.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
