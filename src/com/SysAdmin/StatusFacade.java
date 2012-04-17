package com.SysAdmin;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

/**
 * Downloads the current status.
 * 
 * @author Lukas Bernreiter
 * @version 0.8, 17/04/2012
 * @since 0.8
 */
public abstract class StatusFacade 
{
	private static ByteArrayBuffer mByteArrayBuffer  = null;
	
	public static void downloadStatus(String _url) throws Exception
	{
		InputStream inputStream = null;
		
		// Check if the connection is OK
		try {
			URL url = new URL(_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			inputStream = new BufferedInputStream(connection.getInputStream(), 1024 * 5);				
			int current = 0;
			
			while((current = inputStream.read()) != -1)
				mByteArrayBuffer.append((byte)current);
			
		} catch (Exception _e) {
			Log.e(AppFacade.GetTag(), _e.getMessage());
			throw new Exception(_e.getMessage());
		}
		finally{
			try {
				if(null != inputStream)
				{
					inputStream.close();
					//writeFile();
				}				
			} catch (IOException _e) {
				Log.e(AppFacade.GetTag(), _e.getMessage());
			}
		}
	}
	
	/** Stores the file temporally */
	private static void writeFile()
	{
		ByteArrayBuffer baf = mByteArrayBuffer;
		FileOutputStream output = null;
		
		try {
			output = new FileOutputStream(FilePathFacade.GetTempFile());
			output.write(baf.toByteArray());
			output.close();
		} catch (Exception _e) {Log.e(AppFacade.GetTag(), _e.getMessage());}
	}
}
