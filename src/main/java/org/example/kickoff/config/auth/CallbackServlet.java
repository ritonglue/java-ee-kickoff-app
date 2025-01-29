package org.example.kickoff.config.auth;

import java.io.IOException;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/Callback")
public class CallbackServlet extends HttpServlet {

	@Inject
	private OpenIdContext context;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (context != null) {
			Optional<String> opt = context.getStoredValue(request, response, OpenIdConstant.ORIGINAL_REQUEST);
			String redirect = opt.orElse(request.getContextPath());
			response.sendRedirect(redirect);
		}
	}

}
