package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TitleScreen extends JFrame {

    public TitleScreen() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 640); // Tamanho inicial da janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setTitle("Tela de Título");
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Carregar e desenhar imagem de fundo
                URL imageUrl = getClass().getClassLoader().getResource("image/ikoria.jpg");
                if (imageUrl != null) {
                    ImageIcon imageIcon = new ImageIcon(imageUrl);
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.err.println("Imagem não encontrada.");
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Adicionar imagem em vez da mensagem de texto
        URL titleImageUrl = getClass().getClassLoader().getResource("image/MagicLogo.png");
        if (titleImageUrl != null) {
            ImageIcon titleImageIcon = new ImageIcon(titleImageUrl);
            Image titleImage = titleImageIcon.getImage();
            // Redimensionar a imagem, se necessário
            Image resizedTitleImage = titleImage.getScaledInstance(500, 170, Image.SCALE_SMOOTH);
            titleImageIcon = new ImageIcon(resizedTitleImage);
            JLabel titleImageLabel = new JLabel(titleImageIcon);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            panel.add(titleImageLabel, constraints);
        } else {
            System.err.println("Imagem de título não encontrada.");
        }

        JButton entrarButton = new JButton("Entrar");
        entrarButton.setBackground(Color.decode("#EE7214")); // Cor do botão
        entrarButton.setForeground(Color.BLACK); // Cor do texto
        entrarButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Fonte do texto
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMainGui();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2; // Ocupa duas colunas
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(entrarButton, constraints);

        add(panel);
    }

    private void abrirMainGui() {
        // Aqui você pode abrir a interface principal do seu programa (MainGUI)
        MainGUI mainGui = new MainGUI();
        mainGui.setVisible(true);

        // Fechar a tela de título após abrir a MainGui
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TitleScreen();
            }
        });
    }
}
