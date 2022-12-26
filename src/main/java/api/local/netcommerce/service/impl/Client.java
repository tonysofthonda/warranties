package api.local.netcommerce.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import api.local.netcommerce.dto.RequestDataDto;

@Component
public class Client {

	private final RestTemplate restTemplate;

	public Client() {
		restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
	}

	public ResponseEntity<String> getWithBody(String requestBody, HttpHeaders headers) {
		HttpEntity<String> requestUpdate = new HttpEntity<>("[" + requestBody + "]", headers);
		ResponseEntity<String> responseBody = restTemplate.exchange("http://api.ventasmotos.honda.mx/info_vin", HttpMethod.GET, requestUpdate, String.class);
		return responseBody;
	}

	private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
	
		@Override
		protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
	
			if (HttpMethod.GET.equals(httpMethod)) {
				return new HttpEntityEnclosingGetRequestBase(uri);
			}
			return super.createHttpUriRequest(httpMethod, uri);
		}
	}

	private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {
	
		public HttpEntityEnclosingGetRequestBase(final URI uri) {
			super.setURI(uri);
		}
	
		@Override
		public String getMethod() {
			return HttpMethod.GET.name();
		}
	}
}