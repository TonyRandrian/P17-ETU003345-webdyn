package mg.itu.demoservlet.depense;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.demoservlet.DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DepenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try(Connection conn = DB.connect()) {
            try {
                conn.setAutoCommit(false);
                int id_credit = Integer.parseInt(req.getParameter("id_credit"));
                double montant = Double.parseDouble(req.getParameter("montant"));
                LocalDate date = LocalDate.now();

                if (req.getParameter("date") != null) {
                    try {
                        date = LocalDate.parse(req.getParameter("dates"));
                    } catch (DateTimeParseException e) {
                        date = LocalDate.now();
                    }
                }

                Depense depense = new Depense(id_credit, montant, date);
                int result = depense.saveWithCheck(conn);

                if (result == -1) {
                    conn.rollback();
                    resp.sendRedirect("/ETU003345/new-depense?error=1");
                    return;
                }

                conn.commit();

                resp.sendRedirect("/ETU003345/new-depense");
            } catch (SQLException ex) {
                conn.rollback();
                throw new ServletException(ex);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
