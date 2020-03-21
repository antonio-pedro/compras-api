CREATE TABLE produto (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO produto (descricao) VALUES ('Alvejante');
INSERT INTO produto (descricao) VALUES ('Biscoito');
INSERT INTO produto (descricao) VALUES ('Carne');
INSERT INTO produto (descricao) VALUES ('Musculo');
INSERT INTO produto (descricao) VALUES ('Pernil');
INSERT INTO produto (descricao) VALUES ('Venix');


