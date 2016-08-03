package com.nevelex.clearer;

import com.nevelex.threadSafeContainer.ThreadSafeContainer;

public class ClearQueue implements Runnable {
	
	protected ThreadSafeContainer threadSafeContainer;
	
	public ThreadSafeContainer getThreadSafeContainer() {
		return threadSafeContainer;
	}

	public void setThreadSafeContainer(ThreadSafeContainer threadSafeContainer) {
		this.threadSafeContainer = threadSafeContainer;
	}

	public ClearQueue(ThreadSafeContainer threadSafeContainer) {
		// TODO Auto-generated constructor stub
		
		this.threadSafeContainer=threadSafeContainer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			while(true)
			{
				//Running BlockQueue clear method every 2 seconds
				Thread.sleep(2000);
				threadSafeContainer.clear();
				
				
			}
			
					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted Exception Encountered");
		}
		
		
		
	}

}
