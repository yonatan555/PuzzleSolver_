import java.util.Hashtable;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
public class IDA {
	//start node
	Node start;
	//finish node
	Node board_goal;
	//finish node
	outPut out;
	//exist 
	boolean exist;
	int num = 1  ;
	public IDA(Node start, Node finish, outPut out, boolean exist)
	{
		this.exist = exist ;
		this.start = start;
		this.start.setParent(null);
		this.board_goal = finish;
		this.out = out; 
	}
	public void IDA_algo() 
	{
		heurstic heur = new heurstic();
		//create openList
		Hashtable<String,Node> H = new Hashtable<String,Node>();  
		//create stack queue	
		Stack<Node> myStack = new Stack<Node>();
		int minF = 0 ;
		Node dad = new Node(this.start.getState());
		int t = heur.manhattan_algorithmD(dad.getState(),board_goal.getState());
		dad.setF_G(t + dad.getCost());
		t = dad.getF_G();
		dad.isOut = false; 
		while(t < Integer.MAX_VALUE){					
			minF = Integer.MAX_VALUE;
			H.put(dad.toString(), dad);
			myStack.add(dad);
			while(!myStack.isEmpty()) 
			{ 
				if(this.out.isOpen)				
					printOpenList(H);
				dad = myStack.pop();
				if(dad.isOut) {
					H.remove(dad.toString());
				}else {
					dad.isOut = true; 
					H.put(dad.toString(), dad);
					myStack.add(dad);
					Queue<Node> new_ = dad.findStates(exist);

					for(Node son : new_) {

						son.setToatal_price((int) (son.getCost()+ dad.getToatal_price()));

						son.setParent(dad);

						son.setF_G((int) (son.getToatal_price() + 1.64 * heur.manhattan_algorithmD(son.getState() , board_goal.getState())));

						if(son.getF_G() > t ) {
							minF = Math.min(minF, son.getF_G());
							continue;
						}

						if(H.get(son.toString())!= null && H.get(son.toString()).isOut) {
							continue;
						}

						if(H.get(son.toString()) != null && !H.get(son.toString()).isOut) {
							if(H.get(son.toString()).getF_G() > son.getF_G()) {
								myStack.remove(H.get(son.toString()));
								H.remove(son.toString());
							}
							else {
								continue;
							}
						}
						if(son.isGoal(board_goal)) {
							//							System.out.println(son);
							double end = System.currentTimeMillis();
							double sec = (end - solve.time) / 1000.0;
							this.out.setRuntime(sec);
							this.write(son,num,true);
							return;
						}
						myStack.add(son);
						H.put(son.toString(), son);
						num++;
					}
				}
			}
			dad.isOut = false;
			t = minF;
		}
		double end = System.currentTimeMillis();
		double sec = (end - solve.time) / 1000.0;
		this.out.setRuntime(sec);
		this.write(null,num,false);
		return;	
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
	private void calculate(Node son) {
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
		//		String path ="";
		if (!my_stack.isEmpty()) {
			my_stack.pop();
		}
		//path += "-------------start-------------\n";
		//path += this.start.toString()+'\n';
		while(!my_stack.isEmpty()) 
		{	
			Node iter1 = my_stack.pop();
			if (my_stack.size() == 0)
				moves = moves + iter1.getMove();
			else
				moves = moves + iter1.getMove()+"-";
			cost += iter1.getCost();
			//	path += iter1.toString();
			//path +="\n";
		}
		//	path+="--------------And The Goal Has Been Found------------------";
		out.setMoves(moves);
		out.setCost(cost);
		//		return path;
	}
}