CIS 22C-01Y TTh 1:30-3:20 PM
Class Notes for 02-02-2016

MIDTERM will be on THURSDAY Feb. 11 (whole class time)!
You may bring 4 sheets of your own notes (front and back), pen and pencil w/ eraser (NOTHING ELSE, NO TEXTBOOK, NO EBOOK)!

Review for the midterm will be shown in Catalyst soon!

==========================================
ANSWERS TO IN-CLASS EXERCISE OF 01-28-16:

DON'T DO:  because it may not work and even if it did, is inefficient! (Why?)
public int compareTo(Music o) {

        if (composer.equals(o.composer)) {//equalsIgnoreCase

            return title.compareToIgnoreCase(o.title);

        } else {
            
            return composer.compareToIgnoreCase(o.composer);
        }

    }
The above is inefficient because all the String methods that compare (equals, compareTo, etc.)

MY ANSWER:

public int compareTo(Music param) 
{
	int result = composer.compareToIgnoreCase(param.composer);
	if( result == 0 )
		result = title.compareToIgnoreCase(param.title);

	return result;
}

NOTE: DON'T RETURN -1 NOR 1 if you could use a given compareTo or compareToIgnoreCase method! 

FILL IN THE BLANK BELOW so it calls compareTo in a "normal" Linked List (LList.java) instance method: (CHANGED LList.java so T is <T extends Comparable<T>>)
 import java.util.*;

public class LList<T extends Comparable<T>> implements ListInterface<T>
{
// rest is the same
     public T getSmallest()
     {
          if( firstNode == null )
              return null;
          T smallestT = firstNode.getData();
 		Node currNode = firstNode.getNextNode();
		while( currNode != null )
		{
			if( currNode.getData().compareTo(smallestT) < 0 )
				smallestT = currNode.getData();
			currNode = currNode.getNextNode();
		}
          return smallestT;
   }
}
If time, change Lab Exercise 1.3 so main will call getSmallest (display it).

// only part of main:
	      LList<Music>  musicList = new LList<Music>(); 
	      Music tempMusic;

		// rest of main is the same as in 1.3

			tempMusic = musicList.getSmallest();
			if( tempMusic != null )
				System.out.println("Smallest in the list is: "+tempMusic);
==========================================
Other inefficient examples from LList.java:

