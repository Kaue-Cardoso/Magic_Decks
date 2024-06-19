package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table (name = "Deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Long id;
    @Column (name = "nome_deck")
    private String nome;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<Carta> cartas;

    public Deck(){

    }
    public Deck(Long id, String nome, List<Carta> cartas) {
        this.id = id;
        this.nome = nome;
        this.cartas = cartas;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }
}
