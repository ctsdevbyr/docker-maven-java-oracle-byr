package com.test.utils;

import java.io.*;

public class SitemapExtractor {

	public static void main(String[] args) {
		try
		{
		    Runtime r = Runtime.getRuntime();
		    String path = "C:/Temp/SitemapGenerator/SitemapGenerator.exe";
		    Process p2 = r.exec(path); 
		}
		catch(IOException ex)
		{
		    System.out.println(ex.getMessage());
		}

	}

}
