package com.nichoscode.sportyshoes.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = exception.getMessage();
        System.out.println("Authentication failed: " + errorMessage); // Log the error
        // Optionally, you can include the error message in the redirect URL or handle it as needed
        super.setDefaultFailureUrl("/login?error=true&message=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
