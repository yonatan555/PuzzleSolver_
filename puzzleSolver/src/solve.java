public class solve {
	
	int board [][] ;
	int board_goal [][] ;	
	outPut out ;
	String algo ;
	boolean exist;
	boolean time_ ;
	static double time = 0.0;
	public solve(int[][]brd ,int[][] goal , String algo , boolean time, boolean open,boolean exist ) 
	{
		this.board  = brd ;
		this.board_goal = goal ;
		this.algo = algo;
		this.exist = exist;
		this.time_= time ;
		this.out = new outPut(open,time);
		run_algo(exist);
	}
	private void run_algo(boolean exist) {
		Node start = new Node(this.board);
		Node finish = new Node(this.board_goal);
		
		time = System.currentTimeMillis();
		switch(this.algo) {
		case "BFS":
			BFS bfs_algo = new BFS(start,finish,this.out, exist);
			bfs_algo.BFS_algo();
			break;
		case "DFID":
			DFID DFID_algo = new DFID(start,finish,this.out,exist);
			DFID_algo.DFID_algo();
			break;
		case "A*":
			aStar aStar = new aStar(start , finish ,this.out,exist);
			aStar.aStar_algo();
			break;
		case "IDA*":
			IDA ida = new IDA(start , finish ,this.out,exist);
			ida.IDA_algo();
			break;
		case "DFBnB":
			DFBnB dfbnb = new DFBnB(start , finish ,this.out,exist);
			String a = dfbnb.DFBnB_algo();
//			System.out.println(a);
			break;
		default:
			System.err.println("no Algorithm has choosen");	  
		}
	}
}
