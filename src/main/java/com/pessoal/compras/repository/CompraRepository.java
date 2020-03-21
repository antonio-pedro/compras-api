package com.pessoal.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.compras.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long>{

}
