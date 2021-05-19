import java.util.Comparator;

public class NodePriorityComparator implements Comparator<Node> {
	public int compare(Node x, Node y) {
		if (x.getF_G() < y.getF_G()) {
			return -1 ; 
		}
		if (x.getF_G() > y.getF_G()) {
			return 1;
		}
		if(x.getF_G() == y.getF_G()) {
			if(x.getToatal_price()  < y.getToatal_price()) {
				return -1 ; 
			}else if(x.getToatal_price()  > y.getToatal_price()) {
				return 1 ;
			}	
			else{if(x.getDepth() < y.getDepth()) {
				return -1 ;
			}
			else if(x.getDepth() > y.getDepth()){
				return 1 ;
			}
			
			}
		}
		return 0;
	}
}