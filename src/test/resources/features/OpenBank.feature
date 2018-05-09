Feature:UserStory for Paribas OpenBank APIs Testing


@TC001_Paribas_OpenBank_getToken-US11
Scenario: May9_MICROSERVICE TEST- For BNP Paribas Bank, get Token API Test
Given I as an authorized user who has bank locator role


@TC002_Paribas_OpenBank_getBanksIDs-US12
Scenario Outline: May9_MICROSERVICE TEST- For BNP Paribas Bank,getBanksIDs API Test
Given I as an authorized user who has bank locator role
When I request to get the list of all global bank branches
Then check for the "<BankID>" present in the list extracted

Examples:
         | BankID           |
         |bnpparibas.07.uk  |
                   

@TC003_Paribas_OpenBank_getBranchIDs
Scenario Outline: May9_MICROSERVICE TEST- For BNP Paribas Bank,getBranchIDs API Test
Given I as an authorized user who has bank locator role
When I request to get the list of all global bank branches
Then check for the "<BankID>" present in the list extracted
And get the branches present under a given "<BankID>"
Then check for given "<BranchID>" present in the extracted list


Examples:
         | BankID           | BranchID  |
         |bnpparibas.07.uk  | 4668-1234 |
         
         
         
@TC004_Paribas_OpenBank_getCurrentUser
Scenario:  May9_MICROSERVICE TEST- For BNP Paribas Bank,getCurrentUser API Test
Given get Userdetails


@TC005_Paribas_OpenBank_CreateAccount_GetAccount
Scenario Outline: May9_MICROSERVICE TEST- For BNP Paribas Bank,putCreateAccount API and getAccount API Test
Given I as an authorized user who has bank locator role
When I request to get the list of all global bank branches
Then check for the "<BankID>" present in the list extracted
And get the branches present under a given "<BankID>"
Then check for given "<BranchID>" present in the extracted list
Given get Userdetails
Then create account by inputing "<BankID>" "<BranchID>" and "<AccountID>"
And get Account list present under "<BankID>"
Then check for newly created "<AccountID>" present under generated Account list


Examples:
         | BankID          | BranchID  | AccountID |
         |bnpparibas.07.uk | 4668-1234 | 741219    |
         |bnpparibas.07.uk | 970e-18484| 851219    |
         |bnpparibas.07.uk | 3920-7658 | 961219    |



@TC006_Paribas_OpenBank_CreateAccount_alreadyexist
Scenario Outline: May9_MICROSERVICE TEST- For BNP Paribas Bank, putCreateAccount API Test for already created account
Given I as an authorized user who has bank locator role
When I request to get the list of all global bank branches
Then check for the "<BankID>" present in the list extracted
And get the branches present under a given "<BankID>"
Then check for given "<BranchID>" present in the extracted list
Given get Userdetails
Then recreate account by inputing "<BankID>" "<BranchID>" and "<AccountID>"




Examples:
         | BankID          | BranchID  | AccountID |
         |bnpparibas.07.uk | 4668-1234 | 7832191113|
   
         
         