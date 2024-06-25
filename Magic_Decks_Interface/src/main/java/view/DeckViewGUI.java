package view;

import controller.CartaController;
import model.Carta;
import model.Deck;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
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

    public DeckViewGUI(Deck deck) {
        this.deck = deck;
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 580); // Ajuste o tamanho conforme necessário
        setLocationRelativeTo(null);
        setTitle("Editar Deck: " + deck.getNome());
        setVisible(true);
    }

    private void initComponents() {
        URL backgroundUrl = getClass().getClassLoader().getResource("image/ikoria2.jpg");
        ImageIcon backgroundImage = null;
        if (backgroundUrl != null) {
            backgroundImage = new ImageIcon(backgroundUrl);
        } else {
            System.err.println("Imagem de fundo não encontrada.");
        }

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage != null ? backgroundImage.getImage() : null);
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Carregar imagem padrão
        URL defaultCardUrl = getClass().getClassLoader().getResource("image/card.jpg");
        if (defaultCardUrl != null) {
            defaultCardImage = new ImageIcon(defaultCardUrl);
        } else {
            System.err.println("Imagem padrão não encontrada.");
            defaultCardImage = new ImageIcon(); // ou atribua null se preferir
        }

        // Tabela de Cartas
        cartaTable = new JTable();
        cartaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adiciona um listener para seleção de linhas na tabela
        cartaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = cartaTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        Carta carta = deck.getCartas().get(selectedRow);
                        exibirImagemCarta(carta.getImageUrl());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(cartaTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3; // A tabela ocupará três linhas
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        backgroundPanel.add(scrollPane, constraints);

        cardLabel = new JLabel(defaultCardImage);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1; // A imagem ocupará uma linha
        constraints.weightx = 0.5; // Peso horizontal para a imagem
        constraints.weighty = 0.5; // Peso vertical para a imagem
        constraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(cardLabel, constraints);

        // Botão Adicionar Carta
        JButton addButton = new JButton("Adicionar Carta");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCarta();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        backgroundPanel.add(addButton, constraints);

        // Botão Remover Carta
        JButton removeButton = new JButton("Remover Carta");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCarta();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 2;
        backgroundPanel.add(removeButton, constraints);

        // Botão Salvar Deck
        JButton saveButton = new JButton("Salvar Deck");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDeck();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        backgroundPanel.add(saveButton, constraints);

        // Adicionando o painel de fundo ao JFrame
        add(backgroundPanel);

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
                    carta.setDeck(deck); // Associa a carta ao deck atual
                    deck.adicionarCarta(carta); // Adiciona a carta ao deck
                    cartaController.salvarCarta(carta); // Salva a carta no banco de dados
                    listarCartas(); // Atualiza a lista de cartas após adicionar
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
        carta.setDeck(null); // Desassocia a carta do deck
        deck.getCartas().remove(selectedRow);
        CartaController cartaController = new CartaController();
        cartaController.salvarCarta(carta); // Atualiza a carta no banco de dados
        listarCartas(); // Atualiza a lista de cartas após remover
        JOptionPane.showMessageDialog(this, "Carta removida do deck.");
    }

    private void listarCartas() {
        // Definindo as colunas da tabela
        String[] columnNames = {"Nome", "Custo", "Cor", "Custo Combinado", "Tipo", "Descrição", "Poder", "Resistencia"};

        // Preparando os dados das cartas
        Object[][] data = new Object[deck.getCartas().size()][columnNames.length];

        for (int i = 0; i < deck.getCartas().size(); i++) {
            Carta carta = deck.getCartas().get(i);
            // Preencher os dados conforme sua lógica
            data[i][0] = carta.getName();
            data[i][1] = carta.getManaCost();
            data[i][2] = carta.getColors();
            data[i][3] = carta.getCmc();
            data[i][4] = carta.getType();
            data[i][5] = carta.getDescription();
            data[i][6] = carta.getPower();
            data[i][7] = carta.getToughness(); // Ajuste conforme seus atributos

            // Adicionar mais campos conforme necessário
        }

        // Criar o modelo de tabela com os dados e colunas
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        cartaTable.setModel(model);
    }

    private void salvarDeck() {
        int option = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações no deck?", "Salvar Deck", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            CartaController cartaController = new CartaController();
            for (Carta carta : deck.getCartas()) {
                cartaController.salvarCarta(carta); // Salva cada carta no banco de dados
            }
            JOptionPane.showMessageDialog(this, "Deck salvo com sucesso.");
        }
    }

    private void exibirImagemCarta(String imageUrl) {
        try {
            System.out.println("Tentando carregar a imagem da URL: " + imageUrl);
            URL url = new URL(imageUrl);
            ImageIcon cardImage = new ImageIcon(url);
            if (cardImage.getIconWidth() > 0) {
                cardLabel.setIcon(cardImage);
                cardLabel.setText(null); // Remove qualquer texto que possa estar presente
                System.out.println("Imagem carregada com sucesso.");
            } else {
                throw new Exception("Imagem não foi carregada corretamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cardLabel.setIcon(defaultCardImage); // Volta para a imagem padrão em caso de erro
            cardLabel.setText("Imagem não disponível: " + e.getMessage());
        }
    }


    // JPanel personalizado para fundo com imagem
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Exemplo de inicialização do deck (substitua pelo seu próprio método de inicialização)
                Deck deck = new Deck();
                new DeckViewGUI(deck);
            }
        });
    }
}
