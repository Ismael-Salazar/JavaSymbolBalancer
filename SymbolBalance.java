import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SymbolBalance {
	
	
	public static void main(String args[]) throws FileNotFoundException{
	
		//Detects incorrect execution.
		if(!(args.length == 1)){
			System.out.println("Incorrect line argument, please try again.");
			System.exit(0);
		}
		
		File code = new File (args[0]);
		
		//Sets up scanner and uses delimiter to convert
		//the entire file into one string.
		Scanner myScanner = new Scanner(code);
		String fileContents = myScanner.useDelimiter("\\Z").next();
		myScanner.close();
		
		ArrayList<Character> symbols = new ArrayList<Character>();
		
		//Goes through the string by characters and will add 
		//symbols into a list.
		for(int i = 0; i<fileContents.length(); i++){
			char c = fileContents.charAt(i);
			
			switch(c){
			case '{':
			case '}':
			case '(':
			case ')':
			case '[':
			case ']':
			case '"':
			case '/':
			case '*':
				symbols.add(c);
			}
		}

		//Sets up a stack and booleans to help read 
		//comment symbols.
		MyStack<Character> theStack = new MyStack<Character>();
		boolean quotSeen = false;
		boolean bacSlaSeen = false;
		boolean star1Seen = false;
		boolean star2Seen = false;
		
		//Will go through the list of symbols and does the appropriate
		//action for each symbol.
		//quotSeen, bacSlaSeen, star1Seen, and star2Seen are used
		//used to ignore symbols if they are inside comments. 
		//The code functions by works by popping characters if
		//you reach a closed symbol, and then checks for inequality. 
		for(int j = 0; j < symbols.size(); j++){
			char c = symbols.get(j);
			
			switch(c){
			case '{':
			case '(':
			case '[':
				if(!quotSeen & (!bacSlaSeen & !star1Seen & !star2Seen)){	
					theStack.push(c);
				}
			case '}':
				if(theStack.isEmpty()){
					System.out.println("Error! " + c + " mismatch!");
					System.out.println("Error 1");
					System.exit(0);
				}
				if(!quotSeen & (!bacSlaSeen & !star1Seen & !star2Seen)){
					char t = theStack.pop();
					if(!(t == '{')){
						System.out.println("Error! " + c + " mismatch!");
						System.out.println("Error 2");
						System.exit(0);
					}
				}
			case ')':
				if(theStack.isEmpty()){
					System.out.println("Error! " + c + " mismatch!");
					System.out.println("Error 3");
					System.exit(0);
				}
				if(!quotSeen & (!bacSlaSeen & !star1Seen & !star2Seen)){
					char t = theStack.pop();
					if(!(t == '(')){
						System.out.println("Error! " + c + " mismatch!");
						System.out.println("Error 4");
						System.exit(0);
					}
				}
			case ']':
				if(theStack.isEmpty()){
					System.out.println("Error! " + c + " mismatch!");
					System.out.println("Error 5");
					System.exit(0);
				}
				if(!quotSeen & (!bacSlaSeen & !star1Seen & !star2Seen)){
					char t = theStack.pop();
					if(!(t == '[')){
						System.out.println("Error! " + c + " mismatch!");
						System.out.println("Error 6");
						System.exit(0);
					}
				}
			case'"':
				if(!bacSlaSeen & !star1Seen & !star2Seen){
					if(!quotSeen){
						quotSeen = true;
						theStack.push(c);
					}
					else{	
						quotSeen = false;
						theStack.pop();
					}
				}
			case'/':
				if(!quotSeen){
					if(!bacSlaSeen & !star1Seen & !star2Seen){
						bacSlaSeen = true;
						theStack.push(c);
						if(!(symbols.get(j+1) == null)){
							if(!(symbols.get(j+1) == '*')){
								System.out.println("Error! " + symbols.get(j+1) + " mismatch!");
								System.out.println("Error 7");
								System.exit(0);
							}
						}
					}
					else if(bacSlaSeen & star1Seen & star2Seen){
						theStack.pop();
						bacSlaSeen = false;
						star1Seen = false;
						star2Seen = false;
					}
				}
			case '*':
				if(!quotSeen){					
					if(bacSlaSeen & !star1Seen & !star2Seen){
						theStack.push(c);
						star1Seen=true;
					}
					else if(bacSlaSeen & star1Seen & !star2Seen){
						theStack.pop();
						star2Seen = true;
						if(!(symbols.get(j+1) == null)){
							if(!(symbols.get(j+1) == '/')){
								System.out.println("Error! " + symbols.get(j+1) + " mismatch!");
								System.out.println("Error 8");
								System.exit(0);
							}
						}
					}
				}
			}
		}
		//This checks if open symbols were added, in
		//which case the stack would pop all remaining elements 
		//and declares that symbol the mistake.
		if(!theStack.isEmpty()){
			char t = 0;
			while(!theStack.isEmpty()){
				t = theStack.pop();
			}
			System.out.println("Error! " + t + " mismatch!");
		}
		//message if no errors were found
		System.out.println("The symbols are all balanced :)");
	}
}
