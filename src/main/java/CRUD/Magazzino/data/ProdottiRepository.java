package CRUD.Magazzino.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import CRUD.Magazzino.models.Prodotto;

public interface ProdottiRepository extends JpaRepository<Prodotto, Long>{
    List<Prodotto> findByGiacenzaGreaterThan(int value);
    List<Prodotto> findByNomeProdottoContainingIgnoreCase(String nome_prodotto);
}
