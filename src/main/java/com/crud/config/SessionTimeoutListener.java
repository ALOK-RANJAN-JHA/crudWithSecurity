package com.crud.config;


import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class SessionTimeoutListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // inactivity timeout = 1 hour
        se.getSession().setMaxInactiveInterval(3600);
        se.getSession().setAttribute("sessionCreatedTime", System.currentTimeMillis());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
       
    }
}

