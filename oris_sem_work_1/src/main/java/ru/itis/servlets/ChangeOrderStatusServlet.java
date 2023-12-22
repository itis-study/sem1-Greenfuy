package ru.itis.servlets;

import ru.itis.entities.Order;
import ru.itis.servlets.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/change-order-status")
public class ChangeOrderStatusServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            Order order = (Order) req.getSession().getAttribute("order");
            if (order != null) {
                String userId = (String) req.getSession().getAttribute("userId");
                int status = Integer.parseInt(req.getParameter("status"));
                if (status == 1) {
                    order.setStatus(Order.IN_PROGRESS);
                } else if (status == 2) {
                    order.setStatus(Order.CANCELLED);
                } else if (status == 3) {
                    order.setStatus(Order.COMPLETED);
                } else {
                    order.setStatus(Order.CREATED);
                }
                order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                order.setUpdatedBy(userId);
                req.getSession().setAttribute("order", order);
            }
            resp.sendRedirect("/semestrovka/orders");
        }
    }
}
