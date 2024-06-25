    package view;

    import model.Deck;
    import repository.DeckRepository;

    import javax.swing.*;
    import javax.swing.table.DefaultTableCellRenderer;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.net.URL;
    import java.util.List;

    public class MainGUI extends JFrame {

        private DeckRepository deckRepository = new DeckRepository();
        private JTable deckTable;

        public MainGUI() {
            // Alterar aparência e sensação para Nimbus
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());

                        // Personalização da cor da barra de título (opcional)
                        if (info.getName().contains("Nimbus")) {
                            UIManager.put("nimbusBlueGrey", new Color(82, 120, 83)); // Cor de fundo do Nimbus
                        }

                        break;
                        }
                }
            } catch (Exception e) {
                // Handle exception
            }

            initComponents();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1080, 640); // Ajustando o tamanho para acomodar a imagem ao lado da tabela
            setLocationRelativeTo(null);
            setTitle("Menu Principal");
            setVisible(true);
        }

        private void initComponents() {
            // Carregando imagem de fundo
            URL backgroundUrl = getClass().getClassLoader().getResource("image/ikoria.jpg");
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

            // Carregando imagem de uma carta de Magic
            URL cardImageUrl = getClass().getClassLoader().getResource("image/card.jpg");
            if (cardImageUrl != null) {
                ImageIcon magicCardImage = new ImageIcon(cardImageUrl);
                JLabel cardLabel = new JLabel(magicCardImage);
                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.gridheight = 2; // A imagem ocupará duas linhas
                backgroundPanel.add(cardLabel, constraints);
            } else {
                System.err.println("Imagem da carta não encontrada.");
            }

            // Criação da tabela
            deckTable = new JTable();
            deckTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Aplicando o renderizador de células personalizado
            CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
            deckTable.setDefaultRenderer(Object.class, cellRenderer);

            JScrollPane scrollPane = new JScrollPane(deckTable);
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridheight = 1; // A tabela ocupará apenas uma linha
            constraints.gridwidth = 2; // Abrange duas colunas
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            backgroundPanel.add(scrollPane, constraints);

            // Resetando as restrições
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 0;
            constraints.weighty = 0;
            constraints.anchor = GridBagConstraints.CENTER; // Centraliza os componentes

            JButton addButton = new JButton("Adicionar Deck");
            addButton.setBackground(Color.decode("#EE7214")); // Cor azul marinho
            addButton.setForeground(Color.BLACK);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    adicionarDeck();
                }
            });
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.fill = GridBagConstraints.BOTH; // Preenchimento horizontal
            constraints.weightx = 1.0; // Expandir horizontalmente
            constraints.weighty = 1.0; // Expandir horizontalmente
            constraints.anchor = GridBagConstraints.CENTER; // Ancoragem ao centro
            backgroundPanel.add(addButton, constraints);

            JButton editButton = new JButton("Editar Deck");
            editButton.setBackground(Color.decode("#EE7214")); // Cor verde limão
            editButton.setForeground(Color.BLACK);
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editarDeck();
                }
            });
            constraints.gridx = 2;
            constraints.gridy = 1;
            backgroundPanel.add(editButton, constraints);

            JButton deleteButton = new JButton("Apagar Deck");
            deleteButton.setBackground(Color.decode("#EE7214")); // Cor laranja vermelho
            deleteButton.setForeground(Color.BLACK);
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    apagarDeck();
                }
            });
            constraints.gridx = 1;
            constraints.gridy = 2;
            backgroundPanel.add(deleteButton, constraints);

            JButton exitButton = new JButton("Sair");
            exitButton.setBackground(Color.decode("#EE7214")); // Cor ouro
            exitButton.setForeground(Color.BLACK);
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            constraints.gridx = 2;
            constraints.gridy = 2;
            backgroundPanel.add(exitButton, constraints);

            add(backgroundPanel);

            listarDecks(); // Carrega os decks na tabela ao iniciar
        }

        private void adicionarDeck() {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome do novo deck:");
            if (nome != null && !nome.isEmpty()) {
                Deck deck = new Deck(null, nome, null); // Você precisa ajustar isso conforme sua classe Deck
                deckRepository.create(deck);
                JOptionPane.showMessageDialog(this, "Deck '" + nome + "' adicionado com sucesso.");
                listarDecks(); // Atualiza a tabela após adicionar
            }
        }

        private void editarDeck() {
            int selectedRow = deckTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um deck para editar.");
                return;
            }

            Long deckId = (Long) deckTable.getValueAt(selectedRow, 0);
            Deck deck = (Deck) deckRepository.findById(deckId);
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
            deckRepository.delete(deckId);
            JOptionPane.showMessageDialog(this, "Deck apagado com sucesso.");
            listarDecks(); // Atualiza a tabela após apagar
        }

        private void listarDecks() {
            List<Deck> decks = deckRepository.findAll();

            String[] columnNames = {"ID", "Nome", "Quantidade de Cartas", "Data de Criação"};
            Object[][] data = new Object[decks.size()][4];

            for (int i = 0; i < decks.size(); i++) {
                Deck deck = decks.get(i);
                data[i][0] = deck.getId();
                data[i][1] = deck.getNome();
                data[i][2] = (deck.getCartas() != null) ? deck.getCartas().size() : 0;
                data[i][3] = deck.getDataCriacao(); // Ajuste conforme o campo da data de criação
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            deckTable.setModel(model);
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
                    new MainGUI();
                }
            });
        }
    }
