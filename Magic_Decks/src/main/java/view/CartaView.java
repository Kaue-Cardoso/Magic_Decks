package view;

import controller.CartaController;
import model.Carta;
import service.CartaService;

public class CartaView {
    private final CartaController cartaController;

    public CartaView() {
        this.cartaController = new CartaController(new CartaService());
    }

    public void exibirInformacoesCarta(String nomeCarta) {
        Carta carta = cartaController.buscarCartaPorNome(nomeCarta);

        if (carta != null) {
            System.out.println("Nome: " + carta.getName());
            System.out.println("Poder: " + carta.getPower());
            System.out.println("Descrição: " + carta.getDescription());
            // Adicione mais informações conforme necessário
        } else {
            System.out.println("Carta não encontrada.");
        }
    }
}
