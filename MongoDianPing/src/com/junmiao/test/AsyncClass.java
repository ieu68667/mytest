package com.junmiao.test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class AsyncClass {

    public static void main(String[] args) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(3000)
            .setConnectTimeout(3000).build();
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
        long startTime;
        httpclient.start();
        try {
        	
        	HttpGet httpget = new HttpGet("http://www.dianping.com/search/category/1/10/g0r0");
    		httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0");
    		
    		ArrayList<HttpGet> httpGets = new ArrayList<HttpGet>();
    		
    		for(int i=0; i<100; i++){
    			httpGets.add(httpget);
    		}
    		
            final HttpGet[] requests = new HttpGet[] {
                    httpget,
                    httpget,
                    httpget
            };
            
            startTime = System.currentTimeMillis();
            System.out.println(" -------    start function call ---------  ");
          
            
            final CountDownLatch latch = new CountDownLatch(httpGets.size());
            for (final HttpGet request: httpGets) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {

                    public void completed(final HttpResponse response) {
                    	System.out.println(latch.getCount());
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    }

                    public void failed(final Exception ex) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    public void cancelled() {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + " cancelled");
                    }

                });
            }
            latch.await();
            System.out.println("Shutting down");
            
        } finally {
            httpclient.close();
        }
        
    	long endTime = System.currentTimeMillis();
		long takeTime = endTime - startTime;
		double takeTimeSec = takeTime / 1000;
		
		
		System.out.println(" ---------------   End Function Time    Take Time " + takeTimeSec + "  -------------------------  ");
	
        System.out.println("Done");
    }

}
