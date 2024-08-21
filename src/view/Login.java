/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


/**
 *
 * Author: ANTONIO MILTON
 */
import Controller.produtoController;
import Model.User;
import Mode.DAO.UserDao;
import Model.UserSession;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login implements ActionListener {
    
    private final JLabel background;
    private final JLabel background2;
    private final JTextField txtUser;
    private final JPasswordField txtSenha;
    private final JButton BtLog;
    private final JButton BtCancelar;
    private final JLabel LabelLoginMessage;
    private final UserDao userDao;
     private User user1; 
    private final JFrame frame;

    public Login() {
        // Inicializa o UserDao
        userDao = new UserDao();

        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        JPanel panel = new JPanel();
        panel.setLayout(null); 
        ImageIcon img = new ImageIcon("D:\\Java\\2024\\SGVendas\\src\\Img\\bag1.jpg"); 
        background = new JLabel();
        background.setIcon(img);
        background.setBounds(0, 0, 500, 500);
        panel.add(background);
        
        ImageIcon userIcon = new ImageIcon("C:\\Users\\ANTONIO MILTON\\Downloads\\icons8-test-account-96.png");
        ImageIcon entrarIcon = new ImageIcon("C:\\Users\\ANTONIO MILTON\\Downloads\\icons8-enter-55.png");
          ImageIcon sairIcon = new ImageIcon("D:\\Java\\2024\\Svendas2\\src\\Img\\icons8-exit-35.png");
        
         JLabel user = new JLabel(userIcon);
         user.setBounds(700, 10, 100, 100);
          panel.add(user);
        
        LabelLoginMessage = new JLabel();
        LabelLoginMessage.setBounds(560, 90, 400, 80);
        LabelLoginMessage.setFont(new Font("Arial", Font.BOLD, 17));
        LabelLoginMessage.setForeground(Color.RED);
        panel.add(LabelLoginMessage);

        JLabel userlbl = new JLabel("Usuario");
        userlbl.setBounds(550, 150, 100, 70);
        userlbl.setFont(new Font("Arial", Font.BOLD, 17));
        userlbl.setForeground(Color.BLUE);
        panel.add(userlbl);

        txtUser = new JTextField();
        txtUser.setBounds(660, 170, 210, 30);
        panel.add(txtUser);

        JLabel senhalbl = new JLabel("Senha");
        senhalbl.setBounds(550, 225, 100, 65);
        senhalbl.setFont(new Font("Arial", Font.BOLD, 17));
        senhalbl.setForeground(Color.BLUE);
        panel.add(senhalbl);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(660, 240, 210, 30);
        panel.add(txtSenha);
        
         
         
        BtLog = new JButton("Entrar",entrarIcon);
        BtLog.setBounds(600, 330, 150, 50);
        BtLog.addActionListener(this); // Adiciona o ActionListener
        panel.add(BtLog);

        BtCancelar = new JButton("Cancelar",sairIcon);
        BtCancelar.setBounds(760, 330, 150, 50);
        BtCancelar.addActionListener((ActionEvent e) -> {
            btCancelar();
        });
        panel.add(BtCancelar);

        ImageIcon lg = new ImageIcon(getClass().getResource("/img/b1.png")); 
        background2 = new JLabel();
        background2.setIcon(lg);
        background2.setBounds(500, 0, 500, 500);
        panel.add(background2);

        frame.add(panel);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       try {
            LabelLoginMessage.setText("");
            String user = txtUser.getText();
            String senha = new String(txtSenha.getPassword());

            if (validarCredenciais(user, senha)) {
                 LabelLoginMessage.setText("Validando credenciais...");
                System.out.println("Validando credenciais...");
                 user1 = userDao.autenticarUser(user, senha);
               // Atribui o usuário autenticado
                if (user1 != null) {
                    System.out.println("Login bem-sucedido! Perfil: " +  user1.getPerfil());
                    LabelLoginMessage.setText("Login bem-sucedido! Perfil: " +  user1.getPerfil());
                    // Passa o perfil do usuário autenticado ao Menu
                    UserSession.getInstance().setLoggedUser(user1);
                    Menu menu = new Menu( user1.getPerfil(), userDao, new produtoController());
                    frame.dispose();
                } else {
                    System.out.println("Credenciais inválidas.");
                    LabelLoginMessage.setText("Credenciais inválidas.");
                }
            } else {
                System.out.println("Usuário e senha são obrigatórios.");
                LabelLoginMessage.setText("Usuário e senha são obrigatórios.");
            }
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage());
        }
    }

    private boolean validarCredenciais(String user, String senha) {
        return !user.isEmpty() && !senha.isEmpty();
    }

    private void btCancelar() {
        frame.dispose();
    }

    public static void main(String[] args) {
        new Login();
    }
}
