// SHELL SORT (all these methods are inside a class)
public static <T extends Comparable<? super T>>
   void shellSort(T[] a, int n)
{
   shellSort(a, 0, n - 1);
} // end shellSort
/** Sorts equally spaced elements of an array into
   ascending order.
   @param a      An array of Comparable objects.
   @param first  An integer >= 0 that is the index of the first
   array element to consider.
   @param last   An integer >= first and < a.length that is the
   index of the last array element to consider.
   @param space  The difference between the indices of the
   elements to sort. */
public static <T extends Comparable<? super T>>
   void shellSort(T[] a, int first, int last)   
 {
     int n = last - first + 1; // Number of array elements
   
     for (int space = n / 2; space > 0; space = space / 2)
     {
         for (int begin = first; begin < first + space; begin++)
             incrementalInsertionSort(a, begin, last, space);
     } // end for
 } // end shellSort
   
 //  Sorts equally spaced elements of an array into ascending order.
 //  Parameters:
 //     a      An array of Comparable objects.
 //     first  The integer index of the first array entry to
 //            consider; first >= 0 and < a.length.
 //     last   The integer index of the last array entry to
 //            consider; last >= first and < a.length.
 //     space  The difference between the indices of the
 //            entries to sort.
 private static <T extends Comparable<? super T>>
     void incrementalInsertionSort(T[] a, int first, int last, int space)
 {
     int unsorted, index;
     for (unsorted = first + space; unsorted <= last; 
                                unsorted = unsorted + space)
     {
         T nextToInsert = a[unsorted];
         index = unsorted - space;
         while ((index >= first) && (nextToInsert.compareTo(a[index]) < 0))
         {
             a[index + space] = a[index]; 
             index = index - space;
         } // end while
         a[index + space] = nextToInsert; 
     } // end for
 } // end incrementalInsertionSort
