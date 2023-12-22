package ru.itis.servlets;

import ru.itis.servlets.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signout")
public class SignOutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean redirected = (Boolean) req.getSession().getAttribute("redirected");
        if (redirected == null || !redirected) {
            req.getRequestDispatcher("WEB-INF/views/signout.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String confirmed = req.getParameter("confirmed");
        if (confirmed != null && confirmed.equals("true")) {
            req.getSession().invalidate();
            resp.sendRedirect("/semestrovka/main");
        }
    }
}
