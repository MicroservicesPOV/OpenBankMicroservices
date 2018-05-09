package com.OpenBank.cucumber.serenity;




import com.OpenBank.cucumber.steps.OpenBankSteps;
import com.OpenBank.model.AccountRouting;
import com.OpenBank.model.Balance;
import com.OpenBank.model.CreateAccount;
import com.OpenBank.util.ReusableSpecifications;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class OpenBankSerenitySteps {

//Direct Login
@Step
public ValidatableResponse postDirectLogin()

{

	
return SerenityRest.rest().given()
	       .spec(ReusableSpecifications.getGenericRequestSpectoken())
		//  .contentType("application/json")    
		//  .header("Authorization","DirectLogin username=\"kishore529\",password=\"Sriram529#\", consumer_key=\"axptzfroqfqhqut5xefc1rxshmtk5smyqxg2ziop\"")
		 // .proxy(host, port) 
	       .log()
	      .all()
		  .when()
	      .post("/my/logins/direct")
	      .then();

}    



//Get Banks
@Step
public ValidatableResponse getBanks()

{

	
return SerenityRest.rest().given()
	      .spec(ReusableSpecifications.getGenericRequestSpec())
		  .log()
	      .all()
	      .when()
	      .get("/obp/v1.2.1/banks")
	       .then();

}    

//Get Branches for a particular BankID
@Step
public ValidatableResponse getBranches()

{
	OpenBankSteps obs= new OpenBankSteps();
	
	String getBankid=obs.getBankID();
  	
	System.out.println("Value of bank_id is" +getBankid);
	
	
return SerenityRest.rest().given()
	      .spec(ReusableSpecifications.getGenericRequestSpec())
	      .pathParam("BANK_ID",getBankid)
	      .log()
	      .all()
	      .when()
	      .get("/obp/v3.0.0/banks/{BANK_ID}/branches")
	       .then();

}    



//Get UserDetails
@Step
public ValidatableResponse getUserDetails()

{
	
	
return SerenityRest.rest().given()
	      .spec(ReusableSpecifications.getGenericRequestSpec())
	      .log()
	      .all()
	      .when()
	      .get("/obp/v3.0.0/users/current")
	       .then();

}    


//Creating an Account
@Step
		public ValidatableResponse putCreateAccount()
	 
		{
	
	     OpenBankSteps obs= new OpenBankSteps();
	     String userid=obs.getUserID();
	     String bankid=obs.getBankID();
	     String accountid=obs.getAccountID();
	     String branchid=obs.getBranchID();	
	
	
	    	AccountRouting ar= new AccountRouting();
	    	
	    	ar.setAddress("Test");
	    	ar.setScheme("OBP");
	    	
	    	Balance balance= new Balance();
	    	
	    	balance.setAmount("0.00");
	    	balance.setCurrency("EUR");
	    	
	    	CreateAccount ca=new CreateAccount();
	    	ca.setAccountRouting(ar);
	    	ca.setBalance(balance);
	    	ca.setLabel("Label");
	    	ca.setType("CURRENT");
	    	ca.setBranchId(branchid);
	    	ca.setUserId(userid);

		       
			 
	 return SerenityRest.rest().given()
				       .spec(ReusableSpecifications.getGenericRequestSpec()) 
				       .pathParam("BANK_ID",bankid)
				       .pathParam("ACCOUNT_ID",accountid)
				       .log()
				       .all()
				       .when()
				       .body(ca)
				       .put("/obp/v2.2.0/banks/{BANK_ID}/accounts/{ACCOUNT_ID}")
				       .then();
	 	 
		}    







//Get Accounts
@Step
		public ValidatableResponse getAccounts()
	 
		{
	
	     OpenBankSteps obs= new OpenBankSteps();
	     String bankid=obs.getBankID();
	     
	   
			 
	 return SerenityRest.rest().given()
				       .spec(ReusableSpecifications.getGenericRequestSpec()) 
				       .pathParam("BANK_ID",bankid)
				       .log()
				       .all()
				       .when()
				       .get("/obp/v2.0.0/banks/{BANK_ID}/accounts")
				       .then();
	 	 
		}    






}
