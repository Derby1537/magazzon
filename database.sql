create database magazzino;
use magazzino

CREATE TABLE prodotti (
  id bigint(20) NOT NULL,
  nome_prodotto varchar(30) NOT NULL,
  giacenza int(11) NOT NULL,
  prezzo float NOT NULL
  primary key (id)
);