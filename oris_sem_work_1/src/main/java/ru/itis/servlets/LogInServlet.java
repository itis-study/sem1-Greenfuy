package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.entities.Customer;
import ru.itis.entities.Repairman;
import ru.itis.utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LogInServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordHash = PasswordUtil.passwordToHash(password);

        if (validatePassword(REPAIRMEN, email, passwordHash)) {
            Repairman repairman = getRepairmanByEmail(email);
            req.getSession().setAttribute("userId", repairman.getId());
            req.getSession().setAttribute("role", REPAIRMEN);
            resp.sendRedirect("/semestrovka/profile");
        } else if (validatePassword(CUSTOMERS, email, passwordHash)) {
            Customer customer = getCustomerByEmail(email);
            req.getSession().setAttribute("userId", customer.getId());
            req.getSession().setAttribute("role", CUSTOMERS);
            resp.sendRedirect("/semestrovka/profile");
        } else {
            resp.sendRedirect("/semestrovka/login?invalid=true");
        }
    }
}
