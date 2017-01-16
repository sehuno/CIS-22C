/**
   A class that implements the ADT SORTED circular list by using a chain of
   linked nodes that has a head reference.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/

/*
	CIS 22C 
	HW #3

	Name of Program: "SortedCircularLList"
	Name of Programmer: Sehun Eric Oh
	Date: 1/21/16
	OS: Mac OS X El Capitan
	Compiler: javac 
	Description:	SortedCircularList of BoardMessages

	*Comments before methods, descriptions of variable names and what the represent
*/

import java.util.*;
import java.io.*;

public class SortedCircularLList <T extends Comparable<T>> implements ListInterface<T>
{
	private Node firstNode;            // Reference to first node of chain
	private int  numberOfEntries;

	public SortedCircularLList ()
	{
		initializeDataFields();
	} // end default constructor

	public void clear()
	{
		initializeDataFields();
	} // end clear

	// OutOfMemoryError possible
	public void add(T newEntry) 	      
	{
		Node newNode = new Node(newEntry);
		// System.out.println("adding line: \n" + newEntry.toString());

		if (isEmpty())
		{
			//update firstNode if so
			firstNode = newNode;
			//make firstNode refer to ITSELF
			firstNode.setNextNode(firstNode);
		}
		else                              
		// Add to non-empty list in sorted position:
		{
			Node prevNode = findPreviousNode(newEntry);// MUST CALL THIS
			//  TEST IF THE newEntry <= firstNode's data in the blank below:
			if(newEntry.compareTo(firstNode.getData()) <= 0) // goes before first?
				firstNode = newNode; //update firstNode if so
			Node nextNode = prevNode.getNextNode();// NEW for SortedCircularLList****
			prevNode.setNextNode(newNode); // Make last node reference new node
			newNode.setNextNode(nextNode); // UPDATED for SortedCircularLList****
		} // end if
		numberOfEntries++;
		// System.out.println("add successful");
	}  // end add

    public boolean add(int newPosition, T newEntry) // ignores newPosition
	{
        add(newEntry);
        return true;
    } // end add

	public boolean remove(T anEntry)// ***************YOU WRITE**************
	{
		// THIS MUST DIFFER FROM THE CircularList Exercise:
		// Call findPreviousNode(T), assigning the return value to a local Node 
		Node prevNode = findPreviousNode(anEntry);
		// If it return null, return false
		if(prevNode == null)
			return false;
		// Otherwise, check if the Node after the previous is equal to the
		else 
		{
			//    parameter USING compareTo, and if it is, 
			Node prevNext = prevNode.getNextNode();
			if(prevNext.getData().compareTo(anEntry) == 0)
			{
				if(numberOfEntries == 1)
					firstNode = null;
				else {
					if(prevNext == firstNode)
						firstNode = prevNext.getNextNode();
					prevNode.setNextNode(prevNext.getNextNode());
				}
				numberOfEntries--;
				return true;
			}
			else
				return false;
		}
	}

