package controller;

import model.Carta;
import service.CartaService;

import java.util.List;

public class CartaController {
    CartaService cartaService = new CartaService();

    // Construtor que recebe o serviço de cartas

    public Carta buscaCartaId(int multiverseId){
        return cartaService.buscarCartaPorId(multiverseId);
    }

    // Método para buscar uma carta por nome
    /* public Carta buscarCartaPorNome(String nome) {
        return cartaService.buscarCartaPorNome(nome);
    }

    // Método para buscar todas as cartas de um determinado tipo
    public List<Carta> buscarCartasPorTipo(String tipo) {
        return cartaService.buscarCartasPorTipo(tipo);
    } */
}
