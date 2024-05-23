package model;

import model.Carta;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String nome;
    private List<Carta> cartas;

    public Deck(String nome) {
        this.nome = nome;
        this.cartas = new ArrayList<>();
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
