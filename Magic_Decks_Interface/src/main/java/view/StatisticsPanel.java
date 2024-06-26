package view;

import model.Deck;
import model.Carta;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticsPanel extends JPanel {
    private Deck deck;

    public StatisticsPanel(Deck deck) {
        this.deck = deck;
        initComponents();
    }

    private void initComponents() {
        // Cria um conjunto de dados para o gráfico de exemplo
        CategoryDataset dataset = createDataset();
        // Cria o gráfico
        JFreeChart chart = createChart(dataset);
        // Cria um painel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        // Adiciona o painel de gráfico ao JPanel
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Mapa para contar a frequência dos diferentes valores de Cmc
        Map<Double, Integer> cmcCount = new HashMap<>();

        // Preenche o mapa com contagem de Cmc das cartas no deck
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



    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição de Custos de Mana das Cartas",
                "Custo de Mana (Cmc)",
                "Quantidade",
                dataset
        );
        return chart;
    }
}
