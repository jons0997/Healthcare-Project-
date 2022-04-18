// Import required java libraries
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
//import javax.servlet.*;
//import javax.servlet.http.*;

public class HelloWorld extends HttpServlet {

	private String message = "hello world";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String title = "Test passed";

            String docType =
               "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            out.println(docType +
               "<html>\n" +
               "<head><title>" + title + "</title></head>\n" +
               "<body bgcolor = \"#f0f0f0\">\n" +
               "<h1 align = \"center\">" + title + "</h1>\n");
	}

}