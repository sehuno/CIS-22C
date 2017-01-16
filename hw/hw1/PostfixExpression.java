/*
	CIS 22C 
	HW #1

	Name of Program: "PostfixExpression"
	Name of Programmer: Sehun Eric Oh
	Date: 1/13/16
	OS: Mac OS X El Capitan
	Compiler: javac 
	Description:

	*Comments before methods, descriptions of variable names and what the represent
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class PostfixExpression 
{
	private String pfExpression;	// Postfix Expression
	private ArrayList<String> tokenizedExpression;	// Tokens of expression
	private double result;			// Evaluation result

	// Default constrcutor 
	public PostfixExpression()
	{
		pfExpression = "";
		tokenizedExpression = new ArrayList<>();
	}

	// Custom constructor
	public PostfixExpression(String str_param)
	{
		this();	// Calls default constructor
		stringMutator(str_param);	// Uses setter method for mutating string
	}

	// Mutator function that changes, tokenizes, and evaluates the expression.
	public void stringMutator(String str) 
	{
		pfExpression = str;
		tokenize(pfExpression);
		evaluate();
	}

	// Getter method for postfix expression
	public String getString() 
	{
		return pfExpression;
	}

	// Getter method for evaluation result
	public Double getResult() 
	{
		return result;
	}

	//  Method for transforming the Postfix expression to an Infix expression
	public String getInfixExpression()
	{
		// 1.	Initialize empty stack (of Strings)
		ArrayStack<String> tokenStack = new ArrayStack<>();

		// 2.	For every token in the postfix expression list:
		for(String token : tokenizedExpression) 
		{
			// 1.	If the token is an operator: 
			if(isOperator(token))
			{
				// 1.	Check if the stack contains the sufficient number of values (usually two) for given operator
				if(tokenStack.size() < 2)
					// 2.	If there are not enough values, return a String with "error"
					return "Error";
				// 3.	Remove the top 2 items (pop the right operand, then left operand)
				String rightOp = tokenStack.pop();
				String leftOp = tokenStack.pop();
				// 4.	Create a string with "("+left+ token+right+")"
				String newStringToPush = "( "+leftOp + " "+token +" "+ rightOp +" )"; 
				// 5.	Push the string on the stack
				tokenStack.push(newStringToPush);
			} 
			// 2.	If the token is an operand (number), push it on the stack
			else 
				tokenStack.push(token);
		}
		// 3.	If the stack contains only one value, return it as a final result of the conversion
		if(tokenStack.size() == 1)
			return tokenStack.pop();
		// Otherwise, return a String with "error"
		else
			return "Error";
	}
 
	// Tokenizes the postfix expression given by using tabs as delimiters 
	private void tokenize(String input)
	{
		String [] tokenArray = input.split("[ \t]+");
		tokenizedExpression.clear(); // clear the ArrayList
		for(int i=0; i < tokenArray.length; ++i)
		{
			tokenizedExpression.add(tokenArray[i]); // add the next token to the ArrayList
		}
		// System.out.println(tokenizedExpression);
	} // end tokenize

	// Evaluates the postfix expression by parsing/identfying each token as either operand or operator and sets the 
	//	double result value to the evaluation value
	private void evaluate()
	{
		// 1.	Initialize empty stack (LinkedStack of Doubles)
		LinkedStack<Double> tokenStack = new LinkedStack<>();

		// 2.	For every token in the postfix expression list: 
		for(String token : tokenizedExpression)
		{
			// 1.	If the token is an operator: 
			if(isOperator(token))
			{
				// System.out.println("token is operator");
				// 1.	Check if the stack is empty (break from the loop is it is)
				if(tokenStack.isEmpty())
					break;
				// 2.	Get the value from the top of the stack and store in a variable for the right operand
				// 3.	Pop the top of the stack
				Double rightOp = tokenStack.pop();
				// 4.	Check if the stack is empty (break from the loop is it is)
				if(tokenStack.isEmpty())
					break;
				// 5.	Get the value from the top of the stack and store in a variable for the left operand
				Double leftOp = tokenStack.pop();
				Double res = 0.0;
				// 7.	Evaluate the operator using the left and right operand values and push the single result on the stack (SHOULD USE A SWITCH)
				switch(token) 
				{
					case "+":
						res = leftOp + rightOp;
						break;
					case "-":
						res = leftOp - rightOp;
						break;
					case "*":
						res = leftOp * rightOp;
						break;
					case "/":
						res = leftOp / rightOp;
						break;
					default:
						break;
				}
				tokenStack.push(res);
			}
			// 2.	If the token is an operand (number), push it on the stack (converted to a Double)
			else {
				// System.out.println("token is number");
				tokenStack.push(Double.parseDouble(token));
			}
		}
		// 3.	If the stack contains only one value, the top value is assigned to the result
		if(tokenStack.size() == 1)
			result = tokenStack.pop();
		// 4.	Otherwise, assign 0 to the result
		else
			result = 0.0;
	}

	// Helper function which determines whether the token string is an operator 
	private boolean isOperator(String token) 
	{
		if(token.length() == 1) 
		{
			if(token.equals(new String("*")))
				return true;
			if (token.equals(new String("/")))
				return true;
			if(token.equals(new String("+")))
				return true;
			if (token.equals(new String("-")))
				return true;
			return false;
		} 
		else 
			return false;		
	}

	public static Scanner userScanner = new Scanner(System.in);
	
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try {
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("Can't open input file\n");
   	    	return null; // array of 0 elements
    	} // end catch
    	return scanner;
	} // end openInputFile

	public static void main(String[ ] args) 
	{
		// Get user input for file
		Scanner scanner = openInputFile();
		// If file was not able to be opened, print message and terminate program
		if(scanner == null) 
		{
			System.out.println("File was not opened. Terminating program\n");
			System.exit(0);
		}

		// Default instance of postfix expression
		PostfixExpression exp = new PostfixExpression();
		// While not end of file
		while(scanner.hasNextLine())
		{
			exp.stringMutator(scanner.nextLine().replace("\n",""));		// Set new expression
			System.out.println("The postfix expression: " + exp.getString());	// Get the expression
			System.out.println("The infix equivalent: " + exp.getInfixExpression());	// Get the infix equivalent	
		 	System.out.println("The result of the evaluation: " + exp.getResult() + "\n");	// Get the result value
		}
		// Close file
		scanner.close();
	}
}
