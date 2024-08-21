/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

// CategoriaController.java
import Mode.DAO.CategoriaDAO;
import Model.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaController {
    private final CategoriaDAO categoriaDAO;

    public CategoriaController(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

   

    public void adicionarCategoria(String nome, String descricao) throws Exception {
        Categoria categoria = new Categoria(nome, descricao);
        categoriaDAO.salvar(categoria); 
    }

    public void editarCategoria(int id, String nome, String descricao) throws Exception {
        Categoria categoria = new Categoria(id, nome, descricao);
        categoriaDAO.atualizar(categoria); 
    }

    public void excluirCategoria(int id) throws Exception {
        categoriaDAO.deletar(id); 
    }

    public List<Categoria> listarCategorias() throws Exception {
        return categoriaDAO.listar(); 
    }
}
