CREATE TABLE person (
  person_id VARCHAR(255) NOT NULL,
   name VARCHAR(255),
   first VARCHAR(255),
   last VARCHAR(255),
   middle VARCHAR(255),
   email VARCHAR(255),
   phone VARCHAR(255),
   fax VARCHAR(255),
   title VARCHAR(255),
   CONSTRAINT pk_person PRIMARY KEY (person_id)
);
