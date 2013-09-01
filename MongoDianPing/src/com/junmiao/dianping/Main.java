package com.junmiao.dianping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.junmiao.test.Test;



public class Main {
    private static final int NTHREDS = 500;

    public static void main(String[] args) {
      final Counter counter = new Counter();
      List<Future<Integer>> list = new ArrayList<Future<Integer>>();
      long startTime = System.currentTimeMillis();
      System.out.println(" -------    start function call ---------  ");
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      for (int i = 0; i < 500; i++) {
        Callable<Integer> worker = new  Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
          
        	  
        	  
   	  
        	  try{
        		  Test.parseDianping();
        	  }catch(Exception e){
        		  System.out.println(e.getMessage());
        	  }
        	  
        	  
        	  int number = counter.increment();
            System.out.println(number);
            return number ;
          }
          
        };
        Future<Integer> submit= executor.submit(worker);
        list.add(submit);

      }
      
      
      // This will make the executor accept no new threads
      // and finish all existing threads in the queue
      executor.shutdown();
      // Wait until all threads are finish
      while (!executor.isTerminated()) {
      }
      
  		long endTime = System.currentTimeMillis();
		long takeTime = endTime - startTime;
		double takeTimeSec = takeTime / 1000;
		
		
		System.out.println(" ---------------   End Function Time    Take Time " + takeTimeSec + "  -------------------------  ");
		
      
      Set<Integer> set = new HashSet<Integer>();
      for (Future<Integer> future : list) {
        try {
          set.add(future.get());
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
      if (list.size()!=set.size()){
        throw new RuntimeException("Double-entries!!!"); 
      }

    }


} 
