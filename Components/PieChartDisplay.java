package com.metro.Components;

import com.metro.ThemeManager;
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
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.style.Styler;

public class PieChartDisplay extends JPanel {

    private PieChart chart;

    public PieChartDisplay(String title, int width, int height) {
        this.chart = new PieChartBuilder().title(title).build();
        styleChart();
        setPreferredSize(new Dimension(width, height));
    }

    private void styleChart() {
        chart.getStyler().setChartTitleFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.white);
        chart.getStyler().setAnnotationsFontColor(Color.white);
        chart.getStyler().setAnnotationsFont(ThemeManager.getPoppinsFont(8, Font.BOLD));
 
        chart.getStyler().setAnnotationType(AnnotationType.Percentage);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);
        chart.getStyler().setLegendFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        chart.getStyler().setLegendBorderColor(Color.white);
        
        chart.getStyler().setInfoPanelFont(ThemeManager.getPoppinsFont(8, Font.PLAIN));
        chart.getStyler().setCircular(true);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        chart.getStyler().setDonutThickness(0.50);
        chart.getStyler().setAnnotationDistance(0.75);
        chart.getStyler().setStartAngleInDegrees(90);
        chart.getStyler().setSeriesColors(ThemeManager.getColorForPieChart());
        
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
