package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.entities.Order;
import ru.itis.entities.Repairman;
import ru.itis.entities.Review;
import ru.itis.servlets.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order-page")
public class OrderPageServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            String id = req.getParameter("id");
            Order order = getOrderById(id);
            req.getSession().setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/order-page.jsp").forward(req, resp);
        }
    }
}
