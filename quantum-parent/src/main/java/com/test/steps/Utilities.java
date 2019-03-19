/**
 * 
 */
package com.test.steps;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import javassist.bytecode.stackmap.BasicBlock;
import org.apache.commons.lang3.StringEscapeUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import com.google.inject.spi.Message;
import com.perfecto.reportium.client.Constants.Capabilities;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.keys.ApplicationProperties;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverBaseTestPage;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.annotations.FindBy;
import com.qmetry.qaf.automation.ui.api.PageLocator;
import com.qmetry.qaf.automation.ui.api.TestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import com.qmetry.qaf.automation.util.StringMatcher;
import com.qmetry.qaf.automation.util.StringUtil;
import com.quantum.utils.*;
import com.steadystate.css.dom.Property;
import com.test.utils.Accessibility;
import com.test.utils.PageValidator;
import com.test.utils.SiteVerify;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import jxl.common.AssertionFailed;
import net.bytebuddy.asm.Advice.This;

/**
 * @author chirag.jayswal
 *
 */
@QAFTestStepProvider
public class Utilities extends WebDriverBaseTestPage {
	
	private String contactUsUrl = ConfigurationManager.getBundle().
			getString("universal.common.contactUs.homePage.url");
	
	@FindBy(locator = "universal.common.caTransparencyLink")
	private QAFExtendedWebElement transparencyLink;
	
	@FindBy(locator = "universal.common.caTransparencyValid")
	private QAFExtendedWebElement transparencyValidate;

	@FindBy(locator = "universal.common.contactUs.homePage.validate")
	private QAFExtendedWebElement validateContactUsPage;

	@FindBy(locator = "universal.common.contactUs.selectReason.ddl")
	private QAFExtendedWebElement ddlSelectReason;

	@FindBy(locator = "universal.common.contactUs.selectReason.coupon.ddl")
	private QAFExtendedWebElement ddlSelectReasonCoupon;

	@FindBy(locator = "universal.common.contactUs.selectReason.wtb.ddl")
	private QAFExtendedWebElement ddlSelectReasonWTB;

	@FindBy(locator = "universal.common.contactUs.selectReason.general.ddl")
	private QAFExtendedWebElement ddlSelectReasonGeneralInq;

	@FindBy(locator = "universal.common.contactUs.selectReason.compliment.ddl")
	private QAFExtendedWebElement ddlSelectReasonCompliment;

	@FindBy(locator = "universal.common.contactUs.selectBrand.ddl")
	private QAFExtendedWebElement ddlSelectBrand;

	@FindBy(locator = "universal.common.contactUs.continue.btn")
	private QAFExtendedWebElement btnContinue;

	@FindBy(locator = "universal.common.contactUs.couponPage.validate")
	private QAFExtendedWebElement validateCouponPage;

	@FindBy(locator = "universal.common.contactUs.noCoupon.validate")
	private QAFExtendedWebElement validateNoCoupon;

	@FindBy(locator = "universal.common.contactUs.wtb.validate1")
	private QAFExtendedWebElement validateWTB1;

	@FindBy(locator = "universal.common.contactUs.wtb.validate2")
	private QAFExtendedWebElement validateWTB2;

	@FindBy(locator = "universal.common.contactUs.specialCoupon.validate")
	private QAFExtendedWebElement validateSpecialCoupon;
	
	@FindBy(locator = "universal.common.contactUs.submittedThanks.validate")
	private QAFExtendedWebElement validateThankyou;

	@FindBy(locator = "universal.common.contactUs.whereToBuyPage.link")
	private QAFExtendedWebElement linkWhereToBuy;

	@FindBy(locator = "universal.common.contactUs.whereToBuyPage.validate")
	private QAFExtendedWebElement validateWhereToBuy;

	@FindBy(locator = "universal.common.contactUs.complaintForm.confirm18.chk")
	private QAFExtendedWebElement chkConfirm18;

