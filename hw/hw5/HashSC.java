import java.util.*;
import java.util.Iterator;

public class HashSC<E> extends HashTable<E> // CHANGE TO MAKE THIS A SUBCLASS OF HashTable for HW#5!!!!!!!!!!
{
   static final int INIT_TABLE_SIZE = 97;
   static final double INIT_MAX_LAMBDA = 1.5;
   
   protected LList<E>[] mLists;
   protected int mSize;
   protected int mTableSize;
   protected double mMaxLambda;
   protected int numCollisions = 0;
   protected int longestCollisionRun = 0;

   // ADD Comparator<E> and Hasher<E> parameters for HW#5!!!!!!!!
   public HashSC(Hasher<E> h, Comparator<E> c, int tableSize) 
   {
	   // DONE: Pass Comparator<E> and Hasher<E> parameters to the SUPERCLASS constructor for HW#5!!!!!!!!!!
      super(h,c);

      if (tableSize < INIT_TABLE_SIZE)
         mTableSize = INIT_TABLE_SIZE;
      else
         mTableSize = nextPrime(tableSize);

      allocateMListArray();  // uses mTableSize;
      mMaxLambda = INIT_MAX_LAMBDA;
   }

   // DONE: ADD Comparator<E> and Hasher<E> parameters for HW#5!!!!!!!!
   public HashSC(Hasher<E> h, Comparator<E> c)
   {
      this(h, c, INIT_TABLE_SIZE); // DONE: FIX THIS (also pass Comparator<E> and Hasher<E>)
   }
   
// DONE
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// DON'T FORGET TO OVERRIDE traverseTable() (YOU WRITE FOR HW#5)
//     YOU MUST USE THE ITERATOR RETURNED FROM EACH LINKED LIST
//     AND Call the visitor's visit for EACH entry of EACH non-empty Linked List
//     (see the last part of rehash for examples) 
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

   public E getEntry(E target)
   {
      // DONE
		// FINISH THIS: should be like remove, (SO YOU USE A LinkedList's iterator
		//   AND Comparator), but return what the iterator returned if the comparator's compare
		//   method returns 0  OR null if not found
      LList<E> theList = mLists[myHash(target)];
      Iterator<E> iter = theList.iterator();
      E returnedItem;
      
      for(int i=0; iter.hasNext(); ++i )
      {
         returnedItem = iter.next();
         if(comparator.compare(returnedItem, target)==0)
            return returnedItem;
      }   
      return null;
   }
   
   public boolean contains( E x)
   { 
      // DONE: return theList.contains(x); // CHANGE AS INDICATED ON HW#5!!!!!!!!!!!!!!!!
      // DONE: replace with SEVERAL LINES (USE ITERATOR AND COMPARATOR)
      LList<E> theList = mLists[myHash(x)];
      Iterator<E> iter = theList.iterator();
      while (iter.hasNext())
         if(comparator.compare(iter.next(), x) == 0)
            return true;
     
      return false;
   }
   
   public void makeEmpty()
   {
      int k, size = mLists.length;

      for(k = 0; k < size; k++)
         mLists[k].clear();
      mSize = 0;  
   }
   
   public boolean insert( E x)
   {
      LList<E> theList = mLists[myHash(x)];
      Iterator<E> iter = theList.iterator();
      if(!theList.isEmpty())
         // numCollisions++;  
         numCollisions += theList.getLength();

      // DONE
      // CHANGE AS INDICATED ON HW#5!!!!!!!!!
		// replace with SEVERAL LINES (USE ITERATOR AND COMPARATOR)
		// ADD HERE: check and maybe UPDATE member counter variable

      // not found so we insert
      theList.add(x);

      // DONE
		// ADD HERE: possibly update longestCollisionRun variable
		//    which should be counting the longest linked list
      if(theList.getLength() > longestCollisionRun)
         longestCollisionRun = theList.getLength();

      // check load factor
      if( ++mSize > mMaxLambda * mTableSize )
         rehash();

      return true; 
   }
   
