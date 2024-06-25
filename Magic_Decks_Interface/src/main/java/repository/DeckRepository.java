
package repository;

import model.Deck;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository implements BasicCrud {
    private List<Deck> decks = new ArrayList<>();

    EntityManager em = Persistence.createEntityManagerFactory("magic_decks").createEntityManager();

    @Override
    public Object create(Object object) {
        Deck deck = (Deck) object;
        em.getTransaction().begin();
        em.persist(deck);
        em.getTransaction().commit();
        return findById(deck.getId()); //catch exception
    }

    @Override
    public Object findById(Long id) {
        try {
            Deck deckInBd = em.find(Deck.class, id);
            return deckInBd;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Object updateById(Object object) {
        Deck deckUpdate = (Deck) object;
        em.getTransaction().begin();
        em.merge(deckUpdate);
        em.getTransaction().commit();
        return null;
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Deck deck = (Deck) findById(id);
        em.remove(deck);
        em.getTransaction().commit();
    }

    public List<Deck> findAll(){
        return em.createQuery("SELECT d FROM Deck d", Deck.class).getResultList();
    }

}