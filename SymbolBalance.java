import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SymbolBalance {
	
	
	public static void main(String args[]) throws FileNotFoundException{
	
		//checks if correct line argument was given
		if(!(args.length == 1)){
			System.out.println("Incorrect line argument, please try again.");
			System.exit(0);
		}
		
		File code = new File (args[0]);
		
		//Sets up scanner and uses delimiter to convert
		//the entire file into one string
		Scanner myScanner = new Scanner(code);
		String fileContents = myScanner.useDelimiter("\\Z").next();
		myScanner.close();
		
		ArrayList<Character> symbols = new ArrayList<Character>();
		
		//will go through string by character and will add char
		//into an arraylist if it equals one of the cases
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

		//sets up stack and booleans to help with 
		//comments in order to ignore the inside of the 
		//comments
		MyStack<Character> theStack = new MyStack<Character>();
		boolean quotSeen = false;
		boolean bacSlaSeen = false;
		boolean star1Seen = false;
		boolean star2Seen = false;
		
		//Will go through arraylist of symbols one by one
		//and will do appropriate action for each symbol.
		//The !quotSeen and (!bacSlaSeen & !star1Seen & !star2Seen)
		//portions are used to ignore symbols if they are inside
		//comments. The code checks .isEmpty so that if the stack is
		//empty, you know you have an error right away, because it will
		//have nothing to compare it to.
		//I also added Error markers (i.e. Error 3) just to help pinpoint 
		//where the error occured. The rest works by popping the char if
		//you reach a closed symbol, and checks for inequality. The 
		//symbol.get(j+1) ensures that /* and */ are pairs
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
		//which case it would pop all the way until the
		//bottom and declare that symbol the mistake
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
