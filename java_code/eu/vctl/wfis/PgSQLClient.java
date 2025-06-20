package eu.vctl.wfis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;


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
      // ResultSetMetaData dbMeta = rst.getMetaData();
      // int count = dbMeta.getColumnCount();
      // for(int i=1; i<=count; i++){
      //     System.out.println(i +":"+ dbMeta.getColumnName(i)+"::"+dbMeta.getColumnType(i) );
      // }
      //ResultSet tables = dbmsMeta.getTables(null, null, null, null);
      //Printing the column name and size
      // while (tables.next()) {
      //    System.out.println("Table name: "+tables.getString("Table_NAME"));
      //    System.out.println("Table type: "+tables.getString("TABLE_TYPE"));
      //    System.out.println("Table schema: "+tables.getString("TABLE_SCHEM"));
      //    System.out.println("Table catalog: "+tables.getString("TABLE_CAT"));
      //    System.out.println(" ");
      // }
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
