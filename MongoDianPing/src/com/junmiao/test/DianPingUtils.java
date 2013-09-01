package com.junmiao.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DianPingUtils {

	public static void cleanAllFiles() {

		List<String> files = getAllOutputFileName();

		for (String file : files) {

			File f = new File(file);
			f.delete();
		}

	}

	public static List<String> getAllOutputFileName() {

		List<String> allOutputFileName = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader("funConf.json"));

			JSONObject jsonObject = (JSONObject) obj;

			Collection<String> files = jsonObject.values();

			Iterator<String> it = files.iterator();

			while (it.hasNext()) {
				String file = it.next();
				allOutputFileName.add(file);
			}
			// path = (String) jsonObject.get("showAllCatForFood");
			// System.out.println(path);
		} catch (Exception e) {

		}

		return allOutputFileName;
	}

	public static String getOutputFileName(String keyFile) {

		String path = "/dianPingResults/error.properties";

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader("funConf.json"));

			JSONObject jsonObject = (JSONObject) obj;

			path = (String) jsonObject.get(keyFile);
			// System.out.println(path);
		} catch (Exception e) {

		}

		File f = new File(path);
		// System.out.println(f.getParent());

		if (!f.exists()) {

			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return path;
	}

	public static String httpGetRequest(String url)
			throws ClientProtocolException, IOException {

		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		// 2972056 //3087349 //4101807

		httpget.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0");

		HttpResponse response = httpclient.execute(httpget);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();

			if (entity != null) {

				result = EntityUtils.toString(entity, HTTP.UTF_8);

			}
		} else {

			System.out.println("not very right " + url);
		}

		httpclient = null;
		return result;
	}
}
