import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
public class BFS {
	//start node
	Node start;
	//finish node
	Node board_goal;
	//finish node
	outPut out;
	//exist 2 0
	boolean exist ; 
	public BFS(Node start,Node board_goal ,outPut out,boolean exist) 
	{
		this.start = start;
		this.exist = exist; 
		this.board_goal= board_goal;
		this.out=out;
	}
	public void BFS_algo() 
	{
		Hashtable<String,Node> openList = new Hashtable<String,Node>();  
		Hashtable<String,Node> closeList = new Hashtable<String,Node>();  
		Queue<Node> my_queue = new LinkedList<Node>();
		
		my_queue.add(this.start);
		this.start.setParent(null);
		
		while(!my_queue.isEmpty()) {
		
			if(this.out.isOpen)printOpenList(openList);
			Node parent = my_queue.poll();
			closeList.put(parent.toString(), parent);
			openList.remove(parent.toString(), parent);
			
			Queue<Node> new_ = parent.findStates(exist);
			
			for(Node son: new_) 
			{
				son.setParent(parent);
				if(!openList.containsKey(son.toString()) && !closeList.containsKey(son.toString())) {
					if(parent.isGoal(board_goal)){
						 double end = System.currentTimeMillis();
					     double sec = (end - solve.time) / 1000.0;
					     this.out.setRuntime(sec);
					     this.write(parent,closeList.size(),true);
						 return;
					}else {
						my_queue.add(son);
						openList.put(son.toString(), son);
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