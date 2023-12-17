# magazzon

Dato il seguente database e tabella
```sql
-- create database magazzon;
use magazzon;
CREATE TABLE magazzon.products
(
    id INT(6) UNSIGNED AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    quantity SMALLINT NOT NULL,
    price FLOAT NOT NULL,
    primary key (id)
);
```

creare una o più pagine in java/servlet che implementino le funzioni CRUD.

1. CR (obbligatorio)
    1. inserimento di un nuovo prodotto
    2. stampa dell'elenco dei prodotti con giacenza > 0

2) UD (facoltativo)
    1. aggiungere alla lista (punto 1.2) un link o un bottone "compra" che permette di acquistare il prodotto. Una volta acquistato, la giacenza del prodotto verrà decrementata.
    2. aggiungere alla lista (punto 1.2) un link o un bottone "elimina" che permette di cancellare l'intero record