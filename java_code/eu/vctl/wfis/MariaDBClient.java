package eu.vctl.wfis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


// DOWNLOAD DRIVER:
// https://mariadb.com/downloads/connectors/connectors-data-access/java8-connector
public class MariaDBClient{

    private String connString =  "jdbc:mariadb://localhost:3306/appdb";
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
        for (int i=0; i<5; i++){
            try{
                conn = DriverManager.getConnection(cstring, props);
            }
            catch (Exception e){
                if (i==4) throw e;
                else{
                    conn = null;
                    System.out.println(" Connection is not ready yet!!!");
                    System.out.println("Waiting 1s");
                    Thread.sleep(1000);
                }
            }
            if (conn != null) break;
        }
            
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
        MariaDBClient dbClient = new MariaDBClient();
        if (dbClient.initDB("root", "abc123")){
            dbClient.readData();
        }
        if (dbClient.conn != null){
            dbClient.closeConnection();
        }
    }
}