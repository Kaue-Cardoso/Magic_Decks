package view;

import controller.DeckController;
import model.Deck;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class MainGUI extends JFrame {
    private DeckController deckController = new DeckController();
    private JTable deckTable;
    private BackgroundPanel backgroundPanel;

    public MainGUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    if (info.getName().contains("Nimbus")) {
                        UIManager.put("nimbusBlueGrey", new Color(82, 120, 83));
                    }

                    break;
                }
            }
        } catch (Exception e) {
            // Handle exception
        }

        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 640);
        setLocationRelativeTo(null);
        setTitle("Menu Principal");

        // Carregar e definir o ícone do programa
        URL iconURL = getClass().getClassLoader().getResource("image/card.jpg");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Ícone do programa não encontrado.");
        }

        setVisible(true);
    }

    private void initComponents() {
        URL backgroundUrl = getClass().getClassLoader().getResource("image/ikoria.jpg");
        ImageIcon backgroundImage = null;
        if (backgroundUrl != null) {
            backgroundImage = new ImageIcon(backgroundUrl);
        } else {
            System.err.println("Imagem de fundo não encontrada.");
        }

        backgroundPanel = new BackgroundPanel(backgroundImage != null ? backgroundImage.getImage() : null);
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        URL cardImageUrl = getClass().getClassLoader().getResource("image/card.jpg");
        if (cardImageUrl != null) {
            ImageIcon magicCardImage = new ImageIcon(cardImageUrl);
            JLabel cardLabel = new JLabel(magicCardImage);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridheight = 1;
            backgroundPanel.add(cardLabel, constraints);
        } else {
            System.err.println("Imagem da carta não encontrada.");
        }

        deckTable = new JTable();
        deckTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
        deckTable.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(deckTable);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        backgroundPanel.add(scrollPane, constraints);

        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        JButton updateButton = new JButton("Atualizar");
        updateButton.setBackground(Color.decode("#EE7214"));
        updateButton.setForeground(Color.BLACK);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarDecks();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(updateButton, constraints);

        JButton editButton = new JButton("Editar Deck");
        editButton.setBackground(Color.decode("#EE7214"));
        editButton.setForeground(Color.BLACK);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDeck();
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 2;
        backgroundPanel.add(editButton, constraints);

        JButton addButton = new JButton("Adicionar Deck");
        addButton.setBackground(Color.decode("#EE7214"));
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarDeck();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        backgroundPanel.add(addButton, constraints);

        JButton deleteButton = new JButton("Apagar Deck");
        deleteButton.setBackground(Color.decode("#EE7214"));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apagarDeck();
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 3;
        backgroundPanel.add(deleteButton, constraints);

        JButton exitButton = new JButton("Sair");
        exitButton.setBackground(Color.decode("#EE7214"));
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 3;
        backgroundPanel.add(exitButton, constraints);

        add(backgroundPanel);

        listarDecks();
    }

    private void adicionarDeck() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do novo deck:");
        if (nome != null && !nome.isEmpty()) {
            Deck deck = new Deck(null, nome, null);
            deckController.criarDeck(deck);
            JOptionPane.showMessageDialog(this, "Deck '" + nome + "' adicionado com sucesso.");
            listarDecks();
        }
    }

    private void editarDeck() {
        int selectedRow = deckTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um deck para editar.");
            return;
        }

        Long deckId = (Long) deckTable.getValueAt(selectedRow, 0);
        Deck deck = deckController.encontrarDeckPorId(deckId);
        if (deck != null) {
            new DeckViewGUI(deck);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao encontrar o deck selecionado.");
        }
    }

    private void apagarDeck() {
        int selectedRow = deckTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um deck para apagar.");
            return;
        }

        Long deckId = (Long) deckTable.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja apagar o deck?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            deckController.deletarDeck(deckId);
            JOptionPane.showMessageDialog(this, "Deck apagado com sucesso.");
            listarDecks();
        }
    }

    private void listarDecks() {
        List<Deck> decks = deckController.listarTodosDecks();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Data de Criação", "Quantidade de Cartas"}, 0);

        for (Deck deck : decks) {
            // Carregar cartas para garantir a contagem correta
            deck.getCartas().size();
            tableModel.addRow(new Object[]{deck.getId(), deck.getNome(), deck.getDataCriacao(), deck.getCartas().size()});
        }

        deckTable.setModel(tableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }
}

class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(CENTER);
        return this;
    }
}

class BackgroundPanel extends JPanel {
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
