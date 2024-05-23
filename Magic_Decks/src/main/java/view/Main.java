package view;

import model.Carta;
import model.Deck;
import service.CartaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Deck> decks = new ArrayList<>();
    private static CartaService cartaService = new CartaService();
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
                    editarDeck();
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

    private static void editarDeck() {
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
            System.out.println("Carta '" + carta.getName() + "' adicionada ao deck."); // Concatenando o nome da carta
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






/*
        System.out.print("Digite o nome da carta:");
        String nomeCarta = scanner.nextLine();
        CartaView cartaView = new CartaView();
        cartaView.exibirInformacoesCarta(nomeCarta);
         */
        /**
        Scanner scanner = new Scanner(System.in);



        CartaService cartaService = new CartaService();
        Carta carta = cartaService.buscarCartaPorId(multiverseId);

        if (carta != null) {
            System.out.println("Carta encontrada:");
            System.out.println("Nome: " + carta.getName());
            System.out.println("Mana Cost: " + carta.getManaCost());
            System.out.println("Custo: " + carta.getCost());
            String[] cores = carta.getColors();
            if (cores != null && cores.length > 0) {
                System.out.println("Cores: " + String.join(", ", cores));
            } else {
                System.out.println("Cores: N/A");
            }

            System.out.println("Tipo: " + carta.getType());
            System.out.println("Descrição: " + carta.getDescription());
            System.out.println("Poder: " + carta.getPower());
            System.out.println("Toughness: " + carta.getToughness());
        } else {
            System.out.println("Carta não encontrada.");
        }

        scanner.close();
         */
