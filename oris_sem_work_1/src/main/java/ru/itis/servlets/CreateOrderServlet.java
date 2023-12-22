package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.entities.Customer;
import ru.itis.entities.Order;
import ru.itis.entities.Repairman;
import ru.itis.repositories.CustomerDAO;
import ru.itis.repositories.OrderDAO;
import ru.itis.repositories.RepairmanDAO;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.utils.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/create-order")
public class CreateOrderServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean canCreateOrder = (Boolean) req.getSession().getAttribute("canCreateOrder");
        if (canCreateOrder != null && canCreateOrder) {
            req.getSession().setAttribute("canCreateOrder", false);
            req.getRequestDispatcher("/WEB-INF/views/create-order.jsp").forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = (String) req.getSession().getAttribute("userId");
        Repairman repairman = (Repairman) req.getSession().getAttribute("repairman");
        String repairmanName = repairman.getFirstName() + " " + repairman.getLastName();
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        addOrder(
                Order.builder()
                        .repairmanName(repairmanName)
                        .price(price)
                        .status(Order.CREATED)
                        .description(description)
                        .createdAt(currentTime)
                        .updatedAt(currentTime)
                        .updatedBy(customerId)
                        .build(),
                customerId,
                repairman.getId()
        );

        String recipientEmail = getEmailById(repairman.getId());

        EmailService emailService = new EmailService(recipientEmail, "Order has been created",
                "New order has been created. Check it out http://localhost:8080/semestrovka/orders?show=created");
        Thread thread = new Thread(emailService);
        thread.start();

        resp.sendRedirect("/semestrovka/orders");
    }

    private String getEmailById(String recipientId) throws SQLException {
        return RepairmanDAO.getInstance().getRepairman("id", recipientId).getEmail();
    }
}