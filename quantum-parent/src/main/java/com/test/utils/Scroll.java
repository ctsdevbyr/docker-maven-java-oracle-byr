package com.test.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.text.StringEscapeUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

public class Scroll extends WebDriverTestBase {

	
	private static final String osType="WINDOWS";

	public static void scrollToElement(QAFExtendedWebDriver webDriver, QAFExtendedWebElement webElement, WebDriverWait wait) {
		
			Point point = webElement.getLocation();
			((JavascriptExecutor) webDriver).executeScript("return window.title;");
			wait = new WebDriverWait(webDriver, 30);
			((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + (point.getY() - 200) + ");");
		
	}
	
	public static void moveToElement(QAFExtendedWebDriver webDriver, QAFExtendedWebElement webElment, WebDriverWait wait) {
		
		JavascriptExecutor jse = (JavascriptExecutor)webDriver;

		//jse.executeScript("arguments[0].scrollIntoView()", (WebElement)webElment); 
		
		/* String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                 + "var elementTop = arguments[0].getBoundingClientRect().top;"
                 + "window.scrollBy(0, elementTop-(viewPortHeight/2));";*/

		 //jse.executeScript(scrollElementIntoMiddle, webElment);
		// jse.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");

		Point point = webElment.getLocation();
		((JavascriptExecutor) webDriver).executeScript("return window.title;");
		wait = new WebDriverWait(webDriver, 30);
		//((JavascriptExecutor) webDriver).executeScript("window.scrollBy("+ (point.getX())+"," + (point.getY()) + ");");
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + (point.getY()) + ");");

		
		/*Point point = webElment.getLocation();
		((JavascriptExecutor) webDriver).executeScript("return window.title;");
		List<WebElement> webElementList = new ArrayList();
		webElementList.add(webElment.getWebElement());
        wait.until(ExpectedConditions.visibilityOfAllElements(webElementList));
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + (point.getY() - 200) + ");");
		webElementList.clear();
		webElementList=null;*/
	}

	public static void recallBaseURL(QAFExtendedWebDriver webDriver, String url) {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		webDriver.get(url);
		wait = new WebDriverWait(webDriver, 10);

	}

}
