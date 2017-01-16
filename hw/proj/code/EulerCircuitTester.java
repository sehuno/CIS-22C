/**
   A EulerCircuitTester class that tests the EulerCircuit and auxiliary methods.


	Available commands for use are as shown below.

------------------------
Euler Circuit Program
------------------------
available commands
> load			load from file
> add [options]
>> add			add single edge / start blank graph when none
>> add lr		add last removed edge
>> add allr		add all removed edges
> show [options]
>> show table		show adjacency table
>> show circuit		show graph's euler circuit
>> show br trav		breadth first traversal
>> show dep trav	depth traversal
> delete		delete edge from graph
> euler			find eulers path
> clear			clear graph
> undo			undo last action
> save			save to file
> quit			quit
> help			show this text again

		subtle features to note
			- the "add" command without a loaded graph will load a blank graph
			- the "show" command as itself will only bring up show command options
			- the "show circuit" is better used after calling the "euler" command,
				as it shows the last euler circuit found. However, if called before
				it will also generate a circuit. If no start vertex is entered, it will
				randomly choose a vertex in the graph to 
			- the "undo" commands work also for after "add lr", "add allr", 
			- when entering nothing on the prompt, it will create do nothing while creating a blank line
				- this will make the testing easier on the eyes and allow for flexible line spacing to the user
			- the "save" command will not save the euler circuit to the file unless the "euler" function is called
				for the graph so that the circuit algorithm can run and find the circuit, but will print the
				vertices and adjacency list by default.
			- occasionally call the "help" command to display this command list again

	
		euler circuits are always possible when all the vertices are of even degrees!

   @author Group 2 	22C
*/


import java.util.*;
import java.text.*;
import java.util.Scanner;
import java.io.*;
import java.io.PrintWriter;
import java.io.File;


//------------------------------------------------------
public class EulerCircuitTester
{
	public static Scanner userScanner = new Scanner(System.in);
	// -------  main --------------
	public static void main(String[] args)
	{		
		String input;
		String lastAction = "";
		Edge<String> edge = new Edge<String>();
		Pair<String, String> lastAdded = new Pair("","");
		Pair<String, String> lastDeleted = new Pair("","");
		EulerGraph<String> graph = null;
		HashMap<String, Vertex<String>> clearedVertexSet;
		LinkedStack<Edge<String>> prevEdgesInDeleted = new LinkedStack<Edge<String>>(); 

		System.out.println( "------------------------ ");
		System.out.println("Euler Circuit Program");
		System.out.println( "------------------------ ");

		help();
		while(!(input = prompt()).equalsIgnoreCase("quit"))
		{
			switch(input)
			{
				case "add":
					if(graph == null) {
						System.out.println("no graph found, blank created");
						graph = new EulerGraph<String>(); }
					lastAdded = add(graph);
					break;

				case "add lr":
					if(graph == null) 
						System.out.println("no graph found");
					else {
						lastAdded = new Pair(graph.deletedEdges.peek().source.data, graph.deletedEdges.peek().dest.data);
						if(!graph.deletedEdges.isEmpty()) {
							graph.addLastRemovedEdge();
							System.out.println("last removed edge added back");
						}
						else
							System.out.println("nothing to add back");
					}
					break;

				case "add allr":
					if(graph == null) 
						System.out.println("no graph found");
					else {
						if(graph.deletedEdges.size() == 0) {
							System.out.println("nothing to add back");
							break;
						}
						if(!graph.deletedEdges.isEmpty()) {
							prevEdgesInDeleted = graph.deletedEdges;
							graph.addAllRemovedEdges();
						}
						System.out.println("all removed edges added back");
					}
					break;

				case "clear":
					if(graph == null)
						System.out.println("no graph to clear");
					else {
						// clearedVertexSet = new HashMap<String, Vertex<String>>(graph.getHashMap());
						graph.clear();
						System.out.println("graph cleared");
					}
					break;

				case "delete":
					lastDeleted = delete(graph);
					break;

				case "euler":
					if(graph == null || graph.numEdges == 0) {
						System.out.println("can't euler on empty graph");
						break;
					}						
					System.out.print("> euler > enter start vtx ");
					input = prompt();
					graph.runEulerCircuit(input);
					break;

				case "help":
					help();
					break;

				case "load":
					userScanner = openInputFile();
					// prevGraphState = graph;
					if(userScanner != null)
						graph = createGraphFromFile();
					else 
						userScanner = new Scanner(System.in);
					break;

				case "show":
					show();
					break;

				case "show table":
					if(graph == null)
						System.out.println("no graph");
					else
						graph.showAdjTable();
					break;

				case "show br trav":
					if(graph == null || graph.numEdges == 0)
						System.out.println("no graph");
					else {
						System.out.print("> starting vertex ");
						input = prompt();
						if(graph.traversalVertexChecker(input))
							graph.breadthFirstTraversal(input, new EulerVertexVisitor());
						else
							System.out.println("starting vertex not found");
					}
					break;

				case "show dep trav":
					if(graph == null || graph.numEdges == 0)
						System.out.println("no graph");
					else {
						System.out.print("> starting vertex ");
						input = prompt();
						if(graph.traversalVertexChecker(input))
							graph.depthFirstTraversal(input, new EulerVertexVisitor());
						else
							System.out.println("starting vertex not found");
					}
					break;

				case "show circuit":
					if(graph == null)
						System.out.println("no graph");
					else {
						if(graph.eulerCircuit.isEmpty())
							System.out.println("please run the euler command first");
						else
							graph.printEulerCircuit();
					}
					break;

				case "undo":
					if(lastAction.equalsIgnoreCase("add")) {
						if(graph.remove(lastAdded.first, lastAdded.second))
							System.out.println("success - added edge removed");
						else
							System.out.println("error");
					}
					else if(lastAction.equalsIgnoreCase("delete")) {
						if(lastDeleted != null) {
							graph.addLastRemovedEdge();
							System.out.println("success - deleted edge added");
						}
						else
							System.out.println("can't undo a nonexistent remove");
					} 
					else if(lastAction.equalsIgnoreCase("add lr"))
					{
						lastDeleted = lastAdded;
						graph.remove(lastAdded.first, lastAdded.second);
						System.out.println("was last removed is now, is last removed");
					}
					else if(lastAction.equalsIgnoreCase("add allr"))
					{
						while(!prevEdgesInDeleted.isEmpty()) {
							edge = prevEdgesInDeleted.pop();
							graph.remove(edge.source.data, edge.dest.data);
						}
						System.out.println("WERE last removed edgeS are now back to last removed edgeS");
					}
					else if(lastAction.equalsIgnoreCase("clear"))
					{
						// graph.getHashMap() = new HashMap<String, Vertex<String>>(clearedVertexSet);
						// System.out.println("graph state restored to before clear");
						System.out.println("undo for clear not implemented");
					} 
					else if(lastAction.equalsIgnoreCase("load"))
					{
						// graph = prevGraphState;
						System.out.println("undo for load not implemented");
					} 
					else if(lastAction.equalsIgnoreCase("undo"))
					{
						System.out.println("can't undo an undoing!");
					}
					else
						System.out.println("nothing to undo");
					break;

				case "save":
					if(graph == null)
						System.out.println("no graph");
					else {
						System.out.print("> save > filepath ");
						File file = new File(prompt());
						file.getParentFile().mkdirs();
						try
						{
							PrintWriter pw = new PrintWriter(file);
							if(!graph.onlyEvenVertices()) 
						 		System.out.println("euler circuit not possible\nwill not be printed to file");
						 	if(graph.eulerCircuit.isEmpty())
						 		graph.runEulerCircuit("");
						 	graph.writeToFile(pw);
						 	pw.close();
						 	System.out.println("successfully wrote file");
						}
				        catch (FileNotFoundException e)
				        {
				            e.printStackTrace();
				        }
					}
					break;

				case "":
					break;

				default:
					System.out.println("invalid command");
					break;
			}
			lastAction = input;
		}
	}

