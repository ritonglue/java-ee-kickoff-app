package org.example.kickoff.config.auth;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.OpenIdAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.LogoutDefinition;


@OpenIdAuthenticationMechanismDefinition(
      providerURI = "${oidcConfig.providerURI}"
    , clientId = "${oidcConfig.clientId}"
    , clientSecret = "${oidcConfig.clientSecret}"
    , redirectURI = "${oidcConfig.redirectURI}"
    , logout = @LogoutDefinition(redirectURI = "${oidcConfig.logoutURI}")
)
@ApplicationScoped
@Named("oidcConfig")
public class KickoffFormAuthenticationMechanism {
	private String clientId = "java-ee-kickoff-app";
	private String clientSecret = "IFpT0RtzOGQjLSnGxXOUnVXyR4kUAdPH";
	private String redirectURI = "${baseURL}/Callback";
	private String logoutURI = "${baseURL}/home.xhtml";
	private String providerURI = "http://localhost:8180/realms/java-ee-kickoff-app";
	public String getClientId() {
		return clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public String getRedirectURI() {
		return redirectURI;
	}
	public String getLogoutURI() {
		return logoutURI;
	}
	public String getProviderURI() {
		return providerURI;
	}

}
