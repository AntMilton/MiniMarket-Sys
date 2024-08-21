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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textNome;
    private JTextField textUsuario;
    private JTextField textEmail;
    private JPasswordField textSenha;
    private JPasswordField textConfSenha;
    private JComboBox<String> comboBoxPapel;
    private JLabel labelMensagem;
       private final JLabel title;
       private final UserDao userDao;
       private final  Perfil perfil;

    public Usuario(Perfil perfil, UserDao userDao) {
        this.perfil = perfil;
         this.userDao = new UserDao(); 
   
        setLayout(null);
         setBackground(Color.decode("#E2DFD0"));
         setSize(900,600);
         
        
         
         //salaryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
          title = new JLabel("Cadastro de  Usuario");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(300, 15, 300, 40);
        add(title);
        
        labelMensagem = new JLabel("");
        labelMensagem.setBounds(380, 80, 450, 25);
       labelMensagem.setFont(new Font("Arial", Font.BOLD, 17));
        labelMensagem.setForeground(Color.RED);
         add(labelMensagem);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(50, 150, 100, 25);
        add(labelNome);

        textNome = new JTextField();
        textNome.setBounds(380, 150, 350, 25);
         add(textNome);

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(50, 200, 100, 25);
         add(labelUsuario);

        textUsuario = new JTextField();
        textUsuario.setBounds(380, 200, 350, 25);
         add(textUsuario);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(50, 250, 100, 25);
        add(labelEmail);

        textEmail = new JTextField();
        textEmail.setBounds(380, 250, 350, 25);
         add(textEmail);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 300, 100, 25);
         add(labelSenha);

        textSenha = new JPasswordField();
        textSenha.setBounds(380, 300, 350, 25);
         add(textSenha);

        JLabel labelConfSenha = new JLabel("Confirmar Senha:");
        labelConfSenha.setBounds(50,350, 120, 25);
         add(labelConfSenha);

        textConfSenha = new JPasswordField();
        textConfSenha.setBounds(380, 350, 350, 25);
        add(textConfSenha);

        JLabel labelPapel = new JLabel("Cargo:");
        labelPapel.setBounds(50, 400, 100, 25);
        add(labelPapel);

       String[] papeis = { "Padrao","Gerente","Administrador" };
       comboBoxPapel = new JComboBox<>(papeis);
        comboBoxPapel.setBounds(380, 400, 150, 25);
        add(comboBoxPapel);
        
        

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(150, 450, 100, 30);
        btnCadastrar.addActionListener((ActionEvent e) -> {
            String nome = textNome.getText();
            String usuario = textUsuario.getText();
            String email = textEmail.getText();
            String senha = new String(textSenha.getPassword());
            String confSenha = new String(textConfSenha.getPassword());
            String papel = (String) comboBoxPapel.getSelectedItem();
            
            if (validarDados(nome, usuario, email, senha, confSenha, papel)) {
              Perfil  perfil2 = Perfil.valueOf(papel.toUpperCase()); 
                LocalDateTime datahoraCriacao = LocalDateTime.now();
                LocalDateTime ultimoLogin = LocalDateTime.now();
                User user = new User(nome, senha, usuario, perfil2, true, datahoraCriacao, email, Integer.parseInt("0"), ultimoLogin); 
                try {
                    userDao.inserirUser(user);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro"+ ex.getMessage());
                }
                labelMensagem.setText("Usuário cadastrado com sucesso!");
            } else {
               
            }
        });
        
        add(btnCadastrar);



    } private boolean validarDados(String nome, String usuario, String email, String senha, String confSenha, String papel) {
        if (nome.isEmpty() || usuario.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
            labelMensagem.setText("Todos os campos são obrigatórios.");
            return false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            labelMensagem.setText("Email inválido.");
            return false;
        } else if (senha.length() < 6) {
            labelMensagem.setText("A senha deve ter pelo menos 6 caracteres.");
            return false;
        } else if (!senha.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            labelMensagem.setText("A senha deve conter pelo menos um caractere especial.");
            return false;
        } else if (!senha.equals(confSenha)) {
            labelMensagem.setText("As senhas não coincidem.");
            return false;}
 
        return true;
    }
    
   
}
