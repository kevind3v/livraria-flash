CREATE TABLE usuario
(
    usr_id SERIAL PRIMARY KEY,
    usr_email varchar(100) NOT NULL,
    usr_password varchar(100) NOT NULL,
    usr_is_admin boolean DEFAULT false,
    usr_created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
);

ALTER TABLE usuario ADD CONSTRAINT usr_email_unique UNIQUE (usr_email);

CREATE TABLE tipo_telefone
(
    tpt_id SERIAL NOT NULL,
    tpt_descricao character varying(30) NOT NULL,
    CONSTRAINT pk_tpt PRIMARY KEY (tpt_id)
);

CREATE TABLE genero
(
    gen_id SERIAL NOT NULL ,
    gen_descricao character varying(20) NOT NULL,
    CONSTRAINT pk_gen PRIMARY KEY (gen_id)
);

CREATE TABLE telefone
(
    tel_id SERIAL NOT NULL ,
    tel_ddd character(5) NOT NULL,
    tel_numero character varying(10)  NOT NULL,
    tpt_id integer NOT NULL,
    CONSTRAINT pk_tel PRIMARY KEY (tel_id),
    CONSTRAINT fk_tel_tpt FOREIGN KEY (tpt_id)
        REFERENCES tipo_telefone (tpt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE cliente
(
    cli_id SERIAL NOT NULL ,
    cli_nome character varying(100) NOT NULL,
    cli_dt_nasc character varying(10) NOT NULL,
    cli_cpf character varying(14) NOT NULL,
    gen_id integer NOT NULL,
    tel_id integer NOT NULL,
    usr_id integer NOT NULL,
    CONSTRAINT pk_cli PRIMARY KEY (cli_id),
    CONSTRAINT fk_cli_gen FOREIGN KEY (gen_id)
        REFERENCES genero (gen_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_cli_tel FOREIGN KEY (tel_id)
        REFERENCES telefone (tel_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_cli_usr FOREIGN KEY (usr_id)
        REFERENCES usuario (usr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE endereco
(
    end_id SERIAL NOT NULL ,
    end_identificacao character varying(100) NOT NULL,
    end_logradouro character varying(100)  NOT NULL,
    end_numero character varying(10)  NOT NULL,
    end_bairro character varying(50) NOT NULL,
    end_cep character varying(10) NOT NULL,
    end_cidade character varying(100) NOT NULL,
    end_estado character varying(50) NOT NULL,
    end_pais character varying(50)  NOT NULL,
    end_complemento character varying(200),
    cli_id integer NOT NULL,
    CONSTRAINT pk_end PRIMARY KEY (end_id),
    CONSTRAINT fk_end_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO tipo_telefone (tpt_descricao) VALUES ('fixo');
INSERT INTO tipo_telefone (tpt_descricao) VALUES ('celular');

INSERT INTO genero (gen_descricao) VALUES ('masculino');
INSERT INTO genero (gen_descricao) VALUES ('feminino');
INSERT INTO genero (gen_descricao) VALUES ('nao binario');