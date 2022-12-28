package com.honda.hdm.securityapi.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import com.honda.hdm.securityapi.model.CustomSamlUserDetails;

public class SamlUserDetailsService implements SAMLUserDetailsService{

	@Override
	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
		CustomSamlUserDetails dealerUser = new CustomSamlUserDetails();
		dealerUser.setDealerNumber(credential.getAttributeAsString("DealerNumber"));
		dealerUser.setUserId(credential.getAttributeAsString("UserID"));
		dealerUser.setFullName(credential.getAttributeAsString("FullName"));
		if(credential.getAttributeAsString("Email") != null) {
			dealerUser.setEmail(credential.getAttributeAsString("Email"));
		}

		dealerUser.setAuthorities(Arrays.stream(credential.getAttributeAsString("JobCode").split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		return dealerUser;
	}

}
