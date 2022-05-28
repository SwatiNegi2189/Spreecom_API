package pk_spreecom;




import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class List_all_Countries {

	@Test
	public void Get_Default_Country() {
		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification httpRequest = RestAssured.given();
		//Response response = httpRequest.get(); or remove end point from URL and paste in line 18
		Response response = httpRequest.request(Method.GET,"/api/v2/storefront/countries");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		//String responseBody = response.getBody().asString();or if we want to print in correct line manner
		String responseBody = response.getBody().prettyPrint();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		// First get the JsonPath object instance from the Response interface
		Assert.assertEquals(responseBody.contains("INDIA") /* Expected value */, true /* Actual Value */);

	}

}
