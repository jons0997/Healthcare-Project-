// Loading required libraries
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class updateproducts extends HttpServlet{

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
      // JDBC driver name and database URL
      String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      String DB_URL="jdbc:mysql://localhost:3306/healthcare";

      //  Database credentials
      String USER = "root";
      String PASS = "admin";

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      
      String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      
      out.println(docType +
         "<html>\n" +
         "<head><title>" + title + "</title></head>\n" +
         "<body bgcolor = \"#f0f0f0\">\n" +
         "<h1 align = \"center\">" + title + "</h1>\n");
      try {
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");
		//Class.forName("oracle.jdbc.driver.OracleDriver");

         // Open a connection
         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
         // Execute SQL query
		 //PreparedStatement statement = conn.prepareStatement("INSERT INTO `healthcare`.`products`(`product_id`,`picture_link`,`name`,`description`,`brand`,`quantity`,`price`,`active`)VALUES(\"1\",\"test1\",\"testname1\",\"testdesc1\",\"testbrand1\",\"50\",\"9.99\",\"1\");");
         String product_name = req.getParameter("whatever it's named in the html needs to go here");
		 //String query = "DELETE FROM `healthcare`.`products` where name=%s".formatted(product_name);
		 //String str = "First %s, then %s".formatted(foo, bar);     
		 String query = "DELETE FROM `healthcare`.`products` WHERE name="+product_name;
		 int i = statement.executeUpdate(query);
         if (i>0){
             out.println("inserted successfully");
         }
         else
             out.println("insert failed");

         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      } catch(SQLException se) {
         //Handle errors for JDBC
         se.printStackTrace();
      } catch(Exception e) {
         //Handle errors for Class.forName
         e.printStackTrace();
      } /*finally {
         //finally block used to close resources
         try {
            if(stmt!=null)
               stmt.close();
         } catch(SQLException se2) {
         } // nothing we can do
         try {
            if(conn!=null)
            conn.close();
         } catch(SQLException se) {
            se.printStackTrace();
         } //end finally try

      } //end try
*/
   }
} 