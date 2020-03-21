package com.pessoal.compras.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pessoal.compras.model.Pessoa;

public class CompraFilter {
	
	private String descricao;
	private LocalDate dataDe;
	private LocalDate dataAte;
	private BigDecimal total;
	private Pessoa pessoa;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataDe() {
		return dataDe;
	}
	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}
	public LocalDate getDataAte() {
		return dataAte;
	}
	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
