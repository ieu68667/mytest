package com.junmiao.test;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws XPathExpressionException 
	 * @throws XPatherException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, XPatherException {
		// TODO Auto-generated method stub
	//	System.out.println("hello world");
	/*	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017);
		DB db = mongoClient.getDB( "mydb" );
		System.out.println("你好 ");
		Set<String> colls = db.getCollectionNames();

		for (String s : colls) {
		    System.out.println(s);
		}
		*/
		String[] ctrls = {"dfasdf","30"};
		
		int page = (Integer.parseInt(ctrls[1])) % 15 != 0 ? ((Integer.parseInt(ctrls[1])) / 15 + 1) : (Integer.parseInt(ctrls[1])) / 15;
	    page = page > 50 ? 50 : page;
	    
	    System.out.println(page);
	    
	//	parseDianping();
	}
	
	
	public static void parseDianping() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException, XPathExpressionException, XPatherException {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.dianping.com/search/category/1/10/g0r0");
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0");
		
		long startTime = System.currentTimeMillis();
		System.out.println(" -------    start function call ---------  ");
		
		System.out.println(2387 + 5736 + 2489 + 4722 + 6206 + 12857 + 3521 + 4681 + 3066 + 4337 + 4474 + 3941 + 2973 + 2311 + 1210 + 2566);
//		/html/body/div[3]/div/div/div[2]/dl/dd/ul[2]/li/a
//		/html/body/div[3]/div/div/div[3]/dl/dd/ul[2]/li/a
//		/html/body/div[3]/div/div/div[2]/dl/dd[2]/ul[2]/li/a
//		/html/body/div[3]/div/div/div[2]/dl/dd/ul[2]/li/a
//		/html/body/div[3]/div/div/div[3]/dl/dd[4]/ul[2]/li/a
//		
//		/html/body/div[3]/div/div/div[3]/dl/dd[15]/ul[2]/li/a
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		        // do something useful
		    	
		    	String resp = EntityUtils.toString(entity,HTTP.UTF_8);
		    	//String clean = resp.replaceAll( "&([^;]+(?!(?:\\w|;)))", "&amp;$1" );
		    	
		    //	System.out.println(resp)
		    	 
		    	// convert String into InputStream
		    	InputStream is = new ByteArrayInputStream(resp.getBytes());
		        
		    	// read it with BufferedReader
//		    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		     
//		    	String line;
//		    	while ((line = br.readLine()) != null) {
//		    		System.out.println(line);
//		    	}
//		     
//		    	br.close();
//		    	
		    	
		    	 HtmlCleaner cleaner = new HtmlCleaner();
		         CleanerProperties props = cleaner.getProperties();
		         props.setAllowHtmlInsideAttributes(true);
		         props.setAllowMultiWordAttributes(true);
		         props.setRecognizeUnicodeChars(true);
		         props.setOmitComments(true);
		         
		         TagNode node = cleaner.clean(new InputStreamReader(is));
		        // /html/body/div[3]/div/div/div[2]/dl/dd/ul[2]/li/a
		         Object[] info_nodes = node.evaluateXPath("//body/div[3]/div[1]/div/div[3]/dl/dd[15]/ul[2]/li[1]/a");
		         TagNode info_node = (TagNode) info_nodes[0];
		         
		        
		         
		         String link = info_node.getAttributeByName("href");
		         String myTxt = info_node.getText().toString();
		      //   String info = info_node.getChildren().iterator().next().toString().trim();
		        System.out.println(link);
		         System.out.println(myTxt);
		         
		 		long endTime = System.currentTimeMillis();
		 		long takeTime = endTime - startTime;
		 		double takeTimeSec = takeTime / 1000;
		 		
		 		
		 		System.out.println(" ---------------   End Function Time    Take Time " + takeTimeSec + "  -------------------------  ");
		 		
//		    	
//		    	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
//		        domFactory.setNamespaceAware(true); // never forget this!
//		        DocumentBuilder builder = domFactory.newDocumentBuilder();
//		        Document doc = builder.parse((new InputSource(new ByteArrayInputStream(clean.getBytes("utf-8")))));
//
//		        XPathFactory factory = XPathFactory.newInstance();
//		        XPath xpath = factory.newXPath();
//		        XPathExpression expr 
//		         = xpath.compile("//html//body//div[3]//div//div//div[2]//dl//dd[2]//ul[2]//li//a");
//
//		        Object result = expr.evaluate(doc, XPathConstants.NODE);
//		        Node node_result = (Node)result;
//		        System.out.println(node_result.toString());
		        //System.out.println(resp);
		    } finally {
		        instream.close();
		    }
		}
		
	}

}
