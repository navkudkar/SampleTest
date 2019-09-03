Feature: Testing Test

Scenario: Verify all the UI elements on the open weather website
 Given User is on Home Page on "https://openweathermap.org/"
 Then  verify the page is loaded successfully
 And verify the UI elements are visible on the webpage
 
Scenario: second end-to-end test for negative validation
 Then Enter the invalid city name and verify response
 And search for weather 
 
 Scenario: Third end-to-end test 

 Then Enter the valid city name and verify response

 
 Scenario: Fourth end-to-end test 
 Given validate the rest response