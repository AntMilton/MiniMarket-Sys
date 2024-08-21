/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 

*/
package view;

import Controller.CategoriaController;
import Model.Categoria;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaView extends JPanel {
    private final CategoriaController controller;
    private final JTextField txtNome;
    private final JTextArea txtDescricao;
    private final JButton btnSalvar;
    private final JButton btnEditar;
    private final JButton btnExcluir;
    private final JTable tableCategorias;
    private final DefaultTableModel tableModel;

    public CategoriaView(CategoriaController controller) throws Exception {
        this.controller = controller;
        setLayout(null);
        setBackground(Color.decode("#E2DFD0"));
        
        JLabel title = new JLabel("Categorias");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(380, 15, 300, 40);
        add(title);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(50, 150, 100, 25);
       lblNome.setFont(new Font("Arial", Font.BOLD, 17));
        add(lblNome);

        txtNome = new JTextField(20);
        txtNome.setBounds(380, 150, 350, 25);
        add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
         lblDescricao.setBounds(50, 200, 100, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
         txtDescricao.setBounds(380, 200, 350, 50);
        add(txtDescricao);

        btnSalvar = new JButton("Salvar");
         btnSalvar.setBounds(50, 540, 100, 35);
        add(btnSalvar);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(160, 540, 80, 35);
        add(btnEditar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(280, 540, 80, 35);
        add(btnExcluir);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Permitir edição apenas nas colunas Nome e Descrição
            }
        };
        tableCategorias = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCategorias);
        scrollPane.setBounds(300, 300, 500, 200);
        add(scrollPane);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                try {
                    controller.adicionarCategoria(nome, descricao);
                } catch (Exception ex) {
                    Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    atualizarTabelaCategorias();
                } catch (Exception ex) {
                    Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCategorias.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String nome = (String) tableModel.getValueAt(selectedRow, 1);
                    String descricao = (String) tableModel.getValueAt(selectedRow, 2);
                    try {
                        controller.editarCategoria(id, nome, descricao);
                    } catch (Exception ex) {
                        Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        atualizarTabelaCategorias();
                    } catch (Exception ex) {
                        Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCategorias.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        controller.excluirCategoria(id);
                    } catch (Exception ex) {
                        Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        atualizarTabelaCategorias();
                    } catch (Exception ex) {
                        Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        atualizarTabelaCategorias();
    }

    private void atualizarTabelaCategorias() throws Exception {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Categoria> categorias = controller.listarCategorias();
        for (Categoria categoria : categorias) {
            tableModel.addRow(new Object[]{categoria.getCategoriaId(), categoria.getNome(), categoria.getDescricao()});
        }
    }
}

