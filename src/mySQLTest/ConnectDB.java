package mySQLTest;
/*
 * This test case includes the functionality to establish a connection to a QA instance, in order to execute a query
 * in the DB of that environment, to obtain the videos that have been modified from ETB.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Date;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.mysql.jdbc.ResultSetRow;
//import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.thoughtworks.selenium.SeleneseTestBase;

//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConnectDB extends SeleneseTestBase
{
     WebDriver driver;
     String url ="";
     @BeforeTest

     public void setUp() throws Exception
     {     }
     
     //Funcion de Gabi
     
     public static void go()
     {
       	 String user = "etolbanos-ds";
         String password = "BZU9KRpd";
         String host = "qa-017.dp.discovery.com";
         int port=22;
         int lport, rport;
         String rhost;
         try
         {
             JSch jsch = new JSch();
             Session session = jsch.getSession(user, host, port);
             lport = 3306;
             rhost = "localhost";
             rport = 3306;
             session.setPassword(password);
             session.setConfig("StrictHostKeyChecking", "no");
             System.out.println("Establishing Connection...");
             session.connect();
             int assinged_port=session.setPortForwardingL(lport, rhost, rport);
             System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
         }
         catch(Exception e){System.err.print(e);}
     }
     
     @Test
     public void CreateDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
     {
    	 int videoId;
    	 Timestamp date = new Timestamp(0);
    	 
    	 try
    	 {
             go();
         } 
    	 catch(Exception ex)
    	 {
             ex.printStackTrace();
         }
    	 
    	 //Prepare connection
       	 String url1 ="jdbc:mysql://localhost:3306/devcontent";
         
    	 // Load Microsoft SQL Server JDBC driver
           String dbClass = "com.mysql.jdbc.Driver";
           Class.forName(dbClass).newInstance();
           
           //Get connection to DB
           
           Connection con = DriverManager.getConnection(url1, "root","");
           
           //Create Statement
           Statement stmt = (Statement) con.createStatement();
           
           // Method which returns the requested information as rows of data
           // This is a test query. For demo purposes, the query should be executed twice, once before Vampire runs and updates
           // a specific video and the second time, after Vampire runs, in order to return the new status of the modified_by_etb field.
           // If all the videos are supposed to start with a NULL before Vampire runs, the query can be run just once, filtering for a 
           // specific video id 
          
           // ResultSet result = (ResultSet) stmt.executeQuery("SELECT `dws_video_id` FROM `dws_video` WHERE `modified_by_etb` IS NULL");
                     
           // ResultSet result = (ResultSet) stmt.executeQuery("SELECT `dws_video_id`,`modified_by_etb` FROM `dws_video` WHERE `modified_by_etb` IS NOT NULL");
           
           
           /*
            * Current query returns the dws_video_id and the modified_by_etb timestamp for results that don't have the modified_by_etb set to NULL
           */
           ResultSet result = (ResultSet) stmt.executeQuery("SELECT  `dws_video_id`, `modified_by_etb` FROM `dws_video` WHERE `modified_by_etb` IS NOT NULL ORDER BY `modified_by_etb` DESC");
           
           while(result.next())
           {
        	   System.out.println();
        	   //Retrieve by column name
        	   videoId  = result.getInt("dws_video_id");
        	   date = result.getTimestamp("modified_by_etb");
        	      
        	   System.out.print("Video Id: " + videoId);
        	   System.out.print(" , Modified by ETB on: " + date);
        	   System.out.println();
           }
     }
    
 /*    @AfterTest
     public void tearDown()
     {
    	 driver.close();
     }*/
}