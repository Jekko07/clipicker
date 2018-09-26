package com.clipicker.me.httpclient;

import com.clipicker.me.constant.CharSets;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

public class PostParams {
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	public void add(String key, String value) {
		nameValuePairs.add(new BasicNameValuePair(key, value));
	}

	public HttpEntity getEntity() {
		return new UrlEncodedFormEntity(nameValuePairs, CharSets.UTF8);
	}
	
	public String toStringParam() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		getEntity().writeTo(baos);
		String paramString = new String( baos.toByteArray(), CharSets.UTF8);
		baos.flush();
		baos.close();
		
		return paramString;
	}
}
