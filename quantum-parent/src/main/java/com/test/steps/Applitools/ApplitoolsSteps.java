package com.test.steps.Applitools;

import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.test.utils.ApplitoolsUtils;
import com.quantum.utils.ConfigurationUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by lirona on 26/03/2017.
 */
@QAFTestStepProvider(prefix="cucmber")
public class ApplitoolsSteps extends WebDriverTestBase{
    private static boolean batchFlag = false;
	
	@Given("That the batch name is \"(.*?)\"")
    synchronized public static void ISetBatchName(String batchname){

		ConfigurationUtils.getBaseBundle().setProperty("applitools.batchName", batchname);
		if(batchFlag == false) {
            ApplitoolsUtils.setBatchEyes(batchname);
            batchFlag = true;
        }
        else {
            System.out.println("Batch has already been seet");
        }
    }

    @Given("That the test name is \"(.*?)\"")
    public static void ISetTestName(String testname){ 
		ConfigurationUtils.getBaseBundle().setProperty("applitools.testName", testname);
		System.out.println("Test name is: "+testname);
    }
	
    @Then("I check window \"(.*?)\"")
    public static void IcheckWindow(String tag){ 
    	
    	ApplitoolsUtils.checkWindow(tag, false);
    	}
    
    @Then("I check complete window \"(.*?)\"")
    public static void IcheckCompleteWindow(String tag){ 
    	
    	String url = new WebDriverTestBase().getDriver().getCurrentUrl();
    	System.out.println("Current url is:"+url);
    	tag = tag + " " + url;
    	ApplitoolsUtils.checkWindow(tag, true); 
    	}
    
    @Then("I check multiple windows through url list \"(.*?)\"")
    public static void IcheckMultipleWindow(String list){ ApplitoolsUtils.urlListCheckWindow(list, false);}
    
    @Then("I check multiple complete windows through url list \"(.*?)\"")
    public static void IcheckMultipleCompleteWindow(String list){ ApplitoolsUtils.urlListCheckWindow(list, true);}
    
    @Then("I check multiple complete windows through url list \"(.*?)\" and export to \"(.*?)\"")
    public static void IcheckAndExportMultipleCompleteWindow(String list, String exportPath){ ApplitoolsUtils.urlListCheckWindowScreenshots(list, true, exportPath);}

    @Then("I check region \"(.*?)\"")
    public static void IcheckRegion(String element) {
        QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
        try {
            QAFExtendedWebElement webElement = new QAFExtendedWebElement(element); //TODO: Get element from repository
            ApplitoolsUtils.checkRegion(webElement, element);
        } catch (Exception ex) {
            System.out.println("Locator for element : " + element + "could not be found, please make sure the element exists in your repository.");
        }
        //TODO: Implement - get the element from the locator (or element repository) and send it to CheckRegion function.
    }
    @Then("I export screenshots to \"(.*?)\"")
    public static void IExportScreenshots(String exportPath){ ApplitoolsUtils.exportScreenshots(exportPath);}
    
    @Then("I close eyes")
    public static void ICloseEyes(){
    ApplitoolsUtils.closeApplitoolsEyes();
    }
}

