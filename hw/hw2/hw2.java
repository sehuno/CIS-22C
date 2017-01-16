public static void extraQueueTester()
{
	LinkedQueue<Double> doubQueue = new LinkedQueue<>();
	
	doubQueue.enqueue(new Double(1.2));
	doubQueue.enqueue(new Double(8.9));
	System.out.println("\nTesting Double Queue: "+ doubQueue);
	System.out.println("Testing dequeuing Double Queue: " + 
				doubQueue.dequeue());
	System.out.println("Testing dequeuing Double Queue: " + 
				doubQueue.dequeue());
	System.out.println("Double Queue's size is " + doubQueue.size());
	System.out.println("Testing enqueuing Double Queue after emptying: " +
			doubQueue.enqueue(new Double(3.4)) );
	System.out.println("Double Queue now has: " + doubQueue);
	System.out.println("Double Queue's size is " + doubQueue.size());
} // end extraQueueTester