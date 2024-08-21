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


import Mode.DAO.ProdutoDAO;
import Mode.DAO.VendasDAO;
import Model.Conexao;
import Model.Perfil;
import Model.Produto;
import Model.UserSession;
import Model.Vendas;
import Model.VendasItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendasView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtCodigoProduto;
    private JTextField txtQuantidade;
    private JTextField txtPrecoUnitario;
    private JTextField txtPrecoTotal;
    private JTextField txtPagamento;
    private JTextField txtTotal;
    private JTextField txtTroco;
    private JTextField txtSubtotal;
    private JTextField txtNomeProduto;
    private JTextField txtDescontos;
    private JTextField txtImpostos;
    private JButton btnAdicionarCarrinho;
    private JButton btnFinalizarVenda;
    private JButton btnCancelarVenda;
    private JTable tabelaCarrinho;
    public DefaultTableModel modeloTabela;
    
    private Perfil perfil;
   // private VendasController vendasController;
    private ProdutoDAO produtoDao;
      private java.util.List<VendasItem> itens;
      private VendasDAO vendasDao;
     private final List<Produto> produtosCarrinho;
     private Produto produto;
     private Conexao Conexao;

    public VendasView() {
        setPreferredSize(new Dimension(900, 600));
        setLayout(null);
        setBackground(Color.decode("#E2DFD0"));
        
           produtosCarrinho = new ArrayList<>();  
           produtoDao = new ProdutoDAO(Conexao);
        vendasDao = new VendasDAO(Conexao);
        itens = new ArrayList<>();
        
        // Campos e componentes da tela de vendas
        JLabel lblCodigoProduto = new JLabel("Código do Produto:");
        lblCodigoProduto.setBounds(20, 20, 120, 25);
        add(lblCodigoProduto);

        txtCodigoProduto = new JTextField();
        txtCodigoProduto.setBounds(150, 20, 150, 25);
        txtCodigoProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preencherDetalhesProduto();
            }

          
        });
        add(txtCodigoProduto);

        JLabel lblNomeProduto = new JLabel("Nome do Produto:");
        lblNomeProduto.setBounds(20, 50, 120, 25);
        add(lblNomeProduto);

        txtNomeProduto = new JTextField();
        txtNomeProduto.setEditable(false);
        txtNomeProduto.setBounds(150, 50, 150, 25);
        add(txtNomeProduto);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(20, 80, 120, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(150, 80, 150, 25);
        add(txtQuantidade);

        JLabel lblPrecoUnitario = new JLabel("Preço Unitário:");
        lblPrecoUnitario.setBounds(20, 110, 120, 25);
        add(lblPrecoUnitario);

        txtPrecoUnitario = new JTextField();
        txtPrecoUnitario.setEditable(false);
        txtPrecoUnitario.setBounds(150, 110, 150, 25);
        add(txtPrecoUnitario);

        JLabel lblPrecoTotal = new JLabel("Preço Total:");
        lblPrecoTotal.setBounds(20, 140, 120, 25);
        add(lblPrecoTotal);

        txtPrecoTotal = new JTextField();
        txtPrecoTotal.setEditable(false);
        txtPrecoTotal.setBounds(150, 140, 150, 25);
        add(txtPrecoTotal);
        
        // Tabela para exibir itens no carrinho
        String[] colunas = {"Código", "Nome", "Quantidade", "Preço ", "Preço Total"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaCarrinho = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaCarrinho);
        scrollPane.setBounds(320, 20, 500, 400);
        add(scrollPane);

        // Botões
        btnAdicionarCarrinho = new JButton("Adicionar ao Carrinho");
        btnAdicionarCarrinho.setBounds(700, 439, 200, 25);
        btnAdicionarCarrinho.addActionListener((ActionEvent e) -> {
            try {
                adicionarAoCarrinho(produto);
            } catch (Exception ex) {
                Logger.getLogger(VendasView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add(btnAdicionarCarrinho);

        btnFinalizarVenda = new JButton("Finalizar");
        btnFinalizarVenda.setBounds(700, 469, 200, 25);
        btnFinalizarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    finalizarVenda();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Ocorreu um erro" + ex);
                }
            }
        });
        
        add(btnFinalizarVenda);

        btnCancelarVenda = new JButton("Cancelar");
        btnCancelarVenda.setBounds(700, 499, 200, 25);
        btnCancelarVenda.addActionListener((ActionEvent e) -> {
            cancelarVenda();
        });
        
        add(btnCancelarVenda);

        // Painel de pagamento
        JPanel painelPagamento = new JPanel();
        painelPagamento.setLayout(null);
        painelPagamento.setBounds(350, 430, 300, 120);
        painelPagamento.setBorder(BorderFactory.createTitledBorder("Pagamento"));
        add(painelPagamento);

        JLabel lblPagamento = new JLabel("Pagamento:");
        lblPagamento.setBounds(10, 30, 80, 25);
        painelPagamento.add(lblPagamento);

        txtPagamento = new JTextField();
        txtPagamento.setBounds(100, 30, 150, 25);
        painelPagamento.add(txtPagamento);

        JLabel lblTroco = new JLabel("Troco:");
        lblTroco.setBounds(10, 60, 80, 25);
        painelPagamento.add(lblTroco);

        txtTroco = new JTextField();
        txtTroco.setEditable(false);
        txtTroco.setBounds(100, 60, 150, 25);
        painelPagamento.add(txtTroco);

        // Painel para subtotal, descontos, impostos e total
        JPanel painelResumo = new JPanel();
        painelResumo.setLayout(null);
        painelResumo.setBounds(20, 430, 300, 150);
        painelResumo.setBorder(BorderFactory.createTitledBorder("Resumo"));
        add(painelResumo);

        JLabel lblSubtotal = new JLabel("Subtotal:");
        lblSubtotal.setBounds(10, 30, 80, 25);
        painelResumo.add(lblSubtotal);

        txtSubtotal = new JTextField();
        txtSubtotal.setEditable(false);
        txtSubtotal.setBounds(100, 30, 150, 25);
        painelResumo.add(txtSubtotal);

        JLabel lblDescontos = new JLabel("Descontos:");
        lblDescontos.setBounds(10, 60, 80, 25);
        painelResumo.add(lblDescontos);

        txtDescontos = new JTextField();
        txtDescontos.setBounds(100, 60, 150, 25);
        painelResumo.add(txtDescontos);

        JLabel lblImpostos = new JLabel("Impostos:");
        lblImpostos.setBounds(10, 90, 80, 25);
        painelResumo.add(lblImpostos);

        txtImpostos = new JTextField();
        txtImpostos.setEditable(false);
        txtImpostos.setBounds(100, 90, 150, 25);
        painelResumo.add(txtImpostos);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(10, 120, 80, 25);
        painelResumo.add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setBounds(100, 120, 150, 25);
        painelResumo.add(txtTotal);
        System.out.println("Tabela visível: " + tabelaCarrinho.isVisible());
        setVisible(true);
    }
    
    
   private DefaultTableModel getmodeloTabela(){
    return modeloTabela;
}
    
    private void adicionarAoCarrinho(Produto produto) throws Exception {
     modeloTabela.setRowCount(0);
        String codigoProdutotxt= txtCodigoProduto.getText();
        String quantidadetxt= txtQuantidade.getText();
    int codigoProduto = Integer.parseInt(codigoProdutotxt);
    int quantidade = Integer.parseInt(quantidadetxt);

    // Buscar o produto pelo código na base de dados
    produto = buscarProdutoPorCodigo(codigoProduto);

    if (produto != null) {
        produto.setQuantidade(quantidade);
        produtosCarrinho.add(produto);

    
        double precoTotal = produto.getPreco() * quantidade;
        int Código = produto.getProdutoId();
        String Nome = produto.getNome();
        double preco =produto.getPreco();

        // Adicionar uma nova linha à tabela com os dados do produto
        modeloTabela.addRow(new Object[]{Código, Nome, quantidade, preco, precoTotal});
        modeloTabela.fireTableDataChanged();
       
// modeloTabela.fireTableDataChanged();
        
        System.out.println("Produtos no carrinho: " + produtosCarrinho.size());
        // Atualizar o resumo dos valores na interface
        atualizarResumo();
        System.out.println("Linhas na tabela: " + modeloTabela.getRowCount());
        // Limpar os campos de entrada
        limparInterface();
    } else {
        JOptionPane.showMessageDialog(this, "Produto não encontrado.");
    }
    
    }
    
      private void preencherDetalhesProduto() {
               
       try {
        int codigoProduto = Integer.parseInt(txtCodigoProduto.getText());
         produto = buscarProdutoPorCodigo(codigoProduto);
        
        if (produto != null) {
            txtNomeProduto.setText(produto.getNome());
            txtPrecoUnitario.setText(String.valueOf(produto.getPreco()));
        } else {
            // Se o produto não for encontrado, limpar os campos de texto
            txtNomeProduto.setText("");
            txtPrecoUnitario.setText("");
            JOptionPane.showMessageDialog(this, "Produto não encontrado!");
        }
    } catch (Exception ex) {
        Logger.getLogger(VendasView.class.getName()).log(Level.SEVERE, null, ex);
    }
      }
    
     private Produto buscarProdutoPorCodigo(int codigoProduto) throws Exception {
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT produto_id, nome, preco FROM produto WHERE produto_id = ?")) {
            stmt.setInt(1, codigoProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int produtoId = rs.getInt("produto_id");
                    String nome = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    return new Produto(produtoId, nome, preco);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void atualizarResumo() {
       double subtotal = 0;
       
       for (Produto produto : produtosCarrinho) {
            subtotal += produto.getPreco() * produto.getQuantidade();
        }
       
       
       double descontos = subtotal * 0.0;
        double impostos = subtotal * 0.15;
        double total = subtotal - descontos + impostos;
       
       txtSubtotal.setText(String.valueOf(subtotal));
        txtDescontos.setText(String.valueOf(descontos));
        txtImpostos.setText(String.valueOf(impostos));
        txtTotal.setText(String.valueOf(total));
    
       
       
//       subtotal = itensCarrinho.stream().map((item) -> item.getTotal()).reduce(subtotal, (accumulator, _item) -> accumulator + _item);
//         txtSubtotal.setText(String.valueOf(subtotal));
//         txtTotal.setText(String.valueOf(subtotal));
         
         
    }
        
    
 private void finalizarVenda() {
                try {
            String totalText = txtTotal.getText();
            String pagamentoText = txtPagamento.getText();

            if (totalText.isEmpty() || pagamentoText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Total e pagamento são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalVenda = Double.parseDouble(totalText);
            double valorPago = Double.parseDouble(pagamentoText);
            double troco = valorPago - totalVenda;
            txtTroco.setText(String.valueOf(troco));

            int userId = UserSession.getInstance().getUserId();
            
            Vendas venda = new Vendas( totalVenda, valorPago, troco, 0, new java.sql.Date(System.currentTimeMillis()), itens);
            vendasDao.salvarVenda(venda);

            JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
           limparInterface();
            itens.clear();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Valores de pagamento e total devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(VendasView.class.getName()).log(Level.SEVERE, null, ex);
        }
            
 
 }
    

  private void cancelarVenda() {
              limparInterface();
        itens.clear();
  
  }

   public void limparInterface() {     
       txtCodigoProduto.setText("");
        txtQuantidade.setText("");
        DefaultTableModel model = (DefaultTableModel) tabelaCarrinho.getModel();
        model.setRowCount(0);
   }

   
    
}