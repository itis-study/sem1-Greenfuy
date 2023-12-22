package ru.itis.utils.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/login", "/signup-choice", "/signup"}, filterName = "IsUserNotAuthorizedWebFilter")
public class IsUserNotAuthorizedWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute("userId") != null && req.getSession().getAttribute("role") != null) {
            req.getSession().setAttribute("redirected", true);
            ((HttpServletResponse) servletResponse).sendRedirect("/semestrovka/main");
        } else {
            req.getSession().setAttribute("redirected", false);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
