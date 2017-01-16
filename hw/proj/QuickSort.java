// QUICK SORT
 /** Sorts an array into ascending order. Uses quick sort with
 median-of-three pivot selection for arrays of at least
 MIN_SIZE entries, and uses insertion sort for other arrays. 
 MIN_SIZE is a declared static variable in this class with 10 */
 public static <T extends Comparable<? super T>>
 void quickSort(T[] array, int n)
 {
     quickSort(array, 0, n - 1);
 } // end quickSort


 public static <T extends Comparable<? super T>>
   void quickSort(T[] a, int first, int last)
 {
     if (last - first + 1 < MIN_SIZE)
     {
         insertionSort(a, first, last);
     }
     else
     {
     // Create the partition: Smaller | Pivot | Larger
         int pivotIndex = partition(a, first, last);
         // Sort subarrays Smaller and Larger
         quickSort(a, first, pivotIndex - 1);
         quickSort(a, pivotIndex + 1, last);
     } // end if
 } // end quickSort


 //  Partitions an array as part of quick sort into two subarrays
 //  called Smaller and Larger that are separated by a single
 //  entry called the pivot.
 //  Entries in Smaller are <= pivot and appear before the
 //  pivot in the array.
 //  Entries in Larger are >= pivot and appear after the
 //  pivot in the array.
 //  Parameters:
 //     a      An array of Comparable objects.
 //     first  The integer index of the first array entry;
 //            first >= 0 and < a.length.
 //     last   The integer index of the last array entry;
 //            last - first >= 3; last < a.length.
 //   Returns the index of the pivot.
 private static <T extends Comparable<? super T>>
   int partition(T[] a, int first, int last)
 {
     int mid = first + (last - first) / 2;
     sortFirstMiddleLast(a, first, mid, last);
     // Assertion: The pivot is a[mid]; a[first] <= pivot and 
     // a[last] >= pivot, so do not compare these two array entries
     // with pivot.
     // Move pivot to next-to-last position in array
     swap(a, mid, last - 1);
     int pivotIndex = last - 1;
     T pivotValue = a[pivotIndex];
     // Determine subarrays Smaller = a[first..endSmaller]
     // and                 Larger  = a[endSmaller+1..last-1]
     // such that entries in Smaller are <= pivotValue and
     // entries in Larger are >= pivotValue; initially, these subarrays are empty
     int indexFromLeft = first + 1; 
     int indexFromRight = last - 2;
     boolean done = false;
     while (!done)
     {
     // Starting at beginning of array, leave entries that are < pivotValue;
     // locate first entry that is >= pivotValue; you will find one,
     // since last entry is >= pivot
         while (a[indexFromLeft].compareTo(pivotValue) < 0)
             indexFromLeft++;
     // Starting at end of array, leave entries that are > pivot;
     // locate first entry that is <= pivot; you will find one, 
     // since first entry is <= pivot
         while (a[indexFromRight].compareTo(pivotValue) > 0)
             indexFromRight--;
         assert a[indexFromLeft].compareTo(pivotValue) >= 0 &&
             a[indexFromRight].compareTo(pivotValue) <= 0;
   
         if (indexFromLeft < indexFromRight)
         {
             swap(a, indexFromLeft, indexFromRight);
             indexFromLeft++;
             indexFromRight--;
         }
         else 
             done = true;
     } // end while
     // Place pivotValue between the subarrays Smaller and Larger
     swap(a, pivotIndex, indexFromLeft);
     pivotIndex = indexFromLeft;
     // Assertion:
     //   Smaller = a[first..pivotIndex-1]
     //   Pivot = a[pivotIndex]
     //   Larger = a[pivotIndex+1..last]
     return pivotIndex; 
 } // end partition



 //  Sorts the first, middle, and last entries of an array into ascending order.
 //  Parameters:
 //     a      An array of Comparable objects.
 //     first  The integer index of the first array entry;
 //            first >= 0 and < a.length.
 //     mid    The integer index of the middle array entry.
 //     last   The integer index of the last array entry;
 //            last - first >= 2, last < a.length.
 private static <T extends Comparable<? super T>>
   void sortFirstMiddleLast(T[] a, int first, int mid, int last)
 {
     order(a, first, mid); // Make a[first] <= a[mid]
     order(a, mid, last);  // Make a[mid] <= a[last]
     order(a, first, mid); // Make a[first] <= a[mid]
 } // end sortFirstMiddleLast
 // Orders two given array elements into ascending order
 // so that a[i] <= a[j].


 private static <T extends Comparable<? super T>>
   void order(T[] a, int i, int j)
 {
     if (a[i].compareTo(a[j]) > 0)
         swap(a, i, j);
 } // end order

 
 // Swaps the array entries array[i] and array[j]. 
 private static void swap(Object[] array, int i, int j)
 {
     Object temp = array[i];
     array[i] = array[j];
     array[j] = temp; 
 } // end swap
