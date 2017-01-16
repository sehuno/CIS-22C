import java.util.*;
import java.util.Map.Entry;

public class EulerGraph<E> extends Graph<E> {
	protected int numEdges;

	public EulerGraph() 
	{
		super();
		numEdges = 0;
	}

	// public EulerGraph(Edge<E>[] edges)
	// {
	// 	super(edges);
	// 	numEdges = edges.length;
	// }

	public void addEdge(E source, E dist)
	{
		super.addEdge(source, dist, 0.0);
		numEdges++;
		System.out.println("Edge from " + source.toString() + " to " + dist.toString() + " added.");
	}

	public int getNumEdges() { return numEdges; }

	// public boolean graphConnected()
	// {
	// 	return false;
	// }

	public boolean onlyEvenVertices()
    {
      Iterator<Entry<E, Vertex<E>>> iter;
       
      iter = vertexSet.entrySet().iterator();
      while( iter.hasNext() )
      {
         if(!(iter.next().getValue()).evenVertex())
         	return false;
      }
      return true;
   }

   	public boolean hasPathToStartVertex(Vertex<E> start, Vertex<E> curr)
   	{

   		Iterator<Entry<E, Pair<Vertex<E>, Double>>> cIter = curr.iterator();
   		Iterator<Entry<E, Pair<Vertex<E>, Double>>> sIter = curr.iterator();
   		Vertex<E> c, s;

   		while(cIter.hasNext())
   		{
   			c = cIter.next().getValue().first;
   			while(sIter.hasNext())
   			{
   				s = sIter.next().getValue().first;
   				if(s.equals(c))
   					return true;
   			}
   		}
   		return false;
   	}

	public void showOddVertices()
	{
		System.out.println( "------------------------ ");
		System.out.println("\nFollowing vertices do not have an even degrees:");

		Iterator<Entry<E, Vertex<E>>> iter;
		Vertex<E> currV;
       
		iter = vertexSet.entrySet().iterator();
		while( iter.hasNext() )
		{
			currV = iter.next().getValue();
			if(!currV.evenVertex()) {
				System.out.println(currV.getData() + " - degree: " + currV.adjList.size());
			}
		}
	}

	public void notPossibleErrorMessage()
	{
		System.out.println( "------------------------ ");
		System.out.println("\nEuler circuit is not possible!");
		System.out.println("\nVertices of odd degree have been found.");
		System.out.println("To create a graph with a possible Euler circuit, all vertices must have even degrees");
		System.out.println("\nPossible solutions:");
		System.out.println("\n1. Add edge(s) between vertices of odd degree.");
		System.out.println("2. Add additional vertices with edges to vertices of odd degrees.");
		System.out.println("3. Delete edges between vertices of odd degrees.");
		System.out.println( "------------------------ ");
	}

	public void findEulerCircuit() 
	{
		if(numEdges == 0)
		{
			System.out.println("No entries added yet. Try again after adding edges.");
			return;
		}

		if(!onlyEvenVertices()) {
			notPossibleErrorMessage();
			showOddVertices();
			return;
		}

		List<Vertex<E>> vertexList = new ArrayList<Vertex<E>>(vertexSet.values());
		int randomIndex = new Random().nextInt(vertexList.size());
		Vertex<E> startV = vertexList.get(randomIndex);

		Vertex<E> currV = startV;
		Vertex<E> nextV;

		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter;
		Pair<Edge<E>, Vertex<E>> pop;

		LinkedStack< Pair<Edge<E>, Vertex<E>>> eulerStack = new LinkedStack< Pair<Edge<E>, Vertex<E>>>();

		System.out.println("\nEuler circuit possible!");
		System.out.println("\nEuler circuit algorithm yet to be implemented fully.");

		// for(int i = 0; i < vertexList.size(); i++)
			// System.out.println(hasPathToStartVertex(startV, vertexList.get(i)));

		// iter = currV.iterator();

		// while(numEdges != 0 || !currV.equals(startV))
		// {
		// 	iter = currV.iterator();
		// 	if(!iter.hasNext())
		// 	{
		// 		pop = eulerStack.pop();
		// 		addEdge(currV.getData(), pop.second.getData());
		// 		currV = pop.second;
		// 		continue;
		// 	}
		// 	else
		// 	{
		// 		while(iter.hasNext())
		// 		{
		// 			nextV = iter.next().getValue().first;
		// 			remove(currV.getData(), nextV.getData());
		// 			if(!hasPathToStartVertex(startV, nextV)) {
		// 				addEdge(currV.getData(), nextV.getData());
		// 			}
		// 			else {
		// 				eulerStack.push(new Pair(new Edge(currV, nextV, 0.0), currV));
		// 				currV = nextV;
		// 				numEdges--;
		// 				break;
		// 			}
		// 		}
		// 	}
		// }


		// // // 	nextV = iter.next().getValue().first;
		// // // 	remove(currV.getData(), nextV.getData());
		// // // 	if(!hasPathToStartVertex(startV, nextV)) {
		// // // 		super.addEdge(currV.getData(), nextV.getData(), 0);
		// // // 	}
		// // // 	else {
		// // // 		eulerStack.push(new Pair(new Edge(currV, nextV, 0.0), currV));
		// // // 		currV = nextV;
		// // // 		numEdges--;
		// // // 	}

		// System.out.println(eulerStack.size());




			// LOOKING AT ADJACENT VERTICES OF CURRENT VERTEX
			// prints all adjacent vertices
			// while(iter.hasNext())
			// 	System.out.println(iter.next().getValue().first.getData());

			// break;

		/*
			Start A, look at adjacent vertices
			Remove AB, current vertex is B. 
				Can b still reach A? Yes
			Add AB to euler circuit stack

			Start B, look at adjacent vertices
			Remove BC, current vertex is C. 
				Can c still reach A? Yes
			Add BC to euler circuit stack

			Start C, look at adjacent vertices
			Remove CA, current vertex is A. 
				if current vertex is the one we want to end on
					if numEdges is 0 
						we found circuit
					else
						does A have any more vertices? 
							yes, go.
							no, Pop CA off euler, current vertex is C.
				else can we still reach A?  yes

			Remove CD, current vertex is D.
				Can d still reach A? Yes.
			Add CD to euler

			Start D, look at adjacent vertices
			Remove DB, current vertex is B
				Can B still reach A? Yes
			Add BC to euler

			Start C, look at adjacent vertices
			Remove CA, current vertex is A.
				is current vertex the one we want to end on?
					if numEdges is 0
						we found circuit
		*/


		// System.out.println("fec called");

		// LinkedStack<Pair<Vertex<E>, Edge<E>>> traversed = new LinkedStack<Pair<Vertex<E>, Edge<E>>>();

		// stack of edges

		// start at starting vertex
		// push/pop edges as visited, pop to backtrack.
		// 
		// when stack of edges size is equal to number of edges in graph
		//	&& starting vertex is the ending vertex, 
		//		for each edge in stack of edgesl, print edge.
	}
}