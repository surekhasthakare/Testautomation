package StepDefinitions;

import static org.junit.Assert.assertEquals;


import java.util.List;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class GetExchangeRates
{
	
	String endpoint="";
	private Response response;
	private RequestSpecification request;
	private ValidatableResponse json;
	
	
	@Given("Rest API is up and running")
	public void rest_api_is_up_and_running() 
	{
	    // Write code here that turns the phrase above into concrete actions
		
		 	RestAssured.baseURI ="http://api.ratesapi.io/api/";
	}

	@Given("I set url {string}")
	public void i_set_url(String string) 
	{
	    // Write code here that turns the phrase above into concrete actions
		this.endpoint = string;
	}

	@When("I hit the api with GET request")
	public void i_hit_the_api_with_get_request() 
	{
	    // Write code here that turns the phrase above into concrete actions
		//Getting the base URL for end-point 
				
				request = RestAssured.given();
				response = request.get(endpoint);
	}

	@Then("API return success status code as {int}")
	public void api_return_success_status_code_as(Integer code) 
	{
		System.out.println("Status code : "+ response.getStatusCode());
		//Assertion for response code
		assertEquals((Integer)response.getStatusCode(), code);
	}
	
	
	@Then("base should be {string}")
	public void base_should_be(String string) 
	{
	   	JsonPath jsonPathEvaluator = response.jsonPath();
		String Base = jsonPathEvaluator.get("base");
		System.out.println("Base received from Response is : " + Base);
		Assert.assertEquals(Base, string);
	}
	
	@When("I perform GET operation with Base and Symbols")
	public void i_perform_get_operation_with_base_and_symbols(io.cucumber.datatable.DataTable dataTable)
	{
	    
		List<List<String>> data = dataTable.asLists();
		System.out.println("************printing data"+data.toString() );
	
		System.out.println("************value data"+data.get(1).get(0) );
		request = RestAssured.given();
		request.log();
		response = request.queryParam("base", data.get(1).get(0)).queryParam("symbols", data.get(1).get(1)).get("/latest");
		
		
		
	}
	
	public void printresponse(Response response)
	{
		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// By using the ResponseBody.asString() method, we can convert the  body
		// into the string representation.
		System.out.println("Response Body is: " + body.asString());
		
	}
	
	
	@Then("validate the following response")
	public void validate_the_following_response(io.cucumber.datatable.DataTable dataTable) 
	{
		List<List<String>> data = dataTable.asLists();

		String responseBody = response.body().asString();
		System.out.println("############################");
		printresponse(response);
		//Assertion for response data 
		assertEquals(responseBody.contains(data.get(1).get(0)), true);
		assertEquals(responseBody.contains(data.get(1).get(1)), true);
	}

	@Then("validate the Content Type as {string}")
	public void validate_the_content_type_as(String contentType) 
	{
	     assertEquals(response.contentType().contains(contentType), true);
	}

	@When("I hit the api with GET request {string}")
	public void i_hit_the_api_with_get_request(String string) 
	{
		this.endpoint = string;
		request = RestAssured.given();
		response = request.get(endpoint);
	}
	

	@Then("error message {string}")
	public void error_message(String string) 
	{
		printresponse(response);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String errorMessage = jsonPathEvaluator.get("error");
		System.out.println("Error message received is :" + errorMessage);
		Assert.assertEquals(errorMessage, string);
	}
	
	@Then("USD exchange Rate is {string}")
	public void usd_exchange_rate_is(String string) 
	{
		printresponse(response);
		JsonPath jsonPathEvaluator = response.jsonPath();
	
		Float USD_float= jsonPathEvaluator.get("rates.USD");
		String USD = Float. toString(USD_float);
		
		System.out.println("USD value :" + USD);
		
		Assert.assertEquals(USD, string);
	}
	
	@Then("date is {string}")
	public void date_is(String string) 
	{
		printresponse(response);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String date = jsonPathEvaluator.get("date");
		System.out.println("Date received is :" + date);
		Assert.assertEquals(date, string);
	}
	
	
	@Then("API returns the exchange rate for current date {string}")
	public void api_returns_the_exchange_rate_for_current_date(String CurrentDate) {
		printresponse(response);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String date = jsonPathEvaluator.get("date");
		System.out.println("Date received is :" + date);
		Assert.assertEquals(date, CurrentDate);
	}

	
}

