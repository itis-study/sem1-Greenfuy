package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.entities.Customer;
import ru.itis.entities.Repairman;
import ru.itis.servlets.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            String userId = (String) req.getSession().getAttribute("userId");
            String role = (String) req.getSession().getAttribute("role");

            if (CUSTOMERS.equals(role)) {
                Customer customer = getCustomerById(userId);
                req.getSession().setAttribute("user", customer);
            } else if (REPAIRMEN.equals(role)) {
                Repairman repairman = getRepairmanById(userId);
                req.getSession().setAttribute("user", repairman);
            }
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        }
    }
}
