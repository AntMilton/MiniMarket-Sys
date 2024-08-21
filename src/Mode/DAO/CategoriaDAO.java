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
// CategoriaDAO.java
import Model.Categoria;
import Model.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDAO {
   
    
     private Connection obterConexao() throws  Exception {
        return Conexao.obterConexao();
    }

    public void salvar(Categoria categoria) throws Exception {
        
         
        String sql = "INSERT INTO categoria (nome, descricao) VALUES (?, ?)";

         try (Connection connection = obterConexao();
             PreparedStatement stmt = connection .prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.executeUpdate();
        }
 catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao adicionar "+ e.getMessage());
        }
    }

    public void atualizar(Categoria categoria) throws Exception {
        String sql = "UPDATE categoria SET nome = ?, descricao = ? WHERE categoria_id = ?";

        try (Connection connection = obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getCategoriaId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int categoriaId) throws Exception {
        String sql = "DELETE FROM categoria WHERE categoria_id = ?";

        try (Connection connection = obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, categoriaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Categoria> listar() throws Exception {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (Connection connection = obterConexao();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("categoria_id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Categoria categoria = new Categoria(id, nome, descricao);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}

