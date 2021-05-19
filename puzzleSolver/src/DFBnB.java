
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class DFBnB {

	//start node
	Node start;
	//finish node
	Node board_goal;
	//finish node
	outPut out;
	//if exist 2 0
	boolean exist ; 
	//num of vertexes
	int num = 0 ;

	public DFBnB(Node start, Node finish, outPut out,boolean exist)
	{
		this.exist = exist ;
		this.start = start;
		this.start.setParent(null);
		this.board_goal = finish;
		this.out = out; 
	}
	public String DFBnB_algo() 
	{
		String result = ""; 
		Node ans = null ;
		
		//create openList
		Hashtable<String,Node> H = new Hashtable<String,Node>();  
		//open list
		Stack<Node> myStack = new Stack<Node>();
		//help stack to put the items in reverse
		Stack<Node> myStack2 = new Stack<Node>();
		
		Node parent  = new Node(start.getState());
		
		int t = Integer.MAX_VALUE;
		
		myStack.add(parent);
		H.put(parent.toString(), parent);
		
		while(!myStack.empty()) 
		{
			//for the open list
			if(this.out.isOpen)
			printOpenList(H);
			parent = myStack.pop();
			//if the Node is marked
			if(parent.isOut) {
				H.remove(parent.toString());
			}else{
				parent.isOut = true ;
				myStack.add(parent);
				num++;
				//priority queue for sort the values up order
				//queue//operators
				Queue<Node> new_ = parent.findStates(exist);
				PriorityQueue<Node> my_queue = new PriorityQueue<Node>(new NodePriorityComparator());				
				for(Node a : new_) 
				{		
					a.setParent(parent);
					a.setToatal_price( (a.getCost() + parent.getToatal_price()));
					a.setHeru_value((new heurstic()).manhattan_algorithmD(a.getState(), board_goal.getState()));
					a.setF_G((int)(a.getToatal_price()*0.7)+ a.getHeru_value() );
					my_queue.add(a);
				}
				Iterator<Node> iter = my_queue.iterator();
				while(iter.hasNext()) {
					Node son = iter.next();
					if(son.getF_G() >= t)
					{
						//while the queue is not empty
						while(!my_queue.isEmpty()) {
							//if the current node in head queue is not the son
							//it say that there is someone in the queue is cheaper the son
							if(!my_queue.peek().toString().equals(son.toString())) {
								myStack2.add(my_queue.poll());
							}else {
								//else every one is >= to son
								//clear the whole queue
								my_queue.clear();
							}
						}
						while(!myStack2.isEmpty()) {
							my_queue.add(myStack2.pop());
						}
						iter = my_queue.iterator();
					}
					else if( H.get(son.toString()) != null &&  H.get(son.toString()).isOut){
						my_queue.remove(son);
						iter = my_queue.iterator();
					}else if(H.get(son.toString()) !=null &&  !H.get(son.toString()).isOut) {
						if(H.get(son.toString()).getF_G() <= son.getF_G()) {
							my_queue.remove(son);
							iter = my_queue.iterator();
						}else {
							myStack.remove(H.get(son.toString()));
							H.remove(son.toString());
						}
					}else if(son.isGoal(this.board_goal)) {						
						t = son.getF_G();
						ans = new Node(son);
						double end = System.currentTimeMillis();
						double sec = (end - solve.time) / 1000.0;
						this.out.setRuntime(sec);						
						//find the way of parent
						result = calculate(son);
						
						while(!my_queue.isEmpty()) {
							if(!my_queue.peek().toString().equals(son.toString())) {
								myStack2.add(my_queue.poll());
							}else {
								my_queue.clear();
							}
						}
						while(!myStack2.isEmpty()) {
							my_queue.add(myStack2.pop());
						}	
						iter = my_queue.iterator();	
					}
				}
				//insert in reverse into the stack
				Stack<Node> stack_temp = new Stack<Node>();
				while(!my_queue.isEmpty()) 
				{
					stack_temp.add(my_queue.poll());
				}
				while(!stack_temp.isEmpty()) 
				{
					Node temp = stack_temp.pop();
					myStack.add(temp);
					H.put(temp.toString(), temp);
				}
			}
		}
		this.write(ans,num,true);
		return result;
	}
	private void printOpenList(Hashtable<String, Node> openList) 
	{
		System.out.println();
		Set<String> my_Set = openList.keySet();
		for (String part : my_Set) {
			System.out.println(part);
			System.out.println("----Next---\n");
		}
	}
	private void write(Node node,int n,boolean has_path) 
	{
		calculate(node);
		out.setNum(n);
		this.out.writeToFile(has_path);	
	}	
	private String calculate(Node son) {
		//moves
		String moves="";
		//cost
		int cost = 0;
		Stack<Node> my_stack = new Stack<Node>();
		while(son!=null) 
		{
			my_stack.push(son);
			son = son.getParent();
		}
		String path ="";
		if (!my_stack.isEmpty()) {
			my_stack.pop();
		}
		path += "-------------start-------------\n";
		path += this.start.toString()+'\n';
		while(!my_stack.isEmpty()) 
		{	
			Node iter1 = my_stack.pop();
			if (my_stack.size() == 0)
				moves = moves + iter1.getMove();
			else
				moves = moves + iter1.getMove()+"-";
			cost += iter1.getCost();
			path += iter1.toString();
			path +="\n";
		}
		path+="--------------And The Goal Has Been Found------------------";
		out.setMoves(moves);
		out.setCost(cost);
		return path;
	}
}
