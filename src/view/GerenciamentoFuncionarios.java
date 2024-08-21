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
  

import Mode.DAO.UserDao;
import Model.Perfil;
import Model.User;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciamentoFuncionarios extends JPanel {
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabela;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnDetalhes;
    private UserDao userDAO;
    private Perfil perfil;
    public GerenciamentoFuncionarios(Perfil perfil) throws Exception {
        this.perfil = perfil;
        userDAO = new UserDao();
        
        setSize(900, 600);
        setLayout(null);
        setBackground(Color.decode("#E2DFD0"));
        

        
        JLabel lblTitulo = new JLabel("Gerenciamento de Funcionários");
        lblTitulo.setBounds(200, 10, 400, 30);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 25));
        add(lblTitulo);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Senha", "Usuario", "Perfil", "Estado", "Criado", "Email", "Telefone", "Ultimo Login"}, 0);
        tabelaFuncionarios = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaFuncionarios);
        scrollPane.setBounds(50, 100, 800, 200);
        add(scrollPane);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(50, 470, 100, 30);
        add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(250, 470, 100, 30);
        add(btnEliminar);

        btnDetalhes = new JButton("Detalhes");
        btnDetalhes.setBounds(450, 470, 100, 30);
        add(btnDetalhes);

        carregarDados();

        // Ações dos botões
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                int selectedRow = tabelaFuncionarios.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int userId = (int) modeloTabela.getValueAt(selectedRow, 0);
                        User user = userDAO.buscarUser(userId);
                        if (user != null) {
                           
                            String novoNome = JOptionPane.showInputDialog("Editar Nome:", user.getNome());
                            if (novoNome != null && !novoNome.trim().isEmpty()) {
                                try {
                                    user.setNome(novoNome);
                                    userDAO.atualizarUser(user);
                                    carregarDados();
                                    JOptionPane.showMessageDialog(null, "Funcionário editado com sucesso.");
                                } catch (Exception ex) {
                                    Logger.getLogger(GerenciamentoFuncionarios.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(GerenciamentoFuncionarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário para editar.");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para eliminar funcionário
                int selectedRow = tabelaFuncionarios.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int userId = (int) modeloTabela.getValueAt(selectedRow, 0);
                        userDAO.deletarUser(userId);
                        carregarDados();
                        JOptionPane.showMessageDialog(null, "Funcionário eliminado com sucesso.");
                    } catch (Exception ex) {
                        Logger.getLogger(GerenciamentoFuncionarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário para eliminar.");
                }
            }
        });

        btnDetalhes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para ver detalhes do funcionário
                int selectedRow = tabelaFuncionarios.getSelectedRow();
                if (selectedRow >= 0) {
                    String nome = (String) modeloTabela.getValueAt(selectedRow, 1);
                    String usuario = (String) modeloTabela.getValueAt(selectedRow, 3);
                    String email = (String) modeloTabela.getValueAt(selectedRow, 7);
                    String telefone = modeloTabela.getValueAt(selectedRow, 8).toString();

                    JOptionPane.showMessageDialog(null, "Detalhes do funcionário:\nNome: " + nome + "\nUsuario: " + usuario + "\nEmail: " + email + "\nTelefone: " + telefone);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário para ver detalhes.");
                }
            }
        });
        
        setVisible(true);
    }

    private void carregarDados() throws Exception {
        modeloTabela.setRowCount(0);
        List<User> listaUsuarios = userDAO.listarTodosUsers();
        listaUsuarios.forEach((user) -> {
            modeloTabela.addRow(new Object[]{user.getUser_id(), user.getNome(), user.getSenha(), user.getUser(), user.getPerfil(), user.isEstado(), user.getDatahoraCriacao(), user.getEmail(), user.getTelefone(), user.getUltimoLogin()});
        });
    }

   
}
