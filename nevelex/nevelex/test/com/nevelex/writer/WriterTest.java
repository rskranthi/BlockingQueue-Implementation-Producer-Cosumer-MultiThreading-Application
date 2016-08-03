package com.nevelex.writer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nevelex.clearer.ClearQueue;
import com.nevelex.reader.Reader;
import com.nevelex.threadSafeContainer.ThreadSafeContainer;

public class WriterTest {

	ThreadSafeContainer threadSafeContainer;
	Reader reader;
	Writer writer;
	ClearQueue clearer;
	Thread t,thread;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
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
		
	
	}
	
	
	
	
	@Test
	public void writerAddsIntegerWhenQueueIsNotFull() throws InterruptedException {
		
		Writer writer = new Writer(threadSafeContainer);
		Thread t =new Thread(writer,"TestWriterForAddingTest");
		 // Writer set to high priority to ensure it gets access
		
		
		t.start();
		
		int size;
		synchronized(writer.getThreadSafeContainer().getQueue())
		{
		while(writer.getThreadSafeContainer().getSize()!=writer.getThreadSafeContainer().getCapacity())
		writer.getThreadSafeContainer().add(new Integer(5));
		size=writer.getThreadSafeContainer().getSize();
		writer.getThreadSafeContainer().getQueue().notifyAll();
		}
		
		assertEquals(size,writer.getThreadSafeContainer().getCapacity());
		}
		
	
		

}
