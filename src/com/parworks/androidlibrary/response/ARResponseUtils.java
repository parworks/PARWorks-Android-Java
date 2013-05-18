package com.parworks.androidlibrary.response;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.parworks.androidlibrary.ar.ARException;

public class ARResponseUtils {
	
	public static String convertHttpResponseToString(HttpResponse serverResponse) {
		InputStream contentStream = null;
		String contentString = null;
		try {
			contentStream = serverResponse.getEntity().getContent();
		} catch (IllegalStateException e) {
			throw new ARException("Couldn't convert the http response to an inputstream because of illegal state.",e);
		} catch (IOException e) {
			throw new ARException("Couldn't convert the http response to an inputstream.",e);
		}
		try {
			contentString = IOUtils.toString(contentStream);
		} catch (IOException e) {
			throw new ARException("Couldn't convert the http response to a string. Successfully converted the http response to an inputstream, but failed to convert the input stream to a string to due an ioexception.",e);
		}
		return contentString;
	}

}
