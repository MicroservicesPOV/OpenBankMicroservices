package com.OpenBank.cucumber.steps;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.OpenBank.cucumber.serenity.OpenBankSerenitySteps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.steps.StepEventBus;

public class OpenBankSteps 
{
private Scenario scenario=null;
private static String Token;
private static String RequestedBnkID;
private static String RequestedBranchID;
private static String UserID;
private static String AccountID;
private static String UserStoryInUse;
private static String ScenarioName;
private static String ScenarioStatus;
private static String UserTag;
private static String TestDesc;


private static String  TestCaseDesc;
private static String  TestCaseDesc1;
private static String  TestCaseDesc2;
private static String  TestCaseDesc3;
private static String  TestCaseDesc4;
private static String  TestCaseDesc5;
private static String  TestCaseDesc6;
private static String  TestCaseDesc7;
private static String  TestCaseDesc8;



public static List<String> RallyUserStories = new ArrayList<String>();

private ArrayList<String> Bankids = new ArrayList<String>();
private ArrayList<String> Branchids = new ArrayList<String>();
private ArrayList<String> Accountids = new ArrayList<String>();


@Before
public void before(Scenario scenario) throws IOException {
    this.scenario = scenario;
    System.out.println(scenario.getSourceTagNames());
    ScenarioName=scenario.getName();
    
    setScenarioName(ScenarioName);
	  System.out.println("Set ScenarioName is:"+getScenarioName());
    
    
	  UserTag=scenario.getSourceTagNames().toString();
    System.out.println("UserStory is:" +UserTag);
    
    
    setUserTag(UserTag);
	  System.out.println("Set UserTag is:"+getUserTag());
     
 	    if(UserTag.contains("-US"))
 	    {
	String[] UserStory= UserTag.split("-");
			 		
if(UserStory[1].contains(",@Relaklog]"))
	{
	UserStoryInUse =UserStory[1].replaceAll(",@Relaklog]","");
 		}else if(UserStory[1].contains(", @Relaklog]"))
	{
	UserStoryInUse =UserStory[1].replaceAll(", @Relaklog]","");
		} else
	{
		UserStoryInUse =UserStory[1].replaceAll("]","");
		 		 		
	}	

 	    
	System.out.println("UserStoryInUse is:" +UserStoryInUse);
    RallyUserStories.add(UserStoryInUse); 
    
    setUserStoryInUse(UserStoryInUse);
	  System.out.println("Set userstory is:"+getUserStoryInUse());
    
   Set<String> hs = new HashSet<>();
   hs.addAll(RallyUserStories);
  RallyUserStories.clear();
  RallyUserStories.addAll(hs);
    
   
   for ( int j=0; j<RallyUserStories.size(); j++ )
      System.out.println("UserStory " + j + ": " + RallyUserStories.get(j) );

} 
 	    
 	    else
 	    {
	 		System.out.println("No updates to Rally for Scenario:" +UserTag);

 	    }   
    
    
   
	}




	@Steps
	  OpenBankSerenitySteps steps;
	
	
	
	  @Given("^I as an authorized user who has bank locator role$")
	  public void i_as_an_authorized_user_who_has_bank_locator_role() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
			
			TestCaseDesc=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();
	    
	 	 	    
	 	 	  Response response=steps.postDirectLogin()
	 	 			  .log()
	 	 			  .all()
	 	 			  .extract().response();
	 	 			 
