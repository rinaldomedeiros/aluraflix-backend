ALTER TABLE video
ADD COLUMN categoria_id BIGINT;

ALTER TABLE video
ADD CONSTRAINT fk_categria
FOREIGN KEY (categoria_id) 
REFERENCES categoria (id);