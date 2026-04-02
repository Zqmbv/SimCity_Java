
-- CREATE DATABASE SimCityBD; 

CREATE TABLE IF NOT EXISTS alcaldes (
	id SERIAL PRIMARY KEY,
	nombre VARCHAR(64) NOT NULL,
	apellido VARCHAR(64) NOT NULL,
	dni INTEGER UNIQUE NOT NULL,
	genero VARCHAR(16) NOT NULL,
	clave VARCHAR(32) NOT NULL,
	CONSTRAINT validar_genero CHECK (genero IN ('Masculino', 'Femenino', 'Otro'))
);

SELECT * FROM alcaldes