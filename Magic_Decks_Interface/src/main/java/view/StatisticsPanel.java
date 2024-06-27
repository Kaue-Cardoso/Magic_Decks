package view;

import controller.DeckController;
import model.Deck;
import model.Carta;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsPanel extends JPanel {
    private Deck deck;
    private DeckController deckController;

    public StatisticsPanel(Deck deck) {
        this.deck = deck;
        this.deckController = new DeckController();
        initComponents();
    }

    private void initComponents() {
        // Configura layout do painel
        this.setLayout(new GridLayout(1, 3)); // Três gráficos lado a lado

        // Cria e adiciona o gráfico de barras ao painel
        CategoryDataset barDataset = createBarDataset();
        JFreeChart barChart = createBarChart(barDataset);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        this.add(barChartPanel);

        // Cria e adiciona o gráfico de pizza para tipos de cartas ao painel
        PieDataset typePieDataset = createTypePieDataset();
        JFreeChart typePieChart = createPieChart(typePieDataset, "Distribuição de Tipos de Cartas");
        ChartPanel typePieChartPanel = new ChartPanel(typePieChart);
        this.add(typePieChartPanel);

        // Cria e adiciona o gráfico de pizza para cores das cartas ao painel
        PieDataset colorPieDataset = createColorPieDataset();
        JFreeChart colorPieChart = createColorPieChart(colorPieDataset, "Distribuição de Cores das Cartas");
        ChartPanel colorPieChartPanel = new ChartPanel(colorPieChart);
        this.add(colorPieChartPanel);
    }

    private CategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Mapa para contar a frequência dos diferentes valores de Cmc
        Map<Double, Integer> cmcCount = new HashMap<>();

        // Preenche o mapa com contagem de Cmc das cartas do deck específico
        for (Carta carta : deck.getCartas()) {
            double cmc = carta.getCmc();
            cmcCount.put(cmc, cmcCount.getOrDefault(cmc, 0) + 1);
        }

        // Adiciona os valores de Cmc e suas quantidades ao dataset
        for (Map.Entry<Double, Integer> entry : cmcCount.entrySet()) {
            dataset.addValue(entry.getValue(), "Custo de Mana", String.valueOf(entry.getKey()));
        }

        return dataset;
    }

    private JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Custos de Mana", // Título do gráfico
                "Custo de Mana Combinado (Cmc)", // Rótulo do eixo X
                "Quantidade", // Rótulo do eixo Y
                dataset, // Conjunto de dados
                PlotOrientation.VERTICAL, // Orientação do gráfico
                true, // Incluir legenda
                true, // Exibir tooltips
                false // Não incluir URLs
        );

        // Personaliza o plot do gráfico
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white); // Cor de fundo do gráfico
        plot.setRangeGridlinePaint(Color.black); // Cor das linhas de grade

        // Personaliza o eixo Y (Quantidade)
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // Personaliza o eixo X (Custo de Mana)
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryMargin(0.7); // Espaço entre as categorias
        domainAxis.setLowerMargin(0.02); // Margem esquerda do eixo X
        domainAxis.setUpperMargin(0.02); // Margem direita do eixo X

        return chart;
    }

    private PieDataset createTypePieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Mapa para contar a frequência dos diferentes tipos de cartas
        Map<String, Integer> typeCount = new HashMap<>();

        // Preenche o mapa com contagem de tipos das cartas do deck específico
        for (Carta carta : deck.getCartas()) {
            for (String type : carta.getTypes()) {
                typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
            }
        }

        // Adiciona os tipos e suas quantidades ao dataset
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }

    private PieDataset createColorPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Mapa para contar a frequência das diferentes cores de cartas
        Map<String, Integer> colorCount = new HashMap<>();

        // Mapeamento de códigos de cores para nomes completos
        Map<String, String> colorNames = new HashMap<>();
        colorNames.put("W", "Branco");
        colorNames.put("U", "Azul");
        colorNames.put("B", "Preto");
        colorNames.put("R", "Vermelho");
        colorNames.put("G", "Verde");
        colorNames.put("C", "Sem Cor");

        // Preenche o mapa com contagem de cores das cartas do deck específico
        for (Carta carta : deck.getCartas()) {
            for (String color : carta.getColors()) {
                String colorName = colorNames.getOrDefault(color, color);
                colorCount.put(colorName, colorCount.getOrDefault(colorName, 0) + 1);
            }
        }

        // Adiciona as cores e suas quantidades ao dataset
        for (Map.Entry<String, Integer> entry : colorCount.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }

    private JFreeChart createPieChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart(
                title, // Título do gráfico
                dataset, // Conjunto de dados
                true, // Incluir legenda
                true, // Exibir tooltips
                false // Não incluir URLs
        );

        // Personaliza o plot do gráfico de pizza
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white); // Cor de fundo do gráfico
        plot.setCircular(true); // Definir o gráfico como circular
        plot.setLabelGap(0.02); // Espaço entre os rótulos e o gráfico

        return chart;
    }

    private JFreeChart createColorPieChart(PieDataset dataset, String title) {
        JFreeChart chart = createPieChart(dataset, title);

        // Personaliza as cores das fatias
        PiePlot plot = (PiePlot) chart.getPlot();

        // Define cores para as fatias
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("Branco", Color.WHITE);
        colorMap.put("Azul", Color.BLUE);
        colorMap.put("Preto", Color.BLACK);
        colorMap.put("Vermelho", Color.RED);
        colorMap.put("Verde", Color.GREEN);
        colorMap.put("Sem Cor", Color.GRAY);

        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            plot.setSectionPaint(entry.getKey(), entry.getValue());
        }

        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeckController deckController = new DeckController();
            List<Deck> decks = deckController.listarTodosDecks();
            if (!decks.isEmpty()) {
                Deck deck = decks.get(0); // Exemplo: obtém o primeiro deck da lista
                StatisticsPanel panel = new StatisticsPanel(deck);

                JFrame frame = new JFrame("Estatísticas de Cartas");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum deck encontrado.");
            }
        });
    }
}
