 // Example of timing simple sort algorithms
public class Exercise6_2
{
    public static int MIN_SIZE = 5000;
    public static int MAX_SIZE = 40000;
    // PUT BUBBLE SORT AND SELECTION SORT HERE
    public static void bubbleSort(Integer [] ary)
    {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        int temp;   //holding variable

        while ( flag )
        {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < ary.length -1;  j++ )
            {
                   if ( ary[ j ] < ary[j+1] )   // change to > for ascending sort
                   {
                           temp = ary[ j ];                //swap elements
                           ary[ j ] = ary[ j+1 ];
                           ary[ j+1 ] = temp;
                          flag = true;              //shows a swap occurred  
                  } 
            } 
        } 
    } 
    public static void selectionSort(Integer [] ary) 
    {
        int i, j, first, temp;  
        for ( i = ary.length - 1; i > 0; i --)  
        {
          first = 0;   //initialize to subscript of first element
          for(j = 1; j <= i; j ++)   //locate smallest element between positions 1 and i.
          {
               if( ary[ j ] < ary[ first ] )         
                 first = j;
          }
          temp = ary[ first ];   //swap smallest found with element in position i.
          ary[ first ] = ary[ i ];
          ary[ i ] = temp; 
        }           
    }

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
            {   // THIS USES AUTO-BOXING, SO YOU NEED AT LEAST Java 6 FOR THIS
                arrayOfInts1[k] = arrayOfInts2[k] = (int)(Math.random()*arraySize);
            }
            startTime = System.nanoTime();		// start timing bubble sort----------
            // sort using a bubble sort
            bubbleSort(arrayOfInts1);
            stopTime = System.nanoTime();		// stop timing ---------------------
            elapsedTime =(double)(stopTime - startTime)/1000000.0;
            System.out.println("\nN: " + arraySize + " Bubble Sort Time: " + elapsedTime + " milliseconds.");
            startTime = System.nanoTime();		// start timing selection sort-------
            // sort using a bubble sort
            selectionSort(arrayOfInts2);
            stopTime = System.nanoTime();		// stop timing ---------------------
            elapsedTime =(double)(stopTime - startTime)/1000000.0;
            System.out.println("\nN: " + arraySize + " Selection Sort Time: " + elapsedTime + " milliseconds.");
        }// end for
    }// end main
} // end class Exercise6_2

/* output:

N: 5000 Bubble Sort Time: 177.286 milliseconds.

N: 5000 Selection Sort Time: 19.816 milliseconds.

N: 10000 Bubble Sort Time: 605.665 milliseconds.

N: 10000 Selection Sort Time: 65.173 milliseconds.

N: 20000 Bubble Sort Time: 1580.948 milliseconds.

N: 20000 Selection Sort Time: 188.989 milliseconds.

N: 40000 Bubble Sort Time: 5758.813 milliseconds.

N: 40000 Selection Sort Time: 879.45 milliseconds.

*/

/* 
answers:

1.  
    - bubble sort ratios for doubling n's:
        3.41, 2.61, 3.64 -> average: 3.21
    - selection sort rations for doubling n's:
        3.28, 2.89, 4.60 -> average: 3.59
2. 
    The results agree with what I expected in that I expected the bubble sort to be more efficient
    because the best case time complexity is better than that of the the selection sort.
3.
    The time complexity for the best case scenario for the bubble sort is O(n) while the best case
    time complexity for selection sort is O(n^2). I expected that the bubble sort would be much more
    efficient than the selection sort.
