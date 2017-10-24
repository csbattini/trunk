
# worldplay-test
A simple RESTful software service that will allow a merchant to create a new simple offer.

#Instructions
In order to run assignment, you have to install it in local Maven repository 
#####Step1
    $ git checkout worldpaytest_assignment_branch
    $ mvn compile install
#####Step2
       Import the Assignment into IntelliJ IDEA IDE 
    
#####Step3
       Locate App class and run it [by Ctrl+Shift+F10]  
#####Step4
       A Merchant can "POST" their product/service offerings
       at "http://localhost:8080/merchant" with the following Example Payload 
       {
         "name": "my product",
         "description": "my product description",
         "isService": false,
         "conditions": [
           "condition 1",
           "condition 2",
           "condition 3"
         ],
         "price": 99.99,
         "currency": "GBP"
       }
       
       Note : "isService : false" represents that the offering is a Product


#####Step5
       GET http://localhost:8080/merchant for Usage, followed by the list of offerings.
       