 CREATE TABLE categoria (
	id SERIAL NOT NULL PRIMARY KEY,
	titulo VARCHAR (50) NOT NULL,
	cor VARCHAR (20)
);

INSERT INTO categoria (id, titulo, cor)
VALUES (1, 'LIVRE', '#FFFFFF');

SELECT setval('categoria_id_seq', 1, true);