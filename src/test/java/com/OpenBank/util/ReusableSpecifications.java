package com.OpenBank.util;

import java.util.concurrent.TimeUnit;

import com.OpenBank.cucumber.steps.OpenBankSteps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.Matchers.*;

public class ReusableSpecifications {
	
	public static RequestSpecBuilder rspec;
	public static RequestSpecification requestSpecification;
	
	
	public static ResponseSpecBuilder respec;
	public static ResponseSpecification responseSpecification;
	
	
	
	//get Token Specifications 
	
	public static RequestSpecBuilder rspectoken;
	public static RequestSpecification requestSpecificationtoken;
	

	public static ResponseSpecBuilder respectoken;
	public static ResponseSpecification responseSpecificationtoken;
	
	
	
	
	
//Set headers for Brank,Branches and Account requests

	public static RequestSpecification getGenericRequestSpec(){
		String host="proxy.cognizant.com";
		int port=6050;
		OpenBankSteps token= new OpenBankSteps();
		String gettoken=token.getToken();
	    //System.out.println("Get Token is:" +gettoken);
		rspec = new RequestSpecBuilder();
     	rspec.addHeader("Authorization","DirectLogin username=\"kishore529\",password=\"Sriram529#\", token=" + gettoken);
     	rspec.setContentType(ContentType.JSON);
     	rspec.setProxy(host, port);
		requestSpecification=rspec.build();
		return requestSpecification;
		
		}
	


	public static ResponseSpecification getGenericResponseSpec(){
		respec = new ResponseSpecBuilder();
		respec.expectHeader("Content-Type","application/json;charset-UFT-8");
		respec.expectHeader("Transfer-Encoding","chunked");
		respec.expectResponseTime(lessThan(5L),TimeUnit.SECONDS);
		responseSpecification=respec.build();
		return responseSpecification;
		
		}

	
	
	//Set Token Header
	
	public static RequestSpecification getGenericRequestSpectoken(){
		String host="proxy.cognizant.com";
		int port=6050;
		rspectoken = new RequestSpecBuilder();
		rspectoken.addHeader("Authorization","DirectLogin username=\"kishore529\",password=\"Sriram529#\", consumer_key=\"axptzfroqfqhqut5xefc1rxshmtk5smyqxg2ziop\"");
		rspectoken.setContentType(ContentType.JSON);
		rspectoken.setProxy(host, port);
		requestSpecificationtoken=rspectoken.build();
		return requestSpecificationtoken;
		
		}
	
	
	
	
	public static ResponseSpecification getGenericResponseSpectoken(){
		respectoken = new ResponseSpecBuilder();
		respectoken.expectHeader("Content-Type","application/json;charset-UFT-8");
		respectoken.expectHeader("Transfer-Encoding","chunked");
		respectoken.expectResponseTime(lessThan(5L),TimeUnit.SECONDS);
		responseSpecificationtoken=respectoken.build();
		return responseSpecificationtoken;
		
		}
	


	}




	
	
