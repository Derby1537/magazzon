package edu.unict.magazzon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/magazzon")
public class app extends HttpServlet{
    
    Connection connection; 
    final String connectionString = "jdbc:mysql://localhost:3306/magazzonß?user=root&password=ruttolibero";
    
    public void init() {
        // init db
        // Connection to db 
         try {
             connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            // This print goes into catalina.out or standard output with Jetty
            System.out.println("Error while connecting to database");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return;
        }
    }
    
    private PrintWriter initializeWebPage(HttpServletResponse response) {
        response.setContentType("text/html");
        PrintWriter page=null;
        try {
            page = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        page.write("<html>");
        page.write("<head><title>Magazzon Web Page</title></head>");
        page.write("<body>");
        return (page);
    }

    private void closeWebPage(PrintWriter page) {
        page.write("</body>");
        page.write("</html>");
    }

    private ResultSet getAvailableProducts() {
        ResultSet result = null;
        String query="select * from products where quantity > 0";
        try {
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error while connecting to database");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return (result);
    }

    private void showAvailableProducts(ResultSet result, PrintWriter page) {
        try {
            while (result.next()){
                page.write("<p>");
                page.write(result.getString("name"));
                page.write(" ");
                page.write(result.getString("quantity"));
                page.write(" ");
                page.write(result.getString("price"));
                page.write("</p>");

            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter page=null;
        page=initializeWebPage(response);
        page.println("<h1>Welcome into Magazzon</h1>");
        if (connection==null) {
            page.println("<h1>Database not connected</h1>");
            closeWebPage(page);
            return;
        }
        // 1.2 stampa dell'elenco dei prodotti con giacenza > 0
        ResultSet products=getAvailableProducts();
        showAvailableProducts(products,page);

        showAddProductForm(page);
        closeWebPage(page);
    }

    private void showAddProductForm(PrintWriter page) {
        page.println("<h1>Insert a new product</h1>");
        page.println("<form action='/magazzon' method='post'>");
        page.println("<input type='text' name='name' value='name'/>");
        page.println("<input type='text' name='quantity' value='quantity'/>");
        page.println("<input type='text' name='price' value='price'/>");
        page.println("<input type='submit' value='Create'/>");
        page.println("</form>");
        
    }
     public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter page=null;
        int rows=0;
        page=initializeWebPage(response);
        if (connection==null) {
            page.println("<h1>Database not connected</h1>");
            closeWebPage(page);
            return;
        }
        String name=request.getParameter("name");
        int quantity=Integer.parseInt(request.getParameter("quantity"));
        float price=Float.parseFloat(request.getParameter("price"));
        String query="insert into products(name,quantity,price) values (?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setFloat(3, price);
            rows=stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        page.write("<h1> Succesfully added "+rows+"</h1>");
        closeWebPage(page);
    }

    public void destroy() {
        // close db
    }
}
