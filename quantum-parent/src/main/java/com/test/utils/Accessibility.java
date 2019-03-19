package com.test.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.xml.sax.SAXException;

import com.deque.axe.AXE;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;

public class Accessibility extends WebDriverTestBase {

	
    private static final String lineSeparator = System.getProperty("line.separator");
	private static String reportSiteName; 
	public Accessibility() {
		
	}
    public Accessibility(String siteName) {
		reportSiteName = siteName;
	}
	@SuppressWarnings("deprecation")
	public void getAccesibilityResults(String url) throws XPathExpressionException, ParserConfigurationException,
			TransformerException, SAXException, IOException, InterruptedException {

		URL AXE_URL = Accessibility.class.getResource( "/com/quantum/utils/axe.min.js" );
		
		getDriver().get(url);
		
		Thread.sleep(5000);
		
		JSONObject responseJSON = new AXE.Builder(getDriver(), AXE_URL).analyze();

		JSONArray docs = responseJSON.getJSONArray("violations");
		
		for (int i = 0 ; i < docs.length(); i++) {
	        JSONObject obj = docs.getJSONObject(i);
	        obj.put("Site", url);
	        
		}
		
		
		try {
			Date objDate = new Date(); // Current System Date and time is assigned to objDate
			System.out.println(objDate);
			String strDateFormat = "MM-dd-yy"; //Date format is Specified
			SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); //Date format string is passed as an argument to the Date format object
			System.out.println(objSDF.format(objDate));
			
			File file = new File("accessibilityReports/"+reportSiteName+"_AccessibilityReport_"+ objSDF.format(objDate)+".csv");
			String csv = CDL.toString(docs);
			FileUtils.writeStringToFile(file, csv, true);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static String report( final JSONArray violations )
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Found " ).append( violations.length() ).append( " accessibility violations:" );

        for ( int i = 0; i < violations.length(); i++ )
        {
            JSONObject violation = violations.getJSONObject( i );
            sb.append( lineSeparator ).append( i + 1 ).append( ") " ).append( violation.getString( "help" ) );

            if ( violation.has( "helpUrl" ) )
            {
                String helpUrl = violation.getString( "helpUrl" );
                sb.append( ": " ).append( helpUrl );
            }

            JSONArray nodes = violation.getJSONArray( "nodes" );

            for ( int j = 0; j < nodes.length(); j++ )
            {
                JSONObject node = nodes.getJSONObject( j );
                sb.append( lineSeparator ).append( "  " ).append( getOrdinal( j + 1 ) ).append( ") " ).append( node.getJSONArray( "target" ) ).append( lineSeparator );

                JSONArray all = node.getJSONArray( "all" );
                JSONArray none = node.getJSONArray( "none" );

                for ( int k = 0; k < none.length(); k++ )
                {
                    all.put( none.getJSONObject( k ) );
                }

                appendFixes( sb, all, "Fix all of the following:" );
                appendFixes( sb, node.getJSONArray( "any" ), "Fix any of the following:" );
            }
        }

        return sb.toString();
    }

    private static void appendFixes( final StringBuilder sb, final JSONArray arr, final String heading )
    {
        if ( arr != null && arr.length() > 0 )
        {
            sb.append( "    " ).append( heading ).append( lineSeparator );

            for ( int i = 0; i < arr.length(); i++ )
            {
                JSONObject fix = arr.getJSONObject( i );

                sb.append( "      " ).append( fix.get( "message" ) ).append( lineSeparator );
            }

            sb.append( lineSeparator );
        }
    }
    
    private static String getOrdinal( int number )
    {
        String ordinal = "";

        int mod;

        while ( number > 0 )
        {
            mod = (number - 1) % 26;
            ordinal = (char) (mod + 97) + ordinal;
            number = (number - mod) / 26;
        }

        return ordinal;
    }

}
