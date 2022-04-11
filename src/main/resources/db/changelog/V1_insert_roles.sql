--liquibase formatted sql
--changeset database:inset-employee-01
CREATE TABLE IF NOT EXISTS roles(id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, name varchar(20) DEFAULT NULL);
INSERT INTO roles (name) VALUES('ROLE_ADMIN');
INSERT INTO roles (name) VALUES('ROLE_PATIENT');
INSERT INTO roles (name) VALUES('ROLE_STAFF');
