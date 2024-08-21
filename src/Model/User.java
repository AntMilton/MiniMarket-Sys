/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author ANTONIO MILTON
 */
public class User {
    
      private int user_id;
    private String nome;
    private String senha;
    private String user;
    private Perfil perfil;
    private boolean estado;
    private LocalDateTime datahoraCriacao;
    private String email;
    private int telefone;

    public User(int user_id, String nome, String senha, String user, Perfil perfil, boolean estado, LocalDateTime datahoraCriacao, String email, int telefone, LocalDateTime ultimoLogin) {
        this.user_id = user_id;
        this.nome = nome;
        this.senha = senha;
        this.user = user;
        this.perfil = perfil;
        this.estado = estado;
        this.datahoraCriacao = datahoraCriacao;
        this.email = email;
        this.telefone = telefone;
        this.ultimoLogin = ultimoLogin;
    }

    public User(String senha, String user) {
        this.senha = senha;
        this.user = user;
    }
    
    

    public User(String nome, String senha, String user, Perfil perfil, boolean estado, LocalDateTime datahoraCriacao, String email, int telefone, LocalDateTime ultimoLogin) {
        this.nome = nome;
        this.senha = senha;
        this.user = user;
        this.perfil = perfil;
        this.estado = estado;
        this.datahoraCriacao = datahoraCriacao;
        this.email = email;
        this.telefone = telefone;
        this.ultimoLogin = ultimoLogin;
    }

    public User(String nome, String usuario, String senha, String cargo, String dataHC, String ultimoL, String email, int telefone) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    
     public User() {
        this.estado = true;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getDatahoraCriacao() {
        return datahoraCriacao;
    }

    public void setDatahoraCriacao(LocalDateTime datahoraCriacao) {
        this.datahoraCriacao = datahoraCriacao;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public User(int user_id, String nome, String senha, String user, Perfil perfil, boolean estado, LocalDateTime datahoraCriacao, LocalDateTime ultimoLogin) {
        this.user_id = user_id;
        this.nome = nome;
        this.senha = senha;
        this.user = user;
        this.perfil = perfil;
        this.estado = estado;
        this.datahoraCriacao = datahoraCriacao;
        this.ultimoLogin = ultimoLogin;
    }
    private LocalDateTime ultimoLogin;
    
     public void reset(){
        this.estado= true;
    }
    public void mudarEstado(){
        this.estado = !this.estado;}
    
//     public void autenticarUser(String user, String senha){
//        this.user= user;
//        this.senha= senha;
//        
//    }
}
