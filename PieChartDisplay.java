package com.metro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler.AnnotationType;

import java.util.Map;

public class PieChartDisplay extends JPanel {

    private PieChart chart;

    public PieChartDisplay(String title, int width, int height) {
        this.chart = new PieChartBuilder().title(title).build();
        styleChart();
        setPreferredSize(new Dimension(width, height));
    }

    private void styleChart() {
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.white);
        chart.getStyler().setAnnotationsFontColor(Color.white);
        chart.getStyler().setAnnotationsFont(ThemeManager.getPoppinsFont(8, Font.BOLD));
        chart.getStyler().setLegendBorderColor(Color.white);
        chart.getStyler().setAnnotationType(AnnotationType.LabelAndPercentage);
        chart.getStyler().setLegendVisible(false);
  
    }

    public void addData(Map<String, Double> data) {
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        chart.paint(g2d, getWidth(), getHeight());
    }
}
