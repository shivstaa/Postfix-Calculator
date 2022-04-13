import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author: SHIV SHAH
 * FILENAME: Shah_Shiv_stack.java
 */
public class stack<TemplateType extends Comparable<TemplateType>> {
	private stackNode<TemplateType> head; //head pointer
	private int size; //variable size that will be used for the size of the stack at a given point and time
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Menu(); //calls the menu method
		String storeInput = scan.next(); //stores the user's input
		//create object instance with the Stringtokenizer class that takes in the String storeInput and uses delimiters for multiple mathematical operators
		StringTokenizer tokenizer = new StringTokenizer(storeInput, "+-*/()",true); 
		CalculateStack(storeInput, tokenizer); //calls the CalculateStack method which will take the user's input and convert it into a postfix expression and evaluate
	}
	
	/**
	 * adds an element to the stack at the top
	 * @param item --> element added
	 */
	public void Push(TemplateType item) {
		stackNode<TemplateType> newNode = new stackNode<TemplateType>(item); //object newNode of the TemplateType
		newNode.setNext(this.head); //sets the next element to the head
		this.head = newNode; //sets the head to the node
		this.size++; //increments size that tracks the length of the stack
	}
	
	/**
	 * Removes the most recent element added to the stack at the top (Last in, First out)
	 * @throws NullPointerException --> if there is nothing to delete from the stack, throws the exception
	 */
	public void Pop() throws NullPointerException {
		if(this.head == null) {
			throw new NullPointerException("ERR: Can't delete from an empty stack");
		}
		stackNode<TemplateType> storeHead = head; //sets a variable that will be discarded to the head
		this.head = this.head.getNext(); //sets the head to the next value in the stack (top element removed, next element down is now head)
		this.size--; //decrements size that tracks the length of the stack
	}
	
	/**
	 * @return the head's information at the current node of the stack 
	 */
	public TemplateType Peek() {
		return this.head.getData();
		
	}
	/**
	 * runs like a getter for variable size
	 * @return size
	 */
	public int Count() {
		return this.size;
	}
	
	/**
	 * 1) checks the entire string that the user inputs to see if there are a matching number of parentheses
	 * 2) converts the infix string to a postfix string
	 * 3) evaluates the postfix equation and displays the output
	 * @param storeInput --> input from the user
	 * @param tokenizer  --> string that stores the tokenized output of the String storeInput
	 */
	public static void CalculateStack(String storeInput, StringTokenizer tokenizer) {
		stack<String> stack = new stack<String>(); //creates a stack of the type String
		String convertChar; //used to store a character to String conversion
		
		System.out.print("\nRunning parentheses check... ... ... ");
		for (int i = 0; i < storeInput.length(); i++) { //loops through the entire length of the user input
			if(storeInput.charAt(i) == '(') { //checks if the individual character is equal to the instance of an open parenthesis
				convertChar = Character.toString(storeInput.charAt(i)); //stores character and converts to String
				stack.Push(convertChar); //pushes onto stack
			}
			else if(storeInput.charAt(i) == ')') { //checks if the individual character is equal to the instance of a closing parenthesis
				stack.Pop(); //pops the opening
			} 
		}
		
		if (stack.Count() == 0) { //checks if the size of the stack is 0 (equal # of opening and closing parentheses)
			System.out.println("OK!\n");
			System.out.println("Converting to postfix... ... ...\n");
			
			String tokenFromUser = ""; //string that will be used to store individual tokens of the user input
			String postFixEq = ""; //string that will be used to store the postfix equation calculated

			// loop that runs through the entire String tokenizer as long as there is another token 
			while(tokenizer.hasMoreTokens()) {
				tokenFromUser = tokenizer.nextToken(); //string that stores the next token from String tokenizer
				
				//check for open parenthesis first
				if(tokenFromUser.equals("(")) {				
					stack.Push(tokenFromUser); //push paranthesis onto stack
					
					while(tokenizer.hasMoreTokens()) { //loop that runs through the entire String tokenizer from the next token in the String (after opening paranthesis)
						tokenFromUser = tokenizer.nextToken();
						
						//checks if token is an integer (true or false)
						if(isNumber(tokenFromUser)) { 
							postFixEq+= tokenFromUser + " "; //adds to postfix equation
						}
						//checks if token is another opening parenthesis
						else if (tokenFromUser.equals("(")) { 
							stack.Push(tokenFromUser);
						}
						//checks if token is a multiplication operator
						else if(tokenFromUser.equals("*")) {
							if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("*")) { //left-right precedence: multiplication symbol at top of stack
								
								postFixEq+= stack.Peek() + " "; //adds multiplication operator on the stack to the postfix equation								
								stack.Pop(); //pops the multiplication operator
								stack.Push(tokenFromUser); //pushes the other multiplication operator (not in the stack yet) to the top of the stack 
							}							
							else if(stack.Peek().equals("/")) { //left-right precedence: division symbol at top of stack
					
								postFixEq+= stack.Peek() + " "; //adds division operator on the stack to the postfix equation
								stack.Pop(); //pops the division operator
								stack.Push(tokenFromUser); //pushes multiplication operator to the top of the stack
							}
							else { //if there is no multiplication or division operator at the top of the stack, pushes multiplication operator to the top of the stack
								stack.Push(tokenFromUser);
							}
						}
						//checks if token is a division operator
						else if(tokenFromUser.equals("/")) { 
							if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("*")) { //left-right precedence: division symbol at top of stack
								
								postFixEq+= stack.Peek() + " ";								
								stack.Pop();
								stack.Push(tokenFromUser);
							}							
							else if(stack.Peek().equals("/")) { //left-right precedence: multiplication symbol at top of stack
					
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else { //if there is no multiplication or division operator at the top of the stack, pushes division operator to the top of the stack
								stack.Push(tokenFromUser);
							}
						}
						//checks if token is an addition operator
						else if (tokenFromUser.equals("+")) {
							
							if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("*")) { //hierarchy precedence: multiplication operator at top of the stack
								
								postFixEq+= stack.Peek() + " "; //adds multiplication opreator on the stack to the postfix equation
								stack.Pop(); //pops the multiplication operator
								stack.Push(tokenFromUser); //pushes the addition operator to the top of the stack
							}							
							else if(stack.Peek().equals("/")) { //hierarchy precedence: division operator at the top of the stack
					
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("+")) { //left-right precedence: addition symbol at top of stack
								postFixEq+= stack.Peek() + " "; //adds addition operator to the top of the stack
								stack.Pop(); //pops addition operator
								stack.Push(tokenFromUser); //pushes other addition operator (not in the stack yet) to the top of the stack
							}
							else if(stack.Peek().equals("-")) { //left-right precedence: subtract symbol at top of stack
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else { //if there is no multiplication, division, addition, or subtraction operator at the top of the stack, pushes the addition operator to the top of the stack
								stack.Push(tokenFromUser);
							}

						}
						//checks if token is a subtraction operator
						else if(tokenFromUser.equals("-")) {
							if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("*")) { //hierarchy precedence: multiplication operator at the top of the stack
								
								postFixEq+= stack.Peek() + " ";		
								stack.Pop();
								stack.Push(tokenFromUser);
							}							
							else if(stack.Peek().equals("/")) { //hierarchy precedence: division operator at the top of the stack
					
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("-")) { //left-right precedence: subtract symbol at the top of the stack
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else if(stack.Peek().equals("+")) { //left-right precedence: addition symbol at the top of the stack
								postFixEq+= stack.Peek() + " ";
								stack.Pop();
								stack.Push(tokenFromUser);
							}
							else { //if there is no multiplication, division, addition, or subtraction operator at the top of the stack, pushes the subtraction operator to the top of the stack
								stack.Push(tokenFromUser); 
							}
						//checks if token is a closing parenthesis
						} 
						else if(tokenFromUser.equals(")")) {
							break; //breaks from the running opening parenthesis while loop
						}
						
					}
				}
				//check for closing parenthesis
				if(tokenFromUser.equals(")")) {
					while(!stack.Peek().equals("(")) { //loops through the stack until pointer points to an opening parenthesis
						postFixEq+=stack.Peek() + " "; //adds operator in the stack to the postfix equation
						stack.Pop(); //pops operator in the stack
					}
					//after while loop exits at opening parenthesis, check if top of stack equals to the opening parenthesis
					if(stack.Peek().equals("(")) { 
						stack.Pop(); //pop it if condition is true
					}
				}

				 //checks if token is an integer (true or false)
				if(isNumber(tokenFromUser)) { 
					postFixEq+= tokenFromUser + " "; //adds to postfix equation
				}
				//checks if token is another opening parenthesis
				else if (tokenFromUser.equals("(")) { 
					stack.Push(tokenFromUser);
				}
				//checks if token is a multiplication operator
				else if(tokenFromUser.equals("*")) {
					if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("*")) { //left-right precedence: multiplication symbol at top of stack
						
						postFixEq+= stack.Peek() + " "; //adds multiplication operator on the stack to the postfix equation								
						stack.Pop(); //pops the multiplication operator
						stack.Push(tokenFromUser); //pushes the other multiplication operator (not in the stack yet) to the top of the stack 
					}							
					else if(stack.Peek().equals("/")) { //left-right precedence: division symbol at top of stack
			
						postFixEq+= stack.Peek() + " "; //adds division operator on the stack to the postfix equation
						stack.Pop(); //pops the division operator
						stack.Push(tokenFromUser); //pushes multiplication operator to the top of the stack
					}
					else { //if there is no multiplication or division operator at the top of the stack, pushes multiplication operator to the top of the stack
						stack.Push(tokenFromUser);
					}
				}
				//checks if token is a division operator
				else if(tokenFromUser.equals("/")) { 
					if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("*")) { //left-right precedence: division symbol at top of stack
						
						postFixEq+= stack.Peek() + " ";								
						stack.Pop();
						stack.Push(tokenFromUser);
					}							
					else if(stack.Peek().equals("/")) { //left-right precedence: multiplication symbol at top of stack
			
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else { //if there is no multiplication or division operator at the top of the stack, pushes division operator to the top of the stack
						stack.Push(tokenFromUser);
					}
				}
				//checks if token is an addition operator
				else if (tokenFromUser.equals("+")) {
					
					if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("*")) { //hierarchy precedence: multiplication operator at top of the stack
						
						postFixEq+= stack.Peek() + " "; //adds multiplication opreator on the stack to the postfix equation
						stack.Pop(); //pops the multiplication operator
						stack.Push(tokenFromUser); //pushes the addition operator to the top of the stack
					}							
					else if(stack.Peek().equals("/")) { //hierarchy precedence: division operator at the top of the stack
			
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("+")) { //left-right precedence: addition symbol at top of stack
						postFixEq+= stack.Peek() + " "; //adds addition operator to the top of the stack
						stack.Pop(); //pops addition operator
						stack.Push(tokenFromUser); //pushes other addition operator (not in the stack yet) to the top of the stack
					}
					else if(stack.Peek().equals("-")) { //left-right precedence: subtract symbol at top of stack
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else { //if there is no multiplication, division, addition, or subtraction operator at the top of the stack, pushes the addition operator to the top of the stack
						stack.Push(tokenFromUser);
					}

				}
				//checks if token is a subtraction operator
				else if(tokenFromUser.equals("-")) {
					if(stack.Count() == 0) { //pushes to the top of the stack if the stack length is 0
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("*")) { //hierarchy precedence: multiplication operator at the top of the stack
						
						postFixEq+= stack.Peek() + " ";		
						stack.Pop();
						stack.Push(tokenFromUser);
					}							
					else if(stack.Peek().equals("/")) { //hierarchy precedence: division operator at the top of the stack
			
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("-")) { //left-right precedence: subtract symbol at the top of the stack
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else if(stack.Peek().equals("+")) { //left-right precedence: addition symbol at the top of the stack
						postFixEq+= stack.Peek() + " ";
						stack.Pop();
						stack.Push(tokenFromUser);
					}
					else { //if there is no multiplication, division, addition, or subtraction operator at the top of the stack, pushes the subtraction operator to the top of the stack
						stack.Push(tokenFromUser); 
					}
				}
				//checks if there are anymore tokens to go through
				if(tokenizer.hasMoreTokens() == false) { 
					while(stack.Count() != 0) { //loop until stack is empty
						postFixEq+=stack.Peek() + " "; //add remaining operators to the postfix equation
						stack.Pop(); //pop all remaining operators
					}
				}
			}

			System.out.println("Equation::> " + postFixEq); //displays postfix equation
			System.out.println("\nEvaluating... ... ...\n");
			
			int storeFirstInt; //stores first integer used for mathematical operations
			int storeSecondInt; //stores second integer used for mathematical operations
			String resultString; //stores String that is used to get the peek from the stack; also will be used for the result
			String storeString2; //stores String that is used to get the peek from the stack

			String[] var = postFixEq.split(" "); //creates string array that contains the split characters from the postfix equation (split by space)
			
			for(int i = 0; i < var.length; i++) { //loop that runs until i is at the string array length
				//checks if element is an integer
				if(isNumber(var[i])) {
					stack.Push(var[i]); //pushes to the top of the stack
				}
				//checks if element is an addition operator
				else if(var[i].equals("+")) {
					resultString = stack.Peek(); //sets resultString to the top element from the stack
					stack.Pop(); //pops the stack once
					storeString2 = stack.Peek(); //sets storeString2 to the top element from the stack
					stack.Pop(); //pops the stack again
					storeFirstInt = Integer.parseInt(resultString); //parses resultString to an integer
					storeSecondInt = Integer.parseInt(storeString2); //parses storeString2 to an integer
					resultString = Integer.toString(storeSecondInt + storeFirstInt); //adds the integers and uses toString to store in resultString 
					stack.Push(resultString); //pushes resultString to the top of the stack                   
				}
				//checks if the element is a subtraction operator
				else if(var[i].equals("-")) {
					resultString = stack.Peek();
					stack.Pop();
					storeString2 = stack.Peek();
					stack.Pop();
					storeFirstInt = Integer.parseInt(resultString);
					storeSecondInt = Integer.parseInt(storeString2);
					resultString = Integer.toString(storeSecondInt-storeFirstInt); //subtracts SecondInt from FirstInt and uses toString to store in resultString
					stack.Push(resultString); //pushes resultString to the top of the stack
				}
				//checks if the element is a multiplication operator
				else if(var[i].equals("*")) {
					resultString = stack.Peek();
					stack.Pop();
					storeString2 = stack.Peek();
					stack.Pop();
					storeFirstInt = Integer.parseInt(resultString);
					storeSecondInt = Integer.parseInt(storeString2);
					resultString = Integer.toString(storeSecondInt * storeFirstInt); //multiplies integers and uses toString to store in resultString
					stack.Push(resultString); //pushes resultString to the top of the stack
				}
				//checks if the element is a division operator
				else if(var[i].equals("/")) {
					resultString = stack.Peek();
					stack.Pop();
					storeString2 = stack.Peek();
					stack.Pop();
					storeFirstInt = Integer.parseInt(resultString);
					storeSecondInt = Integer.parseInt(storeString2);
					resultString = Integer.toString(storeSecondInt/storeFirstInt); //divides SecondInt by FirstInt and uses toString to store in resultString
					stack.Push(resultString); //pushes resultString to the top of the stack
				}
			}
			//sets resultString to the top value in the stack (result value after for loop is exited)
			resultString = stack.Peek();

			System.out.println("Result = " + resultString);
		}
		
		else { //if count did not equal 0 (no equal # of opening and closing parentheses)
			System.out.println("\nERR: Syntax- Paranthesis Mismatch");
		}
	}
	
	/**
	 * Checks if the string that was tokenized can be an integer
	 * @param tokenFromUser --> string taken in
	 * @return "true" or "false" statement based on whether it can be parsed to an integer or not
	 */
	public static boolean isNumber(String tokenFromUser) {
		if(tokenFromUser == null) { //checks if it is null (security statement)
			return false;
		}
		try { //checks if it can be parsed to an integer
			int x = Integer.parseInt(tokenFromUser);
		} //if it cannot be parsed to an integer, returns false
		catch (NumberFormatException x) {
			return false;
		}
		return true; //returns true if the try situation passes successfully
	}
	
	/**
	 * Prints out statements that display a welcome message and asks the user for their input
	 */
	public static void Menu() {
		System.out.println("Postfix Calculator!\n");
		System.out.println("Please input an expression (no spaces) to solve (multiply, divide, add, or subtract)");
		System.out.print("::> ");
	}
}
