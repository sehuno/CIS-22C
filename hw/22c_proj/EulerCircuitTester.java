import java.util.*;
import java.text.*;
import java.util.Scanner;
import java.io.*;

//------------------------------------------------------
public class EulerCircuitTester
{
	public static Scanner userScanner;
	// -------  main --------------
	public static void main(String[] args)
	{	
		// WHAT ABOUT DUPLICATE EDGES FROM TWO VERTICES ???

		// EulerGraph<String> myGraph = enterWestCoastDataSet();
		// EulerGraph<String> myGraph = new EulerGraph<String>();
		// myGraph.findEulerCircuit();


		switch(promptUserDataChoice()) 
		{
			case 1: 
				processGraph(enterWestCoastDataSet());
				break;
			case 2:	
				processGraph(enterEastCoastDataSet());
				break;
			case 3:
				processGraph(createGraphFromFile());
				break;
			case 4: 
				processGraph(new EulerGraph<String>());
				break;
		}
	}

	public static int promptUserDataChoice()
	{
		String userInput;
		Integer option;
		boolean validInput = false;
		userScanner = new Scanner( System.in );
		System.out.println( "------------------------ ");
		System.out.println("\nEuler Circuit Program");
		System.out.println( "------------------------ ");
		while(!validInput)
		{
			System.out.println("\nWhich data set would you like to use?");
			System.out.println("1. Pre-loaded West Coast Data Set");
			System.out.println("2. Pre-loaded East Coast Data Set");
			System.out.println("3. Custom file");
			System.out.println("4. Interactive");
			System.out.print("\nEnter choice: ");

			try {
				option = userScanner.nextInt();
				userScanner.nextLine();
				if(option < 1 || option > 4){
					System.out.println("Please enter a choice between 1 - 3. Try again.");
					validInput = false;
				}
				else
					return option;
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input. Try again.");
				userScanner.next();
			}
		}
		return -1; // should never happen
	}

	public static int promptUserProcessChoice()
	{
		String userInput;
		Integer option;
		boolean validInput = false;
		userScanner = new Scanner( System.in );
		while(!validInput)
		{
			System.out.println( "------------------------ ");
			System.out.println("\nWhat would you like to do?");
			System.out.println("1. Find Euler Circuit");
			System.out.println("2. Display Adjacency Table");
			System.out.println("3. Add edge");
			System.out.println("4. Remove edge");
			System.out.println("5. Quit");
			System.out.print("\nEnter choice: ");

			try {
				option = userScanner.nextInt();
				userScanner.nextLine();
				if(option < 1 || option > 5){
					System.out.println("Please enter a choice between 1 - 5. Try again.");
					validInput = false;
				}
				else
					return option;
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input. Try again.");
				userScanner.next();
			}
		}
		return -1; // should never happen
	}

	public static void processGraph(EulerGraph<String> myGraph)
	{
		Integer choice = promptUserProcessChoice();
		String input1, input2;
		userScanner = new Scanner(System.in);
		while(choice != 5)
		{
			switch(choice) {
				case 1:
					// DONT USE NEW VERTEX
					myGraph.findEulerCircuit();
					break;
				case 2:
					System.out.println( "------------------------ ");
					System.out.println("Showing Adjacency Table");
					myGraph.showAdjTable();
					break;
				case 3:
					System.out.print("Enter src vertex: "); 
					input1 = userScanner.nextLine();
					System.out.print("Enter dst vertex: "); 
					input2 = userScanner.nextLine();
					myGraph.addEdge(input1,input2);
					break;
				case 4:
					System.out.print("Enter src vertex: "); 
					input1 = userScanner.nextLine();
					System.out.print("Enter dst vertex: "); 
					input2 = userScanner.nextLine();
					if(myGraph.remove(input1,input2))
						System.out.println("Edge from " + input1 + " to " + input2 + " successfully removed.");
					else
						System.out.println("Remove failed.");
					break;
			}
			choice = promptUserProcessChoice();
		}
	}

	public static EulerGraph<String> enterWestCoastDataSet()
	{
		EulerGraph<String> myGraph = new EulerGraph<String>();

		myGraph.addEdge("Anaheim", "Los Angeles");
		myGraph.addEdge("Anaheim", "Long Beach");
		myGraph.addEdge("Fresno", "Sacramento"); 
		myGraph.addEdge("Fresno", "San Jose");
		myGraph.addEdge("Fresno", "Los Angeles");
		myGraph.addEdge("Fresno", "San Luis Obispo");
		myGraph.addEdge("San Francisco", "Sacramento"); 
		myGraph.addEdge("San Francisco", "Palo Alto");
		myGraph.addEdge("San Jose", "Palo Alto");
		myGraph.addEdge("San Jose", "Los Angeles");
		myGraph.addEdge("San Jose", "San Luis Obispo");
		myGraph.addEdge("Long Beach", "Los Angeles");

		return myGraph;
	}

	public static EulerGraph<String> enterEastCoastDataSet()
	{
		EulerGraph<String> myGraph = new EulerGraph<String>();

		myGraph.addEdge("Albany", "Pittsburgh");
		myGraph.addEdge("Albany", "New York");
		myGraph.addEdge("Buffalo", "Syracuse");
		myGraph.addEdge("Buffalo", "Pittsburgh");
		myGraph.addEdge("Buffalo", "Rochester");
		myGraph.addEdge("Buffalo", "Philadelphia");
		myGraph.addEdge("Buffalo", "Washington");
		myGraph.addEdge("Baltimore", "Washington");
		myGraph.addEdge("Hamilton", "Syracuse");
		myGraph.addEdge("Rochester", "Baltimore");
		myGraph.addEdge("Baltimore","Pittsburgh");
		myGraph.addEdge("Washington", "Philadelphia");
		myGraph.addEdge("Washington", "Pittsburgh");
		myGraph.addEdge("Hamilton", "New York");
		myGraph.addEdge("Baltimore", "Buffalo");

		return myGraph;
	}

	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("Can't open input file\n");
   	    	return null; // array of 0 elements
    	} // end catch
    	return scanner;
	}

	public static EulerGraph<String> createGraphFromFile()
	{
		String src, dst, line;
		List<String> lineList;
		userScanner = openInputFile();
		EulerGraph<String> myGraph = new EulerGraph<String>();
		
		while(userScanner.hasNextLine()) { 
			line = userScanner.nextLine();
			lineList = Arrays.asList(line.split(","));
			myGraph.addEdge(lineList.get(0), lineList.get(1).substring(1));
		}

		return myGraph;
	}
}
