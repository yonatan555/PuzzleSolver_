import java.io.FileWriter;
import java.io.IOException;

public class outPut {
	
	//parameters if to print
	boolean isOpen ;
	boolean time1;
	
	//parameters to print
	double runtime ;
	int num ; 
	int cost ;
	String moves ;
	
	public outPut(boolean isOpen,boolean time) 
	{
		this.time1 = time ;
		this.isOpen = isOpen;
	}
	public void writeToFile(boolean has_path) 
	{
		 try {
		      FileWriter myWriter = new FileWriter("output.txt");
		      if(has_path) {
		      if(time1)
		      myWriter.write(moves+"\n"+"Num: "+ num + "\n"+"Cost: "+cost+"\n"+runtime+" seconds");
		      else {
		    	  myWriter.write(moves+"\n"+"Num: "+ num + "\n"+"Cost: "+cost);  
		      }
		      myWriter.close();
//		      System.out.println("Successfully wrote to the file.");
		    }else if (!has_path){
		    	  myWriter.write("no path"+"\n"+"Num: "+ num);  
		    }
		 }catch (IOException e) {
//		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }	
	}
	public boolean isTime1() {
		return time1;
	}


	public void setTime1(boolean time1) {
		this.time1 = time1;
	}


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getMoves() {
		return moves;
	}
	public void setMoves(String moves) {
		this.moves = moves;
	}
	public double getRuntime() {
		return runtime;
	}

	public void setRuntime(double sec) {
		this.runtime=sec;
	}	
}