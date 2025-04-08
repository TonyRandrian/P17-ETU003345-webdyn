package mg.itu.demoservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.demoservlet.credit.Credit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try (Connection conn = DB.connect()) {
            Credit c = new Credit();
            List<Credit> credits = c.getAll(conn);

            HashMap<String, Double[]> data = new HashMap<>();
            for (Credit credit : credits) {
                data.put(credit.getLibelle(),
                    credit.getSumAndLeft(conn));
            }

            req.setAttribute("data", data);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
