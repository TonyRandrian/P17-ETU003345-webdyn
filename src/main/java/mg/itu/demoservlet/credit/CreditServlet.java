package mg.itu.demoservlet.credit;

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

public class CreditServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try (Connection conn = DB.connect()) {
            String libelle = req.getParameter("libelle");
            double montant = Double.parseDouble(req.getParameter("montant"));
            LocalDate date_debut = LocalDate.now();
            LocalDate date_fin = LocalDate.now();

            try {
                date_debut = LocalDate.parse(req.getParameter("date_debut"));
                date_fin = LocalDate.parse(req.getParameter("date_fin"));
            } catch (DateTimeParseException e) {
                date_debut = LocalDate.now();
                date_fin = LocalDate.now();
            }

            Credit c = new Credit(libelle, montant, date_debut, date_fin);
            c.save(conn);

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.setAttribute("message", "Succès");
        req.getRequestDispatcher("/credit-form.jsp").forward(req, resp);
    }
}
