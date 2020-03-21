package com.pessoal.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.compras.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
