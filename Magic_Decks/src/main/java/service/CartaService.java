package service;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import model.Carta;

import java.util.List;
import java.util.stream.Collectors;

public class CartaService {

    // Método para buscar uma carta por nome
    public Carta buscarCartaPorNome(String nome) {
        System.out.println("Fazendo chamada para a API para buscar carta por nome: " + nome);

        try {
            System.out.println("OOII <3");
            List<Card> cards = CardAPI.getAllCards();
            System.out.println("Teste 2: Chamada para a API realizada com sucesso.");
            for (Card card : cards) {
                System.out.println("Nome da carta: " + card.getName());
                if (card.getName().equalsIgnoreCase(nome)) {
                    System.out.println("Carta encontrada: " + card.getName());
                    return converterParaModelo(card);
                }
            }
            System.out.println("Carta não encontrada.");
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar carta na API: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Carta buscarCartaPorId(int multiverseId) {
        try {
            Card card = CardAPI.getCard(multiverseId);
            if (card != null) {
                return converterParaModelo(card);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar carta por ID: " + e.getMessage());
            return null;
        }
    }

    // Método para buscar todas as cartas de um determinado tipo
    public List<Carta> buscarCartasPorTipo(String tipo) {
        List<Card> cards = CardAPI.getAllCards();
        return cards.stream()
                .filter(c -> c.getType().equalsIgnoreCase(tipo))
                .map(this::converterParaModelo)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter uma carta da API para o modelo Carta
    private Carta converterParaModelo(Card card) {
        if (card == null) {
            return null;
        }
        return new Carta(
                card.getName(),
                card.getManaCost(),
                card.getCmc(),
                card.getColors(), //card.getColors(),
                card.getType(),
                card.getText(),
                card.getPower(),
                card.getToughness()
        );
    }
}