	public static void show()
	{
		System.out.println("> show [options]");
		System.out.println(">> show table\t\tshow adjacency table");
		System.out.println(">> show circuit\t\tshow graph's euler circuit");
		System.out.println(">> show br trav\t\tbreadth first traversal");
		System.out.println(">> show dep trav\tdepth traversal");
	}

	public static void help()
	{
		System.out.println("available commands");
		System.out.println("> load\t\t\tload from file");
		System.out.println("> add [options]");
		System.out.println(">> add\t\t\tadd single edge / start blank graph when none");
		System.out.println(">> add lr\t\tadd last removed edge");
		System.out.println(">> add allr\t\tadd all removed edges");
		show();
		System.out.println("> delete\t\tdelete edge from graph");
		System.out.println("> euler\t\t\tfind eulers path");
		System.out.println("> clear\t\t\tclear graph");
		System.out.println("> undo\t\t\tundo last action");
		System.out.println("> save\t\t\tsave to file");
		System.out.println("> quit\t\t\tquit");
		System.out.println("> help\t\t\tshow this text again\n");
	}

	public static String prompt()
	{
		System.out.print("> ");
		return userScanner.nextLine();
	}

	public static Pair<String, String> add(EulerGraph<String> graph)
	{
		String src, dest;
		System.out.print("> add > src vertex "); 
		src = prompt();
		System.out.print("> add > dest vertex "); 
		dest = prompt();
		graph.addEdge(src, dest);
		System.out.println("successfully added edge");
		return new Pair(src, dest);
	}

	public static Pair<String, String> delete(EulerGraph<String> graph)
	{
		String src, dest;
		System.out.print("> delete > src vertex "); 
		src = prompt();
		System.out.print("> delete > dest vertex "); 
		dest = prompt();
		if(graph.remove(src, dest)) {
			System.out.println("successfully removed edge");
			return new Pair(src, dest);
		}
		else {
			System.out.println("remove failed");
			return null;
		}
	}

	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
        System.out.print("> load > filepath > ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    		System.out.println("error - can't open file");
   	    	return null; // array of 0 elements
    	} // end catch
    	System.out.println("successfully loaded");
    	return scanner;
	}

	public static EulerGraph<String> createGraphFromFile()
	{
		String src, dst, line;
		List<String> lineList;
		EulerGraph<String> myGraph = new EulerGraph<String>();
		
		while(userScanner.hasNextLine()) { 
			line = userScanner.nextLine();
			lineList = Arrays.asList(line.split(","));
			myGraph.addEdge(lineList.get(0), lineList.get(1).substring(1));
		}
		userScanner = new Scanner(System.in);
		return myGraph;
	}
}

class EulerVertexVisitor implements Visitor<String> {
	@Override
	public void visit(String obj) {
		System.out.println(obj.toString());
	}
}
