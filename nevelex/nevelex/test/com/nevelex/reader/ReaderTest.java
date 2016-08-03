package com.nevelex.reader;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nevelex.clearer.ClearQueue;
import com.nevelex.exception.ShutdownException;
import com.nevelex.threadSafeContainer.ThreadSafeContainer;
import com.nevelex.writer.Writer;

public class ReaderTest {

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
		
		
		
		
		//threadSafeContainer.shutDown();
		System.out.println("Shutdown Method Called");
	}
	
	
	@Test
	public void readerGetsBlockedOrWaitingWhileQueueIsEmpty() throws InterruptedException {
		
		Reader blockReader = new Reader(threadSafeContainer);
		Thread t =new Thread(blockReader,"EmptyTestReader");
		t.setPriority(8);
		t.setDaemon(true);
		
		t.start();
		Thread.State state=null;
		synchronized(blockReader.getThreadSafeContainer().getQueue())
		{
		blockReader.getThreadSafeContainer().clear();
		blockReader.getThreadSafeContainer().remove();
		
		state = t.getState();
		blockReader.getThreadSafeContainer().getQueue().notifyAll();
		}
		System.out.println("Reader State: "+state);
		assertTrue(Thread.State.BLOCKED==state || Thread.State.WAITING==state);
		
		}
		
	@Test
	public void readerReturnsIntegerWhileQueueIsNotEmpty() throws InterruptedException {
		
		Reader queueReader = new Reader(threadSafeContainer);
		Thread t =new Thread(queueReader,"TestReader");
		t.setDaemon(true);
		
		t.start();
		
		Integer num;
		boolean return_type_checker;
		synchronized(queueReader.getThreadSafeContainer().getQueue())
		{
		num = queueReader.getThreadSafeContainer().remove();
		return_type_checker = num instanceof Integer;
		queueReader.getThreadSafeContainer().getQueue().notifyAll();
		}
		assertEquals(true, return_type_checker);
		}
		
	
		
	}


