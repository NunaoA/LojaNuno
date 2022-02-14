package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormLogin {
    private JPanel panelLogin;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton entrarButton;
    private JButton cancelarButton;

    public FormLogin() {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser=textFieldUsername.getText();
                String txtPass=String.valueOf(passwordField.getPassword());
                try
                {
                    Connection con=Conexao.CreateConnection();
                    String sql="Select Nome, Telefone, NomeUtilizador, PalavraPasse FROM empregados";
                    PreparedStatement ps=con.prepareStatement(sql);
                    ResultSet resSet=ps.executeQuery();
                    boolean b =false;
                    while (resSet.next())
                    {
                        String nome=resSet.getString("Nome");
                        String telefone=resSet.getString("Telefone");
                        String nomeUtilizador=resSet.getString("NomeUtilizador");
                        String palavraPasse=resSet.getString("PalavraPasse");
                        System.out.printf("%s - %s -%s",nome , telefone, nomeUtilizador, palavraPasse);
                        if(txtUser.equals(nomeUtilizador) &&txtPass.equals(palavraPasse))
                        {
                            new FormEscolher().setVisible(true);
                            b=true;
                        }
                    }
                    if(!b)
                    {
                        JOptionPane.showMessageDialog(null,"Erro! Login incorreto! Password/Username errados");

                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro" +ex.getMessage());
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame=new JFrame("Login");
        frame.setContentPane(new FormLogin().panelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
