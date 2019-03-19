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

public class Utils extends WebDriverTestBase {

	public static String getXPathValue(String xml, String XpathString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		NodeList result = getXPathList(xml, XpathString);

		if (result.item(0) == null) {
			throw new XPathExpressionException("Xpath not found");
		} else {
			return result.item(0).getTextContent();
		}
	}

	public String getXPathAttribute(String xml, String attribute, String XpathString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		NodeList result = getXPathList(xml, XpathString);

		if (result.item(0) == null) {
			throw new XPathExpressionException("Xpath not found");
		} else {
			return result.item(0).getAttributes().getNamedItem(attribute).getTextContent();
		}
	}

	public static NodeList getXPathList(String xml, String XpathString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(XpathString);
		return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	}

	public String[] addToArry(String[] a, String newValue) {
		String[] newArray = new String[a.length + 1];

		for (int i = 0; i < a.length; i++)
			newArray[i] = a[i];

		newArray[newArray.length - 1] = newValue;

		a = newArray;

		return a;
	}

	public ArrayList<String> getPageLinks(String url) throws ParserConfigurationException, TransformerException,
			XPathExpressionException, SAXException, IOException, InterruptedException {
		getDriver().get(url);

		Thread.sleep(5000);

		Document document = new DomSerializer(new CleanerProperties())
				.createDOM(new HtmlCleaner().clean(getDriver().getPageSource()));

		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//a[not(contains(@href,'javascript:void')) and @href]");
		NodeList nodes2 = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

		String site = "";
		ArrayList<String> siteList = new ArrayList<>();
		siteList.add(url);

		java.net.URL uri = null;
		uri = new java.net.URL(url);
		String domain = uri.getHost();
		String protocol = uri.getProtocol();

		for (int i = 0; i < nodes2.getLength(); i++) {

			if (nodes2.item(i).getAttributes().getNamedItem("href").getTextContent().indexOf("/") == 0) {
				site = StringEscapeUtils.unescapeXml(protocol + "://" + domain + nodes2.item(i).getAttributes().getNamedItem("href").getTextContent().replace(" ", "%20"));
			} else {
				site = StringEscapeUtils.unescapeXml(nodes2.item(i).getAttributes().getNamedItem("href").getTextContent().replace(" ", "%20"));
			}

			for (int a = 0; a < siteList.size(); a++) {
				if (siteList.get(a).equals(site)) {
					break;
				}
				if ((a + 1) == siteList.size()) {
					siteList.add(site);
				}
			}

		}

		return siteList;
	}

//	public ArrayList<String> getPageLinks(String url) {
//		getDriver().get(url);
//
//		List<WebElement> we = getDriver().findElementsByXPath("//a[not(contains(@href,'javascript:void')) and @href]");
//
//		String site = "";
//		ArrayList<String> siteList = new ArrayList<>();
//		siteList.add(url);
//
//		for (int i = 0; i < we.size(); i++) {
//
//			site = we.get(i).getAttribute("href").toString();
//
//			for (int a = 0; a < siteList.size(); a++) {
//				if (siteList.get(a).equals(site)) {
//					break;
//				}
//				if ((a + 1) == siteList.size()) {
//					siteList.add(site);
//				}
//			}
//
//		}
//
//		return siteList;
//	}

	public NodeList getSiteMapLinks(String url) throws ParserConfigurationException, MalformedURLException,
			SAXException, IOException, TransformerException, XPathExpressionException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new URL(url).openStream());

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		StringWriter sw = new StringWriter();
		t.transform(new DOMSource(document), new StreamResult(sw));

		return getXPathList(sw.toString(), "//*[contains(text(),'http')]");
	}
	
	

}
