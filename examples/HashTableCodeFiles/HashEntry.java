
// HashEntry class for use by HashQP -----------------------
public class HashEntry<E>
{
   public E data;
   public int state;
   
   public HashEntry( E x, int st )
   {
      data = x;
      state = st;
   }

   public HashEntry()
   {
      this(null, HashQP.EMPTY);
   }
}
