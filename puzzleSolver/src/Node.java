import java.util.LinkedList;
import java.util.Queue;

public class Node {

	//movement
	private String move = ""; 
	//for IDA*
	public boolean isOut = false; 
	private Node parent;
	//STATE
	private int state[][];
	//STATE COST
	private int cost;
	//FOR A*  , IDA* BFBnB
	private int depth=0;
	//total price  = the price until the curr son
	private int toatal_price;

	//heuristic value 
	private int heru_value;
	//cost + heuristic
	private int F_G;

	public Node(Node son) 
	{
		this.move = son.move;
		this.isOut = son.isOut;
		this.parent = son.parent;
		this.state = this.copy_array(son.state);
		this.cost = son.cost;
		this.depth = son.depth;
		this.toatal_price = son.toatal_price;
		this.heru_value = son.heru_value; 
		this.F_G = son.F_G;
	}

	public Node(int[][] board) {
		this.state = board;
	}

	public int [][] getState(){
		return this.state;
	}
	public void setState(int [][] state){
		this.state = state;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	int getToatal_price() {
		return toatal_price;
	}
	public void setToatal_price(int toatal_price) {
		this.toatal_price = toatal_price;
	}
	public int getF_G() {
		return F_G;
	}
	public void setF_G(int f_G) {
		F_G = f_G;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int Depth) {
		this.depth = Depth;
	}	
	public int getHeru_value() {
		return heru_value;
	}
	public void setHeru_value(int heru_value) {
		this.heru_value = heru_value;
	}
	public Queue<Node> findStates(boolean exist) {
		boolean left_left = false;
		boolean up_up= false;
		boolean right_right = false;
		boolean down_down = false;
		Queue<Node> new_ = new  LinkedList<Node>();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if(state[i][j] == 0) {
					if(exist) {
						if(!left_left)
							left_left = moveLeft_Left(new_,i,j);
						if(!up_up)
							up_up = moveUp_Up(new_,i,j);
						if(!right_right )
							right_right = moveRight_Right(new_,i,j);
						if(!down_down )
							down_down = moveDown_Down(new_,i,j);
					}
					moveLeft(new_,i,j);
					moveUp(new_,i,j);
					moveRight(new_,i,j);
					moveDown(new_,i,j);
				}
			}
		}
		return new_;
	}
	private boolean moveDown_Down(Queue<Node> new_, int i, int j) {
		if(i > 0 && j < state[0].length-1 && state[i][j+1] == 0 ) {
			int new_array[][] = copy_array(state);
			swap(new_array, i,j, "up");
			swap(new_array, i,j+1, "up");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(7);
			copy_Node.setMove(state[i-1][j]+"&"+ state[i-1][j+1] +"D");
			new_.add(copy_Node);
			return true;	
		}
		else
			return false;
	}
	private boolean moveRight_Right(Queue<Node> new_, int i, int j) {
		if(j > 0 && i < state.length-1 && state[i+1][j] == 0 ) {
			int new_array[][] = copy_array(state);
			swap(new_array, i,j, "left");
			swap(new_array, i+1,j, "left");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(6);
			copy_Node.setMove(state[i][j-1]+"&"+ state[i+1][j-1] +"R");
			new_.add(copy_Node);
			return true;	
		}
		else
			return false;
	}
	private boolean moveUp_Up(Queue<Node> new_, int i, int j) {
		if(i < state.length-1 && j < state[0].length-1 && state[i][j+1] == 0 ) {
			int new_array[][] = copy_array(state);
			swap(new_array, i,j, "down");
			swap(new_array, i,j+1, "down");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(7);
			copy_Node.setMove(state[i+1][j]+"&"+ state[i+1][j+1] +"U");
			new_.add(copy_Node);
			return true;	
		}
		else
			return false;
	}
	private boolean moveLeft_Left(Queue<Node> new_, int i, int j)
	{
		if(j < state[0].length-1 && i < state.length-1 && state[i+1][j] == 0 ) {
			int new_array[][] = copy_array(state);
			swap(new_array, i,j, "right");
			swap(new_array, i+1,j, "right");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(6);
			copy_Node.setMove(state[i][j+1]+"&"+ state[i+1][j+1] +"L");
			new_.add(copy_Node);
			return true;	
		}
		else
			return false;
	}
	private void moveDown(Queue<Node> new_, int i, int j) {
		if(i > 0  && state[i-1][j] != 0) 
		{
			int new_array[][] = copy_array(state);
			swap(new_array,i,j,"up");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(5);
			copy_Node.setMove(state[i-1][j]+"D");
			new_.add(copy_Node);
		}		
	}
	private void moveRight(Queue<Node> new_, int i, int j) {
		if(j > 0  && state[i][j-1] != 0) 
		{
			int new_array[][] = copy_array(state);
			swap(new_array,i,j,"left");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(5);
			copy_Node.setMove(state[i][j-1]+"R");
			new_.add(copy_Node);
		}
	}
	private void moveUp(Queue<Node> new_, int i, int j) {
		if(i < state.length-1 && state[i+1][j] != 0) 
		{
			int new_array[][] = copy_array(state);
			swap(new_array,i,j,"down");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(5);
			copy_Node.setMove(state[i+1][j]+"U");
			new_.add(copy_Node);
		}
	}
	private void moveLeft(Queue<Node> new_,int i , int j)
	{
		if(j  < state[0].length-1 && state[i][j+1] != 0) 
		{
			int new_array[][] = copy_array(state);
			swap(new_array,i,j,"right");
			Node copy_Node = new Node(new_array);
			copy_Node.setCost(5);
			copy_Node.setMove(state[i][j+1]+"L");
			new_.add(copy_Node);
		}
	}
	private void swap(int[][] new_array,int i,int j,String move) 
	{
		int temp;
		switch(move) {
		case "left":
			//SAVE THE CURR 0
			temp = new_array[i][j-1];
			new_array[i][j-1] = new_array[i][j];
			new_array[i][j] = temp;
			break;
		case "right":
			temp = new_array[i][j+1];
			new_array[i][j+1] = new_array[i][j];
			new_array[i][j] = temp;
			break;
		case "up":
			temp = new_array[i-1][j];
			new_array[i-1][j] = new_array[i][j];
			new_array[i][j] = temp;
			break;
		case "down":
			temp = new_array[i+1][j];
			new_array[i+1][j] = new_array[i][j];
			new_array[i][j] = temp;
			break;
		default:
			System.err.println("cant move anywhere");	  
		}
	}
	private int[][] copy_array(int[][] curr_arr) {
		int arr [] []  =  new int [curr_arr.length][curr_arr[0].length]; 
		for(int i = 0;i < curr_arr.length; i++) {
			for(int j = 0 ;j < curr_arr[0].length; j++) {
				arr [i][j] = curr_arr[i][j];
			}
		}
		return arr;
	}
	public String toString() {
		String result ="";
		for(int i = 0;i<this.state.length;i++) {
			result +="{";
			for(int j = 0; j<this.state[0].length;j++) {
				result+=this.state[i][j] + " ,";
			}
			result+=  "}";
			result+='\n';
		}
		return result;
	}
	public boolean isGoal(Node goal) 
	{
		if(goal.toString().equals(this.toString())) {
			return true;
		}
		return false;
	}
}