	@FindBy(locator = "universal.common.contactUs.complaintForm.firstName.txt")
	private QAFExtendedWebElement txtFirstName;

	@FindBy(locator = "universal.common.contactUs.complaintForm.lastName.txt")
	private QAFExtendedWebElement txtLastName;

	@FindBy(locator = "universal.common.contactUs.complaintForm.email.txt")
	private QAFExtendedWebElement txtEmail;

	@FindBy(locator = "universal.common.contactUs.complaintForm.confirmEmail.txt")
	private QAFExtendedWebElement txtConfirmEmail;

	@FindBy(locator = "universal.common.contactUs.complaintForm.address1.txt")
	private QAFExtendedWebElement txtAddress1;

	@FindBy(locator = "universal.common.contactUs.complaintForm.address2.txt")
	private QAFExtendedWebElement txtAddress2;

	@FindBy(locator = "universal.common.contactUs.complaintForm.city.txt")
	private QAFExtendedWebElement txtCity;

	@FindBy(locator = "universal.common.contactUs.complaintForm.state.ddl")
	private QAFExtendedWebElement ddlState;

	@FindBy(locator = "universal.common.contactUs.complaintForm.stateChoice.ddl")
	private QAFExtendedWebElement ddlStateChoice;

	@FindBy(locator = "universal.common.contactUs.complaintForm.zip.txt")
	private QAFExtendedWebElement txtZip;

	@FindBy(locator = "universal.common.contactUs.complaintForm.phone.txt")
	private QAFExtendedWebElement txtPhone;

	@FindBy(locator = "universal.common.contactUs.complaintForm.upcCode.txt")
	private QAFExtendedWebElement txtUpcCode;

	@FindBy(locator = "universal.common.contactUs.complaintForm.comments.txt")
	private QAFExtendedWebElement txtComments;

	@FindBy(locator = "universal.common.contactUs.complaintForm.submit.btn")
	private QAFExtendedWebElement btnSubmit;

	@FindBy(locator = "universal.common.facebook.loginValid")
	private QAFExtendedWebElement loginValidFb;
	
	@FindBy(locator = "universal.common.facebook.icon")
	private QAFExtendedWebElement fbIcon;
	
	@FindBy(locator = "universal.common.youtube.icon")
	private QAFExtendedWebElement ytIcon;
	
	@FindBy(locator = "universal.common.instagram.icon")
	private QAFExtendedWebElement instaIcon;
	
	@FindBy(locator = "universal.common.facebook.continue")
	private QAFExtendedWebElement fbContinue;
	
	@FindBy(locator = "universal.common.insta.continue")
	private QAFExtendedWebElement instaContinue;
	
	@FindBy(locator = "universal.common.youtube.continue")
	private QAFExtendedWebElement ytContinue;
	
	@FindBy(locator = "universal.common.facebook.pageNotFound")
	private QAFExtendedWebElement pageNotFound;
	
	@FindBy(locator = "universal.common.pharma.contactUs.popUp")
	private QAFExtendedWebElement popUpCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.cookieOk")
	private QAFExtendedWebElement cookieOkCU;

	@FindBy(locator = "universal.common.pharma.contactUs.link")
	private QAFExtendedWebElement contactUsCU;

	@FindBy(locator = "universal.common.pharma.contactUs.ddTopic")
	private QAFExtendedWebElement ddTopicCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.first")
	private QAFExtendedWebElement firstNameCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.last")
	private QAFExtendedWebElement lastNameCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.email1")
	private QAFExtendedWebElement email1CU;

	@FindBy(locator = "universal.common.pharma.contactUs.email2")
	private QAFExtendedWebElement email2CU;

