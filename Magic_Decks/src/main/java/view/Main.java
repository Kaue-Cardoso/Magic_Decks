package view;

import model.Deck;
import repository.DeckRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static DeckRepository deckRepository = new DeckRepository();
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
                    create();
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

    private static void create() {
        System.out.println("Digite o nome do novo deck:");
        String nome = scanner.nextLine();
        Deck deck = new Deck(null, nome,null);
        deckRepository.create(deck);
        System.out.println("Deck '" + nome + "' adicionado com sucesso.");
    }

    private static void selecionarDeckParaEditar() {
        if (deckRepository.findAll()==null) {
            System.out.println("Nenhum deck disponível para editar.");
            return;
        }

        System.out.println("Selecione o deck para editar:");
        for (var x : deckRepository.findAll()){
            System.out.println(x.getId() +" - "+ x.getNome());
        }
        long indiceDeck = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer do scanner

        Deck deck = (Deck)deckRepository.findById(indiceDeck);
        if (deck == null) {
            System.out.println("Índice inválido.");
            return;
        }

        DeckView.editarDeck(deck);
    }

    private static void apagarDeck() {
        if (deckRepository.findAll()==null) {
            System.out.println("Nenhum deck disponível para apagar.");
            return;
        }

        System.out.println("Selecione o deck para apagar:");
        for (var x : deckRepository.findAll()){
            System.out.println(x.getId() +" - "+ x.getNome() + " " + x.getCartas().size());
        }

        long indiceDeck = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer do scanner

        Deck deck = (Deck)deckRepository.findById(indiceDeck);
        if (deck == null) {
            System.out.println("Índice inválido.");
            return;
        }

        deckRepository.delete(indiceDeck);
        System.out.println("Deck '" + deck.getNome() + "' apagado com sucesso.");
    }

    private static void listarDecks() {
        if (deckRepository.findAll()==null) {
            System.out.println("Nenhum deck criado.");
            return;
        }

        System.out.println("Decks criados:");
        for (var x : deckRepository.findAll()){
            System.out.println(x.getId() +" - "+ x.getNome() + " " + x.getCartas().size());
        }
    }
    }
