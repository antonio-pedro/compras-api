CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL,
	id_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(50) NOT NULL,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (id, nome, descricao, email, senha, id_pessoa) values (1, 'Antonio Pedro Costa', 'ADMINISTRADOR', 'pedro@cds.eb.mil.br', '$2a$10$cNeCfTaZcg8p7IUJY/vaXe9.jVdirkx1XROhcU5gqlbyt7N.8y6V.', 1);
INSERT INTO usuario (id, nome, descricao, email, senha, id_pessoa) values (2, 'Marcos Cesar Costa', 'CONSULTOR', 'marcos.costa@eb.mil.br', '$2a$10$cNeCfTaZcg8p7IUJY/vaXe9.jVdirkx1XROhcU5gqlbyt7N.8y6V.', 2);

INSERT INTO permissao (id, role, descricao) values (1, 'ROLE_CADASTRAR_PESSOA', 'Cadastrar Pessoa');
INSERT INTO permissao (id, role, descricao) values (2, 'ROLE_REMOVER_PESSOA', 'Excluir Pessoa');
INSERT INTO permissao (id, role, descricao) values (3, 'ROLE_PESQUISAR_PESSOA', 'Pesquisar Pessoa');

INSERT INTO permissao (id, role, descricao) values (4, 'ROLE_CADASTRAR_USUARIO', 'Cadastrar Usuário');
INSERT INTO permissao (id, role, descricao) values (5, 'ROLE_REMOVER_USUARIO', 'Excluir Usuário');
INSERT INTO permissao (id, role, descricao) values (6, 'ROLE_PESQUISAR_USUARIO', 'Pesquisar Usuário');

INSERT INTO permissao (id, role, descricao) values (7, 'ROLE_CADASTRAR_COMPRA', 'Cadastrar Compra');
INSERT INTO permissao (id, role, descricao) values (8, 'ROLE_REMOVER_COMPRA', 'Excluir Compra');
INSERT INTO permissao (id, role, descricao) values (9, 'ROLE_PESQUISAR_COMPRA', 'Pesquisar Compra');

INSERT INTO permissao (id, role, descricao) values (10, 'ROLE_CADASTRAR_PRODUTO', 'Cadastrar Produto');
INSERT INTO permissao (id, role, descricao) values (11, 'ROLE_REMOVER_PRODUTO', 'Excluir Produto');
INSERT INTO permissao (id, role, descricao) values (12, 'ROLE_PESQUISAR_PRODUTO', 'Pesquisar Produto');

INSERT INTO permissao (id, role, descricao) values (13, 'ROLE_CADASTRAR_ITEM', 'Cadastrar Item');
INSERT INTO permissao (id, role, descricao) values (14, 'ROLE_REMOVER_ITEM', 'Excluir Item');
INSERT INTO permissao (id, role, descricao) values (15, 'ROLE_PESQUISAR_ITEM', 'Pesquisar Item');


-- admin
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 8);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 9);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 10);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 11);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 12);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 13);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 14);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 15);

-- consultor
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 9);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 12);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 15);