	@FindBy(locator = "universal.common.pharma.contactUs.chooseOrgType")
	private QAFExtendedWebElement chooseOrgTypeCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.orgType")
	private QAFExtendedWebElement orgTypeCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.chooseAboutTopic")
	private QAFExtendedWebElement chooseAboutTopicCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.aboutTopic")
	private QAFExtendedWebElement aboutTopicCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.chooseExpect")
	private QAFExtendedWebElement chooseExpectCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.expect")
	private QAFExtendedWebElement expectCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.choosePresc")
	private QAFExtendedWebElement choosePrescCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.presc")
	private QAFExtendedWebElement prescCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.choosePhase")
	private QAFExtendedWebElement choosePhaseCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.phase")
	private QAFExtendedWebElement phaseCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.chooseSubject")
	private QAFExtendedWebElement chooseSubjectCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.subject")
	private QAFExtendedWebElement subjectCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.company")
	private QAFExtendedWebElement companyCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.division")
	private QAFExtendedWebElement divisionCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.chooseTitle")
	private QAFExtendedWebElement chooseTitleCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.title")
	private QAFExtendedWebElement titleCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.usaOption")
	private QAFExtendedWebElement usaCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.country")
	private QAFExtendedWebElement chooseCountryCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.legalCheckBox")
	private QAFExtendedWebElement legalCheckBoxCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.submit")
	private QAFExtendedWebElement submitCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.submitValid")
	private QAFExtendedWebElement submitValidCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.message")
	private QAFExtendedWebElement messageCU;
	
	@FindBy(locator = "universal.common.pharma.contactUs.valid")
	private QAFExtendedWebElement contactUsValid;
	
	@FindBy(locator = "universal.cookie.popup.window")
	private QAFExtendedWebElement cookieWindow;
	
	@FindBy(locator = "universal.cookie.popup.ok")
	private QAFExtendedWebElement cookieConfirm;
	
	@FindBy(locator = "universal.common.consumer.bayerLogo")
	private QAFExtendedWebElement bayerLogo;
	
	public static int tabPointer= 1;
	public static org.openqa.selenium.Capabilities cap = getDriver().getCapabilities();
	
    public static String browser=cap.getBrowserName().toLowerCase();

	public static QAFExtendedWebDriver getDriver() {
		return new WebDriverTestBase().getDriver();
	}

    @When("^I navigate to \"(.*?)\"$")
    public void navigateToPrivacy(String url2) {
        getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        try {
            getDriver().get(url2);
        }
        catch (Exception e) {
            System.out.println("Page did not load within 20 seconds");
        }


    }

    @Then("I parse site map \"(.*?)\" via UI")
	public void iParseSiteMapUI(String url) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, TransformerException {
		SiteVerify s = new SiteVerify();
		s.SiteMapCheckerUI(url);

	}

	@Then("I parse site map \"(.*?)\" via API")
	public void iParseSiteMapAPI(String url) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, TransformerException {
		SiteVerify s = new SiteVerify();
		s.SiteMapCheckerAPI(url);

	}

	@Then("I perform link check for \"(.*?)\" via API")
	public void iLinkCheckAPI(String url) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, TransformerException, InterruptedException {
		SiteVerify s = new SiteVerify();
		s.LinkCheckerAPI(url);

	}

	@Then("I perform link check for \"(.*?)\" via UI")
	public void iLinkCheckUI(String url) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, TransformerException, InterruptedException {
		SiteVerify s = new SiteVerify();
		s.LinkCheckerUI(url);

	}
	
	@When("^I validate page content \"(.*?)\" using object \"(.*?)\"$")
	public void iValidatePage(String file, String object) throws Exception {

		PageValidator v = new PageValidator();
		v.validatePageContent(ConfigurationManager.getBundle().getString("env.resources") + "/" + file, object);
	}
    @QAFTestStep(description="^I run accessibility against \"(.*?)\" for domain \"(.*?)\"$")
    public void iRunAccessibleTest(String url, String domainName) throws XPathExpressionException, ParserConfigurationException, TransformerException, SAXException, IOException, InterruptedException
    {
           Accessibility access = new Accessibility(domainName);
           access.getAccesibilityResults(url);
    }
    
 
	
