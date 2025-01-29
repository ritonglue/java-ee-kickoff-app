package org.example.kickoff.view.auth;

import java.io.IOException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;

@Named
@RequestScoped
public class LoginBacking extends AuthBacking {

	public void login() throws IOException {
		authenticate(new AuthenticationParameters());
	}

}