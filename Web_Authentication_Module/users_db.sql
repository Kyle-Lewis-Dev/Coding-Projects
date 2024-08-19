SHOW databases;

DROP DATABASE IF EXISTS users;

CREATE DATABASE users;

USE users;

DROP TABLE IF EXISTS users_table;

CREATE TABLE users_table (
 username VARCHAR(255) NOT NULL,
 passhash CHAR(60) NOT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DESCRIBE users_table;

-- Hashes determined ahead of time for simplicity in populating the database
INSERT INTO users_table(username, passhash) 
    VALUES ("John Doe", "$2y$10$LjJP7DiqH6QXDJCSwRaVpOGnGUs20/Z7YesdyEATH926nYJno5/1e");
INSERT INTO users_table(username, passhash) 
    VALUES ("Jane Doe", "$2y$10$2WPAFhE08iURICqoHaoa7O4tfHBFaaWS5D/DlL7YyOOg4dg1V0LUK");
INSERT INTO users_table(username, passhash) 
    VALUES ("Third User", "$2y$10$/JAv6N2R.g4RDdez5xiimexs5lr.KvqFZeqY5AQYSjrKnRh77vjDe");

SELECT * from users_table;