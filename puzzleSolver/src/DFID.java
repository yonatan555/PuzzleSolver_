import java.util.Hashtable;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class DFID {
	//start node
	Node start;
	//finish node
	Node board_goal;
	//finish node
	outPut out;
	//if exist
	boolean exist ;
	//size 
	static int size_nodes = 1 ;
	public DFID(Node start,Node board_goal ,outPut out, boolean exist) 
	{
		this.start = start;
		this.exist = exist; 
		this.board_goal= board_goal;
		this.out=out;
	}
	public void DFID_algo() 
	{
		int depth = 0 ; 
		String cutOff = "cutoff";
		while(depth > -1 ) 
		{
			Hashtable<String,Node> openList = new Hashtable<String,Node>();  
			String result = "";

			depth++; 
			result = Limited_DFS(this.start,this.board_goal, depth ,openList);
			if(result.equals("fail")) {
				double end = System.currentTimeMillis();
				double sec = (end - solve.time) / 1000.0;
				this.out.setRuntime(sec);
				this.write(null,size_nodes,false);
				return;
			}
			if(!result.equals(cutOff)) {
				return;
			}
		}
	}
	private String Limited_DFS(Node parent, Node goal, int depth, Hashtable<String, Node> openList) {
		boolean isCutOff = false; 
		String result = "";
		Queue<Node> new_ = null;
		//if we arrived to goal
		if(parent.isGoal(goal)) {
			size_nodes++;
			this.write(printPath(parent),size_nodes,true);
			return printPath(parent);
		}
		//we arrived to the limit
		else if(depth == 0) {return "cutoff";}
		else {
			if(this.out.isOpen)printOpenList(openList);
			//insert into the openList
			openList.put(parent.toString(), parent);
			//find the whole states
			size_nodes++;

			new_ =  parent.findStates(exist);
			for(Node oper : new_) 
			{
				//loop avoidance
				if(openList.containsKey(oper.toString())) {
					continue;
				}
				oper.setParent(parent);
				result = Limited_DFS(oper, goal, depth-1, openList);
				if(result.equals("cutoff")) {
					isCutOff = true;
				}else if(!result.equals("fail")){
					return printPath(parent);
				}
			}
			openList.remove(parent.toString(), parent);
			if(isCutOff)return "cutoff";
			else {
				return "fail";
			}			
		}
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
	private void write(String node_path,int n,boolean has_path) 
	{
		out.setNum(n);
		this.out.writeToFile(has_path);	
	}	
	private String printPath(Node son) {
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