WHY IS THIS INEFFICIENT? (Even though you don't get points off for inefficiency for exercises unless specified, you WOULD in a HW Assignment for this:)

    public boolean remove(T anEntry)
    {
        T temp = null;

        if (this.contains(anEntry)) {

            for (int Index=0; Index< this.getLength(); Index++) 
				{
                if (this.getEntry(Index) != null) {

                    temp = this.getEntry(Index);

                    if (temp.equals(anEntry)) {

                        remove(Index);
                        return true;
                    }
                }
            }
        }
        return false;
    }

SEE ANSWERS TO LAB EXERCISE 4.2 FOR A MORE EFFICIENT WAY!

==========================================
ANSWERS TO LAB EXERCISE 6.2:

public class Lesson6Exercises {
	// Example of timing simple sort algorithms

	//YOU PUT THE BUBBLE SORT AND SELECTION SORT HERE!
	public static <E extends Comparable<E>> void bubbleSort(E[] array)
	{
	for (int k = 0; k < array.length ; k++)
		if (!floatLargestToTop(array, array.length -1-k))
	return;
	}

	// returns true if a modification was made to the array

	public static <E extends Comparable<E>> boolean floatLargestToTop(E data[], int top)
	{
		boolean changed = false;

		// notice we stop at length -2 because of expr. k+1 in loop
		for (int k =0; k < top; k++)
		if (data[k+1].compareTo(data[k]) < 0)
		{
			E temp = data[k];
			data[k] = data[k+1];
			data[k+1] = temp;
			changed = true;
		}
		return changed;
	}
	
	public static <E extends Comparable<E>> void selectionSort(E[] list)
	{
	    for(int i=0; i<list.length -1; i++)
	    {
	        int iSmallest = i;

	        for(int j=i+1; j<list.length; j++)
	        {
	            if(list[iSmallest].compareTo((list[j])) > 0  )
	            {
	                iSmallest = j;
	            }
	        }
	        E iSwap = list[iSmallest];
	        list[iSmallest] = list[i];
	        list[i] = iSwap;

	    }
	}
	// Example of timing simple sort algorithms
	public static int MIN_SIZE = 5000;
	public static int MAX_SIZE = 40000;

	// --------------- main ---------------

	public static void main( String [] args )
	{
		int k, 		// index for array
			arraySize; // size of array (changes)
		long startTime, stopTime; // for timing
		double elapsedTime = 0; // for timing
		Integer [] arrayOfInts1 = null;  // for dynamic array
		Integer [] arrayOfInts2 = null;  // copy of dynamic array elements

		for(arraySize = MIN_SIZE; arraySize <= MAX_SIZE; arraySize*=2)
		{
			// allocate array and stuff will values
			arrayOfInts1 = new Integer[arraySize];
			arrayOfInts2 = new Integer[arraySize];
			for (k = 0; k < arraySize; k++)
			{
				arrayOfInts1[k] = arrayOfInts2[k] = 
					(int)(Math.random()*arraySize);
			}
			startTime = System.nanoTime();// start timing bubble sort

			// sort using a bubble sort
			bubbleSort(arrayOfInts1);

			stopTime = System.nanoTime();		// stop timing ---------------------
			elapsedTime =(double)(stopTime - startTime)/1000000.0;

			System.out.println("\nN: " + arraySize + " Bubble Sort Time: "
						+ elapsedTime + " milliseconds.");

			startTime = System.nanoTime();		// start timing selection sort-------

			// sort using a bubble sort
			selectionSort(arrayOfInts2);

			stopTime = System.nanoTime();		// stop timing ---------------------

			elapsedTime =(double)(stopTime - startTime)/1000000.0;

			System.out.println("\nN: " + arraySize + " Selection Sort Time: "
						+ elapsedTime + " milliseconds.");
		}// end for

	}// end main
} // end class Exercise6_2

/*

N: 5000 Bubble Sort Time: 115.438267 milliseconds.

N: 5000 Selection Sort Time: 75.544314 milliseconds.

N: 10000 Bubble Sort Time: 341.382351 milliseconds.

N: 10000 Selection Sort Time: 94.749884 milliseconds.

N: 20000 Bubble Sort Time: 1611.785831 milliseconds.

N: 20000 Selection Sort Time: 418.762942 milliseconds.

N: 40000 Bubble Sort Time: 7305.453654 milliseconds.

N: 40000 Selection Sort Time: 1594.48879 milliseconds.

 * */
Note: My timings are slower using the Generic methods and calling compareTo to compare elements, instead of using the methods for an array of ints and comparing using < or >

size
bubble sort
ratio 2x
ratio  4x
selection sort
ratio 2x
ratio  4x
5000
115.4
 
 
75.5
 
 
10000
341.4
2.95
 
94.7
1.25

20000
1611.8
4.7
13.9
418.8
4.4
5.5
40000
7305.5
4.5
21.4
1594.5
3.8
16.8

Answers to questions:
•	What is the ratio of the timings for the different sizes? 
for doubling the sizes of the Bubble sort, the ratio was between 3.0 4.7 (averaging about 4)
for doubling the sizes of the Selection sort, the ratio was between 1.25 to 4.4  (the timing for size 5000 seems to be an anomaly)
for quadrupling the sizes (4 x) of the bubble sort, the ratio was 13.9 to 21.4 (average about 16)
for quadrupling the sizes (4 x) of the selection sort, ignoring the strange timing for 5000, the ratio was 16.8
Comparison of Bubble sort to selection sort: Ratio of Bubble sort to Selection sort is about 3:1 slower!  This is because the Bubble sort has more swaps (DONE INSIDE THE INNER LOOP) than the selection sort (SWAP DONE ONLY IN OUTER LOOP) (see highlighted above)
•	Do the timing results agree with what you expected? 
YES! Not only as explained above (more swaps), but after doubling
•	What did you expect and why?  Use the Big-Oh of the sorting algorithms in your analysis.
Explained above (more swaps) when compare Bubble to Selection and ...
(posted on Catalyst)
==========================================
Lesson 7: Trees

	
==========================================
SEE PROG. HW#3 Clarifications

SortedCircularLList's findPreviousNode(T) will return the Node reference previous to where a Node's data in the list is >= T (may NOT be equal, will be used for adding and removing)!  
•	You MUST use compareTo for comparing data (T)!  
•	In remove(T), you MUST check if the next node's data is considered equal (using compareTo) to the T parameter!

NOTE:  findPreviousNode() will return a reference to the last node in the list for 2 possible conditions: it goes before the first, or goes AFTER the last!  Therefore, your remove(T) method MUST check which one it is!

Picture shown on the board to use for today's exercise!

==========================================
IN-CLASS EXERCISE to turn in on paper or upload a picture by 3:50 PM today!

Using the sorted circular list shown on the board, draw a picture of what findPreviousNode should do (showing LOCAL VARIABLES, like prevNode and currNode and the parameter):  findPreviousNode( 7 )  (assume 7 is actually the wrapper Integer object with 7 in it).

==========================================
TO DO THE REST of THIS WEEK:
•	Turn in Lab Exercise 7.1 (due TONIGHT)
•	Work on the exercise due BEFORE CLASS ON THURSDAY
•	Read the REST of Lesson 7 (and the textbook Chapters indicated in Catalyst if you need more examples, in-depth explanation) 
•	Work on HW#3 (due SUNDAY)!!!
•	start studying for the midterm (review sheet will be shown on Catalyst)


