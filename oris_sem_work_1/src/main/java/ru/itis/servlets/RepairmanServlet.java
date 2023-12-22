package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.entities.Repairman;
import ru.itis.entities.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/repairman")
public class RepairmanServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Repairman repairman = getRepairmanById(id);
        req.getSession().setAttribute("repairman", repairman);
        int pageCount = getReviewsCount(id);
        req.setAttribute("pageCount", pageCount % 10 == 0 ? pageCount / 10 : pageCount / 10 + 1);

        int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page")) - 1;

        List<Review> reviews = getReviewsByRepairmanId(id, page);
        req.getSession().setAttribute("reviews", reviews);
        req.getRequestDispatcher("/WEB-INF/views/repairman.jsp").forward(req, resp);
    }
}
