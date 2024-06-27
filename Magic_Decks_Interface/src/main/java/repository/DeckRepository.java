package repository;

import model.Deck;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class DeckRepository implements BasicCrud {
    private EntityManager em = Persistence.createEntityManagerFactory("magic_decks").createEntityManager();

    @Override
    public Deck create(Object object) {
        Deck deck = (Deck) object;
        em.getTransaction().begin();
        em.persist(deck);
        em.getTransaction().commit();
        return findById(deck.getId());
    }

    @Override
    public Deck findById(Long id) {
        try {
            return em.find(Deck.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Deck update(Object object) {
        Deck deckUpdate = (Deck) object;
        em.getTransaction().begin();
        em.merge(deckUpdate);
        em.getTransaction().commit();
        return deckUpdate;
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Deck deck = findById(id);
        em.remove(deck);
        em.getTransaction().commit();
    }

    public List<Deck> findAll() {
        return em.createQuery("SELECT d FROM Deck d", Deck.class).getResultList();
    }

}
