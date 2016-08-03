package com.nevelex;

import com.nevelex.clearer.ClearQueue;
import com.nevelex.reader.Reader;
import com.nevelex.threadSafeContainer.ThreadSafeContainer;
import com.nevelex.writer.Writer;

public class MyBlockingQueueMain {
	
	//variable to handle shutdown method
	public static volatile boolean shutDown_boolean=false;
	
	public static void main(String args[]) throws InterruptedException
	{
		// Setting the BlockingQueue Size
		ThreadSafeContainer queue = new ThreadSafeContainer(2); 
		
		//Creating Reader,Writer and Clearer class objects
		Reader reader = new Reader(queue);
		Writer writer = new Writer(queue);
		ClearQueue clearer = new ClearQueue(queue);
		
		
		//Creating and Starting threads
		for(int i=1;i<15;i++){
			
		Thread t =new Thread(reader,"Reader"+i);
		t.setDaemon(true);
		
		t.start();
		}
		
		for(int i=1;i<15;i++){
			
			
			new Thread(writer,"Writer"+i).start();
		}
		
		
		Thread thread =new Thread(clearer,"Clearer Thread");
		thread.setDaemon(true);
		thread.start();
		
		Thread.sleep(100);
		
		//calling shutdown method
		queue.shutDown();
		
		System.out.println("Shutdown Method Called");
		
		
	
	}

}
