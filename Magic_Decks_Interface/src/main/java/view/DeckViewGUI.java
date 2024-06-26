package view;

import controller.CartaController;
import model.Carta;
import model.Deck;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DeckViewGUI extends JFrame {
    private Deck deck;
    private JTable cartaTable;
    private JLabel cardLabel; // Label para exibir a imagem da carta
    private ImageIcon defaultCardImage; // Imagem padrão
    private JTabbedPane tabbedPane; // Abas para informações adicionais
    private StatisticsPanel statisticsPanel; // JPanel para estatísticas

    public DeckViewGUI(Deck deck) {
        this.deck = deck;
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700); // Ajuste o tamanho conforme necessário
        setLocationRelativeTo(null);
        setTitle("Editar Deck: " + deck.getNome());
        setVisible(true);
    }

    private void initComponents() {
        // Carregar imagem padrão
        URL defaultCardUrl = getClass().getClassLoader().getResource("image/card.jpg");
        if (defaultCardUrl != null) {
            defaultCardImage = new ImageIcon(defaultCardUrl);
        } else {
            System.err.println("Imagem padrão não encontrada.");
            defaultCardImage = new ImageIcon(); // ou atribua null se preferir
        }

        JPanel contentPane = new JPanel(new BorderLayout());

        // Criando o JTabbedPane
        tabbedPane = new JTabbedPane();

        // Adicionando a tabela de cartas como uma aba
        JPanel tablePanel = new JPanel(new BorderLayout());
        cartaTable = new JTable();
        cartaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = cartaTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        Carta carta = deck.getCartas().get(selectedRow);
                        cardLabel.setIcon(defaultCardImage);
                        cardLabel.setText(null);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(cartaTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionando um JPanel para estatísticas como outra aba
        StatisticsPanel statisticsPanel = new StatisticsPanel(deck);

        // Adicionando as abas ao JTabbedPane
        tabbedPane.addTab("Cartas", tablePanel);
        tabbedPane.addTab("Estatísticas", statisticsPanel);

        // Adicionando o JTabbedPane ao contentPane
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // Painel para exibição da imagem da carta
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardLabel = new JLabel(defaultCardImage);
        cardPanel.add(cardLabel, BorderLayout.CENTER);

        // Botões abaixo da tabela
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Adicionar Carta");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCarta();
            }
        });
        buttonsPanel.add(addButton);

        JButton removeButton = new JButton("Remover Carta");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCarta();
            }
        });
        buttonsPanel.add(removeButton);

        JButton saveButton = new JButton("Salvar Deck");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDeck();
            }
        });
        buttonsPanel.add(saveButton);

        contentPane.add(cardPanel, BorderLayout.NORTH);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);

        // Listar cartas inicialmente
        listarCartas();
    }

    private void adicionarCarta() {
        String input = JOptionPane.showInputDialog(this, "Digite o ID da carta que deseja adicionar ao deck:");
        if (input != null && !input.isEmpty()) {
            try {
                int multiverseId = Integer.parseInt(input);
                CartaController cartaController = new CartaController();
                Carta carta = cartaController.buscaCartaId(multiverseId);
                if (carta != null) {
                    carta.setDeck(deck);
                    deck.adicionarCarta(carta);
                    cartaController.salvarCarta(carta);
                    listarCartas();
                    JOptionPane.showMessageDialog(this, "Carta '" + carta.getName() + "' adicionada ao deck.");
                } else {
                    JOptionPane.showMessageDialog(this, "Carta não encontrada.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        }
    }

    private void removerCarta() {
        int selectedRow = cartaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma carta para remover.");
            return;
        }

        Carta carta = deck.getCartas().get(selectedRow);
        carta.setDeck(null);
        deck.getCartas().remove(selectedRow);
        CartaController cartaController = new CartaController();
        cartaController.salvarCarta(carta);
        listarCartas();
        JOptionPane.showMessageDialog(this, "Carta removida do deck.");
    }

    private void listarCartas() {
        String[] columnNames = {"Nome", "Custo", "Cor", "Custo Combinado", "Tipo", "Descrição", "Poder", "Resistência"};
        Object[][] data = new Object[deck.getCartas().size()][columnNames.length];

        for (int i = 0; i < deck.getCartas().size(); i++) {
            Carta carta = deck.getCartas().get(i);
            data[i][0] = carta.getName();
            data[i][1] = carta.getManaCost();
            data[i][2] = carta.getColors();
            data[i][3] = carta.getCmc();
            data[i][4] = carta.getType();
            data[i][5] = carta.getDescription();
            data[i][6] = carta.getPower();
            data[i][7] = carta.getToughness();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        cartaTable.setModel(model);
    }

    private void salvarDeck() {
        int option = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações no deck?", "Salvar Deck", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            CartaController cartaController = new CartaController();
            for (Carta carta : deck.getCartas()) {
                cartaController.salvarCarta(carta);
            }
            JOptionPane.showMessageDialog(this, "Deck salvo com sucesso.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Deck deck = new Deck();
            new DeckViewGUI(deck);
        });
    }
}
