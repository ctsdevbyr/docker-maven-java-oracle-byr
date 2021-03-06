/*******************************************************************************
 * QMetry Automation Framework provides a powerful and versatile platform to
 * author
 * Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven
 * approach
 * Copyright 2016 Infostretch Corporation
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 * You should have received a copy of the GNU General Public License along with
 * this program in the name of LICENSE.txt in the root folder of the
 * distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 * See the NOTICE.TXT file in root folder of this source files distribution
 * for additional information regarding copyright ownership and licenses
 * of other open source software / files used by QMetry Automation Framework.
 * For any inquiry or need additional information, please contact
 * support-qaf@infostretch.com
 *******************************************************************************/

package com.quantum.listeners;

import com.quantum.utils.*;
import com.test.utils.ReportDownloadUtil;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebDriverCommandAdapter;
import com.qmetry.qaf.automation.util.StringUtil;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverListener extends QAFWebDriverCommandAdapter {
	@Override
	public void beforeCommand(QAFExtendedWebDriver driver,
			CommandTracker commandTracker) {
		if (commandTracker.getCommand().equalsIgnoreCase(DriverCommand.QUIT)) {

			try {
				String appName = (String) driver.getCapabilities()
						.getCapability("applicationName");
				if (StringUtil.isNotBlank(appName) && StringUtil.isBlank((String) driver
						.getCapabilities().getCapability("eclipseExecutionId"))) {
					try {
						DeviceUtils.closeApp(appName, "name", true, driver);

					} catch (Exception e) {
					}
				}
				if (ConfigurationManager.getBundle().getString("remote.server").toLowerCase().contains(".perfectomobile.com"))
				{
					
					try
					 {
						 Map<String, Object> params = new HashMap<>();
							driver.executeScript("mobile:execution:close", params);
						driver.close();
						
						if(ConfigurationManager.getBundle().getString("perfecto.download.reports","false").toLowerCase().equals("true")) {
							try {
								
								ReportDownloadUtil.generateTestReport(ConfigurationManager.getBundle().getString("executionId"));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								ConsoleUtils.logError(e.toString());
							}
						}
						if(ConfigurationManager.getBundle().getString("perfecto.download.summaryReports","false").toLowerCase().equals("true")) {

							try {
								ReportDownloadUtil.generateSummaryReports(ConfigurationManager.getBundle().getString("executionId"));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								ConsoleUtils.logError(e.toString());
							}
						}
					
					 }
					 catch(Exception ex)
					 {
						 
					 }

					
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}


	@Override
	public void beforeInitialize(Capabilities desiredCapabilities) {
		if (ConfigurationUtils.getBaseBundle().getString("remote.server", "").contains("perfecto")) {
			String eclipseExecutionId = ConfigurationUtils.getExecutionIdCapability();
			
			if (StringUtil.isNotBlank(eclipseExecutionId)) {
				((DesiredCapabilities) desiredCapabilities)
						.setCapability("eclipseExecutionId", eclipseExecutionId);
			}
			//////
			/*ChromeOptions options = new ChromeOptions();
	        options.addArguments("--start-maximized");
	        Map<String, Object> prefs = new HashMap<String, Object>();
	        prefs.put("profile.default_content_settings.popups", 0);
	        prefs.put("acceptSslCerts", true);
	        prefs.put("prompt_for_download", false);
	        prefs.put("directory_upgrade", "true");
	        prefs.put("safebrowsing.enabled", "true");
	        options.setExperimentalOption("prefs", prefs);
	        ((DesiredCapabilities) desiredCapabilities).setCapability("chromeOptions", options);
	        */
		}
		
	}

	@Override
	public void onInitialize(QAFExtendedWebDriver driver) {
		DesiredCapabilities dcaps = new DesiredCapabilities();
		try {
		dcaps = (DesiredCapabilities) CloudUtils.getDeviceProperties((DesiredCapabilities) driver.getCapabilities());
		ConfigurationUtils.setActualDeviceCapabilities(dcaps.asMap());
		ConsoleUtils.logWarningBlocks("DEVICE PROPERTIES: " + dcaps.toString());
		}
		catch(Exception ex)
		{
			
		}

		Long implicitWait = ConfigurationManager.getBundle().getLong("seleniun.wait.implicit", 0); 
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
		try {
			if(!ConfigurationManager.getBundle().getString("perfecto.capabilities.platformName").contains("Android") || !ConfigurationManager.getBundle().getString("perfecto.capabilities.platformName").contains("iOS"))
			driver.manage().window().maximize();
			if(ConfigurationManager.getBundle().getString("perfecto.capabilities.browserName").contains("Chrome")) {
				System.out.println("Browser name = "+ConfigurationManager.getBundle().getString("perfecto.capabilities.browserName"));
				ChromeOptions options = new ChromeOptions();
	            options.addArguments("--start-maximized");
	            Map<String, Object> prefs = new HashMap<String, Object>();
	            prefs.put("profile.default_content_settings.popups", 0);
	            prefs.put("acceptSslCerts", true);
	            prefs.put("prompt_for_download", false);
	            prefs.put("directory_upgrade", "true");
	            prefs.put("safebrowsing.enabled", "true");
	            options.setExperimentalOption("prefs", prefs);
	            dcaps.setCapability("chromeOptions", options);
			}
		}
		catch (Exception e) {
			System.out.println("Unable to increase window size");
		}
		if (ConfigurationManager.getBundle().getString("remote.server").toLowerCase().contains(".perfectomobile.com"))
		{
			ConfigurationManager.getBundle().addProperty("executionId", driver.getCapabilities().getCapability("executionId"));
		}
	}
	
	
}