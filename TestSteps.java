package stepDefination;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;


public class TestSteps 
{
	private static WebDriver driver=null;
	private static String url=null;
	WebElement elm=null;
	
	@Given("^User is on Home Page on \"([^\"]*)\"$")
	public void user_is_on_Home_Page_on(String arg1) throws Throwable {
		url=arg1;
		System.setProperty("webdriver.chrome.driver", "D:\\SelWorkspace\\Cucumber\\test\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(arg1);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Then("^verify the page is loaded successfully$")
	public void verify_the_page_is_loaded_successfully() throws Throwable {
		elm= driver.findElement(By.xpath("//img[@alt='openweather']"));
		if(elm.isDisplayed())
		{
			System.out.println("Page Is loaded successfully");
		}
	}

	@Then("^verify the UI elements are visible on the webpage$")
	public void verify_the_UI_elements_are_visible_on_the_webpage() throws Throwable {
		elm= driver.findElement(By.xpath("//*[@id='nav-search']"));
		if(elm.isDisplayed())
		{
			System.out.println("Label one visible");
		}
		elm=driver.findElement(By.xpath("//*[@id='widget']/div/div/h2"));
		if(elm.isDisplayed())
		{
			System.out.println("Label two visible");
		}
	}

	@Then("^Enter the invalid city name and verify response$")
	public void enter_the_invalid_city_name_and_verify_response() throws Throwable {
		   Thread.sleep(40);
		   elm=null;
		   elm=driver.findElement(By.xpath("//*[@placeholder='Your city name']"));
		   JavascriptExecutor js1=(JavascriptExecutor)(driver);
		   js1.executeScript("arguments[0].value='XYZ';", elm);
		   elm=driver.findElement(By.xpath("//*[@id='searchform']/button"));
		   elm.click();
		   elm=driver.findElement(By.xpath("//*[@id='forecast_list_ul']/div"));
		   if(elm.isDisplayed())
		   {
			   System.out.println("The Not found is displayed");
		   }
	}

	@Then("^search for weather$")
	public void search_for_weather() throws Throwable {
		   driver.get(url);
		   Thread.sleep(15);
		   elm=driver.findElement(By.xpath("//*[@placeholder='Your city name']"));
		   elm.sendKeys("Mumbai");
		   elm=driver.findElement(By.xpath("//*[@id='searchform']/button"));
		   elm.click();
		   Thread.sleep(50);
		   elm=driver.findElement(By.xpath("//*[@id='forecast_list_ul']/descendant::a[1]"));
		   if(elm.isDisplayed())
		   {
			   System.out.println("The Valid City searched successfully");
		   }
	}

	@Then("^Enter the valid city name and verify response$")
	public void enter_the_valid_city_name_and_verify_response() throws Throwable {
		   driver.get(url);
		   Thread.sleep(15);
		   elm=driver.findElement(By.xpath("//*[@placeholder='Your city name']"));
		   JavascriptExecutor js1=(JavascriptExecutor)(driver);
		   js1.executeScript("arguments[0].value='Mumbai';", elm);
		   elm=driver.findElement(By.xpath("//*[@id='searchform']/button"));
		   elm.click();
		   Thread.sleep(50);
		   elm=driver.findElement(By.xpath("//*[@id='forecast_list_ul']/descendant::a[1]"));
		   if(elm.isDisplayed())
		   {
			   System.out.println("The Valid City searched successfully");
		   }
	}

	@Given("^validate the rest response$")
	public void validate_the_rest_response() throws Throwable {
		// Specify the base URL to the RESTful web service
				 RestAssured.baseURI = "https://openweathermap.org";
				 
				 // Get the RequestSpecification of the request that you want to sent
				 // to the server. The server is specified by the BaseURI that we have
				 // specified in the above step.
				 RequestSpecification httpRequest = RestAssured.given();
				 
				 // Make a request to the server by specifying the method Type and the method URL.
				 // This will return the Response from the server. Store the response in a variable.
				 Response response = httpRequest.request(Method.GET, "/find?q=Mumbai");
				 
				 // Now let us print the body of the message to see what response
				 // we have recieved from the server
				 String responseBody = response.getBody().asString();
				// System.out.println("Response Body in HTML =>  " + responseBody);
				 int statusCode=response.getStatusCode();
				 Assert.assertEquals(200, statusCode,"The REST call fetched successfully");
	}
}
