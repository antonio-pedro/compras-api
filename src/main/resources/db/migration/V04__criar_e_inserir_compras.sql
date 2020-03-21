CREATE TABLE compra (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(30) NOT NULL,
    total VARCHAR(30) NOT NULL,
    data DATE NOT NULL,
    id_pessoa BIGINT(20) NOT NULL,    
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO compra (descricao, total, data, id_pessoa) VALUES ('NOVEMBRO', '1300', '2019-10-25', 1);

