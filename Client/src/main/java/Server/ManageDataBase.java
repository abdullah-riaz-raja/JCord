package Server;


import Jcord.Message;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ManageDataBase {

    // Grabbing the databse driver
    private final String DB_DRIVER = "org.h2.Driver";

    // String that tells the driver the type and location of the
    // file to be accessed
    private final String DB_CONNECTION = "jdbc:h2:./src/main/resources/Jcord";


    // The database user and password,
    // is only the default user
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "";


    public void insertMessage(Message message){

    }


    public Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
    
}