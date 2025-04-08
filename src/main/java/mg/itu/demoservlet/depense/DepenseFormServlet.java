package mg.itu.demoservlet.depense;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.demoservlet.DB;
import mg.itu.demoservlet.credit.Credit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DepenseFormServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("/ETU003345/login");
            return;
        }

        if (req.getParameter("error") != null && req.getParameter("error").equals("1")) {
            req.setAttribute("message", "Solde insuffisant");
        }

        try(Connection conn = DB.connect()) {
            Credit c = new Credit();
            List<Credit> credits = c.getAll(conn);

            req.setAttribute("credits", credits);
            req.getRequestDispatcher("depense-form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
