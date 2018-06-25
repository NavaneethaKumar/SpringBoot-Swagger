# Next-Gen Coffee Shop Services
 

###1. Technologies
Spring-Boot-2.0.0.RELEASEE
Maven 3.5.2
Java 8
###2. To Run this project locally
mvn clean install
mvn spring-boot:run
##3 Swagger Endpoint
http://localhost:8080/swagger-ui.html#/

Credentials to login (Username/Password):
Rob/pwd# ->Super user
<abc>/pwd#-> Basic user

Swagger Endpoint will give you enough information about all services,request,response,status codes,you can execute all
operations here instead of using Soap UI or Postman.

##4 Service Orchestration
Services has been exposed with Authentication and Authorization model.
Since Rob is our super boss he got super user access for all levels of services,other users has limited access.(Swagger Endpoint will give you those Informations).
1) Security(Authentication,Authorization)
  
  Login the  services with valid credentials.
  you can login with any user but password should be pwd#.
  Access the services with valid permissions.
  Rob user only has admin role permission.
  
2) Validation
    Request body has basic validations for customer services.
    customer name should not be null with valid phone number(ex: 9986752467 10 digit numerics).
    
    
3) Onboarding    
    
  To perform process order, prerequesties to onboard  are customers,coffevarieties.
  
  Add a customer
		/coffeeshop/customer
  Add a coffee variety details        
        /coffeeshop/coffee
 4) Process Order 
         Place a order 
		/coffeeshop/order
  Have a look at samples here ->Onboard.txt   
 5) Soldout Reports.
 
  ##5 Exception Handling
 

  
