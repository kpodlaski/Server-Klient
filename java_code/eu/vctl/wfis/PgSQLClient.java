package eu.vctl.wfis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


// DOWNLOAD DRIVER:
// https://jdbc.postgresql.org/download/
public class PgSQLClient{

    private String connString =  "jdbc:postgresql://db_ip/appdb";
    private Connection conn; 
    public boolean initDB(String userName, String passwd) throws Exception{
        String db_ip = System.getenv("DB_ADDRESS");
        if (db_ip == null) {
            db_ip = "localhost";
        }
        String cstring = connString.replace("db_ip", db_ip); 
        System.out.println("Connecting using cstring: "+cstring);
        Properties props = new Properties();
        props.setProperty("user", userName);
        props.setProperty("password", passwd);
        conn = DriverManager.getConnection(cstring, props);
        return true;
    }

    public void readData() throws Exception {
        String query = "SELECT * FROM Pracownik";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(query);
        while (rst.next()){
            System.out.print(rst.getString("imie"));
            System.out.print(" ");
            System.out.println(rst.getString("nazwisko"));
        }
    }

    public void closeConnection() throws Exception {
        conn.close();
        conn = null;
    }

    public static void main(String[] args) throws Exception{
        PgSQLClient pgsqlClient = new PgSQLClient();
        if (pgsqlClient.initDB("dbuser", "dbuser")){
            pgsqlClient.readData();
        }
        if (pgsqlClient.conn != null){
            pgsqlClient.closeConnection();
        }
    }
}