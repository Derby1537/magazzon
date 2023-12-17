package edu.unict.magazzon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/magazzon")
public class app extends HttpServlet{
    
    EntityManager em;
    public void init() {
        // init db
        // Connection to db 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
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

    private List<products> getAvailableProducts() {
        List<products> products = em.createQuery("select p from products p", products.class).getResultList();   
        return (products);
    }

    private void showAvailableProducts(List<products> result, PrintWriter page) {
            for (products p : result){
                page.write("<p>");
                page.write(p.getName());
                page.write(" ");
                page.write(p.getQuantity());
                page.write(" ");
                page.write(Float.toString(p.getPrice()));
                page.write("</p>");

            }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter page=null;
        page=initializeWebPage(response);
        page.println("<h1>Welcome into Magazzon</h1>");
        // 1.2 stampa dell'elenco dei prodotti con giacenza > 0
        List<products> products=getAvailableProducts();
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
    /* public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter page=null;
        int rows=0;
        page=initializeWebPage(response);
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
 */
    public void destroy() {
        // close db
    }
}
