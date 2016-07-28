package AI.Tile;

import Common.Point;

public class Tile extends Point{
	
	private Tile parent;
	private float heuristic; //number of moves away
	public int goalX, goalY;
	
	public Tile(int x, int y, int z, int w, Tile p){
		this.x = x;
		this.y = y;
		goalX = z;
		goalY = w;
		parent = p;
		
		setHeuristic();
		
	}
	public Tile getParent(){
		return parent;
	}
	public float getHeuristic(){
		return heuristic;
	}
	
	private void setHeuristic(){
		heuristic = (float) Math.sqrt((goalX - x)^2 + (goalY-y)^2);
	}
}
