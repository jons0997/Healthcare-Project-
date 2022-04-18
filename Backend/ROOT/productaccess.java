// Loading required libraries
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
import java.sql.*;
 
public class productaccess extends HttpServlet{

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
      // JDBC driver name and database URL
      String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      String DB_URL="jdbc:mysql://localhost:3306/healthcare";
      //String DB_URL="jdbc:mysql://localhost:3306/;databasename=healthcare;";
      //String DB_URL="jdbc:sqlserver://localhost:3306;databaseName=healthcare;integratedSecurity=true;";

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
         Statement stmt = conn.createStatement();
         String sql;
         sql = "SELECT * FROM products";
         ResultSet rs = stmt.executeQuery(sql);
         
/*       //BELOW IS HOW TO INSERT SOMETHING INTO SQL DATABASE
         PreparedStatement statement = conn.prepareStatement("INSERT INTO `healthcare`.`products`(`product_id`,`picture_link`,`name`,`description`,`brand`,`quantity`,`price`,`active`)VALUES(\"1\",\"test1\",\"testname1\",\"testdesc1\",\"testbrand1\",\"50\",\"9.99\",\"1\");");
         int i = statement.executeUpdate();
         if (i>0){
             out.println("inserted successfully");
         }
         else
             out.println("insert failed");
         */
// Extract data from result set
         while(rs.next()){
            //Retrieve by column name
            //int user_id  = rs.getInt("user_id");
            int product_id = rs.getInt("product_id");
            String name = rs.getString("name");
            String desc = rs.getString("description");
            String brand = rs.getString("brand");
            String pic_link = rs.getString("picture_link");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            boolean active = rs.getBoolean("active");
            //int age = rs.getInt("age");
            //String username = rs.getString("username");
            //String last = rs.getString("last");

            //Display values
            out.println("product id is: "+product_id + "<br>");
            out.println("name of product is: "+name + "<br>");
            out.println("description of product is: "+desc + "<br>");
            out.println("brand of product is: "+brand + "<br>");
            out.println("price of product is: "+price + "<br>");
            out.println("quantity of product is: "+quantity + "<br>");
            out.println("product is active?: "+active + "<br>");
            out.println("picture link is: "+pic_link + "<br>");
            //out.println("ID: " + user_id + "<br>");
            //out.println(", Age: " + age + "<br>");
            //out.println(", First: " + username + "<br>");
            //out.println(", Last: " + last + "<br>");
         }
         out.println("</body></html>");

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