package com.pessoal.compras.repository.projecao;

	import java.math.BigDecimal;
	import java.time.LocalDate;

	public class ResumoCompra {
		
		private Long id;
		private String descricao;
		private LocalDate data;
		private BigDecimal total;
		private String pessoa;
		
		public ResumoCompra(Long id, String descricao, LocalDate data, BigDecimal total, String pessoa) {
			
			this.id = id;
			this.descricao = descricao;
			this.data = data;
			this.total = total;
			this.pessoa = pessoa;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public LocalDate getData() {
			return data;
		}

		public void setData(LocalDate data) {
			this.data = data;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public String getPessoa() {
			return pessoa;
		}

		public void setPessoa(String pessoa) {
			this.pessoa = pessoa;
		}
}
