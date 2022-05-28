package pk_spreecom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.IllegalFormatException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utility_Function {

	public static String oAuth_Token() {

		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("grant_type", "password");
		requestParams.put("username", "priya@spree.com");
		requestParams.put("password", "spree123");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post("/spree_oauth/token");
		// response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();

		// String responseBody = response.getBody().toString();
		// System.out.println("Response Body is => " + responseBody);
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String outh_token = jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
		// First get the JsonPath object instance from the Response interface
		// Assert.assertEquals(responseBody.contains("Product was created with UI.")
		// /*Expected value*/, true /*Actual Value*/, "Response body contains ");
		return outh_token;

	}

	// To read data from Excel File
	private static Sheet ExcelWSheet;
	private static Workbook ExcelWBook;

	// This method is to read the test data from the Excel
	public String[][] getExcelData(String fileName, String sheetName)
			throws EncryptedDocumentException, IOException, IllegalFormatException {
		String[][] arrayExcelData = null;
		FileInputStream ExcelFile = new FileInputStream(fileName);
		ExcelWBook = WorkbookFactory.create(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		// System.out.println(ExcelWSheet);
		int totalNoOfRows = ExcelWSheet.getLastRowNum();
		int totalNoOfCols_0 = ExcelWSheet.getRow(0).getLastCellNum();
		arrayExcelData = new String[totalNoOfRows][totalNoOfCols_0];
		for (int i = 0; i < totalNoOfRows; i++) {
			int totalNoOfCols = ExcelWSheet.getRow(i).getLastCellNum();
			// arrayExcelData = new String [totalNoOfRows][totalNoOfCols];
			for (int j = 0; j < totalNoOfCols; j++) {
				arrayExcelData[i][j] = ExcelWSheet.getRow(i + 1).getCell(j).getStringCellValue();
				// System.out.println(arrayExcelData[i][j]);
			}
		}
		System.out.println(arrayExcelData);
		return arrayExcelData;
	}
	
	
	
	
	ExtentSparkReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public void Customize_Test_Report(String ReportName, String Browser){
		System.out.println("Top of Method");
	//htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/"+ReportName);
	htmlreporter = new ExtentSparkReporter("./ExtentReport/"+ReportName);
	extent = new ExtentReports();
	extent.attachReporter(htmlreporter);
	
	extent.setSystemInfo("OS", System.getProperty("os.name"));
	extent.setSystemInfo("Browser", Browser);
	
	htmlreporter.config().setDocumentTitle("Test");
	htmlreporter.config().setReportName(ReportName);
	htmlreporter.config().setTheme(Theme.DARK);
	htmlreporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	System.out.println("Last of Method");
	}
	
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

}
