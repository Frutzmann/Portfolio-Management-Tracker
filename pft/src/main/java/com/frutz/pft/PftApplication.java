package com.frutz.pft;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class PftApplication {

//	private static String apiKey = "cbb801ba-10db-45f3-8e9a-d8e257f374e4";

	public static void main(String[] args) {
		SpringApplication.run(PftApplication.class, args);
	}

//	@GetMapping("/currencies")
//	public ResponseEntity<String> getCurrencies(@RequestParam(value = "starts", defaultValue = "1") String starts, @RequestParam(value = "limit", defaultValue = "10") String limit, @RequestParam(value = "convert", defaultValue = "USD") String convert) {
//		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
//
//
//		try {
//			String result = makeAPICall(uri, starts, limit, convert);
//			return new ResponseEntity<>(result, HttpStatus.OK);
//		} catch (URISyntaxException e) {
//            System.out.println("Error: cannot access content - " + e.toString());
//			return new ResponseEntity<>("Error: cannot access content - " + e.toString(), HttpStatus.BAD_REQUEST);
//        } catch (IOException e) {
//            System.out.println("Error: Invalid URL - " + e.toString());
//			return new ResponseEntity<>("Error: Invalid URL - " + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//    }
//
//	public static String makeAPICall(String uri, String starts, String limit, String convert)
//			throws URISyntaxException, IOException {
//		String responseContent = "";
//		List<String> params = new ArrayList<>();
//		params.add("starts=" + starts);
//		params.add("limit=" + limit);
//		params.add("convert=" + convert);
//
//		String queryString = String.join("&", params);
//		URI queryUri = new URI(uri + "?" + queryString);
//
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpGet request = new HttpGet(queryUri);
//
//		request.setHeader(HttpHeaders.ACCEPT, "application/json");
//		request.addHeader("X-CMC_PRO_API_KEY", apiKey);
//
//		CloseableHttpResponse response = client.execute(request);
//
//		try {
//			HttpEntity entity = response.getEntity();
//			responseContent = EntityUtils.toString(entity);
//			EntityUtils.consume(entity);
//		} finally {
//			response.close();
//		}
//
//		return responseContent;
//
//	}

}
