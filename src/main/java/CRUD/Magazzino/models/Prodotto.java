package CRUD.Magazzino.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "prodotti")
public class Prodotto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max=30, min=4) @NotBlank @Column(name = "nome_prodotto")
    private String nomeProdotto;

    @Min(0) @NotNull
    private int giacenza = 0;

    @Min(0) @NotNull
    private float prezzo;

    public void setNome_prodotto(String nome_prodotto) {
        this.nomeProdotto = nome_prodotto;
    }
    public void setGiacenza(int giacenza) {
        this.giacenza = giacenza;
    }
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public long getId() {return id;}
    public String getNome_prodotto() {return nomeProdotto;}
    public int getGiacenza() {return giacenza;}
    public float getPrezzo() {return prezzo;}
}