	public T remove(int givenPosition)
	{
      T result = null;                           // Return value

      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
         assert !isEmpty();

         if (givenPosition == 1)                 // Case 1: Remove first entry
         {
            result = firstNode.getData();        // Save entry to be removed
            firstNode = firstNode.getNextNode(); // Remove entry
         }
         else                                    // Case 2: Not first entry
         {
            Node nodeBefore = getNodeAt(givenPosition - 1);
            Node nodeToRemove = nodeBefore.getNextNode();
            result = nodeToRemove.getData();     // Save entry to be removed
            Node nodeAfter = nodeToRemove.getNextNode();
            nodeBefore.setNextNode(nodeAfter);   // Remove entry
         } // end if

         numberOfEntries--;                      // Update count
         return result;                          // Return removed entry
      }
      else
          return null;
	} // end remove


   public T getEntry(int givenPosition)
   {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
		{
			assert !isEmpty();
         return getNodeAt(givenPosition).getData();
     	}
      else
          return null;
   } // end getEntry


	public boolean contains(T anEntry)
	{
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null))
		{
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while

		return found;
	} // end contains

   public int getLength()
   {
      return numberOfEntries;
   } // end getLength


   // Initializes the class's data fields to indicate an empty list.
   private void initializeDataFields()
   {
		firstNode = null;
		numberOfEntries = 0;
   } // end initializeDataFields

   // Returns a reference to the node at a given position.
   // Precondition: The chain is not empty;
   //               1 <= givenPosition <= numberOfEntries.
	private Node getNodeAt(int givenPosition)
	{
		assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= numberOfEntries);
		Node currentNode = firstNode;

      // Traverse the chain to locate the desired node
      // (skipped if givenPosition is 1)
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();

		assert currentNode != null;

		return currentNode;
	} // end getNodeAt

   /** Retrieves the Node previous to where the parameter is or should be
       @param anEntry  The data to find or determine where to insert
       @return  A reference to the Node previous to where the data is or
                is to be inserted
       @returns null if the list is empty */
	private Node findPreviousNode(T anEntry)
	{	
		// If empty, return null, otherwise ...
		if(numberOfEntries == 0)
			return null;
		else
		{
		// 	// Check if the parameter < firstNode's data and if it is
			if(anEntry.compareTo(firstNode.getData()) <= 0)
				//     return the last node in the list (can you figure out how easily?)
				return getNodeAt(numberOfEntries);
			else
			{
				// 	If it isn't < the first node, use a loop to find the node where
				//     the parameter < that node's data (but KEEP TRACK OF THE PREVIOUS)
				//     and make sure you END the loop at the right place (NOT at null,
				//     remember, this is a CIRCULAR list)
				Node prevNode = firstNode;
				Node currNode = firstNode.getNextNode();
				if(currNode	 == prevNode)
					return prevNode;
				else
				{
					while(currNode.getData().compareTo(anEntry) < 0) 
					{
						if(currNode.getNextNode() == firstNode) 
							return currNode;
						else {
							prevNode = currNode;
							currNode = currNode.getNextNode();
						}
						
					}
					// After the loop, return the previous
					return prevNode;
				}
			}
		}
	}

   public boolean isEmpty()
   {
        return numberOfEntries==0;

   } // end isEmpty

   public void display()
   {
	   if(firstNode == null)  // ADDED FOR EXERCISE 4.1
		   return; // ADDED FOR EXERCISE 4.1
	   
        Node currNode;

        currNode = firstNode;
        do					 // CHANGED FOR EXERCISE 4.1
        {
            System.out.println(currNode.getData());
            currNode = currNode.getNextNode();
        }while( currNode != firstNode ); 	 // CHANGED FOR EXERCISE 4.1
    } // end display
	
	public Iterator<T> getIterator()
	{ return new CircularIterator(); }// FOR HW 3

	public static Scanner userScanner = new Scanner(System.in);

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("Can't open input file\n");
   	    return null; // array of 0 elements
    	} // end catch
    	return scanner;
	}

	// pause method (pauses for specified milliseconds), 
	//     CALL INSIDE THE DISPLAY MESSAGES LOOP:
	public static void pause(long sleepMs)
	{
		try {
        	Thread.sleep(sleepMs); // assume MS is defined in milliseconds
    	} catch (InterruptedException e) {
        // We've been interrupted: no more messages.
        return;
    	}
	}

	// Read in the files and makes a SortedCircularLList of BoardMessages 
	public static void readFileMakeList(SortedCircularLList<BoardMessage> list)
	{
		BoardMessage newBM; 
		int i;
		String line;
		int seq, numLines;
		while(userScanner.hasNextLine())
		{
			seq = userScanner.nextInt();
			numLines = userScanner.nextInt();
			userScanner.nextLine();
			newBM = new BoardMessage(seq);
			for(i=0; i < numLines; i++)
			{
				line = userScanner.nextLine();
				newBM.setOneLine(line);
			}
			list.add(newBM);
			
			// USING THE TOKEN PARSE METHOD
			// line = userScanner.nextLine();
			// String[] tokens = line.split(" ");
			// newBM = new BoardMessage(Integer.parseInt(tokens[0]));
			// for(i=0; i < Integer.parseInt(tokens[1]); i++)
			// {
			// 	line = userScanner.nextLine();
			// 	newBM.setOneLine(line);
			// }
			// list.add(newBM);

		}
	}

	// Takes in the SortedCircularLList and a BoardMessage to test the getEntry and remove methods and will print
	//	appropriate messages to inform whether or not the remove was successful or not. 
	public static void displayAppropriateMessage(SortedCircularLList<BoardMessage> list, BoardMessage bm)
	{
		System.out.println("===== Test for getEntry and remove functions of SortedCircularLList =====\n");
		if(bm.equals(list.getEntry(1)))
			System.out.println("BoardMessage that we WISH TO DELETE is one with the SAME SEQUENCE NUMBER as the HEAD PTR\n");
		else
			System.out.println("BoarMessage that we WISH TO DELETE has the sequence number of " + bm.getSequenceNum());
		System.out.println("\nREMOVING THE BOARDMESSAGE RETURNED THE BOOLEAN VALUE: " + list.remove(bm));
		System.out.println("\nThe list now is as follows: \n");
		list.display();
		System.out.println("===== Test Finished =====\n\n");
	}

	// Takes in the SortedCircularLList and will print each board message with 2000 ms in between each print until 40000ms 
	//	have been reached.
	public static void testTimeAndLoop(SortedCircularLList<BoardMessage> list)
	{	
		Iterator iter = list.getIterator();
		System.out.println("****Starting displaying Sorted Circular List for 40000 ms.****\n");
		long startTime = System.nanoTime(); //fetch starting time
		while(false||(double)(System.nanoTime()-startTime)/1000000.0 < 40000)
		{
		    System.out.println(iter.next());
		    pause(2000);
		}
		System.out.println("\nEnd of circular display\n");
	}

	public static void main(String [] args)
	{
		userScanner = openInputFile();
		System.out.println("Checking if list read in OK:\n");
		SortedCircularLList<BoardMessage> list = new SortedCircularLList<>();
		readFileMakeList(list);
		list.display();
		testTimeAndLoop(list);

		int seqNumDNE = list.getEntry(1).getSequenceNum() + list.getEntry(list.getLength()).getSequenceNum();
		BoardMessage toRemove = list.getEntry(1);
		displayAppropriateMessage(list, toRemove);

		BoardMessage toRemove2 = new BoardMessage(seqNumDNE);
		displayAppropriateMessage(list, toRemove2);
	}
	

	
	// INNER CLASS FOR Iterator
	private class CircularIterator implements Iterator<T> // FOR HW 3
	{
		private Node currentNode;
		
		public CircularIterator()
		{
			currentNode = firstNode;
		}
		
		@Override // FINISH FOR HW#3************************
		public boolean hasNext() {
			Node nextNode = currentNode.getNextNode();
			if(currentNode == null)
				return false;
			else if(currentNode == firstNode && nextNode == firstNode)
				return false;
			else
			{
				if(nextNode == firstNode)
					return false;
				else
					return true;
			}
		}

		@Override // FINISH FOR HW#3************************
		public T next() {
			T ret = currentNode.getData();
			currentNode = currentNode.getNextNode();
			return ret;
		}

		@Override
		public void remove() {} // do nothing here for HW#3
	}
	
	// INNER CLASS FOR Node--------------------------------------------------------

	private class Node
	{
      private T    data; // Entry in list
      private Node next; // Link to next node

      private Node(T dataPortion)
      {
         data = dataPortion;
         next = null;
      } // end constructor

      private Node(T dataPortion, Node nextNode)
      {
         data = dataPortion;
         next = nextNode;
      } // end constructor

      private T getData()
      {
         return data;
      } // end getData

      private void setData(T newData)
      {
         data = newData;
      } // end setData

      private Node getNextNode()
      {
         return next;
      } // end getNextNode

      private void setNextNode(Node nextNode)
      {
         next = nextNode;
      } // end setNextNode
	} // end Node
} // end LList

