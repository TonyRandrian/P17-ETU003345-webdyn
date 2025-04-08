package mg.itu.demoservlet.credit;

import jakarta.servlet.ServletException;
import mg.itu.demoservlet.DB;
import mg.itu.demoservlet.depense.Depense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Credit {
    int id;
    String libelle;
    double montant;
    LocalDate date_debut;
    LocalDate date_fin;

    public Credit() {
    }

    public Credit(String libelle, double montant, LocalDate date_debut, LocalDate date_fin) {
        this.libelle = libelle;
        this.montant = montant;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Credit(int id, String libelle, double montant, LocalDate date_debut, LocalDate date_fin) {
        this.id = id;
        this.libelle = libelle;
        this.montant = montant;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public void save() throws ServletException {
        try (Connection conn = DB.connect()) {
            save(conn);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void save(Connection conn) throws SQLException, ServletException {
        String request = "INSERT INTO ligne_credit(libelle, montant, date_debut, date_fin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setString(1, libelle);
            ps.setDouble(2, montant);
            ps.setDate(3, Date.valueOf(date_debut));
            ps.setDate(4, Date.valueOf(date_fin));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'insertion de ligne de crédit" + e);
        }
    }

    public void update(String libelle, double montant, LocalDate date_debut, LocalDate date_fin) throws ServletException {
        try (Connection conn = DB.connect()) {
            update(conn, libelle, montant, date_debut, date_fin);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void update(Connection conn, String libelle, double montant, LocalDate date_debut, LocalDate date_fin)
        throws SQLException, ServletException {
        String request = "UPDATE ligne_credit SET libelle = ?, montant = ?, date_debut = ?, date_fin = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setString(1, libelle);
            ps.setDouble(2, montant);
            ps.setDate(3, Date.valueOf(date_debut));
            ps.setDate(4, Date.valueOf(date_fin));
            ps.setInt(5, this.id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mis à jour de crédit" + e);
        }
    }

    public void delete(int id) throws ServletException {
        try (Connection conn = DB.connect()) {
            delete(conn, id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void delete(Connection conn, int id) throws ServletException {
        String request = "DELETE FROM ligne_credit WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public Credit getById(int id) throws ServletException {
        try(Connection conn = DB.connect()) {
            return getById(conn, id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public Credit getById(Connection conn, int id) throws ServletException {
        String request = "SELECT * FROM ligne_credit WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Credit(
                        rs.getInt("id"),
                        rs.getString("libelle"),
                        rs.getDouble("montant"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return null;
    }

    public List<Credit> getAll() throws ServletException {
        try(Connection conn = DB.connect()) {
            return getAll(conn);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public List<Credit> getAll(Connection conn) throws ServletException {
        List<Credit> result = new ArrayList<>();
        String request = "SELECT * FROM ligne_credit";

        try(Statement s = conn.createStatement()) {
            try(ResultSet rs = s.executeQuery(request)) {
                while (rs.next()) {
                    result.add(new Credit(
                        rs.getInt("id"),
                        rs.getString("libelle"),
                        rs.getDouble("montant"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return result;
    }

    public Double[] getSumAndLeft(Connection conn) throws ServletException {
        Depense d = new Depense();
        double sum = d.getSumByCredit(conn, this.id);

        Double[] result = new Double[2];
        result[0] = sum;
        result[1] = this.montant - sum;

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }
}
