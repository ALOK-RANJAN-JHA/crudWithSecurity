package com.crud.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionExpiryFilter implements Filter {

    private static final long ABSOLUTE_TIMEOUT_MS = 3600_000L; // 1 hour

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        if (session != null) {
            Long created = (Long) session.getAttribute("sessionCreatedTime");
            if (created != null) {
                long elapsed = System.currentTimeMillis() - created;
                if (elapsed > ABSOLUTE_TIMEOUT_MS) {
                    session.invalidate();
                    resp.sendRedirect(req.getContextPath() + "/login?expired=true");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }
}

