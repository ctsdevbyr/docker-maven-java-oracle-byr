/**
 * 
 */
package com.test.steps.Network;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverBaseTestPage;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.annotations.FindBy;
import com.qmetry.qaf.automation.ui.api.PageLocator;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.test.utils.Accessibility;
import com.test.utils.PageValidator;
import com.test.utils.SiteVerify;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * @author chirag.jayswal
 *
 */
@QAFTestStepProvider
public class NetworkSteps extends WebDriverBaseTestPage {
	
	private String contactUsUrl = ConfigurationManager.getBundle().
			getString("universal.common.contactUs.homePage.url");
	
	@FindBy(locator = "universal.common.caTransparencyLink")
	private QAFExtendedWebElement transparencyLink;
	
	@FindBy(locator = "universal.common.caTransparencyValid")
	private QAFExtendedWebElement transparencyValidate;


	public static int tabPointer= 1;
	public static org.openqa.selenium.Capabilities cap = getDriver().getCapabilities();

    public static String browser=cap.getBrowserName().toLowerCase();

	public static QAFExtendedWebDriver getDriver() {
		return new WebDriverTestBase().getDriver();
	}

	
    @Given("^I start network virtualization$")
    public void iStartNetworkVirtualization() throws Exception {

    	Map<String, Object> params1 = new HashMap<>();
        params1.put("generateHarFile", "true");
        params1.put("profile", "4g_lte_good");
        Object result1 = getDriver().executeScript("mobile:vnetwork:start", params1);
        
        
    }
    
    @Then("^I end network virtualization$")
    public void iEndNetworkVirtualization() throws Exception {

    	Map<String, Object> params2 = new HashMap<>();
        //params2.put("generateHarFile", "true");
        Object result2 = getDriver().executeScript("mobile:vnetwork:stop", params2); 
    }

    @Then("^I select Washington D.C. from network test locations$")
    public void iSelectLocation() throws Exception {

        QAFExtendedWebElement locationList= getDriver().findElement(By.xpath("//app-select[@class='ng-tns-c3-0 focus']"));
        locationList.waitForPresent(4000);
        locationList.click();
        QAFExtendedWebElement location= getDriver().findElement(By.xpath("//div[contains(text(),'- USA - Washington D.C')]"));
        location.click();
    }


    
	@Override
	protected void openPage(PageLocator locator, Object... args) {
		// TODO Auto-generated method stub

	}

}

