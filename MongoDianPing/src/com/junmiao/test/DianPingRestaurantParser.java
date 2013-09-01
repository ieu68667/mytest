package com.junmiao.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.simple.JSONObject;

public class DianPingRestaurantParser {

	
	//B10c56613b2e095ea092138eb5044be1
	public static void main(String[] args) throws ClientProtocolException,
			IOException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.dianping.com/shop/4101807");
		// 2972056 //3087349 //4101807

		httpget.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0");

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {

				String resp = EntityUtils.toString(entity, HTTP.UTF_8);
				System.out.println(resp);
				parseRestaurant(resp);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void parseResaurantCatgory(String resCatPage) {
		
	}

	// Parse the response page retu
	public static void parseRestaurant(String respPage) throws IOException,
			XPatherException {
		
		JSONObject restaurant = new JSONObject();

		System.out.println(" -------    start function call ---------  ");
		long startTime = System.currentTimeMillis();

		// Stream parsing
		InputStream is = new ByteArrayInputStream(respPage.getBytes());

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);

		TagNode node = cleaner.clean(new InputStreamReader(is));
		Object[] info_nodes = null;
		TagNode info_node = null;
		// ContentNode content_node = null;
		// restaurant name
		info_nodes = node
				.evaluateXPath("//body/div[4]/div[1]/div[1]/div/div[1]/div[1]/h1");
		info_node = (TagNode) info_nodes[0];

		String resName = info_node.getText().toString();
		System.out.println(resName);

		// restaurant address
		info_nodes = node
				.evaluateXPath("//body/div[4]/div[1]/div[1]/div/div[3]/div[2]/div[1]/ul/li[1]/span");
		info_node = (TagNode) info_nodes[0];

		String resAddr = info_node.getText().toString();
		System.out.println(resAddr);

		// restaurant tel
		info_nodes = node
				.evaluateXPath("//body/div[4]/div[1]/div[1]/div/div[3]/div[2]/div[1]/ul/li[2]/span");
		ArrayList<String> res_tels = new ArrayList<String>();

		for (int i = 0; i < info_nodes.length; i++) {
			info_node = (TagNode) info_nodes[i];
			String resTel = info_node.getText().toString();
			System.out.println(resTel);
			res_tels.add(resTel);

		}

		// restaurant attributes
		info_nodes = node
				.evaluateXPath("//body/div[4]/div[1]/div[1]/div/div[3]/div[2]/div[2]/div/ul/li");

		
		
		for (int j = 0; j < (info_nodes.length-1); j++) {

			info_node = (TagNode) info_nodes[j];

			List tag_lists = info_node.getAllChildren();
    
			
			for (int i = 0; i < tag_lists.size(); i++) {

				if (tag_lists.get(i) instanceof TagNode) {
					System.out
							.println(((TagNode) (tag_lists.get(i))).getText());
				}

				if (tag_lists.get(i) instanceof ContentNode) {

					if (((ContentNode) (tag_lists.get(i))).getContent().trim()
							.length() > 0) {
						System.out.println(((ContentNode) (tag_lists.get(i)))
								.getContent().trim());
					}
				}

			}

		}
		/*
		 * info_node = (TagNode) info_nodes[0];
		 * 
		 * int size = info_node.getChildTagList().size(); List<TagNode> children
		 * = info_node.getAllElementsList(true); for(int i=0 ; i<size; i++) {
		 * System.out.println(children.get(i).getText().toString()); }\
		 */
		// System.out.println(info_node.getChildTagList().size());

		long endTime = System.currentTimeMillis();
		long takeTime = endTime - startTime;
		double takeTimeSec = takeTime / 1000;
		System.out.println(" ---------------   End Function Time    Take Time "
				+ takeTimeSec + "  -------------------------  ");

	}

}
