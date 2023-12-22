package ru.itis.utils.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/profile-edit", "/profile", "/create-order", "/orders", "/order-page", "/signout", "/change-order-status"}, filterName = "IsUserAuthorizedWebFilter")
public class IsUserAuthorizedWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute("userId") == null || req.getSession().getAttribute("role") == null) {
            req.getSession().setAttribute("redirected", true);
            ((HttpServletResponse) servletResponse).sendRedirect("/semestrovka/login");
        } else {
            req.getSession().setAttribute("redirected", false);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
