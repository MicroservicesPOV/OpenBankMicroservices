package com.OpenBank.testbase;

import io.restassured.RestAssured;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.BeforeClass;

public class TestBase {
	
	
@BeforeClass	
public static void init() throws IOException
	
	{
	

    FileReader reader=new FileReader("serenity.properties");  
    Properties p=new Properties();  
    p.load(reader);  

		
	System.out.println(p.getProperty("EndPoint"));
	
	String EndPoint=p.getProperty("EndPoint");
	
		RestAssured.baseURI=EndPoint;
		//RestAssured.basePath="/v1";
		
	}

}

