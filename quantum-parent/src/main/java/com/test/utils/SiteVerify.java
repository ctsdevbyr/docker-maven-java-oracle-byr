package com.test.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.util.Validator;
import com.quantum.listeners.QuantumReportiumListener;
import com.quantum.utils.ConsoleUtils;

public class SiteVerify extends WebDriverTestBase {

	private void logResults(boolean error, String site) {
		if (error) {
			ConsoleUtils.logError(site + " : page not found");
			if (ConfigurationManager.getBundle().getString("remote.server").toLowerCase().contains("perfecto")) {
				QuantumReportiumListener.getReportClient().stepStart(site);
				Validator.verifyTrue(0 > 1, site + " : page not found", "");
				QuantumReportiumListener.getReportClient().reportiumAssert(site + " : page not found", false);
				QuantumReportiumListener.getReportClient().stepEnd();
			}
		} else {
			ConsoleUtils.logError(site + " : page found");
			if (ConfigurationManager.getBundle().getString("remote.server").toLowerCase().contains("perfecto")) {
				QuantumReportiumListener.getReportClient().stepStart(site);
				QuantumReportiumListener.getReportClient().reportiumAssert(site + " : page found", true);
				QuantumReportiumListener.getReportClient().stepEnd();
			}
		}
	}

	private void verifyUI(String site) {

		//getDriver().get("https://www.google.com");

		getDriver().get(site);

		try {
			getDriver().findElement("petbasics.common.siteMap.globalVerify").waitForPresent(15000);

		} catch (Exception ex) {

		}

		try {
			getDriver().findElementByXPath("//*[contains(text(),'HTTP ERROR 404')]");
			logResults(true, site);
			return;
		} catch (Exception ex) {

		}

		if (getDriver().findElement("petbasics.common.pageNotFound.container").verifyPresent()) {

			logResults(true, site);
		} else {

			logResults(false, site);
		}
	}

	private void verifyAPI(String site) throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerException {

		HttpURLConnection huc;
		int respCode = 200;

		huc = (HttpURLConnection) (new URL(site).openConnection());

		huc.setRequestMethod("HEAD");

		huc.connect();

		respCode = huc.getResponseCode();

		if (respCode >= 400) {
			logResults(true, site);

		} else {
			logResults(false, site);
		}

	}

	

	public void LinkCheckerAPI(String url) throws XPathExpressionException, ParserConfigurationException, TransformerException, SAXException, IOException, InterruptedException {

		Utils u= new Utils();
		ArrayList<String> siteList = u.getPageLinks(url);

		for (int i = 0; i < siteList.size(); i++) {
			try {
				verifyAPI(siteList.get(i));
			} catch (Exception ex) {

			}
		}

	}

	public void LinkCheckerUI(String url) throws XPathExpressionException, ParserConfigurationException, TransformerException, SAXException, IOException, InterruptedException {
		Utils u= new Utils();
		ArrayList<String> siteList = u.getPageLinks(url);

		for (int i = 0; i < siteList.size(); i++) {
			try {
				verifyUI(siteList.get(i));
			} catch (Exception ex) {

			}
		}
	}

	public void SiteMapCheckerAPI(String url) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerException {

		Utils u= new Utils();
		NodeList nodes = u.getSiteMapLinks(url);
		String site = "";
		for (int i = 0; i <= nodes.getLength() - 1; i++) {
			site = nodes.item(i).getTextContent();

			try {
				verifyAPI(site);
			} catch (Exception ex) {
				System.out.println("broken");
			}

		}
	}
	
	public void SiteMapCheckerAPIAccessibility(String url, String domainName) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerException {
		
		Utils u= new Utils();
		NodeList nodes = u.getSiteMapLinks(url);
		String site = "";
		for (int i = 0; i <= nodes.getLength() - 1; i++) {
			site = nodes.item(i).getTextContent();
		
			try {
				verifyAPI(site);
				Accessibility access = new Accessibility(domainName);
		           access.getAccesibilityResults(site);
			} catch (Exception ex) {
				System.out.println("broken");
			}
		
		}
}
	
	public void SiteMapCheckerUI(String url) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerException {

		Utils u= new Utils();
		NodeList nodes = u.getSiteMapLinks(url);
		String site = "";
		for (int i = 0; i <= nodes.getLength() - 1; i++) {
			site = nodes.item(i).getTextContent();

			verifyUI(site);

			if (i == 10) {
				break;
			}
		}

	}
	
	public void SiteMapCheckerUIAccessibility(String url, String domainName) throws XPathExpressionException, ParserConfigurationException,
		SAXException, IOException, TransformerException {
	
	Utils u= new Utils();
	NodeList nodes = u.getSiteMapLinks(url);
	String site = "";
	for (int i = 0; i <= nodes.getLength() - 1; i++) {
		site = nodes.item(i).getTextContent();
	
		verifyUI(site);
		Accessibility access = new Accessibility(domainName);
        try {
			access.getAccesibilityResults(site);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if (i == 10) {
			break;
		}
	}

}
	
	public void validateTextOcuranceOnPage(String text,Integer occurance) {
		String xpathExpression="//p[contains(text(),'"+text.trim()+"')]";
		Integer conentOccurance=getDriver().findElements(By.xpath(xpathExpression)).size();
		Assert.assertEquals(occurance.intValue(), conentOccurance.intValue());
	}

	

}
