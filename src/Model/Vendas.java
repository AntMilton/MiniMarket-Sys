/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.sql.Date;
import java.util.List;

/**
 *
 * @author ANTONIO MILTON
 */
public class Vendas {
     private int vendaId;
    private double totalVenda;
    private double valorPago;
    private double troco;
    private double desconto;
    private int clienteId;
    public int userId;
    private Date dataHoraCriacao;
    private Date ultimaAtualizacao;
    private List<VendasItem> itens;

    public Vendas(int vendaId, double totalVenda, double valorPago, double troco, double desconto, int clienteId, int userId, Date dataHoraCriacao, Date ultimaAtualizacao, List<VendasItem> itens) {
        this.vendaId = vendaId;
        this.totalVenda = totalVenda;
        this.valorPago = valorPago;
        this.troco = troco;
        this.desconto = desconto;
        this.clienteId = clienteId;
        this.userId = userId;
        this.dataHoraCriacao = dataHoraCriacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.itens = itens;
    }

    public Vendas(){
        
    }

    public Vendas(double totalVenda, double valorPago, double troco, int i, int i0, int userId, Date date, Date date0, List<VendasItem> itensCarrinho) {
       this.totalVenda = totalVenda;
        this.valorPago = valorPago;
        this.troco = troco;
        this.desconto = desconto;
        this.clienteId = clienteId;
        this.userId = userId;
        this.dataHoraCriacao = dataHoraCriacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.itens = itens;
    }

    public Vendas(double totalVenda, double valorPago, double troco, int desconto,Date date, List<VendasItem> itens) {
       
         this.totalVenda = totalVenda;
        this.valorPago = valorPago;
        this.troco = troco;
        this.desconto = desconto;
        this.dataHoraCriacao = dataHoraCriacao;
    }

   

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getTroco() {
        return troco;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(Date dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

  public List<VendasItem> getItens() {
        return itens;
    }

    public void setItens(List<VendasItem> itens) {
        this.itens = itens;
    }
    
    
    
}
