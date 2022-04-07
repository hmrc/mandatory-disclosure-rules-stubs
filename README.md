# Mandatory disclosure rules stubs

This microservice contains stub data for users reporting under mandatory disclosure rules. 

### Overview:

This service currently stubs the response for updating subscription from mandatory-disclosure-rules-frontend.

This service interacts with [Mandatory disclosure rules](https://github.com/hmrc/mandatory-disclosure-rules-frontend).

### API:
| Path | Method | Detail  |
|------|---------|--------------------|
|```/dac6/dct70e/v1``` |POST|  Update subscription |
|```/dac6/dct72a/v1``` |POST|  Submission  			|

**Example update subscription request**

      {
      "updateSubscriptionForMDRRequest": { 
	      "requestCommon": {
	         "regime": "MDR",
	         "receiptDate": "2020-09-09T11:23:10Z",
	         "acknowledgementReference": "8493893huer3ruihuow",
	         "originatingSystem": "MDTP"
	      },
	      "requestDetail": {
	      "IDType": "sub123",
	      "IDNumber": "123",
	      "tradingName": "Test Tools",
	      "isGBUser": true,
	      "primaryContact": [{
	      "email": "testprimary@test.com",
	      "phone": "08778763213789",
	      "mobile": "08778763213789",
	      "individual": {
		      "lastName": "TestTaylor",
		      "firstName": "TestTimothy",
		      "middleName": "TestTrent"
			  }
			}],
			"secondaryContact": [{
				"email": "testsecondary@test.com",
				"organisation": {
					"organisationName": "Tools for Trade"
					}
				}]
			}
		}
	}
	
**Example update subscription response**

	{
	"updateSubscriptionForMDRResponse": {  
		"responseCommon": {  
			"status": "OK",
			"processingDate": "2020-09-09T11:23:12Z"  
			},  
		"responseDetail": {  
			"subscriptionID": "YUDD7483202"  
			}
		}
	}


***API Specs:***
- [Update subscription](https://confluence.tools.tax.service.gov.uk/pages/viewpage.action?spaceKey=DAC6&title=MDR%20Specs&preview=/388662598/434373871/AEOI-DCT70e-1.2-EISAPISpecification-MDRSubscriptionAmend.pdf)

## Run Locally

This service runs on port 10020 and is named REGISTER_FOR_EXCHANGE_OF_INFORMATION_STUBS in service manager.

Run the following command to start services locally:

    sm --start MDR_ALL -f

## Requirements

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), and requires a Java 8 [JRE] to run.

[![Apache-2.0 license](http://img.shields.io/badge/license-Apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
