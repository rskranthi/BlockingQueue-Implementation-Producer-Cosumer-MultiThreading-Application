package com.nevelex.clearer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.MockitoAnnotations.*;

import com.nevelex.reader.Reader;
import com.nevelex.threadSafeContainer.ThreadSafeContainer;
import com.nevelex.writer.Writer;


public class ClearQueueTest {
	
	
	ThreadSafeContainer threadSafeContainer;
	Reader reader;
	Writer writer;
	ClearQueue clearer;
	Thread t,thread;
	
	
	@Before
	public void setup() throws InterruptedException
	{
		
		threadSafeContainer = new ThreadSafeContainer(2);
		
		reader = new Reader(threadSafeContainer);
		writer = new Writer(threadSafeContainer);
		clearer = new ClearQueue(threadSafeContainer);
		
		
		for(int i=1;i<4;i++){
			
			
			new Thread(writer,"Writer"+i).start();
		}
		
		
		for(int i=1;i<4;i++){
			
		t =new Thread(reader,"Reader"+i);
		t.setDaemon(true);
		
		t.start();
		}
		
		
		
		thread =new Thread(clearer,"Clearer Thread");
		thread.setDaemon(true);
		
		
		
		
		//threadSafeContainer.shutDown();
		System.out.println("Shutdown Method Called");
	}

	@Test
	public void clearShouldReturnZeroAsQueueSize() throws InterruptedException {
		thread.start();
		int number=-1;
		
		synchronized(clearer.getThreadSafeContainer().getQueue())
		{
		number = clearer.threadSafeContainer.clear(); 
		clearer.getThreadSafeContainer().getQueue().notifyAll();
		assertEquals(0,number);
		}
		
		
	}

}
