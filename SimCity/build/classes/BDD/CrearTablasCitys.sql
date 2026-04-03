
-- CREATE DATABASE SimCityBD; 

CREATE TABLE IF NOT EXISTS ciudades (
	id SERIAL PRIMARY KEY,
	nombreCiudad VARCHAR(100) NOT NULL,
	Tamaño INT NOT NULL,
	poblacion INT DEFAULT 0 NOT NULL,
	IdAlcalde INT NOT NULL,
        CONSTRAINT fk_estado FOREIGN KEY (IdAlcalde) 
        REFERENCES alcaldes(id) ON DELETE CASCADE
);

SELECT * FROM ciudades