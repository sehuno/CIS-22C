public class LinkedQueueDummy<T> implements QueueInterface<T>
{
      private Node frontNode; // References node at front of queue
      private Node backNode;  // References node at back of queue
      private int count = 0;

	public LinkedQueueDummy()
	{
		frontNode = backNode = new Node(null); // MY CHANGE!!!
	} // end default constructor

	public boolean enqueue(T newEntry)
	{
		Node newNode = new Node(newEntry);
		//	CROSS OUT WHAT LINES SHOULD BE DELETED HERE:***********************
		// if( count == 0 )
		// 	frontNode = newNode;
		// else
		backNode.setNextNode(newNode);
		backNode = newNode;
		++count;
		return true;
	} // end enqueue

	public T peekFront()
	{
		if (isEmpty())
			return null;
		else
            return frontNode.getNextNode().getData();
	} // end getFront

	public T dequeue()
	{
	   T front = peekFront();
		if( count > 0 )
		{
			Node nodeToRemove = frontNode.getNextNode();//*************
			//****DON'T CHANGE frontNode, only its next reference below!	
			frontNode.setNextNode(nodeToRemove.getNextNode());
			if( count == 1 )
				backNode = frontNode;//************************
			--count;
		}
        return front;
	} // end dequeue
