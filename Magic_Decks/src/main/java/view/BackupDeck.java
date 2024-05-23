/*
package view;

import model.Carta;
import model.Deck;
import service.CartaService;

import java.util.Scanner;

public class DeckView {
    private static CartaService cartaService = new CartaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void editarDeck(Deck deck) {
        while (true) {
            System.out.println("Selecione uma opção de edição:");
            System.out.println("1 - Adicionar carta");
            System.out.println("2 - Listar cartas");
            System.out.println("3 - Remover carta");
            System.out.println("4 - Voltar ao menu principal");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    adicionarCartaAoDeck(deck);
                    break;
                case 2:
                    listarCartasDoDeck(deck);
                    break;
                case 3:
                    removerCartaDoDeck(deck);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarCartaAoDeck(Deck deck) {
        System.out.println("Digite o ID da carta que deseja adicionar ao deck:");
        int multiverseId = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        System.out.println("Buscando Carta");
        Carta carta = cartaService.buscarCartaPorId(multiverseId);
        if (carta != null) {
            deck.adicionarCarta(carta);
            System.out.println("Carta '" + carta.getName() + "' adicionada ao deck.");
        } else {
            System.out.println("Carta não encontrada.");
        }
    }

    private static void listarCartasDoDeck(Deck deck) {
        if (deck.getCartas().isEmpty()) {
            System.out.println("O deck não possui cartas.");
        } else {
            System.out.println("Cartas no deck '" + deck.getNome() + "':");
            for (Carta carta : deck.getCartas()) {
                System.out.println("  Nome: " + carta.getName());
                System.out.println("  Tipo: " + carta.getType());
                System.out.println("  Custo de Mana: " + carta.getManaCost());
                System.out.println("  Custo Total: " + carta.getCmc());
                System.out.println("  Cores: " + String.join(", ", carta.getColors()));
                System.out.println("  Descrição: " + carta.getDescription());
                System.out.println("  Poder: " + carta.getPower());
                System.out.println("  Resistência: " + carta.getToughness());
                System.out.println();
            }
        }
    }

    private static void removerCartaDoDeck(Deck deck) {
        if (deck.getCartas().isEmpty()) {
            System.out.println("O deck não possui cartas para remover.");
            return;
        }

        System.out.println("Cartas no deck '" + deck.getNome() + "':");
        for (int i = 0; i < deck.getCartas().size(); i++) {
            Carta carta = deck.getCartas().get(i);
            System.out.println(i + " - " + carta.getName());
        }

        System.out.println("Selecione o índice da carta que deseja remover:");
        int indiceCarta = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (indiceCarta < 0 || indiceCarta >= deck.getCartas().size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Carta cartaRemovida = deck.getCartas().remove(indiceCarta);
        System.out.println("Carta '" + cartaRemovida.getName() + "' removida do deck.");
    }
}
*/