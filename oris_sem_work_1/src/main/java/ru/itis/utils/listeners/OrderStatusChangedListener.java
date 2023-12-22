package ru.itis.utils.listeners;

import lombok.SneakyThrows;
import ru.itis.entities.Order;
import ru.itis.repositories.CustomerDAO;
import ru.itis.repositories.OrderDAO;
import ru.itis.repositories.RepairmanDAO;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.utils.EmailService;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.SQLException;

import static ru.itis.servlets.base.BaseServlet.REPAIRMEN;

public class OrderStatusChangedListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @SneakyThrows
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        String userId = (String) httpSessionBindingEvent.getSession().getAttribute("userId");
        String attributeName = httpSessionBindingEvent.getName();
        Order newValue = (Order) httpSessionBindingEvent.getSession().getAttribute("order");

        if (attributeName.equals("order")) {
            OrderDAO orderDAO = OrderDAO.getInstance();
            orderDAO.updateOrder(newValue, userId);

            String role = (String) httpSessionBindingEvent.getSession().getAttribute("role");
            String recipientId = REPAIRMEN.equals(role) ? newValue.getCustomerId() : newValue.getRepairmanId();
            String recipientEmail = getEmailById(recipientId, role);

            EmailService emailService = new EmailService(recipientEmail, "Order status changed",
                    "Status of your order to " + newValue.getRepairmanName()
                            + " has been changed to " + newValue.getStatus());
            Thread thread = new Thread(emailService);
            thread.start();
        }
    }

    private String getEmailById(String recipientId, String role) throws SQLException {
        if (role.equals(BaseServlet.CUSTOMERS)) {
            return RepairmanDAO.getInstance().getRepairman("id", recipientId).getEmail();
        } else {
            return CustomerDAO.getInstance().getCustomer("id", recipientId).getEmail();
        }
    }
}
