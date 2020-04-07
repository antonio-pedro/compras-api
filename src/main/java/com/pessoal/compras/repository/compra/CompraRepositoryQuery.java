package com.pessoal.compras.repository.compra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pessoal.compras.model.Compra;
import com.pessoal.compras.repository.filter.CompraFilter;
import com.pessoal.compras.repository.projecao.ResumoCompra;

public interface CompraRepositoryQuery {
	
	public Page<Compra> filtrar(CompraFilter compraFilter, Pageable pageable);
	public Page<ResumoCompra> resumir(CompraFilter compraFilter, Pageable pageable);

}
