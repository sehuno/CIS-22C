/**
   A EulerGraph class that extends the Graph<E> class in order to find the Euler Circuit.


	- 2 additional addEdge functions to be able to
		1. addEdges without using a Double cost values
		2. addEdges with actual Edge class
	- 2 additional remove functions to be able to
		1. Keep track of removed connections from Vertices, pushing to LinkedStack of edges of those Vertices
		2. To remove without using cost
	- 2 findEulerCircuit algorithms
		1. Uses a user defined Vertex as starting point
		2. Randomly chooses a starting point if none provided
	- function checking degrees of each vertice
	- LinkedStack of EulerCircuit edges and deleted edges for easy removal, reinsertion
		- addLastRemoved, addLast

   @author Group 2 22C

*/


import java.util.*;
import java.util.Map.Entry;
import java.io.PrintWriter;

public class EulerGraph<E> extends Graph<E> {
	public int numEdgesForCircuit;
	public int numEdges;
	public Vertex<E> start;
	public LinkedStack<Edge<E>> deletedEdges;
	public LinkedStack<Edge<E>> eulerCircuit;

	public EulerGraph() 
	{
		super();
		numEdges = 0;
		deletedEdges = new LinkedStack<Edge<E>>();
		eulerCircuit = new LinkedStack<Edge<E>>();
	}

	public EulerGraph(Edge<E>[] edges)
	{
		super(edges);
		deletedEdges = new LinkedStack<Edge<E>>();
		eulerCircuit = new LinkedStack<Edge<E>>();
		numEdges = edges.length;
	}

	public void addEdge(E source, E dist)
	{
		super.addEdge(source, dist, 0.0);
		numEdges++;
	}

	public void addEdge(Edge<E> e) 
	{ 
		addEdge(e.source.data, e.dest.data); 
	}

	@Override
	public boolean remove(E start, E end)
	{	
		deletedEdges.push(new Edge(new Vertex(start), new Vertex(end), 0.0));
		numEdges--;
		return super.remove(start, end);
	}

	public boolean remove(Vertex<E> start, Vertex<E> end)
	{
		return remove(start.data, end.data);
	}

	@Override public void clear()
	{
		super.clear();
		numEdges = 0;
	}

	public void addAllRemovedEdges()
	{
		while(!deletedEdges.isEmpty())
			addLastRemovedEdge();
	}

	public Edge<E> addLastRemovedEdge()
	{
		Edge<E> lastRemovedEdge;
		if(!deletedEdges.isEmpty()) {
			lastRemovedEdge = deletedEdges.pop();
			addEdge(lastRemovedEdge);
			return lastRemovedEdge;
		}
		return null;
	}

	public int getNumEdges() { return numEdges; }

	public boolean onlyEvenVertices()
    {
      Iterator<Entry<E, Vertex<E>>> iter;
       
      iter = vertexSet.entrySet().iterator();
      while( iter.hasNext() )
      {
         if(iter.next().getValue().adjList.size() % 2 != 0)
         	return false;
      }
      return true;
   }

	public void runEulerCircuit(String input)
	{
		if(!eulerCircuitPossible()) {
			// notPossibleErrorMessage();
			// showOddVertices();
			System.out.println("circuit not possible");
			return;
		}
		numEdgesForCircuit = numEdges;

		if(input.equals(""))
		{
			findEulerCircuitRandom();
			System.out.println("no vertex specified for circuit, randomly chose " + start.data);

			// Repopulate the edges in the original graph
			LinkedStack<Edge<E>> returnStack = new LinkedStack<Edge<E>>(); 
			Edge<E> edge;
			while(!eulerCircuit.isEmpty())
			{
				edge = eulerCircuit.pop();
				addEdge(edge);
				returnStack.push(edge);
			}
			while(!returnStack.isEmpty()) {
				edge = returnStack.pop();
				eulerCircuit.push(edge);
			}
			printEulerCircuit();
		}
		else 
		{	
			start = vertexSet.get(input);
			if(start == null)
				System.out.println("starting vertex not found");
			else {
				if(findEulerCircuit(start)) {
					System.out.println("found!");
					LinkedStack<Edge<E>> returnStack = new LinkedStack<Edge<E>>();
					Edge<E> edge;
					while(!eulerCircuit.isEmpty())
					{
						edge = eulerCircuit.pop();
						addEdge(edge);
						returnStack.push(edge);
					}
					while(!returnStack.isEmpty()) {
						edge = returnStack.pop();
						eulerCircuit.push(edge);
					}
					printEulerCircuit();
				}
				else
					System.out.println("not found...");
			}
		}
	}


