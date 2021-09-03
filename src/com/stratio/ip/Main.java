package com.stratio.ip;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;



public class Main {

  public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

    //String table_to_process = System.getenv("SAPTABLE");
    String pageSize=System.getenv("PAGESIZE");
    String rfcReadTable=System.getenv("RFCREADTABLE");
    String rtk=System.getenv("RTK");
    String host= System.getenv("HOST_SAP");
    String connType=System.getenv("CONNTYPE");
    String user=System.getenv("USER_SAP");
    String pass=System.getenv("PASS_SAP");
    String client=System.getenv("CLIENT_SAP");
    String sysNum=System.getenv("SYSNUM");
    String query=System.getenv("QUERYERP");
    Instant start = Instant.now();
//    try (FileWriter fw = new FileWriter("/tmp/test/output.txt", true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        PrintWriter out = new PrintWriter(bw)) {

    Class.forName("cdata.jdbc.saperp.SAPERPDriver");
    //Connection conn = DriverManager.getConnection("jdbc:saperp:Host=sap.mydomain.com;User=EXT90033;Password=xxx;Client=800;System Number=09;ConnectionType=Classic;");
    Properties prop = new Properties();
    prop.setProperty("Host",host);
    prop.setProperty("User",user);
    prop.setProperty("Password",pass);
    prop.setProperty("Client",client);
    prop.setProperty("System Number",sysNum);
    prop.setProperty("ConnectionType",connType);
    prop.setProperty("ReadTableFunction",rfcReadTable);
    prop.setProperty("RTK",rtk);
    prop.setProperty("Pagesize",pageSize);
    Connection conn = DriverManager.getConnection("jdbc:saperp:",prop);

    System.out.println("[INFO] CONNECTED, ATTEMPTING TO EXTRACT FROM SAP ERP using "+query);

    //String query = "select * from " + table_to_process;

    Statement sqlStatement = conn.createStatement();

    ResultSet rs = sqlStatement.executeQuery(query);
      while (rs.next()) {
        StringBuilder aux = new StringBuilder();
        for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
          aux.append(rs.getObject(i)).append(" ");
        }
        System.out.println(aux.toString());
      }
      sqlStatement.close();
    System.out.println("END");
//    } catch (IOException e){
//      e.printStackTrace();
//    }
    Instant finish = Instant.now();

    System.out.println("[INFO] Seconds elapsed: " + Duration.between(start, finish).toMillis()/1000);

  }
}
