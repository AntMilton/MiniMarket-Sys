/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mode.DAO;

/**
 *
 * @author ANTONIO MILTON
 */


import Model.Categoria;
import Model.Conexao;
import Model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO {

     private final Conexao Conexao;
     private Produto Produto;
     
      public ProdutoDAO(Conexao Conexao) {
        this.Conexao= Conexao;
    }
    public ArrayList<Produto> getProduto() throws Exception {
       List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM produto";

        try (
                Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("produto_id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        rs.getInt("categoria_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("data_hora_criacao")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
        }

        return (ArrayList<Produto>) produtos;
    }

    // Adicionar, atualizar, remover métodos também precisariam ser ajustados conforme necessário.
    


    public void adicionarProduto(Produto produto) throws Exception {
    String query = "INSERT INTO produto (nome, descricao, preco, quantidade, categoria_id, user_id, data_hora_criacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

     try (
            Connection conn =  Conexao.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPreco());
        stmt.setInt(4, produto.getQuantidade());
        stmt.setInt(5, produto.getCategoriaId());
        stmt.setInt(6, produto.getUserId());
        stmt.setTimestamp(7, produto.getDataHoraCriacao());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Falha ao adicionar o produto, nenhuma linha afetada.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int produtoId = generatedKeys.getInt(1);
                produto.setProdutoId(produtoId);
            } else {
                throw new SQLException("Falha ao adicionar o produto, nenhum ID de produto gerado.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


   public void atualizarProduto(Produto produto) throws Exception {
    String query = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, quantidade = ?, categoria_id = ?, user_id = ?, data_hora_criacao = ? WHERE produto_id = ?";

    try (Connection conn = Conexao.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPreco());
        stmt.setInt(4, produto.getQuantidade());
        stmt.setInt(5, produto.getCategoriaId());
        stmt.setInt(6, produto.getUserId());
        stmt.setTimestamp(7, produto.getDataHoraCriacao());
        stmt.setInt(8, produto.getProdutoId());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Falha ao atualizar o produto, nenhum produto encontrado com o ID: " + produto.getProdutoId());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


   public void removerProduto(int produtoId) throws Exception {
    String query = "DELETE FROM produto WHERE produto_id = ?";

    try (Connection conn = Conexao.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, produtoId);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Falha ao remover o produto, nenhum produto encontrado com o ID: " + produtoId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public Produto buscarProdutoPorCodigo(int codigoProduto) throws Exception {
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
        
    

    public ArrayList<Categoria> getCategorias() throws Exception {
        ArrayList<Categoria> categorias = new ArrayList<>();

        // Query SQL para selecionar todas as categorias da tabela categoria
        String query = "SELECT * FROM categoria";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterar sobre o resultado do conjunto de resultados
            while (rs.next()) {
                // Criar um objeto Categoria com os dados do resultado
                Categoria categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
                // Adicionar a categoria à lista de categorias
                categorias.add(categoria);
            }
        } catch (SQLException e) {
        }

        // Retornar a lista de categorias
        return categorias;
    }
    }

        
   



