/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mode.DAO;

import Model.Conexao;
import Model.Vendas;
import Model.VendasItem;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANTONIO MILTON
 */
public class RelatorioDAO {
   
    
    
    public static List<Vendas> getVendas() throws Exception {
        List<Vendas> vendas = new ArrayList<>();
       String sql = "SELECT venda_id, data_hora_criacao, total_venda FROM venda";

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vendas venda = new Vendas();
                venda.setVendaId(rs.getInt("venda_id"));
                venda.setDataHoraCriacao(rs.getDate("data_hora_criacao"));
                venda.setTotalVenda(rs.getDouble("total_venda"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
        }
        return vendas;
    }
}
