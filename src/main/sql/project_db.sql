CREATE TABLE Person (
                        id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        name varchar(100) NOT NULL UNIQUE,
                        birthdate int NOT NULL
);

CREATE TABLE Book (
                      id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                      person_id int REFERENCES Person(id) ON DELETE SET NULL,
                      name varchar(100) NOT NULL,
                      author varchar(100) NOT NULL,
                      year int NOT NULL

);
ALTER TABLE Book ADD COLUMN taken_at TIMESTAMP;