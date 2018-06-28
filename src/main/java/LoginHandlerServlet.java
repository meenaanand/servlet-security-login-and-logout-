import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {"/loginHandler"})
public class LoginHandlerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String theUser = req.getParameter("theUser");
        String thePassword = req.getParameter("thePassword");
        try {
            req.login(theUser, thePassword);
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            forwardToLogin(req, resp, "Error: " + e.getMessage());
            return;
        }
        boolean loggedIn = req.getUserPrincipal() != null && req.isUserInRole("employee");
        if (loggedIn) {
            resp.sendRedirect("/app");
        } else {
            forwardToLogin(req, resp, "Login failed.");
        }
    }

    public static void forwardToLogin(HttpServletRequest req, HttpServletResponse resp,
                                      String errorMessage)
            throws ServletException, IOException {

        req.setAttribute("errorMsg", errorMessage);
        req.getRequestDispatcher("/login.jsp")
           .forward(req, resp);
    }
}