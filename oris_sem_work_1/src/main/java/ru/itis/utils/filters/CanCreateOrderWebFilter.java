package ru.itis.utils.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.servlets.base.BaseServlet.CUSTOMERS;

@WebFilter("/create-order")
public class CanCreateOrderWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute("userId") == null
                || req.getSession().getAttribute("role") != CUSTOMERS
        && req.getParameter("repairmanId") == null) {
            req.getSession().setAttribute("canCreateOrder", false);
            ((HttpServletResponse) servletResponse).sendRedirect("/semestrovka/profile");
        } else {
            req.getSession().setAttribute("canCreateOrder", true);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
