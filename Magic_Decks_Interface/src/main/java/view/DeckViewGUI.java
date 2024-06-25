package view;

import controller.CartaController;
import model.Carta;
import model.Deck;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeckViewGUI extends JFrame {

    private Deck deck;
    private JTable cartaTable;

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
        // Panel com imagem de fundo
        ImageIcon backgroundImage = new ImageIcon("E:\\Github\\Magic_Decks\\Magic_Decks\\src\\main\\java\\image\\ikoria2.jpg");
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage.getImage());
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Tabela de Cartas
        cartaTable = new JTable();
        cartaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Aplicando o renderizador de células personalizado
        CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
        cartaTable.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(cartaTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3; // A tabela ocupará três linhas
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        backgroundPanel.add(scrollPane, constraints);

        // Adicionar imagem ao lado da tabela
        ImageIcon magicCardImage = new ImageIcon("E:\\Github\\Magic_Decks\\Magic_Decks\\src\\main\\java\\image\\card.jpg");
        JLabel cardLabel = new JLabel(magicCardImage);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1; // A imagem ocupará uma linha
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
                    deck.adicionarCarta(carta);
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

        deck.getCartas().remove(selectedRow);
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
            data[i][7] = carta.getToughness();// Ajuste conforme seus atributos

            // Adicionar mais campos conforme necessário
        }

        // Criar o modelo de tabela com os dados e colunas
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        cartaTable.setModel(model);
    }

    // Renderizador de células personalizado para alternar cores de fundo das linhas
    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Definindo cores alternadas para cada linha
            if (row % 2 == 0) {
                rendererComponent.setBackground(Color.decode("#F9E8D9")); // Cor de fundo para linhas pares
            } else {
                rendererComponent.setBackground(Color.decode("#F7B787")); // Cor de fundo para linhas ímpares
            }

            return rendererComponent;
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
                // Exemplo de uso
                Deck deck = new Deck(); // Crie um deck como exemplo
                deck.setNome("Meu Deck");
                DeckViewGUI deckViewGUI = new DeckViewGUI(deck);
            }
        });
    }
}
