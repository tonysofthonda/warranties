package com.honda.hdm.securityapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;

@RestController
public class Controller {


	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping(value = "/saml/SSO")
	public String handleSamlAuth() {
		return "";
	}

	@GetMapping("/home")
	public RedirectView redirect(RedirectAttributes attributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object credentials = authentication.getCredentials();
		SAMLCredential credential = (SAMLCredential) credentials;
		return new RedirectView(
				"https://mss.hondaweb.com/#/iN?DealerNumber=" + credential.getAttributeAsString("DealerNumber")
						+ "&UserID=" + credential.getAttributeAsString("UserID") + "&FullName="
						+ credential.getAttributeAsString("FullName"));
	}

}
