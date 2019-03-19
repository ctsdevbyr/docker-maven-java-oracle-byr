package com.test.utils;


import com.applitools.eyes.*;
import com.applitools.eyes.exceptions.NewTestException;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.test.utils.visual.ApplitoolsTestResultsHandler;
import com.applitools.eyes.TestResults;
import com.perfecto.reportium.client.ReportiumClient;
import com.quantum.utils.AppiumUtils;
import com.quantum.utils.ConfigurationUtils;
import com.quantum.utils.ConsoleUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;


/** Created by lirona on 28/03/2017 **/

public class ApplitoolsUtils extends WebDriverTestBase {
	private static String appName = ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName");
    private static BatchInfo batch =null;

	public static void setBatchEyes(String batchName) {
        Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        Eyes eye = (Eyes) eyeObject;
         batch = new BatchInfo(batchName);
        eye.setBatch(batch);
    }
	
	
    public static void checkWindow(String tag, boolean checkWindowFlag ){
        Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        String urlTag = new WebDriverTestBase().getDriver().getCurrentUrl();
    	
        if (null != eyeObject){
        	System.out.println("Eyes object initiated");
            Eyes eye = (Eyes) eyeObject;
            eye.setApiKey(ConfigurationManager.getBundle().getString("applitools.key"));

            Eyes eyes2 = new Eyes();
            eyes2.setApiKey(ConfigurationManager.getBundle().getString("applitools.key2"));
            
            eye.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));
        	try {
				eye.setServerUrl("https://bayereyes.applitools.com/");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				System.out.println("Unable to access server url");
			}
			try {
                eye.setBatch(batch);
            }
            catch(Exception e1){
			    System.out.println("");
                BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
                eye.setBatch(batch);
            }

