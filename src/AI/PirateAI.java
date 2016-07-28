package AI;

import java.util.ArrayList;
import java.util.Comparator;

import AI.Tile.Tile;
import Common.Point;
import MapEntity.Ship.*;
import Ocean.OceanMap;

public class PirateAI implements AI, Runnable{

	private OceanMap map;
	private ArrayList<Ship> pirates;
	private ArrayList<Ship> enemies;
	private ArrayList<Tile> toVisit;
	private ArrayList<Tile> visited;
	private Point goal;
	
	public PirateAI(OceanMap map, ArrayList<Ship> pirates, ArrayList<Ship> enemies){
		this.map = map;
		this.pirates = pirates;
		this.enemies = enemies;
	}
	
	private void search(Ship pirate){
		visited = new ArrayList<Tile>();
		toVisit = new ArrayList<Tile>();
		
		Point start = pirate.getLocation();
		//find the closest enemy
		double distance = Double.MAX_VALUE;
		goal = new Point(49,49);
		for(Ship e : enemies){
			Point temp = e.getLocation();
			double closest= Math.sqrt((temp.x - start.x)^2 + (temp.y-start.y)^2);
			if(closest < distance){
				distance = closest;
				goal = temp;
			}
		}
		toVisit.add(new Tile(start.x, start.y, goal.x, goal.y, null));
		//need some sort of heuristic to determine if we are getting close or further from the enemy
		Tile visiting = toVisit.remove(0);
		//use the successor function to generate children.
		setChildren(visiting);
		//sort by lowest heuristic
		toVisit.sort(new TileComparator());
		//rest of the search is too large for the purposes of this game
		if(!toVisit.isEmpty())
			visiting = toVisit.remove(0);
		if(visiting.x > start.x)
			pirate.goEast();
		else if(visiting.x < start.x)
			pirate.goWest();
		else if(visiting.y > start.y)
			pirate.goSouth();
		else if (visiting.y < start.y)
			pirate.goNorth();
			
		//}
	}
	
	private void setChildren(Tile p){
		if(p.y+1 < map.getH() && map.tileValue(p.y+1, p.x) == 0 && !visited.contains(p))
			toVisit.add(new Tile(p.x, p.y+1, goal.x, goal.y, p));
		if(p.y-1 >= 0 &&map.tileValue(p.y-1, p.x) == 0 && !visited.contains(p))
			toVisit.add(new Tile(p.x, p.y-1, goal.x, goal.y, p));
		if(p.x+1 < map.getW() && map.tileValue(p.y, p.x+1) == 0 && !visited.contains(p))
			toVisit.add(new Tile(p.x+1, p.y, goal.x, goal.y, p));
		if(p.x-1 >= 0 && map.tileValue(p.y, p.x-1) == 0 && !visited.contains(p)){
			toVisit.add(new Tile(p.x-1, p.y, goal.x, goal.y, p));
		}
	}
	
	@Override
	public void run() {
		for(Ship p : pirates){
			search(p);
		}
	}
}


class TileComparator implements Comparator<Tile>{

	@Override
	public int compare(Tile o1, Tile o2) {
		if(((Tile)o1).getHeuristic() > ((Tile)o2).getHeuristic()){
			return 1;
		}else if((o1).getHeuristic() == o2.getHeuristic()){
			return 0;
		}else{
			return -1;
		}
	}
	
}
