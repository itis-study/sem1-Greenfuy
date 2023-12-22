package ru.itis.servlets;

import lombok.SneakyThrows;
import ru.itis.repositories.RepairmanDAO;
import ru.itis.servlets.base.BaseServlet;
import ru.itis.entities.Repairman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.itis.repositories.RepairmanDAO.DEFAULT;

@WebServlet("/main")
public class MainServlet extends BaseServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int repairmenCount = getRepairmenCount();
        req.setAttribute(
                "pageCount",
                repairmenCount % 10 == 0 ? repairmenCount / 10 : repairmenCount / 10 + 1
        );

        String sort = req.getParameter("sort");
        int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page") ) - 1;
        List<Repairman> repairmen = getRepairmen(
                sort == null ? DEFAULT : sort,
                page
        );

        req.getSession().setAttribute("repairmen", repairmen);
        req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);
    }
}
