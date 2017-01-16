/* 
	Name: Eric Sehun Oh
	22C
	Exercise 5.1
*/
/*
public static int rabbit(int n)    // line 1

{                      // line 2

   if (n <= 2)      // line 3

      return 1;      // line 4

   else // n > 2, so n - 1 > 0 and n - 2 > 0 // line 5

      return rabbit(n - 1) + rabbit(n - 2);  // line 6

}  // end rabbit

1. Which of the above expressions indicates the "base case"?

	Line 3 indicates the base case.

2. Which of the above has recursive calls?
	
	Line 6 has recursive calls

3. How would you describe what the above method returns?

	returns 1 if n <= 2 or returns the addition of values returned from rabbit functions
	 calls with values smaller than n by 1 and by 2

	returns the value at the position n for the fibonacci sequence
	except for when n=0, it will return 1 not 0

	n: 	 	0 1 2 3 4 5 6 7
	output: 1 1 1 2 3 5 8 13

4. Study the code below.  What does that method do?

	if n is 3 or greater, it will return the value at the position n for the fibonacci sequence
	n: 		 3 4 5 6 7  
	output:  2 3 5 8 13
	
	next=13 prev=8 cur=13

5. Is the rabbit method shown above a good use of recursion or a bad use of recursion?  Why or why not? (MORE OF THE EXERCISE BELOW THE FOLLOWING method)
	
	It is not a good use of recursion as there is an iterative solution using a loop rather than recursion. The overhead from the recursive 
	implementation will call the function over a 100 times.

6. After completing the instructions below, what was the last "call #"? What does that number indicate?
	
	Call number indicates the number of recursive calls to the function occured.
*/

public class Main {
	public static int numCalls;

	public static int rabbit(int n) 
	{                      // line 2
		++numCalls;
		System.out.println("n = " + n + " for call #" + numCalls);	
		if (n <= 2)      // line 3
		  return 1;      // line 4
		else // n > 2, so n - 1 > 0 and n - 2 > 0 // line 5
		  return rabbit(n - 1) + rabbit(n - 2);  // line 6
	}  // end rabbit	

	public static int exercise5(int n)
	{
	   int previous = 1; 
	   int current = 1;  
	   int next = 1;     
	   
	   for (int i = 3; i <= n; i++)
	   {
	      next = current + previous;   // exercise5(i)
	      previous = current;          // Get ready for next iteration
	      current = next;
	   }  // end for
	   
	   return next;
	}  // end exercise5

	public static void main(String[] args)
	{
		int res1;
		res1 = rabbit(10);
		System.out.println("\nResult of calling rabbit(10): " + res1);
		System.out.println("Result of calling exercise5(10): " + exercise5(10));
	}
}