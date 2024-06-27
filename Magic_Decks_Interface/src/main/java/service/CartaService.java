package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import model.Carta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartaService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public CartaService() {
        // Criar o EntityManagerFactory usando o nome da unidade de persistência
        emf = Persistence.createEntityManagerFactory("magic_decks");
        em = emf.createEntityManager();
    }

    public void fecharConexao() {
        em.close();
        emf.close();
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

    // Método auxiliar para converter uma carta da API para o modelo Carta
    private Carta converterParaModelo(Card card) {
        if (card == null) {
            return null;
        }

        List<String> colors = new ArrayList<>();
        if (card.getColors() != null) {
            colors.addAll(Arrays.asList(card.getColors()));
        }

        List<String> types = new ArrayList<>();
        if (card.getTypes() != null && card.getTypes().length > 0) {
            types.addAll(Arrays.asList(card.getTypes()));
        }

        return new Carta(
                null,
                card.getName(),
                card.getManaCost(),
                card.getCmc(),
                colors,
                types,
                card.getText(),
                card.getPower(),
                card.getToughness(),
                card.getImageUrl(),
                null
        );
    }

    public void salvarCarta(Carta carta) {
        try {
            em.getTransaction().begin();
            em.persist(carta);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao salvar carta: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }
}
