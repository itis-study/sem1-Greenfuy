package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.entities.Order;
import ru.itis.servlets.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.itis.repositories.OrderDAO.DEFAULT;

@WebServlet("/orders")
public class OrdersServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            String userId = (String) req.getSession().getAttribute("userId");
            String role = (String) req.getSession().getAttribute("role");
            int ordersCount = getOrdersCount(userId, role);
            req.setAttribute(
                    "pageCount",
                    ordersCount % 10 == 0 ? ordersCount / 10 : ordersCount / 10 + 1
            );

            String show = req.getParameter("show");
            int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page") ) - 1;

            List<Order> orders = getOrders(show == null ? DEFAULT : show, page, userId, role);

            req.getSession().setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
        }
    }
}
