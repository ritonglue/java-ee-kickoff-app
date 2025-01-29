package org.example.kickoff.view.auth;

import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;
import static org.omnifaces.util.Messages.addFlashGlobalWarn;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;

import org.example.kickoff.business.service.PersonService;
import org.example.kickoff.model.Person;
import org.example.kickoff.view.ActiveUser;

public abstract class AuthBacking {

	protected Person person;

	@Inject
	protected PersonService personService;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private ActiveUser activeUser;

	@PostConstruct
	public void init() {
		if (activeUser.isPresent()) {
			addFlashGlobalWarn("auth.message.warn.already_logged_in");
		}
		else {
			person = new Person();
		}
	}

	protected void authenticate(AuthenticationParameters parameters) throws IOException {
		securityContext.authenticate(getRequest(), getResponse(), parameters);
	}

	public Person getPerson() {
		return person;
	}

}