    @Then("^I check text \"(.*?)\" present on page for \"(.*?)\" times$")
	public void iCheckText(String text, String occurance) throws Exception {
    	SiteVerify s = new SiteVerify();
    	s.validateTextOcuranceOnPage(text,Integer.parseInt(occurance));
	}
    
    @Then("^I select California Transparency link$")
    public void iSelectCaliforniaTransparency () throws Exception {

    	ArrayList<String> tab = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tab.get(0));
    	
    	QAFExtendedWebElement transparencyLinkab= getDriver().findElement(By.xpath("//*[contains(text(), 'California Transparency') or contains(text(), 'CALIFORNIA TRANSPARENCY')]"));	
    	transparencyLinkab.waitForPresent(8000);
    	transparencyLinkab.click();
    	
    	//ArrayList<String> tab2 = new ArrayList<String>(getDriver().getWindowHandles());
    	//getDriver().switchTo().window(tab2.get(1));
        
    	//getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
   
    	QAFExtendedWebElement transparencyValidate2= getDriver().findElement(By.xpath("//*[contains(text(), 'California Transparency in Supply Chains Act - Bayer United States of America')]"));;	
    	transparencyValidate2.waitForPresent(10000);
    	
    	//getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 2));
    	getDriver().switchTo().window(tab.get(0));
    	//transparencyLink.waitForPresent(10000);
    	
    
    }
    
    @Then("^I validate California Transparency page$")
    public void iValidateTransparencyPage() throws Exception {
    	Assert.assertEquals(getDriver().getCurrentUrl()
    			, "https://www.bayer.us/en/about-bayer/corporate-responsibility/transparency-in-supply-chains/");
    
    }

    @Then("^I validate privacy statement on consumer site$")
    public void utilPrivacyCheck() {
    	
     ArrayList<String> tabs = new ArrayList<String>( getDriver().getWindowHandles());
     getDriver().switchTo().window(tabs.get(0));	
     
     QAFExtendedWebElement privacyLink= getDriver().findElement(By.xpath("//*[contains(text(), 'Privacy')]|//*[contains(text(),'PRIVACY')]"));	
     privacyLink.waitForPresent(10000);
     privacyLink.click();
     
     
     ArrayList<String> tabs2 = new ArrayList<String>( getDriver().getWindowHandles());
     if(browser.equals("firefox")) {
    	 getDriver().switchTo().window(tabs2.get(1));
      }else {
    	  getDriver().switchTo().window(tabs2.get(tabPointer));
      }

     QAFExtendedWebElement privacyCheck = getDriver().findElement(By.xpath("//h1[text()='Privacy Statement']"));

     privacyCheck.waitForPresent(15000);

     String currentUrlConditions=getDriver().getCurrentUrl();
     Boolean Flag = true;

     if (currentUrlConditions.equals("https://bayercare.com/privacy_statement.cfm"))
     {
         Assert.assertTrue(Flag);
     } else if (currentUrlConditions.equals("https://www.bayercare.com/privacy_statement.cfm")){
         Assert.assertTrue(Flag);
     } else  {
         Flag = false;
         Assert.assertFalse(Flag);
     }

     getDriver().switchTo().window(tabs.get(0));
    
     tabPointer++;
    }
    
    @Then("^I validate California Transparency pages TEST$")
    public void utilCaliCheck() {
    System.out.println(browser);	
     ArrayList<String> tabs = new ArrayList<String>( getDriver().getWindowHandles());
     getDriver().switchTo().window(tabs.get(0));	
     
     QAFExtendedWebElement caliLink= getDriver().findElement(By.xpath("//*[contains(text(), 'California Transparency') or contains(text(), 'CALIFORNIA TRANSPARENCY')]"));	
     caliLink.waitForPresent(10000);
     caliLink.click();
     try {
    	Thread.sleep(10000);
	} catch (Exception e) {
		// TODO: handle exception
	}
     
     ArrayList<String> tabs2 = new ArrayList<String>( getDriver().getWindowHandles());
     if(browser.equals("firefox")) {
    	 getDriver().switchTo().window(tabs2.get(1));
    	
     }else {
    	 getDriver().switchTo().window(tabs2.get(tabPointer));
     }

     QAFExtendedWebElement caliCheck = getDriver().findElement(By.xpath("//*[contains(text(), 'California Transparency in Supply Chains Act')]"));
     caliCheck.waitForPresent(15000);
     String caliUrl= "https://www.bayer.us/en/corporate-responsibility/transparency-in-supply-chains/";

     String currentUrl=getDriver().getCurrentUrl();

     Assert.assertEquals(currentUrl, caliUrl);
    
     getDriver().switchTo().window(tabs.get(0));
     
     tabPointer++;
    }
   
    
    @Given("^I navigate to Contact Us page$")
	public void navigateToContactUsURL() {
		getDriver().get(contactUsUrl);
		validateContactUsPage.waitForPresent(15000);
	}

	@Then("^I select get a coupon$")
	public void iSelectGetCoupon() {

		ddlSelectReason.waitForPresent(15000);
		ddlSelectReason.click();
		ddlSelectReasonCoupon.click();
	}

	@Then("^I validate coupons for all brands$")
	public void iValidateAllCouponPages() {

		ArrayList<String> noCoupons = new ArrayList<String>();

		for (int i = 2; i < 42; i ++) {

			ddlSelectReason.waitForPresent(8000);
			ddlSelectBrand.click();

			QAFExtendedWebElement current = getDriver().findElement(By.xpath("//*[@id='brandname']/option[" + i + "]"));
			String currentName = current.getText();
			current.click();

			btnContinue.click();

			try {
				validateCouponPage.waitForPresent(8000);
			} catch (Exception e) {
				try {
					validateNoCoupon.waitForPresent(8000);
					noCoupons.add(currentName);
				} catch(Exception ee) {
					validateSpecialCoupon.waitForPresent(5000);
				}

			}
			getDriver().navigate().back();

		}
		System.out.println("Brands not currently offering coupons: " + noCoupons);
		getDriver().get(contactUsUrl);
	}

	@Then("^I select where to buy$")
	public void selectWhereToBuy() {

		ddlSelectReason.waitForPresent(15000);
		ddlSelectReason.click();
		ddlSelectReasonWTB.click(); 

	}
	
	@Then("^I validate where to buy pages for all brands$")
	public void iValidateAllWhereToBuy() {

		for (int i = 2; i < 42; i ++) {

			ddlSelectReason.waitForPresent(8000);
			ddlSelectBrand.click();

			QAFExtendedWebElement current = getDriver().findElement(By.xpath("//*[@id='brandname']/option[" + i + "]"));
			current.click();

			btnContinue.click();

			try {
				validateWTB1.waitForPresent(8000);  
			} catch (Exception e) {
				validateWTB2.waitForPresent(8000); 
			}

			getDriver().navigate().back();
		}
		getDriver().get(contactUsUrl);
	}

	@Then("^I submit a general inquiry for \"([^\"]*)\"$")
	public void submitGeneralInq(String brand) {

		validateContactUsPage.waitForPresent(15000);

		ddlSelectReason.waitForPresent(15000);
		ddlSelectReason.click();
		ddlSelectReasonGeneralInq.click();

		QAFExtendedWebElement current = getDriver().findElement(By.xpath("//*[@id='brandname']//*[@value='"+ brand +"']"));
		current.click();
		sendKeys();
		btnSubmit.click();
		validateThankyou.waitForPresent(8000);

		getDriver().get(contactUsUrl);


	}

	@Then("^I submit a compliment for \"([^\"]*)\"$")
	public void submitCompliment(String brand) {

		validateContactUsPage.waitForPresent(15000);

		ddlSelectReason.waitForPresent(15000);
		ddlSelectReason.click();
		ddlSelectReasonCompliment.click();

		QAFExtendedWebElement current = getDriver().findElement(By.xpath("//*[@id='brandname']//*[@value='"+ brand +"']"));
		current.click();
		sendKeys();
		btnSubmit.click();
		validateThankyou.waitForPresent(8000);

		getDriver().get(contactUsUrl);

	}
	
	/* Sends text to appropriate text boxes in Contact Us
	 * compliment and general inquiry forms.
	 */
    private void sendKeys() {
    	
    	String fname = "Test";
		String lname = "Test";
		String email = "Test@testing.com";
		String address1 = "36 Columbia Road";
		String city = "Morristown";
		String zip = "07960";
		String phone = "8624040000";
		String upcCode = "NA";
		String comments = "THIS IS A TEST FOR QA PURPOSES.";
		
		btnContinue.click();
		chkConfirm18.waitForPresent(8000);
		chkConfirm18.click();
		txtFirstName.sendKeys(fname);
		txtLastName.sendKeys(lname);
		txtEmail.sendKeys(email);
		txtConfirmEmail.sendKeys(email);
		txtAddress1.sendKeys(address1);
		txtCity.sendKeys(city);
		ddlState.click();
		ddlStateChoice.click();
		txtZip.sendKeys(zip);
		txtPhone.sendKeys(phone);
		txtUpcCode.sendKeys(upcCode);
		txtComments.sendKeys(comments);
		
    }
    
    @Then("^I click on the Facebook icon$")
	public void iClickOnFB() {
    	
    	fbIcon.waitForPresent(10000);
    	fbIcon.click();
    	try {
    		fbContinue.waitForPresent(10000);
    		fbContinue.click();
    	} catch (Exception e) {
    		
    	}
    }
    @And("^If cookie is present I accept$")
	public void iClickOnCookie() {

    	try {
	    	cookieWindow.waitForVisible(10000);
	    	cookieConfirm.click();
    	}
    	catch (Exception e) {
    		System.out.println("Cookie not present");
    	}
    }
    @Then("^I click on the Instagram icon$")
	public void iClickOnInsta() {
    	
    	instaIcon.waitForPresent(10000);
    	instaIcon.click();
    	try {
    		instaContinue.waitForPresent(10000);
    		instaContinue.click();
    	} catch (Exception e) {
    		
    	}
    }
    
    @Then("^I click on the YouTube icon$")
	public void iClickOnYT() {
    	
    	ytIcon.waitForPresent(10000);
    	ytIcon.click();
    	try {
    		ytContinue.waitForPresent(10000);
    		ytContinue.click();
    	} catch (Exception e) {
    		
    	}
    }
    
    @Then("^I validate that I am on the Facebook page$")
	public void iValidateFB() throws Exception {
    	
    	ArrayList<String> tabHandles = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
    	
    	if (!getDriver().getCurrentUrl().toLowerCase().contains("https://www.facebook.com")) {
    		throw new Exception();
    	}
    	
    	try {
    		pageNotFound.waitForPresent(5000);
    		throw new Exception();
    	} catch (Exception e) {

    	}

    }
    
    @Then("^I validate that I am on the Instagram page$")
	public void iValidateInsta() throws Exception {
    	
    	ArrayList<String> tabHandles = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
    	
    	if (!getDriver().getCurrentUrl().contains("https://www.instagram.com")) {
    		throw new Exception();
    	}
    }
    
    @Then("^I validate that I am on the YouTube page$")
	public void iValidateYT() throws Exception {
    	
    	ArrayList<String> tabHandles = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
    	
    	if (!getDriver().getCurrentUrl().contains("https://www.youtube.com")) {
    		throw new Exception();
    	}
    	
    	try {
    		pageNotFound.waitForPresent(5000);
    		throw new Exception();
    	} catch (Exception e) {

    	}

    }
    
    
    @Then("^I validate that I am on the \"(.*?)\" Facebook page$")
	public void iValidateFBLink(String fbLink) throws Exception {
    	
    	ArrayList<String> tabHandles = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
    	
    	if (!getDriver().getCurrentUrl().toLowerCase().contains(fbLink)) {
    		throw new Exception();
    	}
    	
    	try {
    		pageNotFound.waitForPresent(5000);
    		throw new Exception();
    	} catch (Exception e) {

    	}
    }
    
    @Then("^I navigate to Pharma Contact Us page$")
    public void iNavigateToContactUsPage() throws Exception {
    	
    	if (popUpCU.isPresent()) {
			if (popUpCU.isDisplayed()) {
				cookieOkCU.waitForPresent(15000);
				cookieOkCU.click();
			}
		}
    	
    	contactUsCU.waitForPresent();
    	contactUsCU.click();
    	
    	if (popUpCU.isPresent()) {
			if (popUpCU.isDisplayed()) {
				cookieOkCU.waitForPresent(15000);
				cookieOkCU.click();
			}
		}
    	
    	contactUsValid.waitForPresent();
    }
    
    @Then("^I test all topics of interest$")
    public void iTestAllTopicsOfInterest() throws Exception {
    	
    	for (int i = 2; i < 8; i++) {

        	ddTopicCU.click();
        	getDriver().findElement(By.xpath("//*[@id = 'topic']/option["+ i +"]")).click();
        	
        	if (i == 6) {

        		firstNameCU.click();
        		firstNameCU.sendKeys("First");
        		lastNameCU.click();
        		lastNameCU.sendKeys("Last");
        		email2CU.click();
        		email2CU.sendKeys("test@email.com");
        		chooseOrgTypeCU.click();
        		orgTypeCU.click();
        		chooseAboutTopicCU.click();
        		aboutTopicCU.click();
        		chooseExpectCU.click();
        		expectCU.click();
        		choosePrescCU.click();
        		prescCU.click();
        		choosePhaseCU.click();
        		phaseCU.click();
        		chooseSubjectCU.click();
        		subjectCU.click();


        	} else {
        		
        		try {
        			chooseSubjectCU.click();
        			subjectCU.click();
        		} catch (Exception e1) {
        			
        		}
        		try {
        			companyCU.click();
        			companyCU.sendKeys("Employer");
        		} catch (Exception e1) {
        			
        		}
        		try {
        			divisionCU.click();
        			divisionCU.sendKeys("Division");
        		} catch (Exception e1) {
        			
        		}
        		chooseTitleCU.click();
        		titleCU.click();
        		firstNameCU.click();
        		firstNameCU.sendKeys("First");
        		lastNameCU.click();
        		lastNameCU.sendKeys("Last");
        		chooseCountryCU.click();
        		usaCU.click();
        		email1CU.click();
        		email1CU.sendKeys("test@email.com");
        		
        	}
        	
    		messageCU.click();
    		messageCU.sendKeys("This is a test for QA purposes.");
    		legalCheckBoxCU.click();
    		submitCU.click();
    		submitValidCU.waitForPresent(15000);
    		
    		contactUsCU.waitForPresent(8000);
    		contactUsCU.click();
    		contactUsValid.waitForPresent(8000);
    	}

    }

    @Then("^I validate link \"(.*?)\" redirects to \"(.*?)\"$")
	public void iValidateRedirect(String url1, String url2) throws Exception {
    	getDriver().navigate().to(url1);
    	String temp = getDriver().getCurrentUrl();
    	assertEquals(temp, url2);
	}
    
    /* Locates and clicks a web element with the corresponding text 
     * 
     */
    @Then("^I click on \"(.*?)\" link by text")
	public void iClickOnLinkByText(String linkText) throws Exception {
    	getDriver().findElement(By.xpath("//*[contains(text(), '" + linkText + "')]")).click();
	}
    
   /* Locates and clicks a web element with the corresponding href url 
    * 
    */
    @Then("^I click on \"(.*?)\" link by url")
	public void iClickOnLinkByURL(String linkUrl) throws Exception {
    	getDriver().findElement(By.xpath("//*[contains(@href, '" + linkUrl + "')]")).click();
	}
    
    @Then("^I should land on \"(.*?)\"")
	public void iShouldLandOnURL(String url) throws Exception {
    	Assert.assertTrue(getDriver().getCurrentUrl().contains(url));
	}
    
    @Then("^I switch tabs")
	public void iSwitchTabs() throws Exception {
    	ArrayList<String> tabHandles = new ArrayList<String>(getDriver().getWindowHandles());
    	getDriver().switchTo().window(tabHandles.get(tabHandles.size() - 1));
	}
    
    @Then("^I reload page")
  	public void iReloadPage() throws Exception {
      	getDriver().get(getDriver().getCurrentUrl());
  	}
    

    @Then("^I validate Conditions of Use page")
  	public void iValidateCondOfUsePage() throws Exception {
    	

        ArrayList<String> tabs = new ArrayList<String>( getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(0));


        getDriver().findElement(By.xpath("//footer//*[contains(text(), 'Conditions of Use')]|//footer//*[contains(text(), 'CONDITIONS OF USE')]|//footer//*[contains(text(), 'Terms & Conditions')]")).click();

        ArrayList<String> tabs2 = new ArrayList<String>( getDriver().getWindowHandles());
        if(browser.equals("firefox")) {
            getDriver().switchTo().window(tabs2.get(1));
        }else {
            getDriver().switchTo().window(tabs2.get(tabPointer));

        }


        // QAFExtendedWebElement conditionsCheck = getDriver().findElement(By.xpath("//*[@class='paragraph_head']"));
        // conditionsCheck.waitForPresent(15000);

        String currentUrlConditions=getDriver().getCurrentUrl();
        Boolean Flag = true;

        if (currentUrlConditions.equals("https://bayercare.com/conditions_of_use.cfm"))
        {
            Assert.assertTrue(Flag);
        } else if (currentUrlConditions.equals("https://www.bayercare.com/conditions_of_use.cfm")){
            Assert.assertTrue(Flag);
        } else  {
            Flag = false;
            Assert.assertFalse(Flag);
        }

       getDriver().switchTo().window(tabs.get(0));
       
       tabPointer++;
    }

    @Then("^I close the window")
    public void closewindow() {
        String winHandleBefore = getDriver().getWindowHandle();

        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }

        getDriver().close(); //this will close new opened window

        getDriver().switchTo().window(winHandleBefore); //switch back to main window

    }
    @FindBy(locator = "universal.claritin.popup")
    private QAFExtendedWebElement claritinPopup;


    @FindBy(locator = "universal.claritin.popup.close")
    private QAFExtendedWebElement claritinPopupClose;

    @And("^If Claritin pop up is present, I close$")
    public void claritinPopup() {
        try {
            if (claritinPopup.isDisplayed()) {

                claritinPopupClose.waitForPresent(15000);
                claritinPopupClose.click();

            }
        }catch(Exception e1){
            System.out.print("Unable to close popup");
        }
    }


	@And("^If DrScholls pop up is present, I close$")
	public void DrschollsPopup() {
		try {
			if (getDriver().findElementByXPath("//*[@id='submitSpecOff']").isDisplayed()) {

				Map<String, Object> params1 = new HashMap<>();
				params1.put("location", "1%,50%");
				Object result1 = getDriver().executeScript("mobile:touch:tap", params1);

			}
		}catch(Exception e1){
			System.out.print("Unable to close popup");
		}
	}
    @Then("^I click on footer logo$")
    public void iClickFooterLogo() {

        QAFExtendedWebElement footerLogo= getDriver().findElement(By.xpath("//div[@class='footer-bayer']//img"));
        footerLogo.waitForPresent(8000);
        footerLogo.click();


    }
    
	@Override
	protected void openPage(PageLocator locator, Object... args) {
		// TODO Auto-generated method stub

	}

}

