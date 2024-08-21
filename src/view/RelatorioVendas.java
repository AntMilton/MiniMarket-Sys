/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author ANTONIO MILTON
 */

import Mode.DAO.RelatorioDAO;
import Model.Perfil;
import Model.Vendas;
import java.awt.Color;
import java.awt.Font;
  import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class RelatorioVendas extends JPanel {

    private DefaultTableModel modeloTabela;
    private DefaultCategoryDataset dataset;
    //private Vendas vendas;
    private Perfil perfil;
    
    public RelatorioVendas() {
        // Configuração da janela principal
        
        setSize(1200, 800);
       
        setLayout(null);
        setBackground(Color.decode("#E2DFD0"));
        // Label para título
        JLabel lblTitulo = new JLabel("Relatório de Vendas");
        lblTitulo.setBounds(500, 20, 400, 30);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 25));
        add(lblTitulo);

        
        JLabel lblDataInicio = new JLabel("Data Início:");
        lblDataInicio.setBounds(50, 70, 100, 30);
        add(lblDataInicio);

        JTextField txtDataInicio = new JTextField();
        txtDataInicio.setBounds(150, 70, 100, 30);
        add(txtDataInicio);

        JLabel lblDataFim = new JLabel("Data Fim:");
        lblDataFim.setBounds(300, 70, 100, 30);
        add(lblDataFim);

        JTextField txtDataFim = new JTextField();
        txtDataFim.setBounds(400, 70, 100, 30);
        add(txtDataFim);

        
        String[] colunas = {"ID Venda", "Data", "Valor Total"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabelaVendas = new JTable(modeloTabela);

        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        scrollPane.setBounds(50, 120, 500, 500);
        add(scrollPane);

        // Dataset para o gráfico
        dataset = new DefaultCategoryDataset();

        // Criação do gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Vendas por Data",
                "Data",
                "Valor Total",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBounds(800, 120, 350, 500);
        add(chartPanel);

      
        

         JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        btnGerarRelatorio.setBounds(550, 70, 150, 30);
        
        btnGerarRelatorio.addActionListener((ActionEvent e) -> {
            try {
                List<Vendas> vendas = RelatorioDAO.getVendas();
                
                modeloTabela.setRowCount(0);
                dataset.clear();
                
                vendas.stream().map((venda) -> {
                    modeloTabela.addRow(new Object[]{venda.getVendaId(), venda.getDataHoraCriacao(), venda.getTotalVenda()});
                    return venda;
                }).forEachOrdered((venda) -> {
                    dataset.addValue(venda.getTotalVenda(), "Vendas", venda.getDataHoraCriacao());
                });
            } catch (Exception ex) {
                Logger.getLogger(RelatorioVendas.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
        
        
        add(btnGerarRelatorio);







        setVisible(true);
    }

   
}
