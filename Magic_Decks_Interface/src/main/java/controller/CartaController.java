package controller;

import model.Carta;
import service.CartaService;

public class CartaController {
    CartaService cartaService = new CartaService();

    public Carta buscaCartaId(int multiverseId) {
        return cartaService.buscarCartaPorId(multiverseId);
    }

    public void salvarCarta(Carta carta) {
        cartaService.salvarCarta(carta);
    }
}