	public boolean eulerCircuitPossible()
	{
		if(numEdges == 0)
			return false;

		if(!onlyEvenVertices()) {
			// notPossibleErrorMessage();
			// showOddVertices();
			return false;
		}

		eulerCircuit = new LinkedStack<Edge<E>>();
		deletedEdges = new LinkedStack<Edge<E>>();
		return true;
	}

	public boolean findEulerCircuit(Vertex<E> vertex)
	{
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter;
		Vertex<E> curr = new Vertex();
		Vertex<E> temp = new Vertex();

		if(numEdges == 0) {
			if(vertex.equals(start))
				return true;
			else
				return false;
		}

		while(eulerCircuit.size() != numEdgesForCircuit)
		{
			iter = vertex.adjList.entrySet().iterator();
			while(iter.hasNext())
			{
				curr = iter.next().getValue().first; 
				if(temp.data != null && curr.equals(temp)){
					temp.data = null;
					continue;
				}
				remove(vertex, curr);
				eulerCircuit.push(new Edge(vertex, curr, 0.0));
				
				if(!findEulerCircuit(curr)) {
					addEdge(deletedEdges.pop());
					eulerCircuit.pop();
					temp.data = curr.data;
					iter = vertex.adjList.entrySet().iterator();
				} 
				if(numEdges == 0 && start.equals(eulerCircuit.peek().dest))
					return true;
			}
			return false;
		}
		return true;
	}


	public void printEulerCircuit() 
	{
		LinkedStack<Edge<E>> returnStack = new LinkedStack<Edge<E>>();
		Edge<E> edge;
		while(!eulerCircuit.isEmpty())
		{
			edge = eulerCircuit.pop();
			returnStack.push(edge);
		}
		while(!returnStack.isEmpty()) {
			edge = returnStack.pop();
			eulerCircuit.push(edge);
			System.out.println(edge.source.data + " -> " + edge.dest.data);
		}
	}

	public void findEulerCircuitRandom() 
	{
		List<Vertex<E>> vertexList = new ArrayList<Vertex<E>>(vertexSet.values());
		int randomIndex = new Random().nextInt(vertexList.size());
		Vertex<E> randomStartV = vertexList.get(randomIndex);
		start = randomStartV;
		findEulerCircuit(start);
	}

	@Override
	public void writeToFile(PrintWriter pw)
	{
		LinkedStack<Edge<E>> returnStack = new LinkedStack<Edge<E>>();
		Edge<E> edge;

	 	super.writeToFile(pw);
	 	pw.println("\nEuler Circuit");

		while(!eulerCircuit.isEmpty())
		{
			edge = eulerCircuit.pop();
			addEdge(edge);
			returnStack.push(edge);
		}
		while(!returnStack.isEmpty()) {
			edge = returnStack.pop();
			eulerCircuit.push(edge);
			pw.printf("%s -> %s\n", edge.source.data, edge.dest.data);
			// pw.println(edge.source.data + " -> " + edge.dest.data);
		}
	}

	public void showOddVertices()
	{
		System.out.println( "------------------------ ");
		System.out.println("Following vertices do not have an even degrees:");

		Iterator<Entry<E, Vertex<E>>> iter;
		Vertex<E> currV;
       
		iter = vertexSet.entrySet().iterator();
		while( iter.hasNext() )
		{
			currV = iter.next().getValue();
			if(currV.adjList.size() % 2 != 0) {
				System.out.println(currV.getData() + " - degree: " + currV.adjList.size());
			}
		}
	}

	public boolean traversalVertexChecker(E start) 
	{
		return vertexSet.get(start) != null;
	}

	public HashMap<E, Vertex<E>> getHashMap()
	{
		return vertexSet;
	}

	// public void notPossibleErrorMessage()
	// {
	// 	System.out.println( "------------------------ ");
	// 	System.out.println("Euler circuit is not possible!");
	// 	System.out.println("Vertices of odd degree have been found.");
	// 	System.out.println("To create a graph with a possible Euler circuit, all vertices must have even degrees");
	// 	System.out.println("Possible solutions:");
	// 	System.out.println("1. Add edge(s) between vertices of odd degree.");
	// 	System.out.println("2. Add additional vertices with edges to vertices of odd degrees.");
	// 	System.out.println("3. Delete edges between vertices of odd degrees.");
	// 	System.out.println( "------------------------ ");
	// }

}

