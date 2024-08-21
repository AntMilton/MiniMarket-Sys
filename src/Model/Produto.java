/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ANTONIO MILTON
 */


import java.sql.Timestamp;

public class Produto {
    private int produtoId;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;
    private int categoriaId;
    private int user_id;
    private Timestamp dataHoraCriacao;

    // Construtor completo
    public Produto(int produtoId, String nome, String descricao, double preco, int quantidade, int categoriaId, int user_id, Timestamp dataHoraCriacao) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoriaId = categoriaId;
        this.user_id = user_id;
        this.dataHoraCriacao = dataHoraCriacao;
    }

    // Construtor sem produtoId (para inserção de novos produtos)
    public Produto(String nome, String descricao, double preco, int quantidade, int categoriaId, int userId, Timestamp dataHoraCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoriaId = categoriaId;
        this.user_id = user_id;
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public Produto(String nome, int quantidade, double preco, int categoriaId) {
    this.nome = nome;
    this.quantidade = quantidade;
    this.preco = preco;
    this.categoriaId = categoriaId;
    }

    public Produto(String nome, int quantidade, double preco) {
       this.nome = nome;
        this.quantidade = quantidade;
         this.preco = preco;
    }

    public Produto(String nome, int quantidade, double preco, int categoriaId, int user_id) {
        
         this.nome = nome;
         this.quantidade = quantidade;
        this.preco = preco;
        this.categoriaId = categoriaId;
        this.user_id = user_id;
    }

    public Produto(int produtoId, String nome, double preco) {
        this.produtoId = produtoId;
        this.nome= nome;
        this.preco = preco;

    }

    // Getters e setters
    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public Timestamp getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(Timestamp dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }
}
