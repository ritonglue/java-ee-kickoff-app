package org.example.kickoff.config.auth;

import static jakarta.interceptor.Interceptor.Priority.APPLICATION;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import static org.example.kickoff.model.Group.USER;

import java.util.Set;
import java.util.function.Supplier;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;

import org.example.kickoff.business.exception.CredentialsException;
import org.example.kickoff.business.exception.EmailNotVerifiedException;
import org.example.kickoff.business.exception.InvalidGroupException;
import org.example.kickoff.business.service.PersonService;
import org.example.kickoff.model.Person;

@Priority(APPLICATION)
@Dependent
@Decorator
public class KickoffIdentityStore implements IdentityStore {

	@Inject
	@Delegate
	@Any
	private IdentityStore store;

	@Inject
	private OpenIdContext context;

	@Inject
	private PersonService personService;

	@Override
	public CredentialValidationResult validate(Credential credential) {
		CredentialValidationResult result = this.store.validate(credential);
		switch(result.getStatus()) {
		case INVALID:
		case NOT_VALIDATED:
			break;
		case VALID:
			String email = this.context.getClaims().getEmail().orElse(null);
			Supplier<Person> personSupplier = () -> personService.getByEmail(email);
			result = validate(personSupplier);
			break;
		}
		return result;
	}

	static CredentialValidationResult validate(Supplier<Person> personSupplier) {
		if (personSupplier == null) {
			return NOT_VALIDATED_RESULT;
		}

		try {
			Person person = personSupplier.get();

			if (!person.getGroups().contains(USER)) {
				throw new InvalidGroupException();
			}

			if (!person.isEmailVerified()) {
				throw new EmailNotVerifiedException();
			}

			return new CredentialValidationResult(new KickoffCallerPrincipal(person), person.getRolesAsStrings());
		}
		catch (CredentialsException e) {
			return INVALID_RESULT;
		}
	}

	@Override
	public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
		return this.store.getCallerGroups(validationResult);
	}

	@Override
	public Set<ValidationType> validationTypes() {
		return this.store.validationTypes();
	}

	@Override
	public int priority() {
		return this.store.priority();
	}
}