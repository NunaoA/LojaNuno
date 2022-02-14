package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormMarca {
    private JTextField textFieldMarca;
    private JButton removerMarcaButton;
    private JButton adicionarMarcaButton;
    private JButton consultarMarcasButton;
    private JTextArea textAreaConsulta;
    private JPanel panelCategoria;

    Connection con;
    PreparedStatement pst;

    public static void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Gestao de Produtos");
        frame.setContentPane(new FormMarca().panelCategoria);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/lojanuno", "root","1234");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    public FormMarca() {
        Connect();

        adicionarMarcaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marca;
                marca = textFieldMarca.getText();


                try {
                    pst = con.prepareStatement("insert into categorias(NomeCategorias)values(?)");
                    pst.setString(1, marca);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Marca adicionada!");
                    textFieldMarca.setText("");
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Esta marca já existe!");
                    e1.printStackTrace();
                }

            }
        });

        removerMarcaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marca;
                marca = textFieldMarca.getText();
                textAreaConsulta.setText("");
                try {
                    PreparedStatement ps = con.prepareStatement("SELECT NomeCategorias FROM categorias WHERE NomeCategorias = ?");
                    ps.setString (1, marca);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        pst = con.prepareStatement("delete from categorias where NomeCategorias = ?");
                        pst.setString(1, marca);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Marca removida!");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Esta marca não existe!");
                    }
                    textFieldMarca.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        consultarMarcasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("select NomeCategorias from categorias");
                    ResultSet rs = pst.executeQuery();
                    textAreaConsulta.setText("");
                    while(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        textAreaConsulta.append(name + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
