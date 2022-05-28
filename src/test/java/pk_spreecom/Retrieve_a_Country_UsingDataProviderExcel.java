package pk_spreecom;



import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Retrieve_a_Country_UsingDataProviderExcel extends Spreecom_TestData{

	@Test(dataProvider="ISOExcelData")//dataProvider will call method and let us read data from method
	  //public void getCountry(String iso, String iso_name,String iso3, int Expstatuscode) {
		 public void getCountry(String iso, String iso_name,String iso3) {
		  
		  RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront";
		  RequestSpecification httpRequest = RestAssured.given();
		  Response response = httpRequest.get("/countries/"+iso);
		  
			// Now let us print the body of the message to see what response
		  // we have recieved from the server
		  String responseBody = response.getBody().asString();
		  System.out.println("Response Body is =>  " + responseBody);
		  // Status Code Validation
		  int ActstatusCode = response.getStatusCode();
		  System.out.println("Status code is =>  " + ActstatusCode);
		  Assert.assertEquals(ActstatusCode,200);
	 
		  // First get the JsonPath object instance from the Response interface
		  //Assert.assertEquals(responseBody.contains(iso_name) /*Expected value*/, true /*Actual Value*/);
		

		  // First get the JsonPath object instance from the Response interface
		  //Assert.assertEquals(responseBody.contains(iso3) /*Expected value*/, true /*Actual Value*/);
		// Verify ISO_name
		  	JsonPath js = new JsonPath(response.asString());
			String iso_name_act = js.get("data.attributes.iso_name");
			System.out.println(iso_name_act);
			Assert.assertEquals(iso_name_act, iso_name);
			
			// Verify ISO3
			String iso3_act = js.get("data.attributes.iso3");
			System.out.println(iso3_act);
			Assert.assertEquals(iso3_act, iso3);
	  }
	}


