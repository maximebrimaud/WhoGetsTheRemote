import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateFilm extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    Connection con = null;
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con = DriverManager.getConnection("jdbc:odbc:sample", "user", "password");

      // Turn on transactions
      con.setAutoCommit(false);

      Statement stmt = con.createStatement();
      String up = "nameMustChange";
      stmt.executeUpdate("UPDATE Film SET nomFilm = " + up + "where idFilm = 1");
      

      con.commit();
      out.println("Update successful!");
    }
    catch (Exception e) {
      // Any error is grounds for rollback
      try {
        con.rollback();
      }
      catch (SQLException ignored) { }
      out.println("Update failed");
    }
    finally {
      // Clean up.
      try {
        if (con != null) con.close();
      }
      catch (SQLException ignored) { }
    }
  }
}
