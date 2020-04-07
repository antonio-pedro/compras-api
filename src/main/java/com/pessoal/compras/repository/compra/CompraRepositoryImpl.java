package com.pessoal.compras.repository.compra;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.pessoal.compras.model.Compra;
import com.pessoal.compras.model.Compra_;
import com.pessoal.compras.model.Pessoa_;
import com.pessoal.compras.repository.filter.CompraFilter;
import com.pessoal.compras.repository.projecao.ResumoCompra;

public class CompraRepositoryImpl implements CompraRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Compra> filtrar(CompraFilter compraFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteria = builder.createQuery(Compra.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Compra> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(compraFilter));
	}
	
	@Override
	public Page<ResumoCompra> resumir(CompraFilter compraFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoCompra> criteria = builder.createQuery(ResumoCompra.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		criteria.select(builder.construct(ResumoCompra.class
				, root.get(Compra_.id)
				, root.get(Compra_.descricao)
				, root.get(Compra_.data)
				, root.get(Compra_.total)
				, root.get(Compra_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoCompra> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(compraFilter));
	}
	
	
	private Predicate[] criarRestricoes(CompraFilter compraFilter, CriteriaBuilder builder, Root<Compra> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(compraFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Compra_.descricao)), "%" + compraFilter.getDescricao().toLowerCase() + "%"));
		}		
		
		if (compraFilter.getDataDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Compra_.data), compraFilter.getDataDe()));
		}
		
		if (compraFilter.getDataAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Compra_.data), compraFilter.getDataAte()));
		}	
		
		if (compraFilter.getTotal() != null) {
			predicates.add(builder.equal(root.get(Compra_.total), compraFilter.getTotal()));
		}
		
		if (!StringUtils.isEmpty(compraFilter.getPessoa())) {
			predicates.add(builder.like(
		        	builder.lower(root.get(Compra_.pessoa).get(Pessoa_.nome)),"%" + compraFilter.getPessoa().getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	//pageable é o que pega os parâmetros que vem lá do cliente: size=5&page=0
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		//System.out.println("paginaAtual = "+ paginaAtual);//0
		int totalRegistrosPorPagina = pageable.getPageSize();
		//System.out.println("totalRegistrosPorPagina = "+ totalRegistrosPorPagina);//5
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		//System.out.println("primeiroRegistroDaPagina = "+ primeiroRegistroDaPagina);//5 * 0 = 0
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(CompraFilter compraFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));		
		return manager.createQuery(criteria).getSingleResult();
	}
}
