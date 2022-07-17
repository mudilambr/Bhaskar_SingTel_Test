#Author: Bhaskara Rao Mudila
#e-mail: bhaskar.cse.535@gmail.com


Feature: As a todoMVC user, I want to manage my todo list
	@TodoSanityTest
  Scenario: Validate the date field with multiple date formats and values
	 Given I launch MVC todo application url and verify the UI
	 When I enter the task description in text field and save multiple tasks
		| Welcome to SingTel world  | 
		| Buy SingTel SIM only		  |    
		| Buy SingTel SIM with Phone |  
	 Then I verify the footer display
	 And I remove tasks from the UI
		 |2|
	 Then I verify the tasks in the UI
	