package com.OpenBank.cucumber.steps;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;



public class UpdateRally {
	

	
	
	   
	
    public static void update_Rally() throws IOException, URISyntaxException{
        System.out.println("Adding Testcases to Rally and Executing them");
        //https://rally1.rallydev.com/#/218663316472d/dashboard
        OpenBankSteps obs= new OpenBankSteps();
                    
        
         String UserStroyInUse= obs.getUserStoryInUse();
         System.out.println("Get UserStroyInUse..." +UserStroyInUse); 

         
         
         String ScenarioName= obs.getScenarioName();
         System.out.println("Get ScenarioName..." +ScenarioName); 
         
         
         
         String ScenarioStatus= obs.getScenarioStatus();
         System.out.println("Get ScenarioStatus..." +ScenarioStatus); 
         
         
         String UserTag= obs.getUserTag();
         System.out.println("Get UserTag..." +UserTag); 
         
         
         
         String TestDesc= obs.getTestDesc();
         System.out.println("Get TestDesc..." +TestDesc); 
         
       
           String rallyURL = "https://rally1.rallydev.com";
            String applicationName = "OpenBank_RallyUpdates";
            RallyRestApi restApi = new RallyRestApi(new URI(rallyURL), "_wrJQn1rkSBt9mYrhb4Om24Sa4FgFkq8kxCKxw0XvJY");
            restApi.setApplicationName(applicationName);
            String testerUserName = "kishore527@gmail.com";
            // Workspace and Project Settings
            String myWorkspace = "/workspace/218663316040";
            String myProject = "/project/218663316472";
         // FormattedID of Existing Test Case to Query
            String existStoryFormattedID =UserStroyInUse;   

         //Read User
         System.out.println("Read User...");

         QueryRequest userRequest = new QueryRequest("User");
         userRequest.setFetch(new Fetch("UserName", "Subscription", "DisplayName"));
         userRequest.setQueryFilter(new QueryFilter("UserName", "=", testerUserName));
         QueryResponse userQueryResponse = restApi.query(userRequest);
         JsonArray userQueryResults = userQueryResponse.getResults();
         JsonElement userQueryElement = userQueryResults.get(0);
         JsonObject userQueryObject = userQueryElement.getAsJsonObject();
         String userRef = userQueryObject.get("_ref").getAsString();
         System.out.println("Get userRef..." +userRef); 
         
         
         
         
         // Query for existing User Story
         QueryRequest  existUserStoryRequest = new QueryRequest("HierarchicalRequirement");
         existUserStoryRequest.setFetch(new Fetch("FormattedID","Name"));
         existUserStoryRequest.setQueryFilter(new QueryFilter("FormattedID", "=", existStoryFormattedID));
         QueryResponse userStoryQueryResponse = restApi.query(existUserStoryRequest);
         //       JsonObject existUserStoryJsonObject = userStoryQueryResponse.getResults().get(0).getAsJsonObject();
           int existUserStoryRef1 = userStoryQueryResponse.getResults().size();
 	     //  System.out.println("Size" +existUserStoryRef1);

         if(userStoryQueryResponse.getResults().size() !=0)
	     	{
         String existUserStoryRef = userStoryQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
               
         
         //Fetch TestCase detail by Testcase Name
         
           QueryRequest testCaseRequest = new QueryRequest("TestCase");
            testCaseRequest.setProject(myProject);
            testCaseRequest.setWorkspace(myWorkspace);
            testCaseRequest.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));
            testCaseRequest.setLimit(1000);
            testCaseRequest.setScopedDown(false);
            testCaseRequest.setScopedUp(false);
			System.out.println("TestcaseName" +ScenarioName);
			System.out.println("UserStory" +existStoryFormattedID);
            testCaseRequest.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));
            QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
          //  JsonObject testJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
 		//     System.out.println("Name: " + testJsonObject.get("Name") + " FormattedID: " + testJsonObject.get("FormattedID") + "WorkProduct:" + testJsonObject.get("WorkProduct") );

            System.out.println("Successful: " + testCaseQueryResponse.wasSuccessful());
	        System.out.println("Size: " + testCaseQueryResponse.getTotalResultCount());
	        System.out.println("Results Size: " + testCaseQueryResponse.getResults().size());
        	
	/*     for (int t=0; t<testCaseQueryResponse.getResults().size();t++)
	      {
	       JsonObject testJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
	        
	      System.out.println("Name: " + testJsonObject.get("Name") + " FormattedID: " + testJsonObject.get("FormattedID") + "WorkProduct:" + testJsonObject.get("WorkProduct") );*/

	        
            
   
         
         
            String Verdit;
			if (testCaseQueryResponse.getTotalResultCount() == 0)
            
            {
		     
                   
       //  try {
             //Create test case
             JsonObject newTestCase = new JsonObject();
             newTestCase.addProperty("Name", ScenarioName);
             newTestCase.addProperty("Description", TestDesc);
             newTestCase.addProperty("Project", myProject);
             newTestCase.addProperty("Type", "Functional");
             newTestCase.addProperty("Method", "Automated");
             newTestCase.addProperty("DefectStatus", "NONE");
             newTestCase.addProperty("WorkProduct",existUserStoryRef);
           
                     
         CreateRequest createRequest = new CreateRequest("testcase",newTestCase);
         CreateResponse createResponse = restApi.create(createRequest);
         
         String newTestCaseref = createResponse.getObject().get("_ref").getAsString();
       // System.out.println("Attachment Content created: " + newTestCaseref);           
         
             if (createResponse.wasSuccessful()){
             System.out.println("Test case created successfully");
             }
             else {
             System.out.println("The test case could not be created");
             String[] createErrors;
             createErrors = createResponse.getErrors();
             System.out.println("Error occurred creating Test Case: ");
             for (int i=0; i<createErrors.length;i++) {
                System.out.println(createErrors[i]);
            }
             }

   //   }catch (Exception e){
    //         e.printStackTrace();
    //  }
 
         
         
         java.util.Date date= new java.util.Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
         String timestamp = sdf.format(date);

         
         if(ScenarioStatus=="failed")
   		  
   	  {
   		   Verdit="Fail";
   		   
   	
 	      QueryRequest testCasedefectRequest1 = new QueryRequest("TestCase");
 	     
 	     testCasedefectRequest1.setWorkspace(myWorkspace);
 	    testCasedefectRequest1.setProject(myProject);
 	   testCasedefectRequest1.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));
 	  testCasedefectRequest1.setLimit(1000);
 	 testCasedefectRequest1.setScopedDown(false);
 	testCasedefectRequest1.setScopedUp(false);
 	testCasedefectRequest1.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));
	       QueryResponse testCaseQueryResponse1 = restApi.query(testCasedefectRequest1);
	       
	       //  JsonObject testCaseJsonObject = testCaseQueryResponse1.getResults().get(0).getAsJsonObject();
	        
	       if (testCaseQueryResponse1.getTotalResultCount() != 0)
	            
           {

	        
	     
          String testCaseRef = testCaseQueryResponse1.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
	       //  try {
	             //Add a Test Case Result
	            // System.out.println(testCaseRef);
	             
	             String DefectName= UserTag;
  	            String DefectName1="Failed";
  	            String Name=DefectName1 + "---" + DefectName;
	            
  	          
	             JsonObject newDefect = new JsonObject();
  	             newDefect.addProperty("Name",Name);
  	             newDefect.addProperty("Description", "Check for the failed scenario in the test results present under attachment section of UserStory" );
	             newDefect.addProperty("Requirement",existUserStoryRef);
	             newDefect.addProperty("Workspace",myWorkspace);
	             newDefect.addProperty("Project",myProject);
  	             newDefect.addProperty("Priority", "High Attention");
	             newDefect.addProperty("Severity", "Major Problem");
	             newDefect.addProperty("TestCase", testCaseRef);
	             
	             CreateRequest createRequestdefect = new CreateRequest("defect", newDefect);
	             CreateResponse createResponsedefect = restApi.create(createRequestdefect);
	            String defect=createResponsedefect.getObject().get("FormattedID").toString();
	            
	             System.out.println("defect..." +defect);

	             

	             

	            // CreateRequest createRequestdefect = new CreateRequest("defect", newDefect);
	           //  CreateResponse createResponsedefect = restApi.create(createRequestdefect);

	            // CreateRequest createRequestdefect = new CreateRequest("defect",newDefect);
		          //  CreateResponse createResponsedefect = restApi.create(createRequestdefect);
		            
		   
		            
		      
           }
   		   
   		   
   	  }
   	  
   	  
   	  else
   		  
   	  {
   		   Verdit="Pass";
   		   
                  
          		
   	  }
         
         
         
         
      QueryRequest testCaseRequest1 = new QueryRequest("TestCase");
     
      testCaseRequest1.setWorkspace(myWorkspace);
      testCaseRequest1.setProject(myProject);
      testCaseRequest1.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));
      testCaseRequest1.setLimit(1000);
      testCaseRequest1.setScopedDown(false);
      testCaseRequest1.setScopedUp(false);
      testCaseRequest1.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));
       QueryResponse testCaseQueryResponse1 = restApi.query(testCaseRequest1);
       //  JsonObject testCaseJsonObject = testCaseQueryResponse1.getResults().get(0).getAsJsonObject();
        
       if (testCaseQueryResponse1.getTotalResultCount() != 0)
            
       {

        
     
	            String testCaseRef = testCaseQueryResponse1.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
         
       //  try {
             //Add a Test Case Result
            // System.out.println(testCaseRef);
             System.out.println("Creating Test Case Result...");
             JsonObject newTestCaseResult = new JsonObject();
             newTestCaseResult.addProperty("Verdict", Verdit);
             newTestCaseResult.addProperty("Date", timestamp);
             newTestCaseResult.addProperty("Notes",ScenarioStatus);
             newTestCaseResult.addProperty("Build", timestamp);
             newTestCaseResult.addProperty("Tester", userRef);
             newTestCaseResult.addProperty("TestCase", testCaseRef);
              newTestCaseResult.addProperty("Workspace", myWorkspace);
             CreateRequest createRequest1 = new CreateRequest("testcaseresult", newTestCaseResult);
             CreateResponse createResponse1 = restApi.create(createRequest1);
             if (createResponse1.wasSuccessful()) {
            	   System.out.println("Test Case Ran Successfull");
             }
             else {
                 String[] createErrors;
                 createErrors = createResponse.getErrors();
                 System.out.println("Error occurred creating Test Case Result: ");
                 for (int i = 0; i < createErrors.length; i++) {
                     System.out.println(createErrors[i]);
                 }
                 
                            
                 
             }
	     	}
             
             else
            	 
             {
            	   System.out.println("Test Case Run Failed");

             }
             
          //finally {
             //Release all resources
             restApi.close();
        // }
            }
	     	

            else
            	
            {
            	
                
                if(ScenarioStatus=="passed")
          		  
          	  {
          		   Verdit="Pass";
          		   
          		   
          		  
  	             QueryRequest testCasedefectRequest1 = new QueryRequest("TestCase");
  	     	     
  	     	     testCasedefectRequest1.setWorkspace(myWorkspace);
  	     	    testCasedefectRequest1.setProject(myProject);
  	     	   testCasedefectRequest1.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));
  	     	  testCasedefectRequest1.setLimit(1000);
  	     	 testCasedefectRequest1.setScopedDown(false);
  	     	testCasedefectRequest1.setScopedUp(false);
  	     	testCasedefectRequest1.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));
  	    	       QueryResponse testCaseQueryResponse1 = restApi.query(testCasedefectRequest1);
  	    	       
  	    	       //  JsonObject testCaseJsonObject = testCaseQueryResponse1.getResults().get(0).getAsJsonObject();
  	    	        
  	    	       if (testCaseQueryResponse1.getTotalResultCount() != 0)
  	    	            
  	               {

  	    	        
  	    	     
  	      	            String testCaseRef = testCaseQueryResponse1.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
  	    	         
  	    	     /*  //  try {
  	    	             //Add a Test Case Result
  	    	            // System.out.println(testCaseRef);
  	    	             System.out.println("Updating Defect for the created Test Case...");
  	             
  	         */
  	    	    	   
  	    	    	   
  	    	    	   
  	                QueryRequest defectRequest = new QueryRequest("Defect");
  	                defectRequest.setProject(myProject);
  	                defectRequest.setFetch(new Fetch(new String[] {"Name", "FormattedID","State", "Priority", "Severity", "Requirement","TestCase"}));
  	                defectRequest.setLimit(1000);
  	                defectRequest.setScopedDown(false);
  	                defectRequest.setScopedUp(false);
  	                defectRequest.setQueryFilter((new QueryFilter("Requirement", "=", existUserStoryRef)).and(new QueryFilter("TestCase", "=", testCaseRef)));
  	                QueryResponse defectQueryResponse = restApi.query(defectRequest);
  	               
  	              System.out.println("Successful: " + defectQueryResponse.wasSuccessful());
  		        System.out.println("Size: " + defectQueryResponse.getTotalResultCount());
  		        System.out.println("Results Size: " + defectQueryResponse.getResults().size());
  	                
  	             for (int d=0; d<defectQueryResponse.getResults().size();d++)
  	              {
                      JsonObject defectJsonObject = defectQueryResponse.getResults().get(0).getAsJsonObject();
                      String ref = defectJsonObject.get("_ref").toString();
                      
                      String ref1=defectQueryResponse.getResults().get(d).getAsJsonObject().get("_ref").getAsString();
                      System.out.println("defect ref.." +ref);

                      
                      System.out.println("Name: " + defectJsonObject.get("Name") + " State: " + defectJsonObject.get("State") + " Priority: " + defectJsonObject.get("Priority") + " TestCase: " + defectJsonObject.get("TestCase") + " UserStory: " + defectJsonObject.get("Requirement"));
                  
                      
                      System.out.println("\nUpdating defect state...");
                      JsonObject updatedDefect = new JsonObject();
                      updatedDefect.addProperty("State", "Closed");
                      updatedDefect.addProperty("Requirement", existUserStoryRef);
                      updatedDefect.addProperty("TestCase", testCaseRef);
                      
                    UpdateRequest updateRequest = new UpdateRequest(ref1, updatedDefect);
                    UpdateResponse updateResponse = restApi.update(updateRequest);
	              
  	              
  	              }  
  	    	    	   
        
              
  	               }
  	               }   
          	  //}  
  	    	    	   
  	    	    	   
  	    	    	   
          		   
          		   
          	  
          	  
          	  
          	  else
          		  
          	  {
          		   Verdit="Fail";
          		   

          	      QueryRequest testCasedefectRequest1 = new QueryRequest("TestCase");
          	     
          	     testCasedefectRequest1.setWorkspace(myWorkspace);
          	    testCasedefectRequest1.setProject(myProject);
          	   testCasedefectRequest1.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));
          	  testCasedefectRequest1.setLimit(1000);
          	 testCasedefectRequest1.setScopedDown(false);
          	testCasedefectRequest1.setScopedUp(false);
          	testCasedefectRequest1.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));
         	       QueryResponse testCaseQueryResponse1 = restApi.query(testCasedefectRequest1);
         	       
         	       //  JsonObject testCaseJsonObject = testCaseQueryResponse1.getResults().get(0).getAsJsonObject();
         	        
         	       if (testCaseQueryResponse1.getTotalResultCount() != 0)
         	            
                    {

         	        
         	     
           	            String testCaseRef = testCaseQueryResponse1.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
         	         
         	       //  try {
         	             //Add a Test Case Result
         	            // System.out.println(testCaseRef);
         	             System.out.println("Creating Defect for the created Test Case...");
         	             String DefectName= UserTag;
         	             System.out.println("DefectName" +DefectName);

         	            String DefectName1="Failed";
         	            String Name=DefectName1 + "---" + DefectName;
         	             System.out.println("Name" +Name);

         	            
         	           JsonObject newDefect = new JsonObject();
	      	             newDefect.addProperty("Name",Name);
	      	             newDefect.addProperty("Description", "Check for the failed scenario in the test results present under attachment section of UserStory" );
	    	             newDefect.addProperty("Requirement",existUserStoryRef);
         	             System.out.println("Name1" +Name);

	    	             newDefect.addProperty("Workspace",myWorkspace);
         	             System.out.println("Name2" +Name);

	    	             newDefect.addProperty("Project",myProject);
	      	             newDefect.addProperty("Priority", "High Attention");
         	             System.out.println("Name3" +Name);

	    	             newDefect.addProperty("Severity", "Major Problem");
         	             System.out.println("Name4" +Name);

	    	             newDefect.addProperty("TestCase", testCaseRef);
         	             System.out.println("Name5" +Name);

	    	             
	    	             CreateRequest createRequestdefect = new CreateRequest("defect", newDefect);
	    	             CreateResponse createResponsedefect = restApi.create(createRequestdefect);
	    	            String defect=createResponsedefect.getObject().get("FormattedID").toString();
	    	            
	    	             System.out.println("defect..." +defect);
         		         
         		     
         	             
         	             
                    }
          		   
          		   
          		   
          	  }
                
            
            	
            	java.util.Date date= new java.util.Date();
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
   	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
                String timestamp = sdf.format(date);
            	
            	
            	  QueryRequest testCaseRequest1 = new QueryRequest("TestCase");
            	  testCaseRequest1.setWorkspace(myWorkspace);
                  testCaseRequest1.setProject(myProject);
            	  testCaseRequest1.setFetch(new Fetch(new String[]{"FormattedID", "Name", "WorkProduct"}));	     	         testCaseRequest1.setWorkspace(myWorkspace);
                  testCaseRequest1.setLimit(1000);
 	              testCaseRequest1.setScopedDown(false);
 	              testCaseRequest1.setScopedUp(false);
            	          	 
   
 	             testCaseRequest1.setQueryFilter((new QueryFilter("Name", "=", ScenarioName)).and(new QueryFilter("WorkProduct.FormattedID", "=", existStoryFormattedID)));	     	         
     		  //   testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", existStoryFormattedID));

        	         QueryResponse testCaseQueryResponse1 = restApi.query(testCaseRequest1);
     //	         JsonObject testCaseJsonObject = testCaseQueryResponse1.getResults().get(0).getAsJsonObject();
     	         
        	        
        		    if (testCaseQueryResponse1.getTotalResultCount() != 0)
        			     	{
        	         
        	         String testCaseRef = testCaseQueryResponse1.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
     	         
     	         try {
     	             //Add a Test Case Result
     	             System.out.println(testCaseRef);
     	             System.out.println("Creating Test Case Result...");
     	             JsonObject newTestCaseResult = new JsonObject();
     	             newTestCaseResult.addProperty("Verdict",Verdit);
     	             newTestCaseResult.addProperty("Date", timestamp);
     	             newTestCaseResult.addProperty("Notes", ScenarioStatus);
     	             newTestCaseResult.addProperty("Build", timestamp);
     	             newTestCaseResult.addProperty("Tester", userRef);
     	             newTestCaseResult.addProperty("TestCase", testCaseRef);
     	          
     	             newTestCaseResult.addProperty("Workspace", myWorkspace);
     	             CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
     	             CreateResponse createResponse = restApi.create(createRequest);
     	             if (createResponse.wasSuccessful()) {
     	            	   System.out.println("Test Case Re-Run Successful");
     	             }
     	             else {
     	                 String[] createErrors;
     	                 createErrors = createResponse.getErrors();
     	                 System.out.println("Error occurred creating Test Case Result: ");
     	                 for (int i = 0; i < createErrors.length; i++) {
     	                     System.out.println(createErrors[i]);
     	                 }
     	             }

     	         } 
     	         
     	         
     	           
     	         
     	         
     	         finally {
     	             //Release all resources
     	          restApi.close();
     	         }
     	           
     	      

     	       
     	   
            }
     	
        		         else
        		         {
        		        	 System.out.println("TestCase Re-Run Failed");
        		        	 restApi.close();
        		         }
     	
            }
			
	     	
	     	}
	     	
     	
     	else
     		
     	{
     
     		System.out.println("Failed2");
     		System.out.println("Invalid UserStory:" +existStoryFormattedID);
     		 restApi.close();
     	}
    
}
  
	
	

}
