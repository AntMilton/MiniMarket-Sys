
package view;




import Controller.produtoController;
import Mode.DAO.UserDao;
import Model.Categoria;
import Model.Perfil;
import Model.Produto;
import Model.User;
import Model.UserSession;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class Estoque extends JPanel {
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;
    private JTextField txtBusca;
    private JComboBox<String> filtroQuantidade;
    private JComboBox<String> filtroPreco;
    private JTextArea areaRelatorio;
    private produtoController controller;
    private Perfil perfil;
 private User user1; 
 private UserDao userDao;
    public Estoque(produtoController controller,Perfil perfil) throws Exception {
      
         setSize(900, 600);
         setLayout(null);
         setBackground(Color.decode("#E2DFD0"));
        
        this.controller = controller;
       this.perfil = perfil;
        
        

        
        JLabel lblTitulo = new JLabel("Controle de Estoque");
        lblTitulo.setBounds(350, 15, 400, 30);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 25));
        add(lblTitulo);

       
        JLabel lblBusca = new JLabel("Buscar Produto:");
        lblBusca.setBounds(50, 60, 100, 25);
        add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(150, 60, 200, 25);
        add(txtBusca);

       
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(360, 60, 100, 25);
        add(btnBuscar);

       
        JLabel lblFiltroQuantidade = new JLabel("Quantidade:");
        lblFiltroQuantidade.setBounds(480, 60, 80, 25);
        add(lblFiltroQuantidade);

        filtroQuantidade = new JComboBox<>(new String[]{"Todos", "0-10", "11-50", "51-100", "100+"});
        filtroQuantidade.setBounds(560, 60, 100, 25);
        add(filtroQuantidade);

        JLabel lblFiltroPreco = new JLabel("Preço:");
        lblFiltroPreco.setBounds(670, 60, 50, 25);
        add(lblFiltroPreco);

        filtroPreco = new JComboBox<>(new String[]{"Todos", "0-10", "11-50", "51-100", "100+"});
        filtroPreco.setBounds(720, 60, 100, 25);
        add(filtroPreco);

        
        String[] colunas = {"ID", "Nome do Produto", "Quantidade", "Preço"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaEstoque = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);
        scrollPane.setBounds(50, 100, 800, 300);
        add(scrollPane);


        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.setBounds(50, 420, 200, 30);
        add(btnAdicionar);

     
        JButton btnAtualizar = new JButton("Atualizar Quantidade");
        btnAtualizar.setBounds(270, 420, 200, 30);
        add(btnAtualizar);

        // Campo de texto para atualizar quantidade
        JTextField txtQuantidade = new JTextField();
        txtQuantidade.setBounds(490, 420, 100, 30);
        add(txtQuantidade);

       
        JButton btnRemover = new JButton("Remover Produto");
        btnRemover.setBounds(610, 420, 200, 30);
        add(btnRemover);

      
        areaRelatorio = new JTextArea();
        areaRelatorio.setBounds(50, 460, 800, 80);
        areaRelatorio.setEditable(false);
        add(areaRelatorio);

        
        JButton btnRelatorio = new JButton("Gerar Relatório");
        btnRelatorio.setBounds(50, 550, 200, 30);
        add(btnRelatorio);


        atualizarTabela(controller.getProduto());

    
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    aplicarFiltros();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro com no metodo AplicarFiltros- BotaoBuscar"+ ex.getMessage());
                  
                }
            }
        });

        
        ActionListener filtroActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    aplicarFiltros();
                } catch (Exception ex) {
                     JOptionPane.showMessageDialog(null, "Ocorreu um erro com no metodo AplicarFiltros- filtroActionListener"+ ex.getMessage());
                 
                }
            }
        };

        filtroQuantidade.addActionListener(filtroActionListener);
        filtroPreco.addActionListener(filtroActionListener);

        
   btnAdicionar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                   User loggedUser = UserSession.getInstance().getLoggedUser();
                 
                 if (loggedUser!= null) {

                     String nome = JOptionPane.showInputDialog("Nome do Produto:");
                     if (nome != null && !nome.trim().isEmpty()) {
                         try {
                             
                             ArrayList<Categoria> categorias = controller.getCategorias();
                             

                             String[] nomesCategorias = new String[categorias.size()];
                             for (int i = 0; i < categorias.size(); i++) {
                                 nomesCategorias[i] = categorias.get(i).getNome();
                             }
                             
                            
                             String categoriaEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha a Categoria:", "Escolher Categoria", JOptionPane.QUESTION_MESSAGE, null, nomesCategorias, nomesCategorias[0]);
                             
                             if (categoriaEscolhida != null) { 
                                 try {
                                     int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade:"));
                                     double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço:"));
                                     
                                     // Obter o ID 
                                     int categoriaId = categorias.get(Arrays.asList(nomesCategorias).indexOf(categoriaEscolhida)).getCategoriaId();
                                     
                                     
                                     Produto produto = new Produto(nome, quantidade, preco, categoriaId, loggedUser.getUser_id());
                                     

                                     try {
                                         controller.addProduto(produto);
                                     } catch (Exception ex) {
                                         JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o produto: " + ex.getMessage());
                                     }
                                     
                                   
                                     atualizarTabela(controller.getProduto());
                                 } catch (NumberFormatException ex) {
                                     JOptionPane.showMessageDialog(null, "Por favor, insira valores válidos.");
                                 } catch (Exception ex) {
                                     JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o produto: " + ex.getMessage());
                                 }
                             }
                         } catch (Exception ex) {
                             Logger.getLogger(Estoque.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                 } else {
                     JOptionPane.showMessageDialog(null, "Por favor, faça login antes de adicionar um produto.");
                 }        }
         });

        
        btnAtualizar.addActionListener((ActionEvent e) -> {
            int linhaSelecionada = tabelaEstoque.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para atualizar a quantidade.");
                return;
            }
            
            try {
                int novaQuantidade = Integer.parseInt(txtQuantidade.getText());
                if (novaQuantidade < 0) {
                    JOptionPane.showMessageDialog(null, "A quantidade não pode ser negativa.");
                    return;
                }
                
                Produto produtoSelecionado = controller.getProduto().get(linhaSelecionada);
                produtoSelecionado.setQuantidade(novaQuantidade);
                controller.updateProduto(produtoSelecionado);
                aplicarFiltros();
                txtQuantidade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido."+ ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro no AplicarFiltros"+ ex.getMessage());
            }
        });

       
        btnRemover.addActionListener((ActionEvent e) -> {
            int linhaSelecionada = tabelaEstoque.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para remover.");
                return;
            }
            
            int option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este produto?", "Remover Produto", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    Produto produtoRemover = controller.getProduto().get(linhaSelecionada);
                    try {
                        controller.deleteProduto(produtoRemover.getProdutoId());
                    } catch (Exception ex) {
                        Logger.getLogger(Estoque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    aplicarFiltros();
                } catch (Exception ex) {
                    Logger.getLogger(Estoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Ação do botão gerar relatório
        btnRelatorio.addActionListener((ActionEvent e) -> {
            try {
                gerarRelatorio();
            } catch (Exception ex) {
                Logger.getLogger(Estoque.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

       
    }

    private void atualizarTabela(ArrayList<Produto> produtos) {
        modeloTabela.setRowCount(0);
        produtos.forEach((produto) -> {
            modeloTabela.addRow(new Object[]{produto.getProdutoId(), produto.getNome(), produto.getQuantidade(), produto.getPreco()});
        });
    }

    private void aplicarFiltros() throws Exception {
        String termoBusca = txtBusca.getText().toLowerCase();
        String filtroQtd = (String) filtroQuantidade.getSelectedItem();
        String filtroPr = (String) filtroPreco.getSelectedItem();

        ArrayList<Produto> produtosFiltrados = new ArrayList<>();
        for (Iterator<Produto> it = controller.getProduto().iterator(); it.hasNext();) {
            Produto produto = it.next();
            boolean match = true;
            if (!produto.getNome().toLowerCase().contains(termoBusca)) {
                match = false;
            }
            if (match && !filtroQtd.equals("Todos")) {
                int qtd = produto.getQuantidade();
                switch (filtroQtd) {
                    case "0-10":
                        if (qtd < 0 || qtd > 10) match = false;
                        break;
                    case "11-50":
                        if (qtd < 11 || qtd > 50) match = false;
                        break;
                    case "51-100":
                        if (qtd < 51 || qtd > 100) match = false;
                        break;
                    case "100+":
                        if (qtd < 101) match = false;
                        break;
                }
            }
            if (match && !filtroPr.equals("Todos")) {
                double pr = produto.getPreco();
                switch (filtroPr) {
                    case "0-10":
                        if (pr < 0 || pr > 10) match = false;
                        break;
                    case "11-50":
                        if (pr < 11 || pr > 50) match = false;
                        break;
                    case "51-100":
                        if (pr < 51 || pr > 100) match = false;
                        break;
                    case "100+":
                        if (pr < 101) match = false;
                        break;
                }
            }
            if (match) {
                produtosFiltrados.add(produto);
            }
        }
        atualizarTabela(produtosFiltrados);
    }

    private void gerarRelatorio() throws Exception {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de Estoque:\n");
        for (Produto produto : controller.getProduto()) {
            relatorio.append("ID: ").append(produto.getProdutoId())
                     .append(", Nome: ").append(produto.getNome())
                     .append(", Quantidade: ").append(produto.getQuantidade())
                     .append(", Preço: ").append(produto.getPreco()).append("\n");
        }
        areaRelatorio.setText(relatorio.toString());
    }
}