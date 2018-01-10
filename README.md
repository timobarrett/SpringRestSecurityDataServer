# SpringRestSecurityDataServer
Sandbox project to get a handle on spring; boot, data, security, rest
GOAL - Show how to implement a server side spring application that:
  1. Doesn't have a web component to support the login process
    a. mask the login/authorization completly from the android client
    b. reduce client configuration needed.
  2. Makes use of cookies to eliminate the need to pass username and password for every rest request
  3. Server as a test harness for determining performance
  4. Provide a deeper dive into spring security
  5. Used to verify the android httpClient based applications functionality

FINDINGS -
  1. test client adding 5 records to database table to 4 seconds to complete.  
      Logging in first and using the resulting cookie to add 5 records to the same table took 600 millisconds
  2. @PreAuthorize checks for role and authority appear to be optional based on securityconfig config method
  
General Information:
1. Application developed using intellij community edition 2017.2.6 on a windows platform using maven
2. Ran limited tests of application on a Ubuntu linux variant
3. DatabaseSchema.txt - contains the database table schemas for mySql used to support application
4. Notes file contains lessons learned as well a curl commands used for testing
5. Tests folder needs work.  Errant key clicks has impacted setup/folders
