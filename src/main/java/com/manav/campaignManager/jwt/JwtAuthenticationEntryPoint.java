package com.manav.campaignManager.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create your custom response here
        GenericResponse<User> customResponse = GenericResponse.<User>builder()
                .status(Status.builder()
                        .status(false)
                        .message("Operation Failed")
                        .error(authException.getMessage())
                        .build())
                .data(null)
                .build();

        // Set the response body and status
        response.setContentType("application/json"); // Assuming you want JSON response
        PrintWriter writer = response.getWriter();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customResponse);

        writer.println(json); // Serialize your custom response object

        // Optionally, you can set additional headers if needed
        // response.setHeader("Custom-Header", "foo");
    }
}
