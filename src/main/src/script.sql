CREATE DATABASE dashboard;
USE dashboard;

CREATE TABLE users
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(200),
    password VARCHAR(200)
);

CREATE TABLE ligne_credit
(
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    libelle    VARCHAR(200),
    montant    DECIMAL(10, 2),
    date_debut DATE,
    date_fin   DATE
);

CREATE TABLE ligne_depense
(
    id        INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_credit INTEGER,
    montant   DECIMAL(10, 2),
    dates     DATE,
    FOREIGN KEY (id_credit) REFERENCES ligne_credit (id)
);