/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author ANTONIO MILTON
 * 
 */

import Model.Produto;
import Mode.DAO.ProdutoDAO;
import Model.Categoria;
import Model.Conexao;
import java.util.ArrayList;
import view.Estoque;


public class produtoController {
    
    private Estoque view;
    private  ProdutoDAO produtoDAO;
    private Conexao Conexao;
    

    public produtoController() {
        this.produtoDAO = new ProdutoDAO(Conexao);
       
    }
  

    public ArrayList<Produto> getProduto() throws Exception {
        return produtoDAO.getProduto();
    }

    public void addProduto(Produto produto) throws Exception {
        produtoDAO.adicionarProduto(produto);
    }

    public void updateProduto(Produto produto) throws Exception {
        produtoDAO.atualizarProduto(produto);
    }

    public void deleteProduto(int produtoId) throws Exception {
        produtoDAO.removerProduto(produtoId);
    }
    
    public ArrayList<Categoria> getCategorias() throws Exception {
        return produtoDAO.getCategorias();
    }
}



