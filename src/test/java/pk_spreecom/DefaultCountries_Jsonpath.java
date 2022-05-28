package pk_spreecom;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DefaultCountries_Jsonpath {
	@Test

	public void GetDefaultCountries() {

		RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront/countries/default";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		// First get the JsonPath object instance from the Response interface
		Assert.assertEquals(responseBody.contains("UNITED STATES") /* Expected value */, true /* Actual Value */);

		// Verify using JSON Path Value

		JsonPath js = new JsonPath(response.asString());
		String iso_name = js.get("data.attributes.iso_name");
		System.out.println(iso_name);
		Assert.assertEquals(iso_name, "UNITED STATES");

	}
}
