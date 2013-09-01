package com.junmiao.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class QuickTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws XPatherException 
	 */
	private static int count=1;
	public static void main(String[] args) throws ClientProtocolException, IOException, XPatherException {
		
		System.out.println(" -------    start main function call ---------  ");
		long startTime = System.currentTimeMillis();

		DianPingUtils.cleanAllFiles();
		
//		 TODO Auto-generated method stub
		for(int i=1; i<2; i++) {
			
			for(int j=101; j<119; j++){
				showAllCatForFood(j, 5);
			}
			
		}
		
		
		
		String fileInputCat_fileName = DianPingUtils.getOutputFileName("showAllCatForFood");
		File fileInputCat = new File(fileInputCat_fileName);
		
		List<String> catUrls = FileUtils.readLines(fileInputCat);
		
		for(int i=0; i<catUrls.size(); i++){
			
			String[] ctrls = catUrls.get(i).split(",");
			
			int page = (Integer.parseInt(ctrls[1])) % 15 != 0 ? ((Integer.parseInt(ctrls[1])) / 15 + 1) : (Integer.parseInt(ctrls[1])) / 15;
		    
			if (page <= 50) {
			    for (int j=1; j<(page+1); j++) {
			    	 showFoodInCat(ctrls[0], j);
			    }
		    }else{
		    	
		    	String fileName = DianPingUtils.getOutputFileName("furtherBreakDown");
		    	FileUtils.writeStringToFile(new File(fileName), fileName, true);
		    	
		    }
		//	showFoodInCat(catUrls.get(i));
		}
	    
		long endTime = System.currentTimeMillis();
		long takeTime = endTime - startTime;
		double takeTimeSec = takeTime / 1000;
		System.out.println(" ---------------   End Function Time    Take Time "
				+ takeTimeSec + "  -------------------------  ");

		
		// DianPingUtils.getAllOutputFileName();
		
		//showFoodInCat();
//		DianPingUtils.getOutputFileName("showAllCatForFood");
     //   TagNode info_node = (TagNode) info_nodes[0];
		
		
	//	System.out.println(result);
	}
	
	
	public static void showFoodInCat(String url, int page) throws ClientProtocolException, IOException, XPatherException {
		
		String outputPath = DianPingUtils.getOutputFileName("showFoodInCat");
	    List<String> outputResult = new ArrayList<String>();
		
		String result = DianPingUtils.httpGetRequest("http://www.dianping.com" + url + "p" + page);
		
		InputStream is = new ByteArrayInputStream(result.getBytes());

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);

		TagNode node = cleaner.clean(new InputStreamReader(is));
        Object[] info_nodes = node.evaluateXPath("//body/div[3]/div[1]/div/div[3]/dl//dd/ul[2]/li[1]/a[1]");
        TagNode info_node = null;
      
        for (int j = 0; j < info_nodes.length; j++) {

			info_node = (TagNode) info_nodes[j];
            System.out.println(info_node.getAttributeByName("href"));
            outputResult.add(info_node.getAttributeByName("href"));
			
			List tag_lists = info_node.getAllChildren();
    
			
			for (int i = 0; i < tag_lists.size(); i++) {

				if (tag_lists.get(i) instanceof TagNode) {
					System.out
							.println(((TagNode) (tag_lists.get(i))).getText().toString().trim());
				}

/*
				if (tag_lists.get(i) instanceof ContentNode) {

					if (((ContentNode) (tag_lists.get(i))).getContent().trim()
							.length() > 0) {
						System.out.println("" + count +" "+((ContentNode) (tag_lists.get(i)))
								.getContent().trim());
					}
				
					count++;
				}
*/
			}

		}
		
		
		//collecting results
        FileUtils.writeLines(new File(outputPath), outputResult, true);
		
	}
	
	public static void showAllCatForFood(int foodCat, int region) throws ClientProtocolException, IOException, XPatherException{
		
		 
	    String outputPath = DianPingUtils.getOutputFileName("showAllCatForFood");
	    List<String> outputResult = new ArrayList<String>();
		
		String result = DianPingUtils.httpGetRequest("http://www.dianping.com/search/category/1/10/g" + foodCat + "r" + region);
		
		InputStream is = new ByteArrayInputStream(result.getBytes());

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);

		TagNode node = cleaner.clean(new InputStreamReader(is));
        Object[] info_nodes = node.evaluateXPath("//body/div[3]/div[2]/div/ul[1]/li[2]/ul/li[2]/ul/li[2]/ul//a");
        TagNode info_node = null;
        
        for (int j = 0; j < info_nodes.length; j++) {

			info_node = (TagNode) info_nodes[j];
            System.out.println(info_node.getAttributeByName("href"));
         
			
			List tag_lists = info_node.getAllChildren();
			String rest_length = "0";
			
			for (int i = 0; i < tag_lists.size(); i++) {

				if (tag_lists.get(i) instanceof TagNode) {
					rest_length = ((TagNode) (tag_lists.get(i))).getText().toString().trim();
					rest_length = rest_length.substring(1,rest_length.length()-1);
					
					System.out.println(rest_length);
				}

/*
				if (tag_lists.get(i) instanceof ContentNode) {

					if (((ContentNode) (tag_lists.get(i))).getContent().trim()
							.length() > 0) {
						System.out.println("" + count +" "+((ContentNode) (tag_lists.get(i)))
								.getContent().trim());
					}
					
					count++;
				}
*/
			}
			
			   outputResult.add(info_node.getAttributeByName("href") + "," + rest_length);
		}
		
		
		//collecting results
        FileUtils.writeLines(new File(outputPath), outputResult, true);
	}
	
	public static void check(){
		HashMap<Integer, String> testMap = new HashMap();
	}

}
