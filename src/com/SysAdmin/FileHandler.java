package com.SysAdmin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import android.R.integer;
import android.util.Xml.Encoding;

public class FileHandler {

	public static String read(String _path) throws Exception
	{
		StringBuilder file = new StringBuilder();
	
		FileInputStream fileInputStream;
		Scanner scanner = null;
		
		try
		{
			fileInputStream = new FileInputStream(_path);
			
			scanner = new Scanner(fileInputStream, "UTF-8");
			
			while(scanner.hasNextLine())
			{
				file.append(scanner.nextLine());
			}
					
		}
		catch(Exception _exception)
		{ throw _exception; }
		
		finally
		{
			if(scanner != null)
				scanner.close();		
		}
		
		return new String(file);
	}
	
	public static void write(String _path, String _file) throws Exception
	{
		 Writer writer = null;
		 FileOutputStream fileOutPutStream = null;
		 
		 try
		 {
			 fileOutPutStream = new FileOutputStream(_path);
			 writer = new OutputStreamWriter(fileOutPutStream, "UTF-8");
		     
			 writer.write(_file);

		 }
		 catch(Exception _exception)
		 { throw _exception; }
		 
		 finally
		 { 
			 if(writer != null)
				 writer.close();
		 }
	}
}
