CREATE TABLE item (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    id_compra BIGINT(20) NOT NULL,
	id_produto BIGINT(20) NOT NULL,
    quantidade VARCHAR(30) NOT NULL,
    valor VARCHAR(30) NOT NULL,
    
    FOREIGN KEY (id_produto) REFERENCES produto(id),
    FOREIGN KEY (id_compra) REFERENCES compra(id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO item (id_produto, id_compra, quantidade, valor) VALUES (1, 1, '4', '4');
INSERT INTO item (id_produto, id_compra, quantidade, valor) VALUES (2, 1, '2', '5');
INSERT INTO item (id_produto, id_compra, quantidade, valor) VALUES (3, 1, '1', '6');
INSERT INTO item (id_produto, id_compra, quantidade, valor) VALUES (4, 1, '2', '2');

