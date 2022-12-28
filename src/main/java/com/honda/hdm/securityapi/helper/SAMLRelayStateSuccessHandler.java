package com.honda.hdm.securityapi.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of a success handler which interprets meaning of the
 * RelayState inside SAMLCredential as an URL to redirect user to.
 */
public class SAMLRelayStateSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	/**
	 * Implementation tries to load RelayString from the SAMLCredential
	 * authentication object and in case the state is present uses it as the target
	 * URL. In case the state is missing behaviour is the same as of the
	 * SavedRequestAwareAuthenticationSuccessHandler.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		Object credentials = authentication.getCredentials();
//		logger.debug("Credentials in onAuthenticationSuccess: " + credentials);
		if (credentials instanceof SAMLCredential) {
//            SAMLCredential samlCredential = (SAMLCredential) authentication;
//            String relayStateURL = getTargetURL(samlCredential.getRelayState());
//            if (relayStateURL != null) {
//                logger.debug("Redirecting to RelayState Url: " + relayStateURL);
//                getRedirectStrategy().sendRedirect(request, response, relayStateURL);
//                return;
//            }

			SAMLCredential samlCredential = (SAMLCredential) credentials;
			String relayStateURL = this.getTargetURL(samlCredential.getRelayState());
			if (relayStateURL != null) {
//				logger.debug("Redirecting to RelayState Url: " + relayStateURL);
//				logger.debug("Authentication: " + authentication);

				String relayStateUrlWithToken = relayStateURL + "?" + authentication;
				this.getRedirectStrategy().sendRedirect(request, response, relayStateUrlWithToken);
//				logger.debug("Redirect in getRedirectStrategy: " + relayStateUrlWithToken);
				return;
			}
		}

		super.onAuthenticationSuccess(request, response, authentication);

	}

	/**
	 * Method is responsible for processing relayState and returning URL the system
	 * can redirect to. Method can decide to ignore the relayState and redirect user
	 * to default location by returning null.
	 *
	 * @param relayState relay state to process, can be null
	 * @return null to ignore the state, URL to redirect to otherwise
	 */
	protected String getTargetURL(String relayState) {
		return relayState;
	}

}
