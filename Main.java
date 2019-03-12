/*
 * EBRU KIZILKİREN 
 * 150114026
 * Main Class
 * */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	
	public static final Board finalStateBoard = new Board("goal");
	public static final Board initialStateBoard = new Board("initial");
	
	public static String init = "xx111xxxx111xx111111111101111111111xx111xxxx111xx";
	public static char[] initChar = init.toCharArray();
	
	public static String goalStr = "xx000xxxx000xx000000000010000000000xx000xxxx000xx";
	public static char[] goalChar = goalStr.toCharArray();
	
	public static Stack<Board> frontier2 = new Stack<Board>();  
	
	public static List<Board> path = new ArrayList<Board>();
	
	public static boolean done = false;
	public static Board sol = null;
	public static Board min = null;
	public static Board slnDfs = null;
	
	public static long start;
	public static long end; 
	public static long timePassed=0;
	public static int numberOfSuboptimalSolutions=0;
	
	public static String options = "Please select the algorithm to solve Peg Solitaire \n"
			+ "a) Breadth First Search \n"
			+ "b) Depth First Search \n"
			+ "c) Iterative Deepening Search \n"
			+ "d) Depth First Search with Random Selection \n"
			+ "e) Depth First Search with a Node Selection Heuristic \n"
			+ "To finish the program please enter 0! \n";
	
	public static String selection;
	public static boolean okey = false;
	
	public static void main (String[] args) {
		
		
		Board board = null; // Initial board created.
		
		Scanner input = new Scanner(System.in); //To get selection 
		

		while(!okey) {
			System.out.println(options);
			selection = input.nextLine().toString();
			
			
			//All the try catch blocks is written to catch MemoryOutOf errors.
			
			if(selection.equals("a")) { //bfs algorithm
				System.out.println("a\n");
				board = new Board(initChar);
				board.optimality=computeOptimality(board);
				min=board;
				try {
					start = System.currentTimeMillis(); //When algorithm starts to work
					sol=bfs(board);	 //Sol is the solution of the bfs algorithm if it finishes execution without OutOfMemory error 
					end = System.currentTimeMillis(); //When algorithm finishes to work
					
					timePassed = (end-start); //To compute total time passed in milliseconds
					System.out.println("OPTIMUM SOLUTION! found in " + timePassed + " milliseconds!");;
				}catch (OutOfMemoryError e) {
					System.out.println("OutOfMemory error occured! You will see suboptimum solution!");
					
					end = System.currentTimeMillis(); //The time exception catches.
					
					timePassed = (end-start); //To compute total time passed in milliseconds. Previous one wont work just because the method bfs throws error.
					sol=min; //When OutOfMemory occurs, sol is the board which is including minimum remaining pegs.  
					System.out.println("SUB-OPTIMUM SOLUTION WITH REMAINING " + sol.optimality + " PEGS!");
					System.out.println("time: " + timePassed);
				}
				
				sol.printBoard(); //To print solution board that algorithm found.
				
				path=sol.getPathFromRoot(); //To obtain path from initial state to the solution that algorithm found
				
				printPath(path); //To print path from initial state to the solution that algorithm found
				
				//To reset all the parameters that we will use again.
				path.clear();
				board=null;
				min=null;
				numberOfSuboptimalSolutions=0;
				
			}else if (selection.equals("b")) { //dfs algorithm
				
				System.out.println("b\n");
				board = new Board(initChar);
				board.optimality=computeOptimality(board);
				min=board;
				
				
				try {
					start = System.currentTimeMillis(); //When algorithm starts to work
					sol=dfs(board);	//Sol is the solution of the dfs algorithm if it finishes execution without OutOfMemory error
					end = System.currentTimeMillis();//When algorithm finishes to work
					
					timePassed = (end-start);//To compute total time passed in milliseconds
					
				//	System.out.println("OPTIMUM SOLUTION! found in " + timePassed + " milliseconds!");
					System.out.println("OPTIMUM SOLUTION! found in " + timePassed/1000 + " seconds!");
				}catch (OutOfMemoryError e) {
					System.out.println("OutOfMemory error occured! You will see suboptimum solution!");

					
					end = System.currentTimeMillis(); //The time exception catches.
					
					timePassed = (end-start);//To compute total time passed in milliseconds. Previous one wont work just because the method bfs throws error.
					sol=min; //When OutOfMemory occurs, sol is the board which is including minimum remaining pegs.  
					System.out.println("SUB-OPTIMUM SOLUTION WITH REMAINING " + sol.optimality + " PEGS!");
					System.out.println("time: " + timePassed/1000 );
				}
				
				sol.printBoard(); //To print solution board that algorithm found.
				
				path=sol.getPathFromRoot(); //To obtain path from initial state to the solution that algorithm found
				
				printPath(path); //To print path from initial state to the solution that algorithm found
				
				//To reset all the parameters that we will use again.			
				path.clear();
				board=null;	
				min=null;
				numberOfSuboptimalSolutions=0;
	
				
			}else if (selection.equals("c")) { //ids algorithm
				
				System.out.println("c\n");
				board = new Board(initChar);
				board.optimality=computeOptimality(board);
				min=board;
				try {
					start = System.currentTimeMillis(); //When algorithm starts to work
					sol=ids(board);	//Sol is the solution of the ids algorithm if it finishes execution without OutOfMemory error
					end = System.currentTimeMillis();//When algorithm finishes to work
					
					timePassed = (end-start);//To compute total time passed in milliseconds
					
					System.out.println("OPTIMUM SOLUTION! found in " + timePassed + " milliseconds!");
				}catch (OutOfMemoryError e) {
					System.out.println("OutOfMemory error occured! You will see suboptimum solution!");
					
					end = System.currentTimeMillis();//The time exception catches.
					
					timePassed = (end-start);//To compute total time passed in milliseconds. Previous one wont work just because the method bfs throws error.
					sol=min; //When OutOfMemory occurs, sol is the board which is including minimum remaining pegs.  
					System.out.println("SUB-OPTIMUM SOLUTION WITH REMAINING " + sol.optimality + " PEGS!");
					System.out.println("time: " + timePassed);
				}
				
				sol.printBoard();//To print solution board that algorithm found.
				
				path=sol.getPathFromRoot(); //To obtain path from initial state to the solution that algorithm found
				
				printPath(path); //To print path from initial state to the solution that algorithm found
				
				//To reset all the parameters that we will use again.path.clear();
				board=null;	
				min=null;
				numberOfSuboptimalSolutions=0;
				
			}else if (selection.equals("d")) { //dfs random algorithm
				
				System.out.println("d\n");
				board = new Board(initChar);
				board.optimality=computeOptimality(board);
				min=board;
				try {
					start = System.currentTimeMillis(); //When algorithm starts to work
					sol=dfsRandomSelection(board);	//Sol is the solution of the dfsRandomSelection algorithm if it finishes execution without OutOfMemory error 
					end = System.currentTimeMillis();//When algorithm finishes to work
					
					timePassed = (end-start);//To compute total time passed in milliseconds
					
					System.out.println("OPTIMUM SOLUTION! found in " + timePassed + " milliseconds!");
				}catch (OutOfMemoryError e) {
					System.out.println("OutOfMemory error occured! You will see suboptimum solution!");
					
					end = System.currentTimeMillis();//The time exception catches.
					
					timePassed = (end-start);//To compute total time passed in milliseconds. Previous one wont work just because the method bfs throws error.
					sol=min;  //When OutOfMemory occurs, sol is the board which is including minimum remaining pegs.  
					System.out.println("SUB-OPTIMUM SOLUTION WITH REMAINING " + sol.optimality + " PEGS!");
					System.out.println("time: " + timePassed);
				}
				
				sol.printBoard();//To print solution board that algorithm found.
				
				path=sol.getPathFromRoot(); //To obtain path from initial state to the solution that algorithm found
				
				printPath(path); //To print path from initial state to the solution that algorithm found
				
				//To reset all the parameters that we will use again.path.clear();
				board=null;	
				min=null;
				numberOfSuboptimalSolutions=0;
				
				
			}else if (selection.equals("e")) { //dfs heuristic algorithm
				
				System.out.println("e\n");
				board = new Board(initChar);
				board.optimality=computeOptimality(board);
				min=board;
				try {
					start = System.currentTimeMillis(); //When algorithm starts to work
					sol=dfsHeuristic(board);	//Sol is the solution of the dfsHeuristic algorithm if it finishes execution without OutOfMemory errors 
					end = System.currentTimeMillis();//When algorithm finishes to work
					
					timePassed = (end-start);//To compute total time passed in milliseconds
					
					System.out.println("OPTIMUM SOLUTION! found in " + timePassed + " milliseconds!");
				}catch (OutOfMemoryError e) {
					System.out.println("OutOfMemory error occured! You will see suboptimum solution!");

					sol=min; //When OutOfMemory occurs, sol is the board which is including minimum remaining pegs.  
					System.out.println("SUB-OPTIMUM SOLUTION WITH REMAINING " + sol.optimality + " PEGS!");
					System.out.println("time: " + timePassed);
				}
				
				sol.printBoard();//To print solution board that algorithm found.
				
				path=sol.getPathFromRoot(); //To obtain path from initial state to the solution that algorithm found
				
				printPath(path); //To print path from initial state to the solution that algorithm found
				
				//To reset all the parameters that we will use again.path.clear();
				board=null;	
				min=null;
				numberOfSuboptimalSolutions=0;
				
			}else if(selection.equals("0")) {
				System.out.println("Program finished!\n");
				okey = true;
			}else {
				System.out.println("Program finished!\nWrong selection!");
				okey = true;		
			}	
			
		}
		
		input.close(); // closing scanner
		
	}
	
	public static Board bfs(Board init) {
		Queue<Board> queue = new LinkedList<Board>() {{add(init);}};
		Collection<Board> ch;
		Board board;
		numberOfSuboptimalSolutions=1;
		boolean done = false;
		Board solution=null;
		init.optimality= computeOptimality(init);
		min=init;
		
		while(!queue.isEmpty()&&!done) {
			if(queue.peek().getState().equals(goalChar)) {//If the board equal goal state then breaks loop and returns optimal solution
				board = queue.peek();
				board.optimality=computeOptimality(board);
				solution=board;
				return solution;
			}
			board=queue.poll();
			ch=expand(board);
			if(ch.isEmpty()) { //compares optimalities of minimum one and new found suboptimal
				board.optimality=computeOptimality(board);
				min=chooseMin(board, min);
				solution=min;		
				numberOfSuboptimalSolutions++;
//				System.out.println("Number Of Suboptimal Solutions: " + numberOfSuboptimalSolutions); 
				// you may check how many suboptimal solution found by making previous line uncomment
			}else {
				queue.addAll(ch);
			}
					
		}
		return solution;
	}
	
	public static Board dfs(Board init) {
		if (init.getState().toString().equals(goalStr)) {//If the board equal goal state then returns optimal solution
			System.out.println("SUCCESS!");
			slnDfs=init;
			init.printBoard();
		}else {
			Collection<Board> ch;
			ch=expand(init);
			if(!ch.isEmpty()) {
				Collections.reverse((List<Board>) ch);
				for(Board child : ch) {
					slnDfs = dfs(child);
				}
			}else { //compares optimalities of minimum one and new found suboptimal
				numberOfSuboptimalSolutions++;
	//			System.out.println("Number Of Suboptimal Solutions: " + numberOfSuboptimalSolutions); 
				// you may check how many suboptimal solution found by making previous line uncomment
				init.optimality=computeOptimality(init);
				min=chooseMin(init, min);
				slnDfs=min;
			}
			
		}
		
		return slnDfs;
	}	
	
	public static Board dfsHeuristic(Board init) {
		Stack<Board> frontier = new Stack<Board>();  
		Collection<Board> ch;
		frontier.add(init);
		boolean done = false;
		Board solution = null;
		init.optimality= computeOptimality(init);
		min=init;
		
		frontier.pop();
		frontier.addAll(expand(init));
	
		while (!done) {
			if(frontier.isEmpty()) {
				System.out.println("No more nodes in frontier => FAILURE");
                done = true;
			}else {
				Board board = chooseLeaf(frontier);
				String boardStr = new String(board.getState());
				if (boardStr.equals(goalStr)) { //If the board equal goal state then breaks loop and returns optimal solution
					System.out.println("SUCCESS FOUND!");
					board.optimality=computeOptimality(board);
					solution=board;
					board.printBoard();
					done=true;
					break;
				}else {
					ch=expand(board);
					if(!ch.isEmpty()) {
						frontier.addAll(ch);
					}else { //compares optimalities of minimum one and new found suboptimal
						numberOfSuboptimalSolutions++;
	//					System.out.println("Number Of Suboptimal Solutions: " + numberOfSuboptimalSolutions);
						// you may check how many suboptimal solution found by making previous line uncomment 
						board.optimality=computeOptimality(board);
						min=chooseMin(board, min);
						solution=min;
					}
				}
			}
			
		}
		return solution;
		
	}
	
	public static Board dfsRandomSelection(Board init) {
		Stack<Board> frontier = new Stack<Board>();  
		Collection<Board> ch;
		frontier.add(init);
		boolean done = false;
		Board solution = null;
		init.optimality= computeOptimality(init);
		min=init;
		
		frontier.pop();
		frontier.addAll(expandRandom(init));

		
		while (!done) {
			if(frontier.isEmpty()) {
				System.out.println("No more nodes in frontier => FAILURE");
                done = true;
			}else {
				Board board = chooseLeaf(frontier);
				String boardStr = new String(board.getState());
				if (boardStr.equals(goalStr)) {//If the board equal goal state then breaks loop and returns optimal solution
					System.out.println("SUCCESS FOUND!");
					board.optimality=computeOptimality(board);
					solution=board;
					board.printBoard();
					done=true;
					break;
				}else {
					ch=expandRandom(board);
					if(!ch.isEmpty()) {
						frontier.addAll(ch);
					}else { //compares optimalities of minimum one and new found suboptimal
						numberOfSuboptimalSolutions++;
	//					System.out.println("Number Of Suboptimal Solutions: " + numberOfSuboptimalSolutions); 
						// you may check how many suboptimal solution found by making previous line uncomment 
						board.optimality=computeOptimality(board);
						min=chooseMin(board, min);
						solution=min;
					}
				}
			}
			
		}
		return solution;
		
	}	
	
	//Iterative deepening
	public static Board ids(Board init) {
		Board result = null;
		for(int depth=0; depth<=30; depth++) {
			result=depthLimitedDfs(init, depth);
		}
		return result;
	}
	
	//Used in iterative deepening search
	public static Board depthLimitedDfs(Board init, int limit) {
		if (init.getState().toString().equals(goalStr)) {//If the board equal goal state then breaks loop and returns optimal solution
			System.out.println("SUCCESS!");
			slnDfs=init;
			init.printBoard();
		}else {
			Collection<Board> children;
			children=expand(init);
			if(!children.isEmpty()) {
				Collections.reverse((List<Board>) children);
				for(Board child : children) {
					slnDfs = depthLimitedDfs(child, limit-1);
				}
			}else { //compares optimalities of minimum one and new found suboptimal
				numberOfSuboptimalSolutions++;
	//			System.out.println("Number Of Suboptimal Solutions: " + numberOfSuboptimalSolutions); 
				// you may check how many suboptimal solution found by making previous line uncomment
				init.optimality=computeOptimality(init);
				min=chooseMin(init, min);
				slnDfs=min;
			}
			
		}
		
		return slnDfs;
	}
	
	//To print from root the found solution.
	public static void printPath(List<Board> path) {
		int step=0;
		for(Board b:path) {
			System.out.println("STEP " + step);
			b.printBoard();
			step++;
		}
	}
	
	//To set children. This is used for all the algorithms except random selection dfs algorithm.
	public static Collection<Board> expand(Board board) {
        board.setChildren();
        Collection<Board> children = board.getChildren();
        
        return children;
    }
	
	//To set children and shuffle them. This is used for random selection dfs algorithm.
	public static List<Board> expandRandom(Board board) {
        board.setChildren();
        List<Board> children = board.getChildren();
        Collections.shuffle(children);
        return children;
    }
	
	//To choose child which dfs works on
	public static Board chooseLeaf(Collection<Board> frontier) {
		return ((Stack<Board>) frontier).pop();
	}
		
	//This method is used for taking the board which has minimum number of pegs. To choose more optimal one. 
	public static Board chooseMin(Board b1, Board b2) {
		int op1 = b1.optimality;
		int op2 = b2.optimality;
			
		if(op1>op2) {
			return b2;
		}else {
			return b1;
		}
	}		
	
	//Optimality is determined as number of pegs in board. It means that number of ones. High number of pegs means that the less optimal. 
	public static int computeOptimality(Board board) {
		int optimality=0;
		char[] state = board.getState();
		
		for(int i=0; i<=48; i++) {
			if(state[i]=='1') {
				optimality+=1;
			}	
		}
		return optimality;
	}
	
}