   public boolean remove( E x) 
   {
	   LList<E> theList = mLists[myHash(x)];
	   Iterator<E> iter = theList.iterator();
	   E currElem;
	   
	   for(int i=0; iter.hasNext(); ++i )
	   {
		   currElem = iter.next();
		   if(comparator.compare(currElem, x)==0)
		   {
			   theList.remove(i+1);
			   --mSize;
			   return true;
		   }
	   }
      // not found
      return false;   
   }

   public int size()  { return mSize; }
   
   public boolean setMaxLambda( double lam )
   {
      if (lam < .1 || lam > 100.)
         return false;
      mMaxLambda = lam;
      return true;
   }

   @Override
   public void traverseTable(Visitor<E> visitor) 
   {
      Iterator<E> iter;
      int i, j, size;
      for(i = 0; i < mTableSize; i++) {
         if(!mLists[i].isEmpty()) {
            iter = mLists[i].iterator();
            size = mLists[i].getLength();
            while (iter.hasNext())
               visitor.visit(iter.next());
         }
      }
   }

   public void displayStatistics()
   {
	   System.out.println("\nIn the HashSC class:\n");
	   System.out.println( "Table Size = " +  mTableSize );;
	   System.out.println( "Number of entries = " + mSize);
	   System.out.println( "Load factor = " + (double)mSize/mTableSize);
	   System.out.println( "Number of collisions = " + this.numCollisions );
	   System.out.println( "Longest Linked List = " + this.longestCollisionRun );
   }
   
   // protected methods of class ----------------------
   protected void rehash()
   {
		// DONE: ADD CODE HERE TO RESET THE HashTable COUNTERS TO 0 for HW#5!!!!!!!!!!!!!!!!
      numCollisions = 0;
      longestCollisionRun = 0;
	   
      // we save old list and size then we can reallocate freely
      LList<E>[] oldLists = mLists;
      int k, oldTableSize = mTableSize;
      Iterator<E> iter;

      mTableSize = nextPrime(2*oldTableSize);
      
      // allocate a larger, empty array
      allocateMListArray();  // uses mTableSize;

      // use the insert() algorithm to re-enter old data
      mSize = 0;
      for(k = 0; k < oldTableSize; k++)
         for(iter = oldLists[k].iterator(); iter.hasNext() ; )
            insert( iter.next());
   }
   
   protected int myHash( E x)
   {
      int hashVal;

      // DONE: CHANGE TO USE Hasher's hash method for HW#5!!!!!!!!!!!
      // hashVal = x.hashCode() % mTableSize; 
      hashVal = hasher.hash(x) % mTableSize;
      if(hashVal < 0)
         hashVal += mTableSize;

      return hashVal;
   }
   
   protected static int nextPrime(int n)
   {
      int k, candidate, loopLim;

      // loop doesn't work for 2 or 3
      if (n <= 2 )
         return 2;
      else if (n == 3)
         return 3;

      for (candidate = (n%2 == 0)? n+1 : n ; true ; candidate += 2)
      {
         // all primes > 3 are of the form 6k +/- 1
         loopLim = (int)( (Math.sqrt((double)candidate) + 1)/6 );

         // we know it is odd.  check for divisibility by 3
         if (candidate%3 == 0)
            continue;

         // now we can check for divisibility of 6k +/- 1 up to sqrt
         for (k = 1; k <= loopLim; k++)
         {
            if (candidate % (6*k - 1) == 0)
               break;
            if (candidate % (6*k + 1) == 0)
               break;
         }
         if (k > loopLim)
            return candidate;
      }
   }
   
   private  void allocateMListArray()
   {
      int k;
      
      mLists = new LList[mTableSize];
      for (k = 0; k < mTableSize; k++)
         mLists[k] = new LList<E>();
   }
}

//--------------------------------------------------------------------------------------