/*
package view;

import model.Deck;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Deck> decks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Adicionar Deck");
            System.out.println("2 - Editar Deck");
            System.out.println("3 - Apagar Deck");
            System.out.println("4 - Listar Decks");
            System.out.println("5 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    adicionarDeck();
                    break;
                case 2:
                    selecionarDeckParaEditar();
                    break;
                case 3:
                    apagarDeck();
                    break;
                case 4:
                    listarDecks();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarDeck() {
        System.out.println("Digite o nome do novo deck:");
        String nome = scanner.nextLine();
        Deck deck = new Deck(nome);
        decks.add(deck);
        System.out.println("Deck '" + nome + "' adicionado com sucesso.");
    }

    private static void selecionarDeckParaEditar() {
        if (decks.isEmpty()) {
            System.out.println("Nenhum deck disponível para editar.");
            return;
        }

        System.out.println("Selecione o deck para editar:");
        listarDecks();

        int indiceDeck = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (indiceDeck < 0 || indiceDeck >= decks.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Deck deck = decks.get(indiceDeck);
        DeckView.editarDeck(deck);
    }

    private static void apagarDeck() {
        if (decks.isEmpty()) {
            System.out.println("Nenhum deck disponível para apagar.");
            return;
        }

        System.out.println("Selecione o deck para apagar:");
        listarDecks();

        int indiceDeck = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (indiceDeck < 0 || indiceDeck >= decks.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Deck deck = decks.remove(indiceDeck);
        System.out.println("Deck '" + deck.getNome() + "' apagado com sucesso.");
    }

    private static void listarDecks() {
        if (decks.isEmpty()) {
            System.out.println("Nenhum deck criado.");
            return;
        }

        System.out.println("Decks criados:");
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            System.out.println(i + " - " + deck.getNome() + " (Quantidade de cartas: " + deck.getCartas().size() + ")");
        }
    }
}
*/