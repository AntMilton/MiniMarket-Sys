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

import Model.User;
import Model.Conexao;
import Model.Perfil;
 import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class UserDao {
    
   

    private static final String INSERT_USER_SQL = "INSERT INTO user (nome, senha, usuario, perfil, estado, data_hora_Criacao, email, telefone, ultimo_Login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE user_id = ?";
    private static final String UPDATE_USER_SQL = "UPDATE user SET nome = ?, senha = ?, usuario = ?, perfil = ?, estado = ?, data_hora_Criacao = ?, email = ?, telefone = ?, ultimo_Login = ? WHERE user_id = ?";

    // Método para obter conexão com o banco de dados
    private Connection obterConexao() throws  Exception {
        return Conexao.obterConexao();
    }

    // Método para inserir um novo usuário
    public void inserirUser(User user) throws Exception {
        try (
                Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getSenha());
            preparedStatement.setString(3, user.getUser());
            preparedStatement.setString(4, user.getPerfil().name()); // Supondo que Perfil seja um enum
            preparedStatement.setBoolean(5, user.isEstado());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getDatahoraCriacao()));
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setInt(8, user.getTelefone());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(user.getUltimoLogin()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public User autenticarUser(String user, String senha) throws Exception {
        
        
        String sql = "SELECT * FROM user WHERE usuario = ? AND senha = ?";
        
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user1 = new User();
                user1.setUser_id(rs.getInt("user_id"));
                user1.setNome(rs.getString("nome"));
                user1.setSenha(rs.getString("senha"));
                user1.setUser(rs.getString("usuario"));
                user1.setPerfil(Perfil.valueOf(rs.getString("perfil")));
                user1.setEstado(rs.getBoolean("estado"));
                user1.setDatahoraCriacao(rs.getTimestamp("data_hora_Criacao").toLocalDateTime());
                user1.setEmail(rs.getString("email"));
                user1.setTelefone(rs.getInt("telefone"));
                user1.setUltimoLogin(rs.getTimestamp("ultimo_Login").toLocalDateTime());
                return user1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao acessar os dados"+ e.getMessage());
        }
        return null;

        
    }

    // Método para buscar um usuário pelo ID
    public User buscarUser(int userId) throws Exception {
        User user = null;
        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String usuario = rs.getString("usuario");
                Perfil perfil = Perfil.valueOf(rs.getString("perfil")); // Supondo que Perfil seja um enum
                boolean estado = rs.getBoolean("estado");
                LocalDateTime datahoraCriacao = rs.getTimestamp("data_hora_Criacao").toLocalDateTime();
                String email = rs.getString("email");
                int telefone = rs.getInt("telefone");
                LocalDateTime ultimoLogin = rs.getTimestamp("ultimo_Login").toLocalDateTime();
                user = new User(userId, nome, senha, usuario, perfil, estado, datahoraCriacao, email, telefone, ultimoLogin);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    // Método para buscar todos os usuários
    public List<User> listarTodosUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String usuario = rs.getString("usuario");
                Perfil perfil = Perfil.valueOf(rs.getString("perfil")); // Supondo que Perfil seja um enum
                boolean estado = rs.getBoolean("estado");
                LocalDateTime datahoraCriacao = rs.getTimestamp("data_hora_Criacao").toLocalDateTime();
                String email = rs.getString("email");
                int telefone = rs.getInt("telefone");
                LocalDateTime ultimoLogin = rs.getTimestamp("ultimo_Login").toLocalDateTime();
                users.add(new User(userId, nome, senha, usuario, perfil, estado, datahoraCriacao, email, telefone, ultimoLogin));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    // Método para atualizar um usuário
    public void atualizarUser(User user) throws Exception {
        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getSenha());
            preparedStatement.setString(3, user.getUser());
            preparedStatement.setString(4, user.getPerfil().name()); // Supondo que Perfil seja um enum
            preparedStatement.setBoolean(5, user.isEstado());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(user.getDatahoraCriacao()));
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setInt(8, user.getTelefone());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(user.getUltimoLogin()));
            preparedStatement.setInt(10, user.getUser_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // Método para deletar um usuário
    public void deletarUser(int userId) throws Exception {
        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}


