package com.nevelex.threadSafeContainer;

import java.util.LinkedList;
import java.util.List;

import com.nevelex.MyBlockingQueueMain;
import com.nevelex.exception.*;

public class ThreadSafeContainer{
	
	//List to simulate blocking queue
	private List<Integer> queue;
	
	public List<Integer> getQueue() {
		return queue;
	}



	public void setQueue(List<Integer> queue) {
		this.queue = queue;
	}
	
	
	private int  capacity;

		
	public int getCapacity() {
		return capacity;
	}



	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}



	public ThreadSafeContainer(int capacity) {
		// TODO Auto-generated constructor stub
	
		this.queue = new LinkedList<Integer>();
		this.capacity=capacity;
		
	}



	public Integer remove()throws InterruptedException{
		// TODO Auto-generated method stub
		try{
			
			if(MyBlockingQueueMain.shutDown_boolean){				
					throw new ShutdownException(Thread.currentThread());
				}
				
			}
			
				
			catch(ShutdownException e)
			{	
				if((Thread.currentThread().getState()== Thread.State.BLOCKED)){
				this.queue.notifyAll();
				Thread.currentThread().interrupt();
				System.out.println("ShutdownException encountered by: "+Thread.currentThread().getName());
				}
				
				else{
					
					Thread.currentThread().interrupt();
					System.out.println("ShutdownException encountered by: "+Thread.currentThread().getName());
					}
				
			}
		
		
		
		
		synchronized(this.queue)
		{
		
		while(this.queue.isEmpty())
		{
			System.out.println(Thread.currentThread().getName()+" waiting as queue is empty");
			this.queue.wait();
			
		}
		
		System.out.println(Thread.currentThread().getName()+" out of waiting for removing");
		this.queue.notifyAll();
		
		return this.queue.remove(0);
		}
		
		
	}

	
	
	public int clear() throws InterruptedException{
		// TODO Auto-generated method stub
		synchronized (this.queue) {
			while(this.queue.isEmpty())
			{
				System.out.println(Thread.currentThread().getName()+" waiting as queue is empty");
				this.queue.wait();
				
			}
			System.out.println("Clear thread got Access, emptying queue");
			this.queue.removeAll(queue);
			this.queue.notifyAll();
			return this.queue.size();
		}
		
		
	}

	public void shutDown()  {
		// TODO Auto-generated method stub
		
			MyBlockingQueueMain.shutDown_boolean=true;
		
	}
	
	public int getSize()
	{
		return this.queue.size();
	}
	
	
	public boolean add(Integer arg0) throws InterruptedException{
		// TODO Auto-generated method stub
		
		try{
			
			if(MyBlockingQueueMain.shutDown_boolean){				
					throw new ShutdownException(Thread.currentThread());
				}
				
			}
			
				
			catch(ShutdownException e)
			{	
				if(Thread.currentThread().getState()== Thread.State.BLOCKED){
				this.queue.notifyAll();
				Thread.currentThread().interrupt();
				System.out.println("ShutdownException encountered by: "+Thread.currentThread().getName());
				}
				
				else{
					
					
					System.out.println("ShutdownException encountered by: "+Thread.currentThread().getName());
					
				}
				
			}
		synchronized(this.queue){
		
		
		while(this.queue.size()==this.capacity)
		{
			System.out.println(Thread.currentThread().getName()+" waiting as queue is full");	
			
			this.queue.wait();
			
		}
		
		if(this.queue.size()<this.capacity)
		{
			System.out.println(Thread.currentThread().getName()+"adding item: "+arg0);
			this.queue.add(arg0);
			this.queue.notifyAll();
		}
		
		
		return true;
		}
	}

	
}