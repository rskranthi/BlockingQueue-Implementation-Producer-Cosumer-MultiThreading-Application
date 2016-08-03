package com.nevelex.reader;


import com.nevelex.threadSafeContainer.*;

public class Reader implements Runnable {
	
	private ThreadSafeContainer threadSafeContainer;

	
	public Reader(ThreadSafeContainer queue) {
		// TODO Auto-generated constructor stub
		
		this.setThreadSafeContainer(queue);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			
			
			while(true){
			System.out.println(Thread.currentThread().getName()+" Removing "+getThreadSafeContainer().remove() + " from Queue");
			
			}
			
				
			
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
