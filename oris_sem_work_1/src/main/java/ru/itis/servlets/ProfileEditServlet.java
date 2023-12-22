package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.entities.Customer;
import ru.itis.entities.Repairman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile-edit")
public class ProfileEditServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            req.getRequestDispatcher("/WEB-INF/views/profile-edit.jsp").forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = (String) req.getSession().getAttribute("role");
        String userId = (String) req.getSession().getAttribute("userId");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String description = req.getParameter("description");

        if (CUSTOMERS.equals(role)) {
            Customer user = Customer.builder().firstName(firstName).lastName(lastName).email(email).build();
            updateCustomer(user, userId);
        } else if (REPAIRMEN.equals(role)) {
            Repairman user = Repairman.builder().firstName(firstName).lastName(lastName).email(email).description(description).build();
            updateRepairman(user, userId);
        }
        resp.sendRedirect("/semestrovka/profile");
    }
}
