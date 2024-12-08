package ws.rest.tp8;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnexion {
    private static Connection connection =null;
    static
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/SOA", "root","");
        } catch (Exception e) {
            System.out.println("Problème de connexion à la BD..");
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return connection;
    }
    public static void setConnection(Connection conn) {
        connection = conn;
    }

}
