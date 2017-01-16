/* Adjacency List for 11.1
	
	1: 2, 3, 4
	2: 4, 5
	3: 6
	4: 3, 6, 7
	5: 4, 7
	6: 
	7: 6

*/


import java.util.*;
import java.text.*;

//------------------------------------------------------
public class GraphTester11_1 {
	   // -------  main --------------
	   public static void main(String[] args)
	   {
	      // build graph
	      Graph<Integer> myGraph1 = new Graph<Integer>();
	      myGraph1.addEdge(1, 2, 0);	myGraph1.addEdge(1, 3, 0);	myGraph1.addEdge(1, 4, 0);
	      myGraph1.addEdge(2, 4, 0);	myGraph1.addEdge(2, 5, 0);
	      myGraph1.addEdge(3, 6, 0);
	      myGraph1.addEdge(4, 3, 0); 	myGraph1.addEdge(4, 6, 0);	myGraph1.addEdge(4, 7, 0);
	      myGraph1.addEdge(5, 4, 0);	myGraph1.addEdge(5, 7 ,0); 
	      myGraph1.addEdge(7, 6, 0);
	      myGraph1.showAdjTable();
	   }

}

/* OUTPUT:

------------------------
Adj List for 1: 2(0.0) 3(0.0) 4(0.0)
Adj List for 2: 4(0.0) 5(0.0)
Adj List for 3: 6(0.0)
Adj List for 4: 3(0.0) 6(0.0) 7(0.0)
Adj List for 5: 4(0.0) 7(0.0)
Adj List for 6:
Adj List for 7: 6(0.0)

*/