            if(checkWindowFlag == true) {
            	eye.setForceFullPageScreenshot(true);
            }
            else {
            	eye.setForceFullPageScreenshot(false);
            }
              eye.setStitchMode(StitchMode.CSS);
            //eye.setImageCut(new FixedCutProvider(150,40,0,0));
            //System.out.println("Image cut iOS 2");
            if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("iPhone-X")) {

                eye.setImageCut(new FixedCutProvider(95,35,0,0));
                //remove URL and footer. values = (header, footer, left, right)
                System.out.println("Image cut iOS 2");
            }
            else if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("Galaxy S8")) {

                eye.setImageCut(new FixedCutProvider(80,30,0,0)); //remove URL and footer. values = (header, footer, left, right)
                System.out.println("Image cut Android");
            }
            else {
                System.out.println("No image cut occurred");
            }
            WebDriver driver = new WebDriverTestBase().getDriver();
            if (!eye.getIsOpen()) {

                //Selenium case:
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.name").contains("perfectoRemoteDriver")){ 
                	driver = eye.open(driver, appName, ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName")); //TODO: check what to do with test names? should be on property? definable per check?
                	//System.out.println("Eyes opened 1");
                }

                //Appium case:
                else{
                    driver = eye.open(driver, AppiumUtils.getAppiumDriver().getCapabilities().getCapability("appName").toString(), ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName"));
                    //System.out.println("Eyes opened 2");
                }
                

            }
            
			
            try {
            	System.out.println("current url: "+urlTag);
            	String tagConst = tag + " "+ urlTag; //Add url to tag so that link appears in dashboard
                try {
                    eye.checkWindow(tag);
                } catch (Exception e2) {
                    System.out.println("Unable to check window");
                }
                
                
            }   catch (NewTestException ex) { System.out.println(ex.getMessage()); }
                catch (Exception ex) { ConsoleUtils.logError(ex.getMessage()); }
            
        }
        else {
            ConsoleUtils.logError("Eye object was not initiated!");
            String applitoolsAPIKey = ConfigurationManager.getBundle().getString("applitools.key");
            if (null != applitoolsAPIKey) {

                Eyes e = new Eyes();
                e.setApiKey(applitoolsAPIKey);
                //e.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));
                try {
                    e.setServerUrl("https://bayereyes.applitools.com/");
                } catch (URISyntaxException e1) {
                    // TODO Auto-generated catch block
                    System.out.println("Unable to access server url");
                }
                try {
                    e.setBatch(batch);
                } catch (Exception e1) {
                    System.out.println("Unable to set batch");
                    BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
                    e.setBatch(batch);
                }
                e.setForceFullPageScreenshot(true);
                e.setStitchMode(StitchMode.CSS);
                String applitoolsMatchLevel = ConfigurationManager.getBundle().getString("applitools.matchLevel"); //Exact/Strict/Content/Layout/Layout2 (Recommended)
                if (null != applitoolsMatchLevel) {
                    switch (applitoolsMatchLevel) {
                        case "Exact":
                            e.setMatchLevel(MatchLevel.EXACT);
                            break;
                        case "Strict":
                            e.setMatchLevel(MatchLevel.STRICT);
                            break;
                        case "Content":
                            e.setMatchLevel(MatchLevel.CONTENT);
                            break;
                        case "Layout":
                            e.setMatchLevel(MatchLevel.LAYOUT);
                            break;
                        case "Layout2":
                            e.setMatchLevel(MatchLevel.LAYOUT2);
                            break;
                        default:
                            System.out.println("Wrong Applitools Match Level configuration - eye object will use default match level.");
                            break;
                    }
                }
                ConfigurationManager.getBundle().setProperty("Eyes", e);
                String tagConst = tag + " "+ urlTag;
                try {
                    e.checkWindow(tag);
                } catch (Exception e2) {
                    System.out.println("Unable to check window");
                }
                   /* TestResults testResult= e.close(false);
                    String viewKey ="Unhi1011LAV5107iLxwhvF100tfkJC4qLN3IpiSgbLVmNdb7E110";
                    try {
         			ApplitoolsTestResultsHandler testResultHandler= new ApplitoolsTestResultsHandler(testResult,viewKey);
         			testResultHandler.downloadCurrentImages("C:/Temp/Applitools/Test");
         		} catch (Exception x) {
         			// TODO Auto-generated catch block
         			System.out.println("Unable to print screenshots");
         			x.printStackTrace();
         		} 
                } catch (NewTestException ex) { System.out.println(ex.getMessage()); }
                */
            }
        }
    }
    
    public static void urlListCheckWindow(String urlList, boolean checkWindowFlag ){
        
    	String [] urlNames = new String[150];
		int numEntries = 0;
		int c = 0;
		WebDriver urlDriver = new WebDriverTestBase().getDriver();
		try {
			//File file = new File("src/test/java/com/bayer/exampleTemplate/config/TestData/urlList.txt");
			File file = new File(urlList);
			System.out.println("Urllist " + urlList);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				urlNames[i] = line;
				numEntries++;
				i++; }
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        if (null != eyeObject){
        	System.out.println("Eyes object initiated");
            Eyes eye = (Eyes) eyeObject;
            eye.setApiKey(ConfigurationManager.getBundle().getString("applitools.key"));
            
           // eye.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));
        	try {
				eye.setServerUrl("https://bayereyes.applitools.com/");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				System.out.println("Unable to access server url");
			}
            try {
                    eye.setBatch(batch);
            }
            catch(Exception e1){
                System.out.println("Unable to set batch");
                BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
                eye.setBatch(batch);
            }
            if(checkWindowFlag == true) {
            	eye.setForceFullPageScreenshot(true);
            }
            else {
            	eye.setForceFullPageScreenshot(false);
            }
              eye.setStitchMode(StitchMode.CSS);
            if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("iPhone-X")) {

                eye.setImageCut(new FixedCutProvider(95,35,0,0));
                //remove URL and footer. values = (header, footer, left, right)
                System.out.println("Image cut iOS 2");
            }
            else if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("Galaxy S8")) {

                eye.setImageCut(new FixedCutProvider(80,30,0,0)); //remove URL and footer. values = (header, footer, left, right)
                System.out.println("Image cut Android");
            }
            else {
                System.out.println("No image cut occurred");
            }

            WebDriver driver = new WebDriverTestBase().getDriver();
            if (!eye.getIsOpen()) {

                //Selenium case:
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.name").contains("perfectoRemoteDriver")){
                	driver = eye.open(driver, appName, ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName")); //TODO: check what to do with test names? should be on property? definable per check?
                	//System.out.println("Eyes opened 1");
                }

                //Appium case:
                else{
                    driver = eye.open(driver, AppiumUtils.getAppiumDriver().getCapabilities().getCapability("appName").toString(), ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName"));
                    //System.out.println("Eyes opened 2");
                }
                //eye.setViewportSize(driver, new RectangleSize(412, 604));
            }
            
            try {
            	String tag = null;
            	int counter=0;
            	while(c < numEntries) { 
	    			String url = urlNames[c];
	    			System.out.println(url);
	    			urlDriver.navigate().to(url);
	    			tag = url; //Add url to tag so that link appears in dashboard
	    			counter = c+1;
	    			tag = "Page:" +counter + " " + url;
	    			eye.checkWindow(tag);
	    			System.out.println("Current url in loop is: " + url);
	    			c++;
	    			
            	}
            }   catch (NewTestException ex) { System.out.println(ex.getMessage()); }
                catch (Exception ex) { ConsoleUtils.logError(ex.getMessage()); }
        }
        else {
            ConsoleUtils.logError("Eye object was not initiated!");
            String applitoolsAPIKey = ConfigurationManager.getBundle().getString("applitools.key");
            if (null != applitoolsAPIKey) {
            	Eyes e = new Eyes();
                e.setApiKey(applitoolsAPIKey);
              // e.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));

            	try {
    				e.setServerUrl("https://bayereyes.applitools.com/");
    			} catch (URISyntaxException e1) {
    				// TODO Auto-generated catch block
    				System.out.println("Unable to access server url");
    			}
                try {
                    e.setBatch(batch);
                }
                catch(Exception e1){
                    System.out.println("Unable to set batch");
                    BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
                    e.setBatch(batch);
                }
               e.setForceFullPageScreenshot(true);
                 e.setStitchMode(StitchMode.CSS);
                String applitoolsMatchLevel = ConfigurationManager.getBundle().getString("applitools.matchLevel"); //Exact/Strict/Content/Layout/Layout2 (Recommended)
                if (null != applitoolsMatchLevel){
                    switch (applitoolsMatchLevel){
                        case "Exact" :
                            e.setMatchLevel(MatchLevel.EXACT);
                            break;
                        case "Strict" :
                            e.setMatchLevel(MatchLevel.STRICT);
                            break;
                        case "Content" :
                            e.setMatchLevel(MatchLevel.CONTENT);
                            break;
                        case "Layout" :
                            e.setMatchLevel(MatchLevel.LAYOUT);
                            break;
                        case "Layout2" :
                            e.setMatchLevel(MatchLevel.LAYOUT2);
                            break;
                        default :
                            System.out.println("Wrong Applitools Match Level configuration - eye object will use default match level.");
                            break;
                    }
                }
                ConfigurationManager.getBundle().setProperty("Eyes", e);
                try{
                	String tag = null;
                	int counter=0;
                	while(c < numEntries) { 
    	    			String url = urlNames[c];
    	    			System.out.println(url);
    	    			urlDriver.navigate().to(url);
    	    			tag = url; //Add url to tag so that link appears in dashboard
    	    			counter = c+1;
    	    			tag = "Page:" +counter + " " + url;
    	    			e.checkWindow(tag);
    	    			System.out.println("Current url in loop is: " + url);
    	    			c++;
    	    			
                	}
                } catch (NewTestException ex) { System.out.println(ex.getMessage()); }
            }
        }
    }

    public static void checkRegion(WebElement element, String tag){
        Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");

        if (null != eyeObject) {
            Eyes eye = (Eyes) eyeObject;
            WebDriver driver = new WebDriverTestBase().getDriver();
            if (!eye.getIsOpen()) {

                //Selenium case:
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.name").contains("Remote")) {
                    driver = eye.open(driver, appName, ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName")); //TODO: check what to do with test names? should be on property? definable per check?
                }

                //Appium case:
                else {
                    driver = eye.open(driver, AppiumUtils.getAppiumDriver().getCapabilities().getCapability("appName").toString(), ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName"));
                }
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("iPhone-X")) {

                    eye.setImageCut(new FixedCutProvider(95,35,0,0));
                    //remove URL and footer. values = (header, footer, left, right)
                    System.out.println("Image cut iOS 2");
                }
                else if (ConfigurationUtils.getBaseBundle().getPropertyValue("perfecto.capabilities.model").contains("Galaxy S8")) {

                    eye.setImageCut(new FixedCutProvider(80,30,0,0)); //remove URL and footer. values = (header, footer, left, right)
                    System.out.println("Image cut Android");
                }
                else {
                    System.out.println("No image cut occurred");
                }
            }
            try {
                eye.checkRegion(element, tag);
            } catch (Exception ex) {
                ConsoleUtils.logError(ex.getMessage());
            }
        }
        else { ConsoleUtils.logError("Eye object was not initiated!"); }
    }

    public static void exportScreenshots(String exportPath){
        Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        
        if (null != eyeObject) {
            Eyes eye = (Eyes) eyeObject;
            try {
    			eye.setServerUrl("https://bayereyes.applitools.com/");
    		} catch (URISyntaxException e1) {
    			// TODO Auto-generated catch block
    			System.out.println("Unable to access server url");
    		}
            WebDriver driver = new WebDriverTestBase().getDriver();
            TestResults testResult= eye.close(false);
            System.out.println("Passed 1");
            String viewKey ="Unhi1011LAV5107iLxwhvF100tfkJC4qLN3IpiSgbLVmNdb7E110";
            exportPath = "H:/Personal Data/Automation/Applitools/Screenshots/Aspirin";
            try {
 			ApplitoolsTestResultsHandler testResultHandler= new ApplitoolsTestResultsHandler(testResult,viewKey);
 			System.out.println("Passed 2");
 			testResultHandler.downloadCurrentImages(exportPath);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			System.out.println("Unable to export screenshots to "+ exportPath);
 			e.printStackTrace();
 		} 
        }
        else { ConsoleUtils.logError("Eye object was not initiated!"); }
    }
   
    public static void urlListCheckWindowScreenshots(String urlList, boolean checkWindowFlag, String exportPath){
        
    	String [] urlNames = new String[100];
		int numEntries = 0;
		int c = 0;
		WebDriver urlDriver = new WebDriverTestBase().getDriver();
		try {
			//File file = new File("src/test/java/com/bayer/exampleTemplate/config/TestData/urlList.txt");
			File file = new File(urlList);
			System.out.println("Urllist " + urlList);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				urlNames[i] = line;
				numEntries++;
				i++; }
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        if (null != eyeObject){
        	System.out.println("Eyes object initiated");
            Eyes eye = (Eyes) eyeObject;
            eye.setApiKey(ConfigurationManager.getBundle().getString("applitools.key"));

         // eye.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));

        	try {
				eye.setServerUrl("https://bayereyes.applitools.com/");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				System.out.println("Unable to access server url");
			}
            BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
            eye.setBatch(batch);
            if(checkWindowFlag == true) {
            	eye.setForceFullPageScreenshot(true);
            }
            else {
            	eye.setForceFullPageScreenshot(false);
            }
              eye.setStitchMode(StitchMode.CSS);
            WebDriver driver = new WebDriverTestBase().getDriver();
            if (!eye.getIsOpen()) {

                //Selenium case:
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.name").contains("perfectoRemoteDriver")){ 
                	driver = eye.open(driver, appName, ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName")); //TODO: check what to do with test names? should be on property? definable per check?
                	//System.out.println("Eyes opened 1");
                }

                //Appium case:
                else{
                    driver = eye.open(driver, AppiumUtils.getAppiumDriver().getCapabilities().getCapability("appName").toString(), ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.testName"));
                    //System.out.println("Eyes opened 2");
                }
                if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.capabiltiites.platform").contains("iOS")) {
		        	   
		               eye.setImageCut(new FixedCutProvider(95,35,0,0)); //remove URL and footer. values = (header, footer, left, right)
		       			System.out.println("Image cut iOS");
		           }
                else if (ConfigurationUtils.getBaseBundle().getPropertyValue("driver.capabiltiites.platform").contains("Android")) {
		        	   
		               eye.setImageCut(new FixedCutProvider(80,30,0,0)); //remove URL and footer. values = (header, footer, left, right)
		       			System.out.println("Image cut Android");
		           }
                else {
                    System.out.println("No image cut occurred");
                }
                //eye.setViewportSize(driver, new RectangleSize(412, 604));
            }
            
            try {
            	String tag = null;
            	int counter=0;
            	while(c < numEntries) { 
	    			String url = urlNames[c];
	    			System.out.println(url);
	    			urlDriver.navigate().to(url);
	    			tag = url; //Add url to tag so that link appears in dashboard
	    			counter = c+1;
	    			tag = "Page:" +counter + " " + url;
	    			eye.checkWindow(tag);
	    			System.out.println("Current url in loop is: " + url);
	    			c++;
	    			
            	}
            	TestResults testResult= eye.close(false);
                String viewKey ="KL99zxdo4peUIDFNMwyVe7F104Lf2F5b2l57PghSmU106vVA110";
                try {
        			ApplitoolsTestResultsHandler testResultHandler= new ApplitoolsTestResultsHandler(testResult,viewKey);
        			testResultHandler.downloadDiffs(exportPath);
        		} catch (Exception ey) {
        			// TODO Auto-generated catch block
        			ey.printStackTrace();
        		} 
            }   catch (NewTestException ex) { System.out.println(ex.getMessage()); }
                catch (Exception ex) { ConsoleUtils.logError(ex.getMessage()); }
        }
        else {
            ConsoleUtils.logError("Eye object was not initiated!");
            String applitoolsAPIKey = ConfigurationManager.getBundle().getString("applitools.key");
            if (null != applitoolsAPIKey) {
            	Eyes e = new Eyes();
                e.setApiKey(applitoolsAPIKey); 
               // e.setProxy(new ProxySettings("http://ptb-proxy.na.bayer.cnb/"));
            	try {
    				e.setServerUrl("https://bayereyes.applitools.com/");
    			} catch (URISyntaxException e1) {
    				// TODO Auto-generated catch block
    				System.out.println("Unable to access server url");
    			}
    	       BatchInfo batch = new BatchInfo(ConfigurationUtils.getBaseBundle().getPropertyValue("applitools.batchName"));
               e.setBatch(batch);
               e.setForceFullPageScreenshot(true);
                 e.setStitchMode(StitchMode.CSS);
                String applitoolsMatchLevel = ConfigurationManager.getBundle().getString("applitools.matchLevel"); //Exact/Strict/Content/Layout/Layout2 (Recommended)
                if (null != applitoolsMatchLevel){
                    switch (applitoolsMatchLevel){
                        case "Exact" :
                            e.setMatchLevel(MatchLevel.EXACT);
                            break;
                        case "Strict" :
                            e.setMatchLevel(MatchLevel.STRICT);
                            break;
                        case "Content" :
                            e.setMatchLevel(MatchLevel.CONTENT);
                            break;
                        case "Layout" :
                            e.setMatchLevel(MatchLevel.LAYOUT);
                            break;
                        case "Layout2" :
                            e.setMatchLevel(MatchLevel.LAYOUT2);
                            break;
                        default :
                            System.out.println("Wrong Applitools Match Level configuration - eye object will use default match level.");
                            break;
                    }
                }
                ConfigurationManager.getBundle().setProperty("Eyes", e);
                try{
                	String tag = null;
                	int counter=0;
                	while(c < numEntries) { 
    	    			String url = urlNames[c];
    	    			System.out.println(url);
    	    			urlDriver.navigate().to(url);
    	    			tag = url; //Add url to tag so that link appears in dashboard
    	    			counter = c+1;
    	    			tag = "Page:" +counter + " " + url;
    	    			e.checkWindow(tag);
    	    			System.out.println("Current url in loop is: " + url);
    	    			c++;
    	    			
                	}
                	TestResults testResult= e.close(false);
                    String viewKey ="KL99zxdo4peUIDFNMwyVe7F104Lf2F5b2l57PghSmU106vVA110";
                    try {
            			ApplitoolsTestResultsHandler testResultHandler= new ApplitoolsTestResultsHandler(testResult,viewKey);
            			testResultHandler.downloadDiffs(exportPath);
            		} catch (Exception ey) {
            			// TODO Auto-generated catch block
            			ey.printStackTrace();
            		} 
                } catch (NewTestException ex) { System.out.println(ex.getMessage()); }
                
            }
        }
        
        
    }
    
    public static void closeApplitoolsEyes(){
    //get eye
        Object eyeObject = ConfigurationManager.getBundle().getObject("Eyes");
        if (null != eyeObject){
            Eyes eye = (Eyes) eyeObject;
            try{
              eye.close();
            } finally {
                eye.abortIfNotClosed(); //TODO: check how abortIfNotClosed reacts to null eye object
            }
        }
    }
}
