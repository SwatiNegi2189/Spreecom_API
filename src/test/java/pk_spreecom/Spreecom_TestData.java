package pk_spreecom;

import org.testng.annotations.DataProvider;

public class Spreecom_TestData {

	@DataProvider(name="country_iso")
	public Object[][] iso_name(){
		//2X2, 2X3, 3X3 4X3 4X4
	    return new Object[][] {

	            {"usa","UNITED STATES","USA",200},
	            {"ind","INDIA","IND",200},
	            {"pak","PAKISTAN","PAK",200},
	            {"gb","UNITED KINGDOM","GBR",200}
	            };
	}


	//-------------------------------------------- This is to read Excel Data------------

		@DataProvider(name = "ISOExcelData")
		public Object[][] ReadDataFromExcel() throws Exception{
			Utility_Function excel = new Utility_Function();
			Object[][] testObjArray = excel.getExcelData(".\\TestData\\spreecom.xlsx","Retreivecountry_iso");
			System.out.println(testObjArray);
			return testObjArray;

		}

}
