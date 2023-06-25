import java.util.*;
import java.io.*;
import java.awt.*;

// First Programmer  ->  Name: Ahmet Arda     Surname: Nalbant   No: 150121004
// Second Programmer ->  Name: Hasan  	      Surname: Özeren    No: 150121036
// Third Programmer  ->  Name: Niyazi Ozan    Surname: Ateþ      No: 150121991

public class LexicalAnalysis {

	public static ArrayList<String> removedList = new ArrayList<>();
	public static String nameOfTheFile = "";
	public static int emptySpots = 0;
	
	public static void main(String[] args) throws Exception{
		
		// Take file pointers
		Scanner tempScan = new Scanner(System.in);
        System.out.print("What is the file labeled as: ");
        String fileName = tempScan.nextLine();
        nameOfTheFile = fileName;
        File file = new File(fileName);
        
        if (file.exists()) {
            
        Scanner input = new Scanner(new File(fileName));
        Scanner errorFinder = new Scanner(new File(fileName));
		Scanner control = new Scanner(new File(fileName));
		
		
		File outputFile = new File("output.txt");
	    FileWriter writer = new FileWriter(outputFile);
	    
	    // Create arrays for reading and checking
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> controlList = new ArrayList<>();
		
		// Tokenize the control array and remove strings, braces, keywords and booleans
		tokenizeInput(control, controlList);
		
		// Declare Variables
		int indexLine = 0;
		int index1 = -1;
		int index2 = -1;
		int count = 0;
		String errorString = "";
		boolean errorFound = true;
		
		// Controlling the tokens after removing strings, braces, keywords and booleans
		while (indexLine < controlList.size()) {
			
			String temp[] = controlList.get(indexLine).split(" ");
			int i = 0;
			while (i < temp.length) {
				int indexPlace = 0;
				count = 0;
				while (indexPlace < temp[i].length()) {

				if (temp[i].charAt(indexPlace) == '.' && (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')) {
					count++;
					if (temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
						while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
							if (indexPlace + 1 == temp[i].length()) {
								indexPlace++;
								break;
							}
							indexPlace++;
						}
					}
					if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == 'E' || temp[i].charAt(indexPlace + 1) == 'e')) {
						indexPlace++;
						if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == '+' || temp[i].charAt(indexPlace + 1) == '-' || (( temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')))) {
							indexPlace += 2;
							if (indexPlace >= temp[i].length()) {
								count = 2;
							}
							while (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						if (indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')) {
							count = 2;
						}
					}
				}
				else if (((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+') && (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9')) || (temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') || ((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+') && indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '.')) {
					boolean isHex = false;
					boolean isBinary = false;
					count++;
					if (indexPlace + 1 < temp[i].length() && (temp[i].charAt(indexPlace + 1) == 'e' || temp[i].charAt(indexPlace + 1) == 'E' || temp[i].charAt(indexPlace + 1) == '.')) {
						indexPlace++;
					}
						if ((temp[i].charAt(indexPlace) == '-' || temp[i].charAt(indexPlace) == '+')){
							indexPlace++;
						}
						if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == 'x') {
							isHex = true;
							if (indexPlace + 2 >= temp[i].length()) {
								count = 2;
							}
							indexPlace += 2;
							while (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') || (temp[i].charAt(indexPlace + 1) >= 'A' && temp[i].charAt(indexPlace + 1) <= 'F') || (temp[i].charAt(indexPlace + 1) >= 'a' && temp[i].charAt(indexPlace + 1) <= 'f'))) {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == 'b') {
							isBinary = true;
							indexPlace += 2;
							if (indexPlace >= temp[i].length()) {
								count = 2;
							}
							while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '1') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {

							while (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) == 'e' || temp[i].charAt(indexPlace + 1) == 'E'))) {
								indexPlace++;
							}
							if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '.') {
								indexPlace++;
							}
						}
						if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '.') {
							indexPlace++;
							while (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == temp[i].length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace - 1 < temp[i].length() && !(temp[i].charAt(indexPlace - 1) >= '0' && temp[i].charAt(indexPlace - 1) <= '9')) {
								count = 2;
							}
						}
						if (indexPlace < temp[i].length() && (temp[i].charAt(indexPlace) == 'E' || temp[i].charAt(indexPlace) == 'e')) {
							if (indexPlace + 1 >= temp[i].length()) {
								count = 2;
							}
							indexPlace++;
							if (indexPlace < temp[i].length() && (temp[i].charAt(indexPlace) == '+' || temp[i].charAt(indexPlace) == '-' || (( temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')))) {
								indexPlace++;
								while (temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') {
									if (indexPlace + 1 == temp[i].length()) {
										indexPlace++;
										break;
									}
									indexPlace++;
								}
								indexPlace--;
							}
						}
						if (isHex && (indexPlace < temp[i].length() && !((temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9') || (temp[i].charAt(indexPlace) >= 'a' && temp[i].charAt(indexPlace) <= 'f') || (temp[i].charAt(indexPlace) >= 'A' && temp[i].charAt(indexPlace) <= 'F')))) {
							count = 2;
						}
						else if (isBinary && (indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) == '0' || temp[i].charAt(indexPlace) == '1'))) {
							count = 2;
						}
						else if (!isHex && !isBinary && indexPlace < temp[i].length() && !(temp[i].charAt(indexPlace) >= '0' && temp[i].charAt(indexPlace) <= '9')) {
							count = 2;
						}
					}
				else if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
					count++;
					indexPlace++;
					if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\\') {
						indexPlace++;
						if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
							indexPlace++;
							if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) == '\'') {
								indexPlace++;
							}
						}
					}
					else if (indexPlace + 1 < temp[i].length() && temp[i].charAt(indexPlace + 1) == '\'') {
						indexPlace++;
					}
				}
				else if (temp[i].charAt(indexPlace) == '.' || temp[i].charAt(indexPlace) == '+' || temp[i].charAt(indexPlace) == '-') {
					count++;
					indexPlace++;
					if (indexPlace < temp[i].length() && temp[i].charAt(indexPlace) != ' ') {
						count = 2;
					}
				}
				else if (temp[i].charAt(indexPlace) == '!' || temp[i].charAt(indexPlace) == '*' || temp[i].charAt(indexPlace) == '/' || temp[i].charAt(indexPlace) == ':' || temp[i].charAt(indexPlace) == '<' || temp[i].charAt(indexPlace) == '=' || temp[i].charAt(indexPlace) == '>' || temp[i].charAt(indexPlace) == '?' || (temp[i].charAt(indexPlace) >= 'a' && temp[i].charAt(indexPlace) <= 'z')) {
					String temps = "" + temp[i].charAt(indexPlace);
					int currentIndex = indexPlace;
					
					while (indexPlace + 1 < temp[i].length() && ((temp[i].charAt(indexPlace + 1) >= 'a' && temp[i].charAt(indexPlace + 1) <= 'z') || (temp[i].charAt(indexPlace + 1) >= '0' && temp[i].charAt(indexPlace + 1) <= '9') || temp[i].charAt(indexPlace + 1) == '.' || temp[i].charAt(indexPlace + 1) == '+' || temp[i].charAt(indexPlace + 1) == '-')) {
						indexPlace++;
						temps += temp[i].charAt(indexPlace);
					}
						count++;
				}
				else if (temp[i].charAt(0) == '~') {
					break;
				}
				else {
					count = 2;
				}
				
				indexPlace++;
			}
				if (count > 1 && errorFound) {
					index1 = indexLine;
					index2 = i;
					errorString = temp[index2];
					errorFound = false;
				}
				i++;
			}
			indexLine++;
		}
		
		if (!errorFound) { // Error finder and printer
			int i = 0;
			while (i < index1 && errorFinder.hasNext()) {
				String test = errorFinder.nextLine();
				i++;
			}
			String test = errorFinder.nextLine();
			index2 = test.indexOf(errorString);
		}
				indexLine = 1;
				boolean exitControl = true;
				String wrongString = "";
		while (input.hasNext() && exitControl) {
			
			String test = input.nextLine();
			int indexPlace = 0;
			
			while (indexPlace < test.length() && exitControl) {
				
				if (index1 == (indexLine-1) && index2 == indexPlace) {
					wrongString = "LEXICAL ERROR [" + (index1 + 1) + ":" + (test.indexOf(errorString) + 1) + "]: Invalid token \'" + errorString + "\'";
					exitControl = false;
				}
				else {
					if (test.charAt(indexPlace) == '(' ) {
					list.add("LEFTPAR " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == ')') {
					list.add("RIGHTPAR " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '[') {
					list.add("LEFTSQUAREB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == ']') {
					list.add("RIGHTSQUAREB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '{') {
					list.add("LEFTCURLYB " + indexLine + ":" + (indexPlace + 1));
				}
				else if (test.charAt(indexPlace) == '}') {
					list.add("RIGHTCURLYB " + indexLine + ":" + (indexPlace + 1));
				}

				else if (test.charAt(indexPlace) == '.' && (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9')) {
					list.add("NUMBER " + indexLine + ":" + (indexPlace + 1));
					if (test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
						while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
							if (indexPlace + 1 == test.length()) {
								indexPlace++;
								break;
							}
							indexPlace++;
						}
					}
					if (indexPlace + 1 < test.length() && (test.charAt(indexPlace + 1) == 'E' || test.charAt(indexPlace + 1) == 'e')) {
						indexPlace++;
						if (test.charAt(indexPlace + 1) == '+' || test.charAt(indexPlace + 1) == '-' || (( test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9'))) {
							indexPlace += 2;
							while (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
					}
				}
				else if (((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+') && (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9')) || (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') || ((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+') && indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '.')) {
					list.add("NUMBER " + indexLine + ":" + (indexPlace + 1));
					
					if (indexPlace + 1 < test.length() && (test.charAt(indexPlace + 1) == 'e' || test.charAt(indexPlace + 1) == 'E' || test.charAt(indexPlace + 1) == '.')) {
						indexPlace++;
					}
						if ((test.charAt(indexPlace) == '-' || test.charAt(indexPlace) == '+')){
							indexPlace++;
						}
						if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == 'x') {
							
							indexPlace += 2;
							while (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') || (test.charAt(indexPlace + 1) >= 'A' && test.charAt(indexPlace + 1) <= 'F') || (test.charAt(indexPlace + 1) >= 'a' && test.charAt(indexPlace + 1) <= 'f'))) {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == 'b') {
							
							indexPlace += 2;
							while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '1') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						else if (indexPlace + 1 < test.length() && test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {

							while (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
							if (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) == 'e' || test.charAt(indexPlace + 1) == 'E'))) {
								indexPlace++;
							}
							if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '.') {
								indexPlace++;
							}
						}
						if (indexPlace < test.length() && test.charAt(indexPlace) == '.') {
							indexPlace++;
							while (indexPlace < test.length() && test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
								if (indexPlace + 1 == test.length()) {
									indexPlace++;
									break;
								}
								indexPlace++;
							}
						}
						if (indexPlace < test.length() && (test.charAt(indexPlace) == 'E' || test.charAt(indexPlace) == 'e')) {
							indexPlace++;
							if (test.charAt(indexPlace ) == '+' || test.charAt(indexPlace ) == '-' || (( test.charAt(indexPlace ) >= '0' && test.charAt(indexPlace ) <= '9'))) {
								indexPlace++;
								while (test.charAt(indexPlace) >= '0' && test.charAt(indexPlace) <= '9') {
									if (indexPlace + 1 == test.length()) {
										indexPlace++;
										break;
									}
									indexPlace++;
								}
								indexPlace--;
							}
						}
					}
				else if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
					list.add("CHAR " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
					if (indexPlace < test.length() && test.charAt(indexPlace) == '\\') {
						indexPlace++;
						if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
							indexPlace++;
							if (indexPlace < test.length() && test.charAt(indexPlace) == '\'') {
								indexPlace++;
							}
						}
					}
					else if (indexPlace + 1 < test.length() && test.charAt(indexPlace + 1) == '\'') {
						indexPlace++;
					}
				}
				else if (indexPlace < test.length() && test.charAt(indexPlace) == '"') {
					list.add("STRING " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
					while (indexPlace < test.length() && test.charAt(indexPlace) != '"') {
						if (indexPlace + 1 < test.length() && test.charAt(indexPlace) == '\\' && test.charAt(indexPlace + 1) == '"') {
							indexPlace += 2;
						}
						else {
							indexPlace++;
						}
					}
				}
				else if (test.charAt(indexPlace) == '.' || test.charAt(indexPlace) == '+' || test.charAt(indexPlace) == '-') {
					list.add("IDENTIFIER " + indexLine + ":" + (indexPlace + 1));
					indexPlace++;
				}
				else if (test.charAt(indexPlace) == '!' || test.charAt(indexPlace) == '*' || test.charAt(indexPlace) == '/' || test.charAt(indexPlace) == ':' || test.charAt(indexPlace) == '<' || test.charAt(indexPlace) == '=' || test.charAt(indexPlace) == '>' || test.charAt(indexPlace) == '?' || (test.charAt(indexPlace) >= 'a' && test.charAt(indexPlace) <= 'z')) {
					String temp = "" + test.charAt(indexPlace);
					int currentIndex = indexPlace;
					
					while (indexPlace + 1 < test.length() && ((test.charAt(indexPlace + 1) >= 'a' && test.charAt(indexPlace + 1) <= 'z') || (test.charAt(indexPlace + 1) >= '0' && test.charAt(indexPlace + 1) <= '9') || test.charAt(indexPlace + 1) == '.' || test.charAt(indexPlace + 1) == '+' || test.charAt(indexPlace + 1) == '-')) {
						indexPlace++;
						temp += test.charAt(indexPlace);
					}
					
					if (temp.compareTo("define") == 0) {
						list.add("DEFINE " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("if") == 0) {
						list.add("IF " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("begin") == 0) {
						list.add("BEGIN " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("let") == 0) {
						list.add("LET " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("cond") == 0) {
						list.add("COND " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("true") == 0) {
						list.add("BOOLEAN " + indexLine + ":" + (currentIndex + 1));
					}
					else if (temp.compareTo("false") == 0) {
						list.add("BOOLEAN " + indexLine + ":" + (currentIndex + 1));
					}
					else {
						list.add("IDENTIFIER " + indexLine + ":" + (currentIndex + 1));
					}
					
				}
				else if (test.charAt(0) == '~') {
					break;
				}
				
				indexPlace++;
				}
				
			}
			indexLine++;
		}
		
		// Opens output file
		Desktop.getDesktop().open(outputFile);
		
// -------------------------------------------------------------PART2------------------------------------------------------------------------------------
		
		// We check whenever there is an Lexical Error.
		//If there is no error we go into the second part of this project.
		if (exitControl) {
			
		for (int j = 0 ; j < list.size() ; j++) {
			removedList.add(list.get(j)); // We copy the information of the Lexical Analyzer to a new ArrayList.
		}
		
		program(writer); // We start the program function. This will print the Syntax part (second part of the project).
		
		}
		else {
			// Printing on the console and output file of the Lexical Analyzer. This happends when there is an error in the Lexical part.
			int i = 0;
			while (i < list.size()) {
				System.out.println(list.get(i));
				writer.write(list.get(i)+ "\n");
				i++;
			}
			if (!exitControl) {
				System.out.println(wrongString);
				writer.write(wrongString);
			}
		}
		
		// Closing files
				input.close();
				errorFinder.close();
				control.close();
				writer.close();
				tempScan.close();
        }
        else {
            System.out.println("File not found: " + fileName);
        }
        
	}
	
	// Method to tokenize and remove strings, braces, keywords and booleans
    public static void tokenizeInput(Scanner input, ArrayList<String> copy) {
        input.useLocale(Locale.US);
        int i = 0;
        
        while (input.hasNext()) {
            copy.add(input.nextLine().replaceAll("[{}\\[\\]()]", " ").replaceAll("\\b(let|define|cond|if|begin|true|false)\\b", " ").replaceAll("\\\\\"", "").replaceAll("\"[^\"]*\"", ""));
            copy.set(i, copy.get(i).replaceAll("\\s+", " "));
            i++;
        }
    }
    
// -------------------------------------------------------------------PART2----------------------------------------------------------------------------------
    
    public static void program(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Program'.
    	System.out.println("<Program>");
    	writer.write("<Program>\n");
    	emptySpots++;
    	if (!removedList.isEmpty()) {
    		topLevelForm(writer);
    		program(writer);
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void topLevelForm(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'TopLevelForm'.
    	createSpots(writer, emptySpots);
    	System.out.println("<TopLevelForm>");
    	writer.write("<TopLevelForm>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		createSpots(writer, emptySpots);
    		System.out.println("LEFTPAR (()");
    		writer.write("LEFTPAR (()\n");
    		removedList.remove(0);
    		if (!removedList.isEmpty()) {
        		secondLevelForm(writer);
        		if (removedList.get(0).contains("RIGHTPAR")) {
        			createSpots(writer, emptySpots);
        			System.out.println("RIGHTPAR ())");
        			writer.write("RIGHTPAR ())\n");
        			removedList.remove(0);
        		}
        		else {
        			System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
        			writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
        			writer.close();
        			System.exit(0);
        		}
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
		return;
    }
    
    public static void secondLevelForm(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'SecondLevelForm'.
    	createSpots(writer, emptySpots);
    	System.out.println("<SecondLevelForm>");
    	writer.write("<SecondLevelForm>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("DEFINE")) {
    		definition(writer);
		}
    	else if (removedList.get(0).contains("LEFTPAR")){
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
        		removedList.remove(0);
        		if (!removedList.isEmpty()) {
            		funCall(writer);
            		if (removedList.get(0).contains("RIGHTPAR")) {
            			createSpots(writer, emptySpots);
            			System.out.println("RIGHTPAR ())");
            			writer.write("RIGHTPAR ())\n");
            			removedList.remove(0);
            		}
            		else {
            			System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
            			writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
            			writer.close();
            			System.exit(0);
            		}
            	}
        	}
    		else {
    			System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
    			writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
    			writer.close();
    			System.exit(0);
    		}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE' or '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE' or '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void definition(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Definition'.
    	createSpots(writer, emptySpots);
    	System.out.println("<Definition>");
    	writer.write("<Definition>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("DEFINE")) {
    		createSpots(writer, emptySpots);
    		System.out.println("DEFINE (" + findTheRightWord(removedList, 7) + ")");
    		writer.write("DEFINE (" + findTheRightWord(removedList, 7) + ")\n");
    		removedList.remove(0);
    		if (!removedList.isEmpty()) {
        		definitionRight(writer);
        	}
		}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void definitionRight(FileWriter writer) throws IOException { // This is the function of the Syntax Analyzer for the 'DefinitionRight'.
    	createSpots(writer, emptySpots);
    	System.out.println("<DefinitionRight>");
    	writer.write("<DefinitionRight>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER")) {
    		createSpots(writer, emptySpots);
    		System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
    		writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
    		removedList.remove(0);
    		expression(writer);
		}
    	else if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
    			removedList.remove(0);
    			if (removedList.get(0).contains("IDENTIFIER")) {
    				createSpots(writer, emptySpots);
        			System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
        			writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
        			removedList.remove(0);
        			if (!removedList.isEmpty()) {
            			argList(writer);
            			if (removedList.get(0).contains("RIGHTPAR")) {
                			createSpots(writer, emptySpots);
                			System.out.println("RIGHTPAR ())");
                			writer.write("RIGHTPAR ())\n");
                			removedList.remove(0);
                			if (!removedList.isEmpty()) {
                    			statements(writer);
                    		}
                		}
            			else {
            	    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
            	    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
            	    		writer.close();
            	    		System.exit(0);
            	    	}
            		}
        		}
    			else {
    	    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected");
    	    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected\n");
    	    		writer.close();
    	    		System.exit(0);
    	    	}
    		}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' or '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' or '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void argList(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'ArgList'.
    	createSpots(writer, emptySpots);
    	System.out.println("<ArgList>");
    	writer.write("<ArgList>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER")) {
    		createSpots(writer, emptySpots);
    		System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
    		writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
    		removedList.remove(0);
    		argList(writer);
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void statements(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Statements'.
    	createSpots(writer, emptySpots);
    	System.out.println("<Statements>");
    	writer.write("<Statements>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("DEFINE")) {
    		definition(writer);
    		statements(writer);
    	}
    	else if (removedList.get(0).contains("IDENTIFIER") || removedList.get(0).contains("NUMBER") || removedList.get(0).contains("CHAR") || removedList.get(0).contains("BOOLEAN") || removedList.get(0).contains("STRING") || removedList.get(0).contains("LEFTPAR")) {
    		expression(writer);
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE', 'IDENTIFIER', 'NUMBER', 'CHAR', 'BOOLEAN', 'STRING' or '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'DEFINE', 'IDENTIFIER', 'NUMBER', 'CHAR', 'BOOLEAN', 'STRING' or '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void expressions(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Expressions'.
    	createSpots(writer, emptySpots);
    	System.out.println("<Expressions>");
    	writer.write("<Expressions>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER") || removedList.get(0).contains("NUMBER") || removedList.get(0).contains("CHAR") || removedList.get(0).contains("BOOLEAN") || removedList.get(0).contains("STRING") || removedList.get(0).contains("LEFTPAR")) {
    		expression(writer);
    		expressions(writer);
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void expression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Expression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<Expression>");
    	writer.write("<Expression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER")) {
    		createSpots(writer, emptySpots);
    		System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
    		writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
    		removedList.remove(0);
    	}
    	else if (removedList.get(0).contains("NUMBER")) {
    		createSpots(writer, emptySpots);
    		System.out.println("NUMBER (" + findTheRightWord(removedList, 7) + ")");
    		writer.write("NUMBER (" + findTheRightWord(removedList, 7) + ")\n");
    		removedList.remove(0);
    	}
    	else if (removedList.get(0).contains("CHAR")) {
    		createSpots(writer, emptySpots);
    		System.out.println("CHAR (" + findTheRightWord(removedList, 5) + ")");
    		writer.write("CHAR (" + findTheRightWord(removedList, 5) + ")\n");
    		removedList.remove(0);
    	}
    	else if (removedList.get(0).contains("BOOLEAN")) {
    		createSpots(writer, emptySpots);
    		System.out.println("BOOLEAN" + findTheRightWord(removedList, 8) + ")");
    		writer.write("BOOLEAN" + findTheRightWord(removedList, 8) + ")\n");
    		removedList.remove(0);
    	}
    	else if (removedList.get(0).contains("STRING")) {
    		createSpots(writer, emptySpots);
    		System.out.println("STRING" + findTheRightWord(removedList, 7) + ")");
    		writer.write("STRING" + findTheRightWord(removedList, 7) + ")\n");
    		removedList.remove(0);
    	}
    	else if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
    			removedList.remove(0);
    			if (!removedList.isEmpty()) {
        			expr(writer);
        			if (removedList.get(0).contains("RIGHTPAR")) {
        				createSpots(writer, emptySpots);
            			System.out.println("RIGHTPAR ())");
            			writer.write("RIGHTPAR ())\n");
            			removedList.remove(0);
            		}
        			else {
                		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
                		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
                		writer.close();
                		System.exit(0);
                	}
        		}
    		}
        	else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER', 'NUMBER', 'CHAR', 'BOOLEAN', 'STRING' or '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER', 'NUMBER', 'CHAR', 'BOOLEAN', 'STRING' or '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void expr(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'Expr'.
    	createSpots(writer, emptySpots);
    	System.out.println("<Expr>");
    	writer.write("<Expr>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LET")) {
    		letExpression(writer);
    	}
    	else if (removedList.get(0).contains("COND")) {
    		condExpression(writer);
    	}
    	else if (removedList.get(0).contains("IF ")) {
    		ifExpression(writer);
    	}
    	else if (removedList.get(0).contains("BEGIN")) {
    		beginExpression(writer);
    	}
    	else if (removedList.get(0).contains("IDENTIFIER")) {
    		funCall(writer);
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'LET', 'COND', 'IF', 'BEGIN' or 'IDENTIFIER' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'LET', 'COND', 'IF', 'BEGIN' or 'IDENTIFIER' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void funCall(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'FunCall'.
    	createSpots(writer, emptySpots);
    	System.out.println("<FunCall>");
    	writer.write("<FunCall>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER")) {
    		createSpots(writer, emptySpots);
    		System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
    		writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
    		removedList.remove(0);
    		expressions(writer);
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void letExpression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'LetExpression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<LetExpression>");
    	writer.write("<LetExpression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LET")) {
    		createSpots(writer, emptySpots);
    		System.out.println("LET (" + findTheRightWord(removedList, 4) + ")");
    		writer.write("LET (" + findTheRightWord(removedList, 4) + ")\n");
    		removedList.remove(0);
    		letExpr(writer);
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'LET' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'LET' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void letExpr(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'LetExpr'.
    	createSpots(writer, emptySpots);
    	System.out.println("<LetExpr>");
    	writer.write("<LetExpr>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
        		removedList.remove(0);
        		if (!removedList.isEmpty()) {
        			varDefs(writer);
        			if (removedList.get(0).contains("RIGHTPAR")) {
        				createSpots(writer, emptySpots);
            			System.out.println("RIGHTPAR ())");
            			writer.write("RIGHTPAR ())\n");
                		removedList.remove(0);
                		if (!removedList.isEmpty()) {
                			statements(writer);
                		}
                	}
        			else {
        	    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' or 'IDENTIFIER' is expected");
        	    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' or 'IDENTIFIER' is expected\n");
        	    		writer.close();
        	    		System.exit(0);
        	    	}
        		}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else if (removedList.get(0).contains("IDENTIFIER")) {
    		if (removedList.get(0).contains("IDENTIFIER")) {
    			createSpots(writer, emptySpots);
    			System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
    			writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
        		removedList.remove(0);
        		if (removedList.get(0).contains("LEFTPAR")) {
        			createSpots(writer, emptySpots);
        			System.out.println("LEFTPAR (()");
        			writer.write("LEFTPAR (()\n");
            		removedList.remove(0);
            		if (!removedList.isEmpty()) {
            			varDefs(writer);
            			if (removedList.get(0).contains("RIGHTPAR")) {
            				createSpots(writer, emptySpots);
                			System.out.println("RIGHTPAR ())");
                			writer.write("RIGHTPAR ())\n");
                    		removedList.remove(0);
                    		if (!removedList.isEmpty()) {
                    			statements(writer);
                    		}
                    	}
            			else {
                    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
                    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
                    		writer.close();
                    		System.exit(0);
                    	}
                	}
            	}
        		else {
            		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
            		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
            		writer.close();
            		System.exit(0);
            	}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' or 'IDENTIFIER' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' or 'IDENTIFIER' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void varDefs(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'VarDefs'.
    	createSpots(writer, emptySpots);
    	System.out.println("<VarDefs>");
    	writer.write("<VarDefs>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
    			removedList.remove(0);
    			if (removedList.get(0).contains("IDENTIFIER")) {
    				createSpots(writer, emptySpots);
        			System.out.println("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")");
        			writer.write("IDENTIFIER (" + findTheRightWord(removedList, 11) + ")\n");
        			removedList.remove(0);
        			if (!removedList.isEmpty()) {
            			expression(writer);
            			if (removedList.get(0).contains("RIGHTPAR")) {
            				createSpots(writer, emptySpots);
                			System.out.println("RIGHTPAR ())");
                			writer.write("RIGHTPAR ())\n");
                			removedList.remove(0);
                			if (!removedList.isEmpty()) {
                    			varDef(writer);
                        	}
                    	}
            			else {
                    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
                    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
                    		writer.close();
                    		System.exit(0);
                    	}
                	}
            	}
    			else {
            		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected");
            		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IDENTIFIER' is expected\n");
            		writer.close();
            		System.exit(0);
            	}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	emptySpots--;
    	return;
    }
    
    public static void varDef(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'VarDef'.
    	createSpots(writer, emptySpots);
    	System.out.println("<VarDef>");
    	writer.write("<VarDef>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		varDefs(writer);
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void condExpression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'CondExpression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<CondExpression>");
    	writer.write("<CondExpression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("COND")) {
    		if (removedList.get(0).contains("COND")) {
    			createSpots(writer, emptySpots);
    			System.out.println("COND (" + findTheRightWord(removedList, 5) + ")");
    			writer.write("COND (" + findTheRightWord(removedList, 5) + ")\n");
    			removedList.remove(0);
    			if (!removedList.isEmpty()) {
        			condBranches(writer);
            	}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'COND' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'COND' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'COND' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'COND' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void condBranches(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'CondBranches'.
    	createSpots(writer, emptySpots);
    	System.out.println("<CondBranches>");
    	writer.write("<CondBranches>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
    			removedList.remove(0);
    			if (!removedList.isEmpty()) {
        			expression(writer);
        			if (!removedList.isEmpty()) {
            			statements(writer);
            			if (removedList.get(0).contains("RIGHTPAR")) {
            				createSpots(writer, emptySpots);
                			System.out.println("RIGHTPAR ())");
                			writer.write("RIGHTPAR ())\n");
                			removedList.remove(0);
                			if (!removedList.isEmpty()) {
                    			condBranch(writer); // Biz condBranches, condBranch e deðiþtirdik yollamadan bir önce bak!!!!
                        	}
                    	}
            			else {
            	    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
            	    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
            	    		writer.close();
            	    		System.exit(0);
            	    	}
                	}
            	}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots++;
    	return;
    }
    
    public static void condBranch(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'CondBranch'.
    	createSpots(writer, emptySpots);
    	System.out.println("<CondBranch>");
    	writer.write("<CondBranch>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("LEFTPAR")) {
    		if (removedList.get(0).contains("LEFTPAR")) {
    			createSpots(writer, emptySpots);
    			System.out.println("LEFTPAR (()");
    			writer.write("LEFTPAR (()\n");
    			removedList.remove(0);
    			if (!removedList.isEmpty()) {
        			expression(writer);
        			if (!removedList.isEmpty()) {
            			statements(writer);
            			if (removedList.get(0).contains("RIGHTPAR")) {
            				createSpots(writer, emptySpots);
                			System.out.println("RIGHTPAR ())");
                			writer.write("RIGHTPAR ())\n");
                			removedList.remove(0);
                    	}
            			else {
            	    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected");
            	    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: ')' is expected\n");
            	    		writer.close();
            	    		System.exit(0);
            	    	}
                	}
            	}
        	}
    		else {
        		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected");
        		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: '(' is expected\n");
        		writer.close();
        		System.exit(0);
        	}
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void ifExpression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'IfExpression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<IfExpression>");
    	writer.write("<IfExpression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IF ")) {
    		if (removedList.get(0).contains("IF ")) {
    			createSpots(writer, emptySpots);
    			System.out.println("IF (" + findTheRightWord(removedList, 3) + ")");
    			writer.write("IF (" + findTheRightWord(removedList, 3) + ")\n");
    			removedList.remove(0);
    			if (!removedList.isEmpty()) {
        			expression(writer);
        			if (!removedList.isEmpty()) {
            			expression(writer);
            			endExpression(writer);
                	}
            	}
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IF' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'IF' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static void endExpression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'EndExpression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<EndExpression>");
    	writer.write("<EndExpression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("IDENTIFIER") || removedList.get(0).contains("NUMBER") || removedList.get(0).contains("CHAR") || removedList.get(0).contains("BOOLEAN") || removedList.get(0).contains("STRING") || removedList.get(0).contains("LEFTPAR")) {
    		expression(writer);
    	}
    	else {
    		createSpots(writer, emptySpots);
    		System.out.println("__");
    		writer.write("__\n");
    	}
    	emptySpots--;
    	return;
    }
    
    public static void beginExpression(FileWriter writer) throws IOException{ // This is the function of the Syntax Analyzer for the 'BeginExpression'.
    	createSpots(writer, emptySpots);
    	System.out.println("<beginExpression>");
    	writer.write("<beginExpression>\n");
    	emptySpots++;
    	if (removedList.get(0).contains("BEGIN")) {
    		if (removedList.get(0).contains("BEGIN")) {
    			createSpots(writer, emptySpots);
    			System.out.println("IF (" + findTheRightWord(removedList, 6) + ")");
    			writer.write("IF (" + findTheRightWord(removedList, 6) + ")\n");
    			removedList.remove(0);
    			statements(writer);
        	}
    	}
    	else {
    		System.out.println("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'BEGIN' is expected");
    		writer.write("SYNTAX ERROR [" + removedList.get(0).substring(removedList.get(0).indexOf(" ")+1) + "]: 'BEGIN' is expected\n");
    		writer.close();
    		System.exit(0);
    	}
    	emptySpots--;
    	return;
    }
    
    public static String findTheRightWord(ArrayList <String> tempList, int index) throws FileNotFoundException { // This function is used to find the correct text from the input file.
    	Scanner CurrentInput = new Scanner(new File(nameOfTheFile));											 // We do this by using our knowledge of the index from the Lexical Analyzer part.
    	int line = Integer.parseInt(tempList.get(0).substring(index, tempList.get(0).indexOf(":")));
    	int start = Integer.parseInt(tempList.get(0).substring(tempList.get(0).indexOf(":") + 1));
    	String returnString = "";
    	
    	for (int i = 0 ; i < line ; i++) {
    		returnString = CurrentInput.nextLine();
    		if (i == line - 1) {
    			returnString = returnString.substring(start - 1);
    			int leftParIndex = returnString.indexOf("(");
    			int rightParIndex = returnString.indexOf(")");
    			int emptyIndex = returnString.indexOf(" ");
    			int minIndex = returnString.length();
    			
    			if (leftParIndex >= 0 && leftParIndex < minIndex) {
    				minIndex = leftParIndex;
    			}
    			if (rightParIndex >= 0 && rightParIndex < minIndex) {
    				minIndex = rightParIndex;
    			}
    			if (emptyIndex >= 0 && emptyIndex < minIndex) {
    				minIndex = emptyIndex;
    			}
    			
    			returnString = returnString.substring(0, minIndex);
    		}
    	}
    	
    	CurrentInput.close();
    	return returnString;
    }
    
    public static void createSpots(FileWriter writer, int empty) throws IOException { // This function is used to create the necessarily empty spaces before printing.
    	for (int i = 0 ; i < empty ; i++) {
    		System.out.print(" ");
    		writer.write(" ");
    	}
    	return;
    }
    
}
