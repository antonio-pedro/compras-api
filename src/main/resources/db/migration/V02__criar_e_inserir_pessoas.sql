CREATE TABLE pessoa (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(15) NOT NULL,
        logradouro VARCHAR(50),
	    numero VARCHAR(20),
	    complemento VARCHAR(50),
	    bairro VARCHAR(50),
	    cep VARCHAR(15),
	    id_cidade BIGINT(20),
	ativo BOOLEAN NOT NULL,
	
	FOREIGN KEY (id_cidade) REFERENCES cidade(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO pessoa( nome, cpf, logradouro, numero, complemento, bairro, cep, id_cidade, ativo ) VALUES ('ANTONIO PEDRO COSTA', '758.347.857-47', 'SQS 209 BLOCO K', null, 'apto 604', 'ASA SUL', '70.272-110', 2, 1 ); 
