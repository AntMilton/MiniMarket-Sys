/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Controller.CategoriaController;
import Controller.produtoController;
import Mode.DAO.CategoriaDAO;
import Mode.DAO.ProdutoDAO;
import Mode.DAO.UserDao;
import Mode.DAO.VendasDAO;
import Model.Conexao;
import Model.Perfil;
import Model.User;
import Model.UserSession;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final JButton btnVender;
    private final JButton btnEstoque;
    private final JButton btnConsultar;
    private final JButton btnFuncionario;
    private final JButton btnCategoria;
    private final JButton btnRelatorio;
    private final JButton btnUsuario;
    private final produtoController controller;
    private final CategoriaController categoriaController;
    private final Perfil perfil;
    private final UserDao userDao;
     private VendasView vendasView;
     private Conexao Conexao;
     private JLabel  userLabel;
   

    public Menu(Perfil perfil, UserDao userDao, produtoController controller) throws Exception {
        this.perfil = perfil;
        this.userDao = userDao;
        this.controller = controller;

        JFrame frame = new JFrame("ONE STORE");
        frame.setSize(1901, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        

        JLabel label = new JLabel();
        label.setBounds(0, 0, 1901, 90);
        label.setOpaque(true);
        label.setBackground(Color.decode("#028391"));
        frame.add(label);
        
        

        CategoriaDAO categoriaDAO = new CategoriaDAO();
         categoriaController = new CategoriaController(categoriaDAO);
         
           ProdutoDAO produtoDAO = new ProdutoDAO(Conexao); 
            VendasDAO vendasDAO = new VendasDAO(Conexao);
            vendasView = new VendasView();
           
         
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBounds(200, 100, 1701, 800);
        cardPanel.setBackground(Color.decode("#E2DFD0"));

        // Adiciona as telas ao cardPanel
         cardPanel.add(new VendasView(), "Vendas");
        cardPanel.add(new Usuario(perfil, userDao), "Usuario");
        cardPanel.add(new Estoque(controller, perfil), "Estoque");
        cardPanel.add(new RelatorioVendas(), "Relatorio");
       
        cardPanel.add(new CategoriaView(categoriaController), "Categoria");
        cardPanel.add(new GerenciamentoFuncionarios(perfil), "Funcionários");
      
        
               
       ImageIcon venderIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\cart1.png");
    ImageIcon estoqueIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\stock1.png");
    ImageIcon funcionarioIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\employees.png");
    ImageIcon categoriaIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\icons8-categories-35.png");
    ImageIcon relatorioIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\Relatorio.png");
    ImageIcon usuarioIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\icons8-user-35.png");
    ImageIcon sairIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\icons8-exit-35.png");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 100, 200, 900);
         buttonPanel.setBackground(Color.decode("#6B8A7A"));
        frame.add(buttonPanel);

        btnVender = new JButton("Vendas",venderIcon);
        btnVender.setBounds(20, 55, 135, 55);
        btnVender.addActionListener(e -> cardLayout.show(cardPanel, "Vendas"));
        buttonPanel.add(btnVender);

        btnEstoque = new JButton("Estoque",estoqueIcon);
        btnEstoque.setBounds(20, 120, 135, 55);
        btnEstoque.addActionListener(e -> cardLayout.show(cardPanel, "Estoque"));
        buttonPanel.add(btnEstoque);

        btnFuncionario = new JButton("Funcionários",funcionarioIcon);
        btnFuncionario.setBounds(20, 180, 135, 55);
        btnFuncionario.addActionListener(e -> cardLayout.show(cardPanel, "Funcionários"));
        buttonPanel.add(btnFuncionario);

        btnCategoria = new JButton("Categoria",categoriaIcon);
        btnCategoria.setBounds(20, 240, 135, 55);
        btnCategoria.addActionListener(e -> cardLayout.show(cardPanel, "Categoria"));
        buttonPanel.add(btnCategoria);

        btnRelatorio = new JButton("Relatorio",relatorioIcon);
        btnRelatorio.setBounds(20, 300, 135, 55);
        btnRelatorio.addActionListener(e -> cardLayout.show(cardPanel, "Relatorio"));
        buttonPanel.add(btnRelatorio);

        btnUsuario = new JButton("Usuario",usuarioIcon);
        btnUsuario.setBounds(20, 360, 135, 55);
        btnUsuario.addActionListener(e -> cardLayout.show(cardPanel, "Usuario"));
        buttonPanel.add(btnUsuario);
        
         btnConsultar = new JButton("Sair",sairIcon);
        btnConsultar.setBounds(20, 425, 135, 55);
         btnConsultar.addActionListener(e -> sair());
        buttonPanel.add(btnConsultar);

        frame.add(cardPanel);
        frame.setVisible(true);

        ajustarPermissoes();
    }

    private void ajustarPermissoes() {
          User loggedUser = UserSession.getInstance().getLoggedUser();
        if (loggedUser != null) {
            switch (loggedUser.getPerfil())  {
            case PADRAO:
                btnVender.setVisible(true);
                btnFuncionario.setVisible(false);
                btnCategoria.setVisible(false);
                btnRelatorio.setVisible(false);
                btnUsuario.setVisible(false);
                btnEstoque.setVisible(false);
               
                break;
            case GERENTE:
                btnFuncionario.setVisible(false);
                btnUsuario.setVisible(false);
                btnCategoria.setVisible(false);
                break;
            case ADMIN:
                // Administrador tem acesso total
                break;
        }
    }else{
            JOptionPane.showMessageDialog(null,"Nenhum User Logado");  
        }}

    
      private void sair() {
        int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            UserSession.getInstance().logout();
            
            new Login(); 
            ((JFrame) cardPanel.getTopLevelAncestor()).dispose();
        }
    }
    
}
