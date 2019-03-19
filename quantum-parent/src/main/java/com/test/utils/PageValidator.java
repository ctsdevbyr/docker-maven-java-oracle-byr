/**
 * 
 */
package com.test.utils;

import static org.testng.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.annotations.FindBy;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.util.StringMatcher;
import com.qmetry.qaf.automation.util.StringUtil;
import com.quantum.utils.*;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


@QAFTestStepProvider
public class PageValidator extends WebDriverTestBase {
	
	
	public void validatePageContent(String file, String object) throws Exception {

		Properties props = new Properties();
		FileInputStream in = new FileInputStream(file);
		props.load(new InputStreamReader(in, Charset.forName("UTF-8")));
		in.close();
		
		

		Set<String> keys = props.stringPropertyNames();
		for (String key : keys) {
			if (key.contains(object)) {
				

				QAFExtendedWebElement we = getDriver()
						.findElement(By.xpath("(//*[contains(text(),\"" + props.getProperty(key).trim() + "\")])[1]"));

					we.verifyAttribute("innerText",StringMatcher.containsIgnoringCase(props.getProperty(key).trim()));


			}

		}
	}
	
	
}
