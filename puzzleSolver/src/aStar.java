import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
public class aStar   {
	//start node
	Node start;
	//finish node
	Node board_goal;
	//finish node
	outPut out;
	//exist 
	boolean exist;
	int num = 0 ;
	public aStar(Node start, Node finish, outPut out , boolean exist)
	{
		this.exist = exist; 
		this.start = start;
		this.start.setParent(null);
		this.board_goal = finish;
		this.out = out; 
	}
	public void aStar_algo() 
	{
		int depth  = 0 ;
		//create open and close list
		Hashtable<String,Node> openList = new Hashtable<String,Node>();  
		Hashtable<String,Node> closeList = new Hashtable<String,Node>(); 
		//create priority queue
		PriorityQueue<Node> my_queue = new PriorityQueue<Node>(new NodePriorityComparator());

		my_queue.add(start);
		openList.put(start.toString(), start);

		while(!my_queue.isEmpty()) {
			if(this.out.isOpen)printOpenList(openList);				
			//poll node and remove from the openList
			Node parent = my_queue.poll();
			//remove from the openList
			openList.remove(parent.toString(), parent);
			//if the head is the goal state
			if(parent.isGoal(board_goal)) {
				double end = System.currentTimeMillis();
				double sec = (end - solve.time) / 1000.0;
				this.out.setRuntime(sec);
				this.write(parent,num,true);
				return;
			}
			//increase depth
			depth++;
			//if is not the goal put it in the close list
			closeList.put(parent.toString(), parent);
			num++;
			//find the operations that available
			Queue<Node> new_ = parent.findStates(exist);
			//iterate over the whole sons 

			for(Node son: new_) 
			{
				son.setDepth(depth);
				//put total price of each son
				son.setParent(parent);
				//if the son is not in both open and close list
				if(!openList.containsKey(son.toString()) && !closeList.containsKey(son.toString())) {
					//new son that never appear
					son.setToatal_price((int) (son.getCost()  + parent.getToatal_price()));
					son.setHeru_value((new heurstic().manhattan_algorithmD(son.getState(), this.board_goal.getState())));
					son.setF_G((int) ( son.getToatal_price() + son.getHeru_value() ));
					my_queue.add(son);
					openList.put(son.toString(), son);
				}
				else if(my_queue.contains(son)) {
					//if the son is already exist
					//check if the current path is cheaper
					//create temporary child that return the same key 
					Node temp = openList.get(son.toString());
					//if the current son is cheaper than what exist in 
					//our open_list
					if(son.getF_G() < temp.getF_G()){
						//swap	
						my_queue.remove(temp);
						my_queue.add(son);
						openList.put(son.toString(),son);
						openList.remove(son.toString());
					}
				}
			}
		}
		this.write(null,closeList.size(),false);
		System.out.println("my queue is empty");
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
