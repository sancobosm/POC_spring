--MUY SUCIO PERO UNICA MANERA DE CAMBIAR ORDEN
DROP TABLE person;
CREATE TABLE person (
   person_id INTEGER NOT NULL,
   name VARCHAR(255),
   first VARCHAR(255),
   last VARCHAR(255),
   middle VARCHAR(255),
   email VARCHAR(255),
   phone INTEGER,
   title VARCHAR(255),
   CONSTRAINT pk_person PRIMARY KEY (person_id)
);