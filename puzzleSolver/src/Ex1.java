import java.io.File;  // Import the File class
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
public class Ex1 {
	public static void main(String[] args) throws Exception {
		//read from file
		File myFile = new File("input2.txt");
//		File myFile2 = new File("input2.txt");
		Scanner input = new Scanner(myFile);
//		Scanner input2 = new Scanner(myFile2);
		//insert file to vector
		Vector<String> input_arguments = new Vector<String>();
//		Vector<String> input_arguments2 = new Vector<String>();
		while(input.hasNext()) input_arguments.add(input.nextLine());
//		while(input.hasNext()) input_arguments2.add(input2.nextLine());
		run(input_arguments);
//		run(input_arguments2);
	}
	public static void run(Vector<String> input) 
	{
		//which algo---------------
		String algo= input.get(0);
		//-------------------------
		String time = input.get(1);
		boolean with_time =true;
		String isOpen = input.get(2);
		boolean isOPEN = true;
		//check with time | isOpen------------------
		if(time.contains("no")) with_time = false;
		if(isOpen.contains("no")) isOPEN = false;
		//-------------------------------------------
		//size of board------------------------------
		String size = input.get(3); 
		//-------------------------------------------
		String rows ="";
		String cols ="";
		boolean saw = false; 
		//read cols and rows
		for(int i = 0 ; i<size.length();i++) 
		{
			if(size.charAt(i) != 'x' && !saw ) {rows += size.charAt(i);}
			else if(saw) {cols +=size.charAt(i);}
			else if(size.charAt(i) == 'x' )saw = true;
		}
		
		//---------------------initalize first and goal boards-----------------------------
		int  i = 4;
		int j = i;
		Vector<String> brd  = new Vector<String>();
		while(i != j + Integer.parseInt(rows)) {
			brd.add(input.get(i));
			i++;
		}
		//pass the goal state
		i++;
		//init arrays
		int bord [][] = new int [Integer.parseInt(rows)][Integer.parseInt(cols)];
		boolean exist2 = readboard(brd,bord);
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		Vector<String> brd_goal  = new Vector<String>();
		while(i < input.size()) {
			brd_goal.add(input.get(i));
			i++;
		}
		int bord_goal [][] = new int [Integer.parseInt(rows)][Integer.parseInt(cols)];
		readboard(brd_goal,bord_goal);
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//start running the algorithms--------------------------------------------------------
		start(bord,bord_goal,algo,with_time,isOPEN,exist2);
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------
	}
	private static boolean readboard(Vector<String> brd, int[][] bord) {
		int cols = 0 ;
		int counter = 0 ;
		for(int i = 0;i<brd.size();i++) {
			StringTokenizer multiTokenizer = new StringTokenizer(brd.get(i), ",");
			while (multiTokenizer.hasMoreTokens())
			{	
				String token = multiTokenizer.nextToken();
				//replacing _ to 0
				if(token.charAt(0) == '_') {
					bord[i][cols]= 0;
				counter++;	
				}
				else {bord[i][cols] = Integer.parseInt(token);}
				cols++;
			}	
		cols= 0;
		}
		if(counter ==  2) return true;
		else return false;
	}
	private static void start(int[][] bord, int[][] bord_goal,String algo,boolean time,boolean isOpen
			,boolean exist) {
	solve slv = new solve(bord, bord_goal, algo,time, isOpen,exist);
	}
}
