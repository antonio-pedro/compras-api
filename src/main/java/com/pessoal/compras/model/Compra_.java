package com.pessoal.compras.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, BigDecimal> total;
	public static volatile ListAttribute<Compra, ItemDaCompra> itens;
	public static volatile SingularAttribute<Compra, Pessoa> pessoa;
	public static volatile SingularAttribute<Compra, LocalDate> data;
	public static volatile SingularAttribute<Compra, Long> id;
	public static volatile SingularAttribute<Compra, String> descricao;

}

