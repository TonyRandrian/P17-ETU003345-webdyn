package mg.itu.demoservlet.depense;

import jakarta.servlet.ServletException;
import mg.itu.demoservlet.DB;
import mg.itu.demoservlet.credit.Credit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Depense {
    int id;
    int id_credit;
    double montant;
    LocalDate date;

    public Depense() {
    }

    public Depense(int id, int id_credit, double montant, LocalDate date) {
        this.id = id;
        this.id_credit = id_credit;
        this.montant = montant;
        this.date = date;
    }

    public Depense(int id_credit, double montant, LocalDate date) {
        this.id_credit = id_credit;
        this.montant = montant;
        this.date = date;
    }

    public void save() throws ServletException {
        try (Connection conn = DB.connect()) {
            save(conn);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void save(Connection conn) throws ServletException {
        String request = "INSERT INTO ligne_depense(id_credit, montant, dates) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id_credit);
            ps.setDouble(2, montant);
            ps.setDate(3, Date.valueOf(date));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void delete(int id) throws ServletException {
        try (Connection conn = DB.connect()) {
            delete(conn, id);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void delete(Connection conn, int id) throws ServletException {
        String request = "DELETE FROM ligne_depense WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public Depense getById(int id) throws ServletException {
        try (Connection conn = DB.connect()) {
            return getById(conn, id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public Depense getById(Connection conn, int id) throws ServletException {
        String request = "SELECT * FROM ligne_depense WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Depense(
                        rs.getInt("id"),
                        rs.getInt("id_credit"),
                        rs.getDouble("montant"),
                        rs.getDate("dates").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return null;
    }

    public void update(int id_credit, double montant, LocalDate date) throws ServletException {
        try (Connection conn = DB.connect()) {
            update(conn, id_credit, montant, date);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void update(Connection conn, int id_credit, double montant, LocalDate date) throws ServletException {
        String request = "UPDATE ligne_depense SET id_credit = ?, montant = ?, dates = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id_credit);
            ps.setDouble(2, montant);
            ps.setDate(3, Date.valueOf(date));
            ps.setInt(4, this.id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public List<Depense> getAll() throws ServletException {
        try (Connection conn = DB.connect()) {
            return getAll(conn);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public List<Depense> getAll(Connection conn) throws ServletException {
        List<Depense> result = new ArrayList<>();
        String request = "SELECT * FROM ligne_depense";

        try (Statement s = conn.createStatement()) {
            try (ResultSet rs = s.executeQuery(request)) {
                while (rs.next()) {
                    result.add(new Depense(
                        rs.getInt("id"),
                        rs.getInt("id_credit"),
                        rs.getDouble("montant"),
                        rs.getDate("dates").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public double getSumByCredit(Connection conn, int id_credit) throws ServletException {
        String request = "SELECT SUM(montant) total FROM ligne_depense WHERE id_credit = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id_credit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return 0;
    }

    public int saveWithCheck(Connection conn) throws ServletException {
        Credit credit = new Credit();
        credit = credit.getById(this.id_credit);

        double sum = getSumByCredit(conn, id_credit);
        if (credit.getMontant() < sum + this.montant) {
            return -1;
        }

        save(conn);
        return 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_credit() {
        return id_credit;
    }

    public void setId_credit(int id_credit) {
        this.id_credit = id_credit;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
