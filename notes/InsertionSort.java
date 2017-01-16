// INSERTION SORT (all these methods are inside a class)
 public static <T extends Comparable<? super T>> 
    void insertionSort(T[] a, int n)
 {
     insertionSort(a, 0, n - 1);
 } // end insertionSort


 public static <T extends Comparable<? super T>> 
    void insertionSort(T[] a, int first, int last)
 {
     for (int unsorted = first + 1; unsorted <= last; unsorted++)
     {   // Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]
   
         T firstUnsorted = a[unsorted];
   
         insertInOrder(firstUnsorted, a, first, unsorted - 1);
     } // end for
 } // end insertionSort


 private static <T extends Comparable<? super T>> 
    void insertInOrder(T anEntry, T[] a, int begin, int end)
 {
     int index = end;
   
     while ((index >= begin) && (anEntry.compareTo(a[index]) < 0))
     {
         a[index + 1] = a[index]; // Make room
         index--;
     } // end for
   
     // Assertion: a[index + 1] is available
     a[index + 1] = anEntry;  // Insert
 } // end insertInOrder