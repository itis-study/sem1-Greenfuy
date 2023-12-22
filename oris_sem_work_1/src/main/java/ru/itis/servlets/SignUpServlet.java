package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.entities.Customer;
import ru.itis.entities.Repairman;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.utils.PasswordUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUpServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordHash = PasswordUtil.passwordToHash(password);
        String role = (String) req.getSession().getAttribute("role");

        if (isEmailUnique(email)) {
            if (REPAIRMEN.equals(role)) {
                addRepairman(Repairman.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .build(),
                        passwordHash);
                resp.sendRedirect("/semestrovka/login");
            } else if (CUSTOMERS.equals(role)) {
                addCustomer(Customer.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .build(),
                        passwordHash);
                resp.sendRedirect("/semestrovka/login");
            } else {
                resp.sendRedirect("/semestrovka/signup-choice");
            }
        } else {
            resp.sendRedirect("/semestrovka/signup?emailExists=true");
        }
    }
}
