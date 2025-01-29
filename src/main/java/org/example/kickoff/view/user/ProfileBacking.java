package org.example.kickoff.view.user;

import static org.omnifaces.util.Messages.addGlobalInfo;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.example.kickoff.business.service.PersonService;
import org.example.kickoff.model.Person;
import org.example.kickoff.view.ActiveUser;

@Named
@RequestScoped
public class ProfileBacking {

	private Person person;

	@Inject
	private ActiveUser activeUser;

	@Inject
	private PersonService personService;

	@PostConstruct
	public void init() {
		person = activeUser.get();
	}

	public void save() {
		personService.update(person);
		addGlobalInfo("user_profile.message.info.account_updated");
	}

	public Person getPerson() {
		return person;
	}
}