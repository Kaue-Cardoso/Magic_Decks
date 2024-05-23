package repository;

import model.Deck;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    private List<Deck> decks = new ArrayList<>();

    public void adicionarDeck(Deck deck) {
        decks.add(deck);
    }

    public void apagarDeck(int indice) {
        if (indice >= 0 && indice < decks.size()) {
            decks.remove(indice);
        }
    }

    public List<Deck> listarDecks() {
        return new ArrayList<>(decks); // Retorna uma cópia para evitar manipulação direta
    }

    public Deck obterDeck(int indice) {
        if (indice >= 0 && indice < decks.size()) {
            return decks.get(indice);
        }
        return null;
    }

    public boolean isEmpty() {
        return decks.isEmpty();
    }
}
