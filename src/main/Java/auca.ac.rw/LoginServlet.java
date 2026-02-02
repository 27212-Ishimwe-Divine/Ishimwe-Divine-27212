
package auca.ac.rw;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();

        if (password.length() < 8) {
            out.println("Hello " + username + ", your password is weak. Try a strong one.");
        } else {
            out.println("Welcome " + username);
        }
    }
}
