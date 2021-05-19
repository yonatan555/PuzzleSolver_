
public class Point {
	private int i;
	private int j;
	public Point(int i ,int j) {
		this.i = i;
		this.j = j;
	}
	public Point(Point other) {
		if(other != null) {
		this.i = other.i;
		this.j = other.j;
		}
	}
	public Point() {}
	
	public int getJ() {
		return j;
	}
	
	public int getI() {
		return i;
	}
	public void setI(int y) {
		this.i = y;
	}
	public void setJ(int x) {
		this.j = x;
	}
}
