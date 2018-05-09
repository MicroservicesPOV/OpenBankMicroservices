$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("OpenBank.feature");
formatter.feature({
  "line": 1,
  "name": "UserStory for Paribas OpenBank APIs Testing",
  "description": "",
  "id": "userstory-for-paribas-openbank-apis-testing",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 10,
  "name": "May9_MICROSERVICE TEST- For BNP Paribas Bank,getBanksIDs API Test",
  "description": "",
  "id": "userstory-for-paribas-openbank-apis-testing;may9-microservice-test--for-bnp-paribas-bank,getbanksids-api-test",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 9,
      "name": "@TC002_Paribas_OpenBank_getBanksIDs-US12"
    }
  ]
});
formatter.step({
  "line": 11,
  "name": "I as an authorized user who has bank locator role",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "I request to get the list of all global bank branches",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "check for the \"\u003cBankID\u003e\" present in the list extracted",
  "keyword": "Then "
});
formatter.examples({
  "line": 15,
  "name": "",
  "description": "",
  "id": "userstory-for-paribas-openbank-apis-testing;may9-microservice-test--for-bnp-paribas-bank,getbanksids-api-test;",
  "rows": [
    {
      "cells": [
        "BankID"
      ],
      "line": 16,
      "id": "userstory-for-paribas-openbank-apis-testing;may9-microservice-test--for-bnp-paribas-bank,getbanksids-api-test;;1"
    },
    {
      "cells": [
        "bnpparibas.07.uk"
      ],
      "line": 17,
      "id": "userstory-for-paribas-openbank-apis-testing;may9-microservice-test--for-bnp-paribas-bank,getbanksids-api-test;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 451376500,
  "status": "passed"
});
formatter.scenario({
  "line": 17,
  "name": "May9_MICROSERVICE TEST- For BNP Paribas Bank,getBanksIDs API Test",
  "description": "",
  "id": "userstory-for-paribas-openbank-apis-testing;may9-microservice-test--for-bnp-paribas-bank,getbanksids-api-test;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 9,
      "name": "@TC002_Paribas_OpenBank_getBanksIDs-US12"
    }
  ]
});
formatter.step({
  "line": 11,
  "name": "I as an authorized user who has bank locator role",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "I request to get the list of all global bank branches",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "check for the \"bnpparibas.07.uk\" present in the list extracted",
  "matchedColumns": [
    0
  ],
  "keyword": "Then "
});
formatter.match({
  "location": "OpenBankSteps.i_as_an_authorized_user_who_has_bank_locator_role()"
});
formatter.result({
  "duration": 13051903962,
  "status": "passed"
});
formatter.match({
  "location": "OpenBankSteps.I_request_to_get_the_list_of_all_global_bank_branches()"
});
formatter.result({
  "duration": 1302396281,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "bnpparibas.07.uk",
      "offset": 15
    }
  ],
  "location": "OpenBankSteps.check_for_the_present_in_the_list_extracted(String)"
});
formatter.result({
  "duration": 6233128,
  "status": "passed"
});
formatter.after({
  "duration": 5223318094,
  "status": "passed"
});
});