package com.OpenBank.cucumber;

import net.serenitybdd.cucumber.CucumberWithSerenity;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.OpenBank.cucumber.steps.AddAttachmentToUserStory;
import com.OpenBank.testbase.TestBase;

import cucumber.api.CucumberOptions;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		 features = "src/test/resources/features/",
	    	plugin={"pretty",
				"html:target/cucumber-html-report"
				},
	  
	   tags={"@TC002_Paribas_OpenBank_getBanksIDs-US12"},
		  dryRun = false
          )
public class OpenBankRunner extends TestBase {
	
	
	@AfterClass

	public static void getRallyAttachment() throws IOException, URISyntaxException, InterruptedException, MojoExecutionException 
	{
		
	 AddAttachmentToUserStory.getRallyAttachment();
			 
	}

	
 }
