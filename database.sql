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

CREATE TABLE bandeira
(
    ban_id SERIAL NOT NULL ,
    ban_descricao character varying(25) NOT NULL,
    CONSTRAINT pk_ban PRIMARY KEY (ban_id)
);


CREATE TABLE cartoes_credito
(
    ctc_id SERIAL NOT NULL ,
    ctc_nomeidentificacao character varying(255) NOT NULL,
    ctc_nometitular character varying(255) NOT NULL,
    ctc_numero character varying(25) NOT NULL,
    ctc_cvv character varying(5) NOT NULL,
    ctc_validade character varying(25) NOT NULL,
    ban_id integer NOT NULL,
    cli_id integer NOT NULL,
    CONSTRAINT pk_ctc PRIMARY KEY (ctc_id),
    CONSTRAINT fk_ctc_ban FOREIGN KEY (ban_id)
        REFERENCES bandeira (ban_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ctc_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE autores
(
    atr_id SERIAL NOT NULL,
    atr_nome character varying(50),
    CONSTRAINT pk_atr PRIMARY KEY (atr_id)
);

CREATE TABLE categorias
(
    cat_id SERIAL NOT NULL,
    cat_descricao character varying(50),
    CONSTRAINT pk_cat PRIMARY KEY (cat_id)
);

CREATE TABLE livros
(
    lvr_id SERIAL NOT NULL ,
    lvr_titulo character varying(255) NOT NULL,
    lvr_ano integer,
    lvr_editora character varying(255) NOT NULL,
    lvr_edicao integer,
    lvr_isbn character(13) NOT NULL,
    lvr_numero_paginas integer,
    lvr_sinopse character varying(3000) NOT NULL,
    lvr_altura numeric NOT NULL,
    lvr_largura numeric NOT NULL,
    lvr_profundidade numeric NOT NULL,
    lvr_peso numeric NOT NULL,
    lvr_grupo_precificacao integer NOT NULL,
    lvr_codigo_barras character(13) NOT NULL,
    lvr_link_capa character varying(255) NOT NULL,
    CONSTRAINT pk_lvr PRIMARY KEY (lvr_id)
);

CREATE TABLE tiposcupons
(
    tpc_id SERIAL NOT NULL,
    tpc_descricao character varying(20) NOT NULL,
    CONSTRAINT pk_tpc PRIMARY KEY (tpc_id)
);

CREATE TABLE status_pedidos
(
    stp_id SERIAL NOT NULL ,
    stp_descricao character varying(25),
    CONSTRAINT pk_stp PRIMARY KEY (stp_id)
);

CREATE TABLE endereco_entregas
(
    ede_id SERIAL NOT NULL,
    ede_logradouro character varying(100)  NOT NULL,
    ede_numero character varying(10)  NOT NULL,
    ede_bairro character varying(50) NOT NULL,
    ede_cep character varying(10) NOT NULL,
    ede_cidade character varying(100) NOT NULL,
    ede_estado character varying(50) NOT NULL,
    ede_pais character varying(50)  NOT NULL,
    ede_complemento character varying(200),
    ede_prazo_entrega integer,
    ede_valor_frete character varying(10) ,
    CONSTRAINT pk_ede PRIMARY KEY (ede_id)
);

CREATE TABLE pedidos
(
    pdd_id SERIAL NOT NULL ,
    pdd_valor_total character varying(10) ,
    pdd_data date NOT NULL DEFAULT CURRENT_DATE,
    cli_id integer,
    stp_id integer,
    ede_id integer,
    CONSTRAINT pk_pdd PRIMARY KEY (pdd_id),
    CONSTRAINT fk_pdd_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_pdd_ede FOREIGN KEY (ede_id)
        REFERENCES endereco_entregas (ede_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_pdd_stp FOREIGN KEY (stp_id)
        REFERENCES status_pedidos (stp_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE cartoescompras
(
    ccc_id SERIAL NOT NULL ,
    ccc_nometitular character varying(255) NOT NULL,
    ccc_numero character varying(25) NOT NULL,
    ccc_cvv character varying(5) NOT NULL,
    ccc_validade character varying(25) NOT NULL,
    ban_id integer NOT NULL,
    pdd_id integer,
    ccc_valor character varying(20),
    CONSTRAINT pk_ccc PRIMARY KEY (ccc_id),
    CONSTRAINT fk_ccc_ban FOREIGN KEY (ban_id)
        REFERENCES bandeira (ban_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ccc_pdd FOREIGN KEY (pdd_id)
        REFERENCES pedidos (pdd_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE autores_livros
(
    atl_atr_id integer NOT NULL,
    atl_lvr_id integer NOT NULL,
    CONSTRAINT pk_atl PRIMARY KEY (atl_atr_id, atl_lvr_id),
    CONSTRAINT fk_atl_atr FOREIGN KEY (atl_atr_id)
        REFERENCES autores (atr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_atl_lvr FOREIGN KEY (atl_lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE categorias_livros
(
    ctl_cat_id integer NOT NULL,
    ctl_lvr_id integer NOT NULL,
    CONSTRAINT pk_ctl PRIMARY KEY (ctl_cat_id, ctl_lvr_id),
    CONSTRAINT fk_ctl_cat FOREIGN KEY (ctl_cat_id)
        REFERENCES categorias (cat_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ctl_lvr FOREIGN KEY (ctl_lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE cupons
(
    cpm_id SERIAL NOT NULL,
    cpm_codigo character varying(20) ,
    cpm_valor character varying(10) ,
    pdd_id integer,
    cpm_isresgatado boolean DEFAULT false,
    cpm_validade date,
    cli_id integer,
    tpc_id integer,
    CONSTRAINT fk_cpm_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_cpm_pdd FOREIGN KEY (pdd_id)
        REFERENCES pedidos (pdd_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_cpm_tcp FOREIGN KEY (tpc_id)
        REFERENCES tiposcupons (tpc_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE categoriastatus
(
    cgs_id SERIAL NOT NULL,
    cgs_descricao character varying(50),
    CONSTRAINT pk_cgs PRIMARY KEY (cgs_id)
);

CREATE TABLE estoque
(
    etq_id SERIAL NOT NULL ,
    lvr_id integer NOT NULL,
    etq_quantidade integer NOT NULL,
    etq_status boolean NOT NULL,
    etq_justificativa character varying(255) ,
    cgs_id integer,
    etq_valor_venda character varying(10) ,
    etq_preco_custo character varying(10) ,
    CONSTRAINT pk_etq PRIMARY KEY (etq_id),
    CONSTRAINT fk_etq_cgs FOREIGN KEY (cgs_id)
        REFERENCES categoriastatus (cgs_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_etq_lvr FOREIGN KEY (lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE carrinhos
(
    crr_id SERIAL NOT NULL,
    cli_id integer NOT NULL,
    CONSTRAINT pk_crr PRIMARY KEY (crr_id),
    CONSTRAINT fk_crr_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE itenscarrinhos
(
    itc_id SERIAL NOT NULL ,
    crr_id integer NOT NULL,
    lvr_id integer NOT NULL,
    itc_quantidade integer NOT NULL,
    itc_valor_venda character varying(10),
    CONSTRAINT pk_itc PRIMARY KEY (itc_id),
    CONSTRAINT fk_itc_crr FOREIGN KEY (crr_id)
        REFERENCES carrinhos (crr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_itc_lvr FOREIGN KEY (lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE itenspedidos
(
    itp_id SERIAL NOT NULL,
    lvr_id integer,
    itp_quantidade integer,
    pdd_id integer,
    itp_valor_unitario character varying(10),
    CONSTRAINT pk_itp PRIMARY KEY (itp_id),
    CONSTRAINT fk_itp_lvr FOREIGN KEY (lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_itp_pdd FOREIGN KEY (pdd_id)
        REFERENCES pedidos (pdd_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE notificacoes
(
    ntf_id SERIAL NOT NULL ,
    ntf_mensagem character varying(200) NOT NULL,
    ntf_islida boolean NOT NULL DEFAULT false,
    usr_id integer NOT NULL,
    ntf_data_cadastro date DEFAULT CURRENT_DATE,
    CONSTRAINT pk_ntf PRIMARY KEY (ntf_id),
    CONSTRAINT fk_ntf_cli FOREIGN KEY (usr_id)
        REFERENCES usuario (usr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE pedidostrocas
(
    pdt_id SERIAL NOT NULL,
    cli_id integer NOT NULL,
    pdt_data date NOT NULL DEFAULT CURRENT_DATE,
    pdd_id integer NOT NULL,
    CONSTRAINT pk_pdt PRIMARY KEY (pdt_id),
    CONSTRAINT fk_pdt_cli FOREIGN KEY (cli_id)
        REFERENCES cliente (cli_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_pdt_pdd FOREIGN KEY (pdd_id)
        REFERENCES pedidos (pdd_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE itenstrocas
(
    itt_id SERIAL NOT NULL,
    pdt_id integer NOT NULL,
    lvr_id integer NOT NULL,
    itt_quantidade integer NOT NULL,
    itt_valor_unitario character varying(10)NOT NULL,
    itt_data date NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT pk_itt PRIMARY KEY (itt_id),
    CONSTRAINT fk_itt_lvr FOREIGN KEY (lvr_id)
        REFERENCES livros (lvr_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_itt_pdt FOREIGN KEY (pdt_id)
        REFERENCES pedidostrocas (pdt_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO tipo_telefone (tpt_descricao) VALUES ('fixo');
INSERT INTO tipo_telefone (tpt_descricao) VALUES ('celular');

INSERT INTO genero (gen_descricao) VALUES ('masculino');
INSERT INTO genero (gen_descricao) VALUES ('feminino');
INSERT INTO genero (gen_descricao) VALUES ('nao binario');

INSERT INTO bandeira  (ban_descricao) VALUES ('Visa');
INSERT INTO bandeira (ban_descricao) VALUES ('Mastercard');

INSERT INTO AUTORES (atr_id, atr_nome) VALUES (1, 'Eduardo Felberg');
INSERT INTO AUTORES (atr_id, atr_nome) VALUES (2, 'Ziraldo A. Pinto');
INSERT INTO AUTORES (atr_id, atr_nome) VALUES (3, 'Anna Lembke');
INSERT INTO AUTORES (atr_id, atr_nome) VALUES (4, 'Christina Lauren');
INSERT INTO AUTORES (atr_id, atr_nome) VALUES (5, 'Workman Publishing');
INSERT INTO AUTORES (atr_id, atr_nome) VALUES (6, ' Manual do Mundo');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (1, 'Deixe De Ser Pobre', 2023, 'Maquinaria Editorial', 1, '9786588370988', 256, 'Eduardo Feldberg sempre foi fascinado pelo modo com que as pessoas lidam e investem o próprio dinheiro. Depois de uma formação em música, ele começou a observar o universo das finanças, mas percebeu que a maioria dos conteúdos tinham a mesma fórmula: um engravatado usando termos complicados, mais confundindo do que ajudando os que o assistiam. E também estava indignado: ‘com o fato de que muita gente boa trabalha demais o tempo todo para, no final das contas, ter uma vida mais lascada que joelho de freira.’ Então, foi criado o ‘Primo Pobre’, canal no YouTube com mais de 1,3 milhão de seguidores, destinado para o ‘povão’, para quem cansou de não entender a própria vida financeira e quer aprender a cuidar das finanças de forma fácil e descomplicada. Porque, afinal, todo brasileiro merece a tranquilidade de pagar as contas do mês, sair para almoçar com a família em um lugar legal, e conseguir investir um pouco do dinheiro.', 23, 15, 1.54, 250, 2, '9786588370988', 'produto1-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (2, 'O Menino Maluquinho', 2008, 'Melhoramentos', 1, '9788506055106', 112, 'Um menininho traquinas, diziam. Tinha macaquinhos no sótão, deitava e rolava, fazendo confusão. Um anjinho, um saci? Alegria da casa, liderava a garotada. Namorador, fazia versinhos, compunha canções, inventava brincadeiras. Era sabido, um amigão. “Menino Maluquinho”, diziam sorrindo as pessoas. Não era, não! Só mais tarde descobriram que tinha sido um garotinho muito amado e, por isso mesmo, muito feliz.', 22.8, 15, 0.9, 200, 2, '9786588370988', 'produto2-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (3, 'Nação dopamina', 2022, 'Vestígio', 1, '9786586551716', 256, 'Este livro é sobre prazer. É também sobre sofrimento. Mas mais importante, é um livro que trata de como encontrar o delicado equilíbrio entre os dois, e por que hoje em dia, mais do que nunca, encontrar o equilíbrio é essencial. Estamos vivendo em uma época de excessos, de acesso sem precedentes a estímulos de alta recompensa e alta dopamina: drogas, comida, notícias, jogos, compras, sexo, redes sociais. A variedade e a potência desses estímulos são impressionantes - assim como seu poder adictivo. Nossos telefones celulares oferecem dopamina digital 24 horas por dia, 7 dias por semana, para uma sociedade ao mesmo tempo conectada e alheia do que acontece ao redor. Estamos todos vulneráveis ao consumo excessivo e à compulsão.', 23, 20, 1.3, 180, 2, '9786586551716', 'produto4-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (4, 'Imperfeitos', 2022, 'Faro Editorial', 1, '9786559571284', 256, 'Olive se sente como a gêmea azarada da casa: dos acidentes estranhamente inexplicáveis ao fracasso na vida profissional e amorosa ― nada dá certo para ela. Porém, parece que o jogo vira quando sua alergia a frutos do mar a protege de um desastre, já que todos os convidados da festa de casamento da irmã sofrem com intoxicação alimentar. Na verdade... nem todos. Ethan, o irmão do noivo, também ficou de fora desse pesadelo. Então, a irmã de Olive, sempre muito prática, propõe a eles que aproveitem a viagem de lua-de-mel, que não é reembolsável, para uma ilha do Havaí. Mas há um “pequeno” problema: Olive e Ethan são inimigos mortais. Há um passado entre eles que tornou a convivência impossível. Mas quem vai dizer não para essa viagem? Ainda mais de graça? Nem pensar! A ideia de ambos era ficar bem longe um do outro, mas a situação muda quando uma mentirinha boba vai crescendo e não podem voltar atrás. E dividindo a mesma suíte, entre farpas e sarcasmos, já se pode desconfiar.... onde tem raiva tem fogo? Com diálogos inteligentes e divertidos, dois personagens cativantes, e cenários de tirar o fôlego, Imperfeitos é o livro ideal para rir sem parar e ainda ver uma história de amor nascer no lugar mais improvável.', 23, 16, 4, 300, 2, '9786559571284', 'produto3-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (5, 'O Grande Livro de Matemática do Manual do Mundo', 2022, 'Sextante', 1, '9786555643367', 528, 'Com mais de 16 milhões de inscritos, o Manual do Mundo é considerado pelo Guinness World Records o maior canal de Ciência e Tecnologia em língua portuguesa do planeta! Nada mais natural que este Grande Livro de Matemática – um dos maiores sucessos da coleção americana Big Fat Notebook, que já vendeu mais de 8 milhões de exemplares – chegasse ao Brasil com a chancela do Manual do Mundo. Supercolorido, rabiscado de marca-texto e com ilustrações divertidas, este livro é garantia de informação de qualidade para todos que querem entender a arte da matemática. A matéria vai grudar na sua mente feito cola com definições claras e testes de conhecimento Tudo para você tirar as melhores notas! Neste volume, revisto e atualizado pelo Manual do Mundo, você vai encontrar: tipos de números, máximo divisor comum, frações, números decimais, razões, porcentagens, equações, geometria, probabilidade, plano cartesiano e muito mais.', 15.6, 21, 3.5, 300, 2, '9786555643367', 'produto-5-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (6, 'O Grande Livro de Ciências do Manual do Mundo', 2019, 'Sextante', 1, '9788543108667', 528, 'Com mais de 18 milhões de inscritos no YouTube, o Manual do Mundo é um dos maiores canais de Ciência e Tecnologia do planeta! Dando continuidade à bem-sucedida coleção Big Fat Notebook – que já vendeu mais de 8 milhões de exemplares –, Iberê e Mari agora trazem O Grande Livro de Química para o leitor brasileiro. Supercolorido, rabiscado de marca-texto e com ilustrações divertidas, este livro é garantia de informação de qualidade para todos que desejam desbravar o mundo da Química. A matéria vai grudar na sua mente feito cola com: macetes de memorização, definições claras, tabelas práticas e testes de conhecimento. Tudo para você tirar as melhores notas! Neste volume, revisto e atualizado pelo Manual do Mundo, você vai encontrar: massa atômica, tabela periódica, eletroquímica, ligações, estequiometria, molaridade, entropia e entalpia, solubilidade, escala de ph, termodinâmica, química orgânica e muito mais.', 15.6, 21, 3.5, 300, 2, '9788543108667', 'produto6-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (7, 'O Grande Livro de História do Manual do Mundo', 2020, 'Sextante', 1, '9786555640748', 592, 'Com 14 milhões de inscritos, o Manual do Mundo é considerado pelo Guinness World Records o maior canal de Ciência e Tecnologia em língua portuguesa do planeta! Sempre em busca de formas criativas de levar conhecimento às novas gerações, o Manual do Mundo apresenta O Grande Livro de História, mais um sucesso da coleção americana Big Fat Notebook, que já vendeu quase 5 milhões de exemplares. Supercolorido, rabiscado de marca-texto e com ótimas ilustrações, este livro ganhou seções caprichadas sobre a História do Brasil. Tudo com o selo de qualidade e a garantia de diversão do Manual do Mundo. A matéria vai grudar na sua mente feito cola com: macetes de memória, definições simples, observações interessantes e testes de conhecimento conhecimento. Tudo para você tirar as MELHORES NOTAS! Neste volume revisto e ampliado pelo Manual do Mundo, você vai encontrar: Primeiros Humanos, Idade Média, Renascimento, Brasil Colonial, Imperialismo, Primeira República, Primeira Guerra Mundial, Era Vargas, Segunda Guerra Mundial, Guerra Fria, Ditadura Militar, Transformações globais no mundo moderno e muito mais.', 15.6, 21, 3.7, 300, 2, '9786555640748', 'produto7-1.jpg');

INSERT INTO LIVROS (lvr_id, lvr_titulo, lvr_ano, lvr_editora, lvr_edicao, lvr_isbn, lvr_numero_paginas, lvr_sinopse, lvr_altura, lvr_largura, lvr_profundidade, lvr_peso, lvr_grupo_precificacao, lvr_codigo_barras, lvr_link_capa)
VALUES (8, 'O Grande Livro de Química do Manual do Mundo', 2023, 'Sextante', 1, '9786555646450', 592, 'Com mais de 18 milhões de inscritos no YouTube, o Manual do Mundo é um dos maiores canais de Ciência e Tecnologia do planeta! Dando continuidade à bem-sucedida coleção Big Fat Notebook – que já vendeu mais de 8 milhões de exemplares –, Iberê e Mari agora trazem O Grande Livro de Química para o leitor brasileiro. Supercolorido, rabiscado de marca-texto e com ilustrações divertidas, este livro é garantia de informação de qualidade para todos que desejam desbravar o mundo da Química. A matéria vai grudar na sua mente feito cola com: macetes de memorização, definições claras, tabelas práticas e testes de conhecimento. Tudo para você tirar as melhores notas! Neste volume, revisto e atualizado pelo Manual do Mundo, você vai encontrar: massa atômica, tabela periódica, eletroquímica, ligações, estequiometria, molaridade, entropia e entalpia, solubilidade, escala de ph, termodinâmica, química orgânica e muito mais.', 15.6, 21, 3.7, 300, 2, '9786555646450', 'produto8-1.jpg');


INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(1, 'Consumismo');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(2, 'Humor e Entretenimento');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(3, 'Economia');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(4, 'Aventura');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(5, 'Literatura Estrangeira');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(6, 'Autoajuda');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(7, 'Psicologia');
INSERT INTO CATEGORIAS (cat_id, cat_descricao) values(8, 'Escolas e Ensino');

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(1,1);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(2,1);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(3,1);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(5,4);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(6,4);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(4,2);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(5,2);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(6,3);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(7,3);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(4,5);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(8,5);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(4,6);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(8,6);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(4,8);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(8,8);

INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(4,7);
INSERT INTO CATEGORIAS_LIVROS (ctl_cat_id, ctl_lvr_id) values(8,7);

INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(1,1);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(2,2);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(3,3);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(4,4);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(5,5);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(6,5);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(5,6);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(6,6);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(5,7);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(6,7);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(5,8);
INSERT INTO AUTORES_LIVROS(atl_atr_id, atl_lvr_id) values(6,8);

INSERT INTO CATEGORIASTATUS (cgs_descricao) VALUES('Em estoque');
INSERT INTO CATEGORIASTATUS (cgs_descricao) VALUES('Fora de mercado');
INSERT INTO CATEGORIASTATUS (cgs_descricao) VALUES('Sem estoque');

INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (1, 'Em Processamento');
INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (2, 'Pagamento Realizado');
INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (3, 'Pagamento Recusado');
INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (4, 'Cancelado');
INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (5, 'Em Trânsito');
INSERT INTO STATUS_PEDIDOS (stp_id, stp_descricao) values (6, 'Entregue');

INSERT INTO ESTOQUE (lvr_id, etq_quantidade, etq_status, etq_justificativa, cgs_id, etq_valor_venda, etq_preco_custo) values (
  1, 25, true, 'Primeira entrada', 1, '29.90', '0'
)

INSERT INTO ESTOQUE (lvr_id, etq_quantidade, etq_status, etq_justificativa, cgs_id, etq_valor_venda, etq_preco_custo) values (
  2, 20, true, 'Primeira entrada', 1, '43.10', '0'
)

INSERT INTO ESTOQUE (lvr_id, etq_quantidade, etq_status, etq_justificativa, cgs_id, etq_valor_venda, etq_preco_custo) values (
    3, 15, true, 'Primeira entrada', 1, '53.80', '0'
)

INSERT INTO ESTOQUE (lvr_id, etq_quantidade, etq_status, etq_justificativa, cgs_id, etq_valor_venda, etq_preco_custo) values (
 4, 10, true, 'Primeira entrada', 1, '16.99', '0'
)

INSERT INTO ESTOQUE (lvr_id, etq_quantidade, etq_status, etq_justificativa, cgs_id, etq_valor_venda, etq_preco_custo) values (
    5, 13, true, 'Primeira entrada', 1, '74.93', '0'
)

INSERT INTO TIPOSCUPONS (tpc_id, tpc_descricao) values (1, 'Troca');
INSERT INTO TIPOSCUPONS (tpc_id, tpc_descricao) values (2, 'Promocional');