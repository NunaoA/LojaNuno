package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao
{
    public static Connection CreateConnection() throws SQLException{
        String url="jdbc:mysql://localhost:3306/lojanuno";
        String user="root";
        String pass="1234";
        Connection conexaoReal=null;
        conexaoReal= DriverManager.getConnection(url,user,pass);
        return conexaoReal;
    }
}