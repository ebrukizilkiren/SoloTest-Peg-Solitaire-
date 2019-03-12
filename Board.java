/*
 * EBRU KIZILKİREN 
 * 150114026
 * Board Class
 * */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Board{

	private char[] state = new char[49]; 
	
	private Board parent; 
	private int cost;
	int optimality;
	int depth;
	ArrayList<Board> children=null;
	
		
	//This constructor method is used for initial state and final state.
	public Board(String s) {
		if(s=="initial") {
			for(int i=0; i<=48; i++) {
				if(i==0||i==1||i==5||i==6||i==7||i==8||i==12||i==13||i==35||i==36||i==40||i==41||i==42||i==43||i==47||i==48) {
					this.getState()[i]='x';
				}else if(i==2||i==3||i==4||i==9||i==10||i==11||(i>=14&&i<=23)||(i>=25&&i<=34)||i==27||i==37||i==38||i==39||i==44||i==45||i==46){
					this.getState()[i]='1';
				}else {
					this.getState()[i]='0';
				}
			}
		}else if(s=="goal") {
			for(int i=0;i<=48;i++) {
				if(i==0||i==1||i==5||i==6||i==7||i==8||i==12||i==13||i==35||i==36||i==40||i==41||i==42||i==43||i==47||i==48) {
					this.getState()[i]='x';
				}else if(i==2||i==3||i==4||i==9||i==10||i==11||(i>=14&&i<=23)||(i>=25&&i<=34)||i==27||i==37||i==38||i==39||i==44||i==45||i==46){
					this.getState()[i]='0';
				}else {
					this.getState()[i]='1';
				}
			}
		}	

	}	
	
	//This is the constructor method of the board class with given state parameter. //This used when initial board created.
	public Board(char[] state) {
		this.setState(state);
		this.cost=0;
		this.setChildren();
		this.depth=0;
	}
	
	//This is the constructor method of the board class with given parameters. This used when childs created.
	public Board (char[] state, Board parent, int stepCost) {
		this.setState(state);
		this.setParent(parent);
		this.cost=parent.cost+stepCost;
		this.depth=parent.depth+1;
	}
	
	//This method is used for printing the state of the board as grid
	public void printBoard() {
		for(int i=0;i<=48;i++) {
			System.out.print(getState()[i]+" ");
			if(i%7==6) {
				System.out.println();
			}
		}
		
		System.out.println();
	}

	//This method is used for movement. 
	public void movePeg(char[] state, int src,int mid, int dst) { 
		state[src]='0';
		state[mid]='0';
		state[dst]='1';
	}

	//This method is used for getting parent of the board.
	public Board getParent() {
		return parent;
	}

	//This method is used for setting parent of the board.
	public void setParent(Board parent) {
		this.parent = parent;
	}
	
	//This method is used for getting cost.
	public int getCost() {
		return this.cost;
	}
	
	//This method is used for setting cost.
	public void setCost(int cost) {
		this.cost = cost;
	}

	//This method is used for checking this board is root or not. This used only in getPathFromRoot() method 
	public boolean isRootBoard() {
        return parent == null;
    }
	
	//This method is used for finding the path from initial state to this state
	public List<Board> getPathFromRoot() {
        List<Board> path = new ArrayList<Board>();
        Board curr = this;
        while (!curr.isRootBoard()) {
            path.add(0, curr);
            curr = curr.getParent();
        }
        // ensure the root node is added
        path.add(0, curr);
        return path;
    }
	
	//This method is used for getting state information
	public char[] getState() {
		return state;
	}

	//This method is used for setting state information
	public void setState(char[] state) {
		this.state = state;
	}
	
	//This method is used for getting all the children
	public List<Board> getChildren(){ 
		return children;
	}
	
	//This method is used for creating all the children of the current board.
	public void setChildren(){ 
		ArrayList<Board> actions = new ArrayList<Board>();
		char plus7='x',plus14='x',plus1='x',plus2='x',minus1='x',minus2='x',minus7='x',minus14='x',own;
		
		String stateStr = new String(this.getState());
		char[] stateCharArray = stateStr.toCharArray();
		char[] statenew = stateStr.toCharArray();		
		
		for(int j=2;j<47;j++) {
			int costAction=this.getCost();
			own=stateCharArray[j];			
			
			if(own=='x') {
				continue;
			}
			if(j>=14) {
				minus7=stateCharArray[j-7];
				minus14=stateCharArray[j-14];
				
			}
			if(j<=33) {
				
				plus7=stateCharArray[j+7];
				plus14=stateCharArray[j+14];
				
			}
				
			plus1=stateCharArray[j+1];
			plus2=stateCharArray[j+2];

			minus1=stateCharArray[j-1];
			minus2=stateCharArray[j-2];
					
			if(own=='1' && plus7=='1' && plus14=='0') { //move down
				
				movePeg(statenew, j, j+7, j+14);
				costAction+=j+j+14;
				
				actions.add(new Board(statenew, this, costAction));

			}else if(own=='1' && plus1=='1' && plus2=='0') { //move right
				if(j%7==5||j%7==6) {
					continue;
				}
				movePeg(statenew, j, j+1, j+2);
				costAction+=j+j+2;
				
				actions.add(new Board(statenew, this, costAction));

			}else if(own=='1' && minus1=='1' && minus2=='0') { //move left
				if(j%7==0||j%7==1) {
					continue;
				}
				movePeg(statenew, j, j-1, j-2);
				costAction+=j+j-2;
				
				actions.add(new Board(statenew, this, costAction));

			}else if (own=='1' && minus7=='1' && minus14=='0') { //move up
				movePeg(statenew, j, j-7, j-14);
				costAction+=j+j-7;
				
				actions.add(new Board(statenew, this, costAction));
			}else {						
//				System.out.println("no movement");
			}
			statenew=stateStr.toCharArray();
			this.setState(stateStr.toCharArray());
        }
		if(!actions.isEmpty()) {
			Collections.sort(actions, Board.sortDescChildren);
		}
		
		this.children=actions;
	}
	
	
	// This method is used for sorting children descending order. 
	public static Comparator<Board> sortDescChildren = new Comparator<Board>() {
	
		public int compare(Board b1, Board b2) {
			int cost1 = b1.getCost();
			int cost2 = b2.getCost();
			
			return cost2-cost1;
		}		
	}; 
	
}
