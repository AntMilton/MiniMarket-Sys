/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mode.DAO;

import Model.Conexao;
import Model.UserSession;
import Model.Vendas;
import Model.VendasItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


         

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANTONIO MILTON
 */
public class VendasDAO {
    
    private  Conexao Conexao;
    
     public VendasDAO(Conexao Conexao) {
        this.Conexao = Conexao;
    }
      

    public void salvarVenda(Vendas venda) throws Exception {
    Connection conn = Conexao.obterConexao();
    String sqlVenda = "INSERT INTO venda (total_venda, valor_pago, troco, desconto,  data_hora_criacao) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);

    stmtVenda.setDouble(1, venda.getTotalVenda());
    stmtVenda.setDouble(2, venda.getValorPago());
    stmtVenda.setDouble(3, venda.getTroco());
    stmtVenda.setDouble(4, venda.getDesconto());
    stmtVenda.setDate(5, venda.getDataHoraCriacao());

    int affectedRows = stmtVenda.executeUpdate();
    if (affectedRows == 0) {
        throw new SQLException("Falha ao salvar a venda, nenhuma linha afetada.");
    }

     int vendaId;
    try (ResultSet generatedKeys = stmtVenda.getGeneratedKeys()) {
        if (generatedKeys.next()) {
            vendaId = generatedKeys.getInt(1);
            System.out.println("Venda ID: " + vendaId);  // Adicionado para depuração
        } else {
            throw new SQLException("Falha ao salvar a venda, nenhum ID obtido.");
        }
    }

    // Inserir itens de venda na tabela venda_item
    
             
    }


    public void finalizarVenda(Vendas venda) throws Exception {
       Connection conn = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtItem = null;

        try {
            conn = Conexao.obterConexao();
            conn.setAutoCommit(false);

            // Inserir a venda na tabela Vendas
            String sqlVenda = "INSERT INTO venda (total_venda ,data_hora_criacao) VALUES (?,CURRENT_TIMESTAMP)";
            stmtVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtVenda.setDouble(1, venda.getTotalVenda());
            stmtVenda.executeUpdate();

            // Recuperar o ID da venda gerada
            ResultSet generatedKeys = stmtVenda.getGeneratedKeys();
            if (generatedKeys.next()) {
                venda. setVendaId(generatedKeys.getInt(1));
            }

            // Inserir os itens da venda na tabela VendasItem
            String sqlItem = "INSERT INTO venda_item (venda_id, produto_id, quantidade, total) VALUES (?, ?, ?, ?)";
            stmtItem = conn.prepareStatement(sqlItem);

            for (VendasItem item : venda.getItens()) {
                stmtItem.setInt(1, venda. getVendaId());
                stmtItem.setInt(2, item.getProduto().getProdutoId());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getTotal());
                stmtItem.addBatch();
            }

            stmtItem.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmtVenda != null) {
                stmtVenda.close();
            }
            if (stmtItem != null) {
                stmtItem.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    }
    
    


