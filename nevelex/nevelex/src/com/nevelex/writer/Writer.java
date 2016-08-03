package com.nevelex.writer;

import com.nevelex.threadSafeContainer.*;

public class Writer implements Runnable {

	private ThreadSafeContainer threadSafeContainer;
	
	public Writer(ThreadSafeContainer threadSafeContainer) {
		// TODO Auto-generated constructor stub
		
		this.setThreadSafeContainer(threadSafeContainer);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			//adding elements using thread
			getThreadSafeContainer().add(new Integer(1));
			
			getThreadSafeContainer().add(new Integer(2));
			
			getThreadSafeContainer().add(new Integer(3));
			
			getThreadSafeContainer().add(new Integer(4));
			
			getThreadSafeContainer().add(new Integer(5));
			
			getThreadSafeContainer().add(new Integer(6));
					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
			
		}
		
	}
	public ThreadSafeContainer getThreadSafeContainer() {
		return threadSafeContainer;
	}
	public void setThreadSafeContainer(ThreadSafeContainer threadSafeContainer) {
		this.threadSafeContainer = threadSafeContainer;
	}

}
