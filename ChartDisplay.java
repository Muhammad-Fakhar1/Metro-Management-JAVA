/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.metro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.SwingWrapper;

import java.util.List;
import javax.swing.JPanel;
import org.knowm.xchart.style.Styler;

public class ChartDisplay extends JPanel {

    private XYChart chart;

    public ChartDisplay(String title, String xAxisTitle, String yAxisTitle, int width, int height) {
        chart = new XYChartBuilder()
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        styleChart();
        setPreferredSize(new Dimension(width, height));
    }

    private void styleChart() {
        chart.getStyler().setChartBackgroundColor(java.awt.Color.WHITE);
        chart.getStyler().setPlotGridLinesColor(java.awt.Color.LIGHT_GRAY);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendBackgroundColor(java.awt.Color.WHITE);
        chart.getStyler().setChartFontColor(java.awt.Color.DARK_GRAY);
        chart.getStyler().setChartTitleFont(ThemeManager.getPoppinsFont(14, Font.BOLD));
        chart.getStyler().setAxisTickLabelsColor(java.awt.Color.DARK_GRAY);
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setPlotGridVerticalLinesVisible(false);
        
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        chart.getStyler().setAxisTicksLineVisible(false);
        chart.getStyler().setAxisTickLabelsColor(Color.gray);
        chart.getStyler().setAxisTickLabelsFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        chart.getStyler().setAxisTickPadding(20);
    }

    public void addSeries(String seriesName, List<Number> xData, List<Number> yData) {
        chart.addSeries(seriesName, xData, yData);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        chart.paint(g2d, getWidth(), getHeight());
    }
}
