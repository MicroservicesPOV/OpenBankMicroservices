package com.OpenBank.cucumber.steps;

import com.google.gson.JsonArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import net.thucydides.core.reports.html.*;



import org.apache.commons.codec.binary.Base64;
import org.apache.maven.plugin.MojoExecutionException;

public class AddAttachmentToUserStory {


    	private List<String> fileList = new ArrayList<>();
     	public  static void getRallyAttachment() throws IOException,URISyntaxException, InterruptedException, MojoExecutionException{
          	   	
    		GenerateSerenityReport  report= new GenerateSerenityReport();
    		report.generateHtmlStoryReports();
    		Thread.sleep(11000);
    		 String dir = "./target/generated-sources";
            String zipFile = "./target/serenity.zip";

            AddAttachmentToUserStory zip = new  AddAttachmentToUserStory();
            zip.compressDirectory(dir, zipFile);
        }

        void compressDirectory(String dir, String zipFile) throws IOException, URISyntaxException, InterruptedException {
            File directory = new File(dir);
            getFileList(directory);

           
            
            try {
                FileOutputStream fos  = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                for (String filePath : fileList) {
                    System.out.println("Compressing: " + filePath);

                    //
                    // Creates a zip entry.
                    //

                    String name = filePath.substring(directory.getAbsolutePath().length() + 1,
                            filePath.length());
                    ZipEntry zipEntry = new ZipEntry(name);
                    zos.putNextEntry(zipEntry);

                    //
                    // Read file content and write to zip output stream.
                    //
                    
                    FileInputStream fis = new FileInputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    //
                    // Close the zip entry and the file input stream.
                    //
                    zos.closeEntry();
                    fis.close();
                }

                //
                // Close zip output stream and file output stream. This will
                // complete the compression process.
                //
                zos.close();
                fos.close();
                
                
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            for (int z=0;z<OpenBankSteps.RallyUserStories.size();z++)
            {     
            String rallyURL = "https://rally1.rallydev.com";
		     System.out.println("This will run after the Scenario");

       // String wsapiVersion = "1.40";
        String applicationName = "OpenBank";
       
      
       RallyRestApi restApi = new RallyRestApi(new URI(rallyURL), "_wrJQn1rkSBt9mYrhb4Om24Sa4FgFkq8kxCKxw0XvJY");

       restApi.setApplicationName(applicationName);

       
     //  restApi.setWsapiVersion(wsapiVersion);
       restApi.setApplicationName(applicationName);

       // User settings
       String testerUserName = "kishore527@gmail.com";


       // Workspace and Project Settings
       String myWorkspace = "/workspace/218663316040";
       String myProject = "/project/218663316472";

       // FormattedID of Existing Test Case to Query
       
    
       
       String existStoryFormattedID = null;
       OpenBankSteps us=new OpenBankSteps();
       
      	 existStoryFormattedID= OpenBankSteps.RallyUserStories.get(z);
        
       
       
       
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
   
       existUserStoryRequest.setQueryFilter(new QueryFilter("FormattedID", "=",existStoryFormattedID));
       QueryResponse userStoryQueryResponse = restApi.query(existUserStoryRequest);
       int existUserStoryRef1 = userStoryQueryResponse.getResults().size();
	    System.out.println("Size" +existUserStoryRef1);
       
       if(userStoryQueryResponse.getResults().size() !=0)
    	{
       
    	   System.out.println("Attatching Test Results to UserStory");
       String existUserStoryRef = userStoryQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();

 
       
           String FolderPath = "./target/";
	        String FileName = "serenity.zip";
	        String fullFolderPath = FolderPath + FileName;
	        String imageBase64String;
	        long attachmentSize;
	        
	        // Open file
	        RandomAccessFile myTestResults = new RandomAccessFile(fullFolderPath, "r");

	        try {
	            // Get and check length
	            long longlength = myTestResults.length();
	            // Max upload size for Rally attachments is 5MB
	            long maxAttachmentLength = 5120000;
	    
				if (longlength > maxAttachmentLength) throw new IOException("File size too big for Rally attachment, > 5 MB");

	            // Read file and return data
	            byte[] fileBytes = new byte[(int) longlength];
	            myTestResults.readFully(fileBytes);
	            imageBase64String = Base64.encodeBase64String(fileBytes);
	            attachmentSize = longlength;

	            // First create AttachmentContent from image string
	            JsonObject myAttachmentContent = new JsonObject();
	            myAttachmentContent.addProperty("Content", imageBase64String);
	            CreateRequest attachmentContentCreateRequest = new CreateRequest("AttachmentContent", myAttachmentContent);
	            CreateResponse attachmentContentResponse = restApi.create(attachmentContentCreateRequest);
	            String myAttachmentContentRef = attachmentContentResponse.getObject().get("_ref").getAsString();
	            System.out.println("Attachment Content created: " + myAttachmentContentRef);            
	            
		         java.util.Date date1= new java.util.Date();
	           // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
	            String timestamp1 = sdf1.format(date1);
	            
	            String Description=timestamp1 + " -UserStoryTestResulst";
	            		
	            		// Now create the Attachment itself
	            JsonObject myAttachment = new JsonObject();
	            myAttachment.addProperty("Artifact", existUserStoryRef);
	            myAttachment.addProperty("Content", myAttachmentContentRef);
	            myAttachment.addProperty("Name", "UserStoryTestResults.zip");
	            myAttachment.addProperty("Description", Description);
	            myAttachment.addProperty("ContentType","file");
	            myAttachment.addProperty("Size", attachmentSize);
	            myAttachment.addProperty("User", userRef);          

	            CreateRequest attachmentCreateRequest = new CreateRequest("Attachment", myAttachment);
	            CreateResponse attachmentResponse = restApi.create(attachmentCreateRequest);
	            String myAttachmentRef = attachmentResponse.getObject().get("_ref").getAsString();
	            System.out.println("Attachment  created: " + myAttachmentRef);  

	            if (attachmentResponse.wasSuccessful()) {
	                System.out.println("Successfully created Attachment");
	            } else {
	                String[] attachmentContentErrors;
	                attachmentContentErrors = attachmentResponse.getErrors();
	                        System.out.println("Error occurred creating Attachment: ");
	                for (int i=0; i<attachmentContentErrors.length;i++) {
	                        System.out.println(attachmentContentErrors[i]);
	                }                   
	            }
	        } catch (Exception e) {
	                System.out.println("Exception occurred while attempting to create Content and/or Attachment: ");
	                e.printStackTrace();            
	        }

	        finally {
	            //Release all resources
	            restApi.close();
	        }                

       }
       
       else
       {
		System.out.println("Invalid UserStory:" +existStoryFormattedID);
		 restApi.close();
       }
       
            }
            
           

       } 
            
            
            
        

        /**
         * Get files list from the directory recursive to the sub directory.
         */
        private void getFileList(File directory) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file.getAbsolutePath());
                    } else {
                        getFileList(file);
                    }
                }
            
            
            
            
            
            
            }

        
            
            
                   
        }
        
   

	  

}
   
	         
    



