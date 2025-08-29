package murach.email;

import murach.business.User;
import murach.data.UserDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SurveyServlet", urlPatterns = { "/survey" })
public class SurveyServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        UserDB.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null)
            action = "add";

        if ("add".equals(action)) {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String dob = req.getParameter("dob");
            String heardFrom = req.getParameter("heardFrom");
            boolean wantsOffers = req.getParameter("wantsOffers") != null; // checkbox
            boolean wantsEmail  = req.getParameter("wantsEmail")  != null;
            String contact = req.getParameter("contact");

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setDateOfBirth(dob);   // <-- thêm field + setter
            user.setRef(heardFrom);     // <-- thêm field + setter
            user.setContact(contact);   // <-- thêm field + setter
            user.setWantOffers(wantsOffers); // <-- boolean + getter "isWantOffers"
            user.setWantEmail(wantsEmail);   // <-- boolean + getter "isWantEmail"
            req.setAttribute("user", user);
            req.getRequestDispatcher("/thanks.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.html");
        }
    }
}