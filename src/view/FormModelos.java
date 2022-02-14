package view;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class FormModelos {
    private JPanel PanelModelos;
    private JTabbedPane tabbedPane1;
    private JTextField textFieldPreço;
    private JTextField textFieldQuantidade;
    private JTextField textFieldMarca;
    private JTextField textFieldModelo;
    private JButton guardarModeloButton;
    private JTextField textFieldAltPesquisa;
    private JButton altPesquisarButton;
    private JTextField textFieldAltModelo;
    private JButton altAlterarButton;
    private JTextField textFieldAltPreço;
    private JTextField textFieldAltQuantidade;
    private JTextField textFieldAltMarca;
    private JButton consultarModeloButton;
    private JTextField textFieldConsultarVeiculo;
    private JButton consultarTodosOsModelosButton;
    private JTextArea textAreaConsultarMarca;
    private JTextArea textAreaConsultarTudo;
    private JButton apagarModeloButton;
    private JTextField textFieldNomeModeloAApagar;
    private JTextField textFieldEliModelo;
    private JTextField textFieldEliPreço;
    private JTextField textFieldEliQuantidade;
    private JTextField textFieldEliMarca;
    private JPanel panelModelos;
    private JTextField textFieldConsultarModelo;
    private JButton encontrarModeloButton;
    private JTextField textFieldConID;
    private JTextField textFieldConMarca;
    private JTextField textFieldConModelo;
    private JTextField textFieldConPreço;
    private JTextField textFieldConQuantidade;
    private JButton buttonPrimeiro;
    private JButton buttonAnterior;
    private JButton buttonProximo;
    private JButton buttonUltimo;
    private JLabel labelConImagem;
    private JButton procurarButton;
    private JLabel labelImagem;
    Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;
    private String path=null;
    private byte[] userImage;

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormModelos().panelModelos);
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
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select id, NomeProdutos, Preço, Quantidade, NomeCategorias, Foto from produtos");
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

    public FormModelos() {
        Connect();

        guardarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo, quantidade, marca;
                double preço= Double.valueOf(textFieldPreço.getText());
                modelo = textFieldModelo.getText();
                //preço = textFieldPreço.getText();
                quantidade = textFieldQuantidade.getText();
                marca = textFieldMarca.getText();
                try {
                    ps = con.prepareStatement("insert into produtos(Preço,NomeCategorias,NomeProdutos,Quantidade,Foto)values(?,?,?,?,?)");
                    ps.setDouble(1, preço);
                    ps.setString(2, marca);
                    ps.setString(3, modelo);
                    ps.setString(4, quantidade);
                    ps.setBytes(5,userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo adicionado!");
                    textFieldModelo.setText("");
                    textFieldPreço.setText("");
                    textFieldQuantidade.setText("");
                    textFieldMarca.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Esta marca não existe!");
                }
            }
        });

        altAlterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo,preco,quantidade, marca, id;

                modelo = textFieldAltModelo.getText();
                preco = textFieldAltPreço.getText();
                quantidade = textFieldAltQuantidade.getText();
                marca = textFieldAltMarca.getText();
                id = textFieldAltPesquisa.getText();

                try {

                    ps = con.prepareStatement("update produtos set Preço = ?,NomeCategorias = ?,NomeProdutos = ?, Quantidade = ? where id = ?");
                    ps.setString(1, preco);
                    ps.setString(2, marca);
                    ps.setString(3, modelo);
                    ps.setString(4, quantidade);
                    ps.setString(5,id);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo atualizado!");

                    textFieldAltModelo.setText("");
                    textFieldAltPreço.setText("");
                    textFieldAltQuantidade.setText("");
                    textFieldAltPesquisa.requestFocus();
                    textFieldAltMarca.setText("");
                }

                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente/Insira um número");
                    e1.printStackTrace();
                }
            }
        });

        consultarTodosOsModelosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ps = con.prepareStatement("select id , Preço, NomeCategorias,NomeProdutos, Quantidade from produtos");
                    ResultSet rs = ps.executeQuery();
                    textAreaConsultarTudo.setText("");
                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5);
                        textAreaConsultarTudo.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        consultarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id;
                    id = textFieldConsultarModelo.getText();

                    ps = con.prepareStatement("select id, Preço, NomeCategorias,NomeProdutos, Quantidade from produtos WHERE NomeCategorias = ?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();
                    textAreaConsultarMarca.setText("");

                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5);
                        textAreaConsultarMarca.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente");
                    e1.printStackTrace();
                }
            }
        });

        apagarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo, preco, quantidade, marca;
                modelo = textFieldEliModelo.getText();
                preco = textFieldEliPreço.getText();
                quantidade = textFieldEliQuantidade.getText();
                marca = textFieldEliMarca.getText();

                try {
                    ps = con.prepareStatement("delete from produtos where Preço = ? and NomeCategorias = ? and NomeProdutos = ? and Quantidade = ?");
                    ps.setString(1, preco);
                    ps.setString(2, marca);
                    ps.setString(3, modelo);
                    ps.setString(4, quantidade);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Modelo Apagado!");

                    textFieldEliModelo.setText("");
                    textFieldEliPreço.setText("");
                    textFieldEliQuantidade.setText("");
                    textFieldEliMarca.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        encontrarModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String id = textFieldNomeModeloAApagar.getText();
                    ps = con.prepareStatement("select Preço, NomeCategorias,NomeProdutos, Quantidade from produtos where id = ?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();

                    if(rs.next()==true)
                    {
                        String preço = rs.getString(1);
                        String marca = rs.getString(2);
                        String modelo = rs.getString(3);
                        String quantidade = rs.getString(4);


                        textFieldEliModelo.setText(modelo);
                        textFieldEliPreço.setText(preço);
                        textFieldEliQuantidade.setText(quantidade);
                        textFieldEliMarca.setText(marca);
                    }
                    else
                    {
                        textFieldEliModelo.setText("");
                        textFieldEliPreço.setText("");
                        textFieldEliQuantidade.setText("");
                        textFieldEliMarca.setText("");
                        JOptionPane.showMessageDialog(null,"ID inválido");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }

        });
        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    rs.first();
                    textFieldConID.setText(String.valueOf(rs.getInt("id")));
                    textFieldConMarca.setText(rs.getString("NomeCategorias"));
                    textFieldConModelo.setText(rs.getString("NomeProdutos"));
                    textFieldConPreço.setText(String.valueOf(rs.getDouble("Preço")));
                    textFieldConQuantidade.setText(rs.getString("Quantidade"));
                    Blob blob=rs.getBlob("Foto");
                    byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon= new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelConImagem.setIcon(imgIcon);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(!rs.isFirst()) {
                        rs.previous();
                        textFieldConID.setText(String.valueOf(rs.getInt("id")));
                        textFieldConMarca.setText(rs.getString("NomeCategorias"));
                        textFieldConModelo.setText(rs.getString("NomeProdutos"));
                        textFieldConPreço.setText(String.valueOf(rs.getDouble("Preço")));
                        textFieldConQuantidade.setText(rs.getString("Quantidade"));
                        Blob blob=rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250,
                                Image.SCALE_DEFAULT));
                        labelConImagem.setIcon(imgIcon);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(rs.isLast()) {
                        rs.next();
                        textFieldConID.setText(String.valueOf(rs.getInt("id")));
                        textFieldConMarca.setText(rs.getString("NomeCategorias"));
                        textFieldConModelo.setText(rs.getString("NomeProdutos"));
                        textFieldConPreço.setText(String.valueOf(rs.getDouble("Preço")));
                        textFieldConQuantidade.setText(rs.getString("Quantidade"));
                        Blob blob=rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelConImagem.setIcon(imgIcon);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    rs.last();
                    textFieldConID.setText(String.valueOf(rs.getInt("ID")));
                    textFieldConMarca.setText(rs.getString("NomeCategorias"));
                    textFieldConModelo.setText(rs.getString("NomeProdutos"));
                    textFieldConQuantidade.setText(rs.getString("Quantidade"));
                    textFieldConPreço.setText(String.valueOf(rs.getDouble("Preço")));
                    Blob blob=rs.getBlob("Foto");
                    byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon= new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelConImagem.setIcon(imgIcon);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        procurarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoose=new JFileChooser();
                imgChoose.showOpenDialog(null);
                File img=imgChoose.getSelectedFile();

                path=img.getAbsolutePath();
                BufferedImage image;
                try{
                    //colocar a imagem selecionada na label
                    image= ImageIO.read(imgChoose.getSelectedFile());
                    ImageIcon imgIcon= new ImageIcon(new ImageIcon(image).
                            getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);

                    //Preparar a imagem e guarda-la na variavel "userimage"
                    // para poder ser guardada na base de dados
                    File imgg=new File(path);
                    FileInputStream fs=new FileInputStream(imgg);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytes_read=0;

                    while((nBytes_read=fs.read(buff)) !=-1) {
                        bos.write(buff, 0, nBytes_read);
                    }
                    userImage=bos.toByteArray();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

