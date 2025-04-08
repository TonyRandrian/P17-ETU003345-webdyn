package mg.itu.demoservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.getRequestDispatcher("login-form.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String name = req.getParameter("username");
        String password = req.getParameter("password");

        try(Connection conn = DB.connect()) {
            User u = new User();
            u = u.login(conn, name, password);

            if (u == null) {
                resp.sendRedirect("/ETU003345/login");
                return;
            }

            req.getSession().setAttribute("user", u);
            resp.sendRedirect("/ETU003345/");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
