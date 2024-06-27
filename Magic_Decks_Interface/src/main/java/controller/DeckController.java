package controller;

import model.Deck;
import repository.DeckRepository;

import java.util.List;

public class DeckController {
    private DeckRepository deckRepository;

    public DeckController() {
        this.deckRepository = new DeckRepository();
    }

    public Deck criarDeck(Deck deck) {
        return (Deck) deckRepository.create(deck);
    }

    public Deck encontrarDeckPorId(Long id) {
        return (Deck) deckRepository.findById(id);
    }

    public Deck editarDeck(Deck deck) {
        return (Deck) deckRepository.update(deck);
    }

    public void deletarDeck(Long id) {
        deckRepository.delete(id);
    }

    public List<Deck> listarTodosDecks() {
        return deckRepository.findAll();
    }

    public void atualizarDeck(Deck deck) {
        deckRepository.update(deck);
    }
}
