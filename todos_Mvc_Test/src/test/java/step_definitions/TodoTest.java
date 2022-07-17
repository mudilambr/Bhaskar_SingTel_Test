package step_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import utilities.WebActions;

import static enums.IdType.*;
import static utilities.FinalProperties.*;

import java.util.ArrayList;
import java.util.List;

public class TodoTest extends WebActions {

	// Landing page
	private static String TODO_HEADER = "//h1";
	private static String TODO_FIELD = "//input[@class='new-todo']";
	private static String TOGGLE_ALL = "//label[@for='toggle-all']";
	private static String TASKS_LIST = "//ul[@class='todo-list']/li";
	private static String TASK_CHECK_BOX = "/div[1]/input[@type='checkbox']";
	private static String TASK_LABEL = "/div[1]/label";
	private static String TASK_DESTROY = "/div[1]/button";
	
	//Tasks Footer section
	private static String TASK_LEFT_FOOTER = "//footer[@class='footer']/span";
	private static String FOOTER_FILTERS ="//footer[@class='footer']/ul/li[%s]/a";
	
	private static List<String> removedTasks = new ArrayList<String>();
	
	
    @Given("^I launch MVC todo application url and verify the UI$")
    public void i_launch_mvc_todo_application_url_and_verify_the_ui() throws Throwable {
    	openUrl(env.getProperty("applicationUrl"));
		waitForPageToLoadFully();
		softAssert.assertThat(getText(Xpath, TODO_HEADER)).as("Date field label is not matching with the UI")
		.isEqualTo("todos");
		softAssert.assertThat(getAttribute(Xpath, TODO_FIELD, "placeholder")).as("Place Holder text is not matching with the UI")
		.isEqualTo("What needs to be done?");
		softAssert.assertThat(isPresent(Xpath, TODO_FIELD)).as("Results field label is not matching with the UI")
		.isTrue();
    }

    @When("^I enter the task description in text field and save multiple tasks$")
    public void i_enter_the_task_description_in_text_field_and_save_multiple_tasks(DataTable taskDescriptions) throws Throwable {
        click(Xpath, TODO_FIELD);
        List<String> descriptions = taskDescriptions.asList();
        for(String desc : descriptions) {
        typeText(Xpath, TODO_FIELD, desc);
        pressEnterKey(Xpath, TODO_FIELD);
        Thread.sleep(2000);
        waitForPageToLoadFully();
        }
    }
    
    @Then("^I verify the footer display$")
    public void i_verify_the_footer_display() throws Throwable {
    	int tasksInUI = locateElements(Xpath, TASKS_LIST).size();
    	String tasksLeft = String.valueOf(tasksInUI)+ " items left";
    	softAssert.assertThat(isPresent(Xpath, TOGGLE_ALL)).as("Tasks left text in Footer is not matching with the UI")
		.isTrue();
    	softAssert.assertThat(getText(Xpath, TASK_LEFT_FOOTER)).as("Tasks left text in Footer is not matching with the UI")
		.isEqualTo(tasksLeft);
    	softAssert.assertThat(getText(Xpath, String.format(FOOTER_FILTERS, "1"))).as("Footer filter1 text is not matching with the UI")
		.isEqualTo("All");
    	softAssert.assertThat(getText(Xpath, String.format(FOOTER_FILTERS, "2"))).as("Footer filter2 text is not matching with the UI")
		.isEqualTo("Active");
    	softAssert.assertThat(getText(Xpath, String.format(FOOTER_FILTERS, "3"))).as("Footer filter3 text is not matching with the UI")
		.isEqualTo("Completed");
    }

    @And("^I remove tasks from the UI$")
    public void i_remove_tasks_from_the_ui(DataTable count) throws Throwable {    	
    int noOfTasksInUI = locateElements(Xpath, TASKS_LIST).size();
    String removalCount =count.asList().get(0);	
    removedTasks = new ArrayList<String>();
    	String taskDescription = "";
    	
    	//below method to delete the given no of tasks (count) from UI
    	if(noOfTasksInUI>Integer.valueOf(removalCount)) {
    			for(int i=0; i<Integer.valueOf(removalCount); i++) {
    				taskDescription = getText(Xpath, TASKS_LIST+"[1]"+TASK_LABEL);
    				removedTasks.add(taskDescription);
    				clickDestroyicon(Xpath, TASKS_LIST+"[1]", TASKS_LIST+"[1]"+TASK_DESTROY);
    				Thread.sleep(2000);
    				waitForPageToLoadFully();
    			}
    			int noOfTasksAfterRemoval = locateElements(Xpath, TASKS_LIST).size();
    			if(!(noOfTasksAfterRemoval == (noOfTasksInUI - Integer.valueOf(removalCount))))
    				softAssert.fail("Tasks removing from the UI is partially failed");
    				
    	} else {
    		softAssert.fail("No of Tasks in UI is less than the required no of tasks to be deleted");
    	}
        
    }
    
    @Then("^I verify the tasks in the UI$")
    public void i_verify_the_tasks_in_the_ui() throws Throwable {
        int noOfTasks = locateElements(Xpath, TASKS_LIST).size();
        for (int j=0; j<noOfTasks; j++) {
        	String taskDescription =getText(Xpath, TASKS_LIST+"["+(j+1)+"]"+TASK_LABEL);
        		for(int k=0; k<removedTasks.size(); k++) {
        			if(taskDescription.equalsIgnoreCase(removedTasks.get(k)))
        				softAssert.fail("Task description" + removedTasks.get(k) + " is failed deleting from UI");
        		}        	
        }
    }
	
	
}