/* 
OUTPUT:
Enter the input filename: HW3 Input.txt
Checking if list read in OK:

#5
Keep yourself alert, active, and educated. Beat to your own drum.

#14
Do one thing each day that is just for you.

#28
Life is fun. It's all up to the person. Be satisfied.
You don't have to be "happy" all the time, you need to be satisfied.

#44
We all remember how as children, when we were having fun, we often forgot to eat or sleep.
I believe that we can keep that attitude as adults, too.
It's best not to tire the body with too many rules such as lunchtime and bedtime.

#51
Don't be crazy about amassing material things.
Remember: you don't know when your number is up, and
you can't take it with you to the next place.

#68
I think [people] have to be curious.
They have to be interested in life outside their little aches and pains.
They have to be excited about seeing new things, meeting new people, watching a new play--
just passionate about life.

#74
Always listen to the other person.
You'll learn something. Try to sit back, because you will learn a lot more
listening to others than telling them what you know.

#75
You have to love what you do. if you find a job you love,
you will never have to work a day in your life.

#78
I try to take the time to look at and appreciate the smaller things that make this life
beautiful. When I do that, time slows.

#89
Laughter keeps you healthy.
You can survive by seeing the humor in everything.
Thumb your nose at sadness; turn the tables on tragedy.
You can't laugh and be angry, you can't laugh and feel sad,
you can't laugh and feel envious.

****Starting displaying Sorted Circular List for 40000 ms.****

#5
Keep yourself alert, active, and educated. Beat to your own drum.

#14
Do one thing each day that is just for you.

#28
Life is fun. It's all up to the person. Be satisfied.
You don't have to be "happy" all the time, you need to be satisfied.

#44
We all remember how as children, when we were having fun, we often forgot to eat or sleep.
I believe that we can keep that attitude as adults, too.
It's best not to tire the body with too many rules such as lunchtime and bedtime.

#51
Don't be crazy about amassing material things.
Remember: you don't know when your number is up, and
you can't take it with you to the next place.

#68
I think [people] have to be curious.
They have to be interested in life outside their little aches and pains.
They have to be excited about seeing new things, meeting new people, watching a new play--
just passionate about life.

#74
Always listen to the other person.
You'll learn something. Try to sit back, because you will learn a lot more
listening to others than telling them what you know.

#75
You have to love what you do. if you find a job you love,
you will never have to work a day in your life.

#78
I try to take the time to look at and appreciate the smaller things that make this life
beautiful. When I do that, time slows.

#89
Laughter keeps you healthy.
You can survive by seeing the humor in everything.
Thumb your nose at sadness; turn the tables on tragedy.
You can't laugh and be angry, you can't laugh and feel sad,
you can't laugh and feel envious.

#5
Keep yourself alert, active, and educated. Beat to your own drum.

#14
Do one thing each day that is just for you.

#28
Life is fun. It's all up to the person. Be satisfied.
You don't have to be "happy" all the time, you need to be satisfied.

#44
We all remember how as children, when we were having fun, we often forgot to eat or sleep.
I believe that we can keep that attitude as adults, too.
It's best not to tire the body with too many rules such as lunchtime and bedtime.

#51
Don't be crazy about amassing material things.
Remember: you don't know when your number is up, and
you can't take it with you to the next place.

#68
I think [people] have to be curious.
They have to be interested in life outside their little aches and pains.
They have to be excited about seeing new things, meeting new people, watching a new play--
just passionate about life.

#74
Always listen to the other person.
You'll learn something. Try to sit back, because you will learn a lot more
listening to others than telling them what you know.

#75
You have to love what you do. if you find a job you love,
you will never have to work a day in your life.

#78
I try to take the time to look at and appreciate the smaller things that make this life
beautiful. When I do that, time slows.

#89
Laughter keeps you healthy.
You can survive by seeing the humor in everything.
Thumb your nose at sadness; turn the tables on tragedy.
You can't laugh and be angry, you can't laugh and feel sad,
you can't laugh and feel envious.


End of circular display

===== Test for getEntry and remove functions of SortedCircularLList =====

BoardMessage that we WISH TO DELETE is one with the SAME SEQUENCE NUMBER as the HEAD PTR


REMOVING THE BOARDMESSAGE RETURNED THE BOOLEAN VALUE: true

The list now is as follows:

#14
Do one thing each day that is just for you.

#28
Life is fun. It's all up to the person. Be satisfied.
You don't have to be "happy" all the time, you need to be satisfied.

#44
We all remember how as children, when we were having fun, we often forgot to eat or sleep.
I believe that we can keep that attitude as adults, too.
It's best not to tire the body with too many rules such as lunchtime and bedtime.

#51
Don't be crazy about amassing material things.
Remember: you don't know when your number is up, and
you can't take it with you to the next place.

#68
I think [people] have to be curious.
They have to be interested in life outside their little aches and pains.
They have to be excited about seeing new things, meeting new people, watching a new play--
just passionate about life.

#74
Always listen to the other person.
You'll learn something. Try to sit back, because you will learn a lot more
listening to others than telling them what you know.

#75
You have to love what you do. if you find a job you love,
you will never have to work a day in your life.

#78
I try to take the time to look at and appreciate the smaller things that make this life
beautiful. When I do that, time slows.

#89
Laughter keeps you healthy.
You can survive by seeing the humor in everything.
Thumb your nose at sadness; turn the tables on tragedy.
You can't laugh and be angry, you can't laugh and feel sad,
you can't laugh and feel envious.

===== Test Finished =====


===== Test for getEntry and remove functions of SortedCircularLList =====

BoarMessage that we WISH TO DELETE has the sequence number of 94

REMOVING THE BOARDMESSAGE RETURNED THE BOOLEAN VALUE: false

The list now is as follows:

#14
Do one thing each day that is just for you.

#28
Life is fun. It's all up to the person. Be satisfied.
You don't have to be "happy" all the time, you need to be satisfied.

#44
We all remember how as children, when we were having fun, we often forgot to eat or sleep.
I believe that we can keep that attitude as adults, too.
It's best not to tire the body with too many rules such as lunchtime and bedtime.

#51
Don't be crazy about amassing material things.
Remember: you don't know when your number is up, and
you can't take it with you to the next place.

#68
I think [people] have to be curious.
They have to be interested in life outside their little aches and pains.
They have to be excited about seeing new things, meeting new people, watching a new play--
just passionate about life.

#74
Always listen to the other person.
You'll learn something. Try to sit back, because you will learn a lot more
listening to others than telling them what you know.

#75
You have to love what you do. if you find a job you love,
you will never have to work a day in your life.

#78
I try to take the time to look at and appreciate the smaller things that make this life
beautiful. When I do that, time slows.

#89
Laughter keeps you healthy.
You can survive by seeing the humor in everything.
Thumb your nose at sadness; turn the tables on tragedy.
You can't laugh and be angry, you can't laugh and feel sad,
you can't laugh and feel envious.

===== Test Finished =====
*/
