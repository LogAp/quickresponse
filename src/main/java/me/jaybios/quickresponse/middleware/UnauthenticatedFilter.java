package me.jaybios.quickresponse.middleware;

import me.jaybios.quickresponse.controllers.SessionController;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.REQUEST;

@WebFilter(urlPatterns = "/unauthenticated/*", filterName = "Unauthenticated User Filter",
        description = "Allow viewing of page only if unauthenticated", dispatcherTypes = { REQUEST, FORWARD })
public class UnauthenticatedFilter implements Filter {
    @Inject
    private SessionController session;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (session.isUserAuthenticated()) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
