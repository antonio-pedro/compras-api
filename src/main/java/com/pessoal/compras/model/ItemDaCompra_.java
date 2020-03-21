package com.pessoal.compras.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemDaCompra.class)
public abstract class ItemDaCompra_ {

	public static volatile SingularAttribute<ItemDaCompra, Compra> compra;
	public static volatile SingularAttribute<ItemDaCompra, Produto> produto;
	public static volatile SingularAttribute<ItemDaCompra, BigDecimal> valor;
	public static volatile SingularAttribute<ItemDaCompra, Long> id;
	public static volatile SingularAttribute<ItemDaCompra, BigDecimal> quantidade;

}