	 	 	  response.path("token");
	 	 			 	 
	 	 			  
	 	 			  setToken(response.path("token"));
	 	 			  System.out.println("Direct Login token is:"+getToken());
	 	 			  
	 	 			  
	 	 			
	   
	}

	

	@When("^I request to get the list of all global bank branches$")
	  public void I_request_to_get_the_list_of_all_global_bank_branches() throws Throwable
	  {
		
		TestCaseDesc1=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

	  Response Banks=  steps.getBanks()
				  .log()
	 			  .all()
	 			  .extract().response();
	  
			Bankids = Banks.path("banks.id");
			
		for (String ids : Bankids){
			 System.out.println("Extracted Bank ids are "+Bankids.indexOf(ids)+" " +ids);

		}
	  
		
	  }
	
	
	
	@Then("^check for the \"([^\"]*)\" present in the list extracted$")
	public void check_for_the_present_in_the_list_extracted(String RequestedBankID) throws Throwable {
	   
		
	/*	int searchListLength = Bankids.size();
		for (int i = 0; i < searchListLength; i++)
		{
		if (Bankids.get(i).equalsIgnoreCase(RequestedBankID))
		
		{
		
			 System.out.println("BankID found in the extracted list:" +RequestedBankID);
			 
		}
			 
		else if (!Bankids.get(i).equalsIgnoreCase(RequestedBankID))
				 
			 {
				 System.out.println("BankID not found in the extracted list");

				 Assert.fail();
			 }
		
		else
			
		{
			 System.out.println("BankID not found in  extracted list");
			 Assert.fail();
			 
		}
			 
		
		 
		}*/
		

		
			Assert.assertTrue("BankID not present in the list", Bankids.contains(RequestedBankID));

		}
		
		

	
	@And("^get the branches present under a given \"([^\"]*)\"$")
	
	public void get_the_branches_present_under_a_given_BankID(String RequestedBankID) throws Throwable {
		
		TestCaseDesc2=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

	
		  setBankID(RequestedBankID);
			  System.out.println("Set Bank ID is:"+getBankID());
							
			
		  Response Branches=  steps.getBranches()
				   .log()
	 			  .all()
	 			  .extract().response();
	  
			Branchids = Branches.path("branches.id");
			
			
			for (String ids : Branchids){
				 System.out.println("Extracted Branches ids are "+Branchids.indexOf(ids)+" "+ids);

			}
			
		
	}

		
		
		

	@Then("^check for given \"([^\"]*)\" present in the extracted list$")
	public void check_for_given_present_in_the_extracted_list(String RequestedBranchID) throws Throwable {

		TestCaseDesc3=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

/*		int searchListLength = Branchids.size();

		for (int i = 0; i<searchListLength; i++)
		

		if (Branchids.get(i).equalsIgnoreCase(RequestedBranchID))
		{
		
			 System.out.println("BranchID found in the extracted list:" +RequestedBranchID);
			 
		}
		
		

		else	
					
				{
					
					 System.out.println("BranchID not found in the extracted list");
					 
					 Assert.fail();

				}*/
			 
		Assert.assertTrue("BranchID not present in the list", Branchids.contains(RequestedBranchID));

	
	}
		
			
		
	

	

	@Given("^get Userdetails$")
	public void get_Userdetails() throws Throwable {
		
		TestCaseDesc4=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();


		  Response Userdetails=  steps.getUserDetails()
				   .log()
	 			  .all()
	 			  .extract().response();
	  
		  UserID = Userdetails.path("user_id");
		  setUserID(UserID);
			 System.out.println("Userid for the current user is:" +UserID);
			
		
	}
	
	
	@Then("^create account by inputing \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
	public void create_account_by_inputing_and(String RequestedBankID, String RequestedBranchID, String AccountID) throws Throwable {
		
		TestCaseDesc5=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

		
		setAccountID(AccountID);
		  System.out.println("Set Account ID is:"+getAccountID());
		  
		  setBranchID(RequestedBranchID);
		  System.out.println("Set Branch ID is:"+getBranchID());
		
		Response CreateAccount=steps.putCreateAccount()
				 .log()
	 			  .all()
	 			  .extract().response();
		
		 String Branchid = CreateAccount.path("branch_id");
		 
			Assert.assertEquals(Branchid ,RequestedBranchID);


		
		
	}
	
	
	@Then("^recreate account by inputing \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
	public void recreate_account_by_inputing_and(String RequestedBankID, String RequestedBranchID, String AccountID) throws Throwable {
		
		TestCaseDesc6=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

		
		setAccountID(AccountID);
		  System.out.println("Set Account ID is:"+getAccountID());
		  
		  setBranchID(RequestedBranchID);
		  System.out.println("Set Branch ID is:"+getBranchID());
		
		Response CreateAccount=steps.putCreateAccount()
				 .log()
	 			  .all()
	 			  .extract().response();
		
		 String error = CreateAccount.path("error");
		 
			Assert.assertEquals(error ,"OBP-30208: Account_ID already exists at the Bank.");


		
		
	}
	
	@And("^get Account list present under \"([^\"]*)\"$")
		public void get_Account_list_present_under_bankid(String BankID)
	{
		
		TestCaseDesc7=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

		
		Response getAccounts=steps.getAccounts()
				 .log()
	 			  .all()
	 			  .extract().response();
		Accountids = getAccounts.path("id");
		
		
		for (String ids : Accountids){
			 System.out.println("Extracted Account ids are "+Accountids.indexOf(ids)+" "+ids);

		}
		
	}
		
	
	@Then("^check for newly created \"([^\"]*)\" present under generated Account list$")
	public void  check_for_newly_created_accountid_present_under_generated_Account_list(String RequestedAccountID)
	{
	
		TestCaseDesc8=StepEventBus.getEventBus().getCurrentStep().transform(TestStep::getDescription).get();

		
		int searchListLength = Accountids.size();
		for (int i = 0; i < searchListLength; i++) {
		if (Accountids.get(i).contains(RequestedAccountID)) {
			 System.out.println("AccountID found in the extracted list" +RequestedAccountID);
		}
			 
			
		}
		
		
	}
	



	public static String getToken() {
		return Token;
	}



	public static void setToken(String token) {
		Token = token;
	}
		
	
	
	public static String getBankID() {
		return RequestedBnkID;
	}



	public static void setBankID(String bankid) {
		RequestedBnkID = bankid;
	}
		
	
	
	public static String getAccountID() {
		return AccountID;
	}



	public static void setAccountID(String accountid) {
		AccountID = accountid;
	}
	
	
	
	
	
	
	public static String getUserID() {
		return UserID;
	}



	public static void setUserID(String userid) {
		UserID = userid;
	}
	
	
	
	public static String getBranchID() {
		return RequestedBranchID;
	}



	public static void setBranchID(String branchid) {
		RequestedBranchID = branchid;
	}
	
	
	

	
	public static String getUserStoryInUse() {
		return UserStoryInUse;
	}



	public static void setUserStoryInUse(String userstory) {
		UserStoryInUse = userstory;
	}
	
	
	
	
	public static String getScenarioName() {
		return ScenarioName;
	}



	public static void setScenarioName(String scenarioname) {
		ScenarioName = scenarioname;
	}
	
	
	public static String getUserTag() {
		return UserTag;
	}



	public static void setUserTag(String usertag) {
		UserTag = usertag;
	}
	
	
	@After
	
	public void update_Rally() throws IOException, URISyntaxException{
        System.out.println("Adding Testcases to Rally and Executing them");
        
	       TestCaseDesc = TestCaseDesc == null   ? "" : TestCaseDesc;
	       TestCaseDesc1 = TestCaseDesc1 == null ? "" : TestCaseDesc1;
	       TestCaseDesc2 = TestCaseDesc2 == null ? "" : TestCaseDesc2;
	       TestCaseDesc3 = TestCaseDesc3 == null ? "" : TestCaseDesc3;
	       TestCaseDesc4 = TestCaseDesc4 == null ? "" : TestCaseDesc4;
	       TestCaseDesc5 = TestCaseDesc5 == null ? "" : TestCaseDesc5;
	       TestCaseDesc6 = TestCaseDesc6 == null ? "" : TestCaseDesc6;
	       TestCaseDesc7 = TestCaseDesc7 == null ? "" : TestCaseDesc7;
	       TestCaseDesc8 = TestCaseDesc8 == null ? "" : TestCaseDesc8;

	       
	       
	        TestDesc=  TestCaseDesc + " " + TestCaseDesc1 + " " + TestCaseDesc2 + " " + TestCaseDesc3 + " " + TestCaseDesc4 + " " + TestCaseDesc5 + " " + TestCaseDesc6 + " " + TestCaseDesc7 + " " + TestCaseDesc8;

	       setTestDesc(TestDesc);
			  System.out.println("Set TestDesc  is:"+getTestDesc());
        
        ScenarioStatus=scenario.getStatus();
        
        setScenarioStatus(ScenarioStatus);
		  System.out.println("Set ScenarioStatus  is:"+getScenarioStatus());
        
    	UpdateRally.update_Rally();
    	
               
        
	}
	
	
	public static String getScenarioStatus() {
		return ScenarioStatus;
	}



	public static void setScenarioStatus(String scenariostatus) {
		ScenarioStatus = scenariostatus;
	}
	
	
	
	
	public static String getTestDesc() {
		return TestDesc;
	}



	public static void setTestDesc(String testdesc) {
		TestDesc = testdesc;
	}
	
	
	
}