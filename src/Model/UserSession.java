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
public class UserSession {
    private static UserSession instance;
    private User loggedUser;
     private int userId;

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void clearSession() {
        loggedUser = null;
    }
    
      public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void logout() {
        loggedUser = null;  // Limpa o usuário logado
        instance = null;    // Opcional: redefine a instância para garantir uma nova sessão futura
    }
}

