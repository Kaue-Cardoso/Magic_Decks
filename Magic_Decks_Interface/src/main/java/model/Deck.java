package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    public Deck(){

    }
    public Deck(Long id, String nome, List<Carta> cartas) {
        this.id = id;
        this.nome = nome;
        this.cartas = new ArrayList<>();
        this.dataCriacao = new Date();
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

    public void adicionarCarta(Carta carta) {
        this.cartas.add(carta);
    }

    public void removerCarta(Carta carta) {
        this.cartas.remove(carta);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
