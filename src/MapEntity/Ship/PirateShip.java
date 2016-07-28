package MapEntity.Ship;
import java.util.Observable;
import java.util.Random;

import Common.Point;
import Ocean.OceanMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PirateShip extends Observable implements Ship {

	private Point coord;
	private ImageView image;
	private int scale = 20;
	private Point prev;
	private int health;
	private OceanMap map;
	
	public PirateShip(Ship CC, Pane root){
		health = 3;
		Random rnGWBush = new Random();
		Image image = new Image("pirateShip.png", scale,scale,true,true);
		this.image = new ImageView(image);
		//this.addObserver(map);
		//((PlayerShip)CC).addObserver(this);
		map = OceanMap.getInstance(root);
		
		int x=0, y=0;
		while(coord == null){
			x = rnGWBush.nextInt((35)+1);
			y = rnGWBush.nextInt((35)+1);
			if(map.getMap()[x][y] == 0){
				coord = new Point(x,y);
			}
		}
		prev = new Point(x,y);
		
		map.registerEntity(this);
		draw();
		root.getChildren().add(this.image);
	}
	/*@Override
	public void update(Observable obs, Object obj) {
		Point CCS = ((Ship)obs).getShipLocation();
		if(this.coord.x > CCS.x)
			goSouth();
		else if(this.coord.x < CCS.x)
			goNorth();
		else if(this.coord.y < CCS.y)
			goEast();
		else if(this.coord.y > CCS.y)
			goWest();
	}*/
	public Point getPosition(){
		return coord;
	}

	public void goEast(){
		if(coord.x+1 < map.getW() && (map.tileValue(coord.y, coord.x+1) == 0 || map.tileValue(coord.y, coord.x+1) == 6)){
			prev = new Point(coord.x, coord.y);
			coord.x++;
			//System.out.println(coord.x + "," +coord.y);
		}
		setChanged();
		notifyObservers();
		draw();
	}
	
	public void goWest(){
		if(coord.x-1 < map.getW() && (map.tileValue(coord.y, coord.x-1) == 0 || map.tileValue(coord.y, coord.x-1) == 6)){
			prev = new Point(coord.x, coord.y);
			coord.x--;
			//System.out.println(coord.x + "," +coord.y);
		}
		setChanged();
		notifyObservers();
		draw();
	}
	
	public void goNorth(){
		if(coord.y-1 < map.getH() && (map.tileValue(coord.y-1, coord.x) == 0 || map.tileValue(coord.y-1, coord.x) == 6)){
			prev = new Point(coord.x, coord.y);
			coord.y--;
			//System.out.println(coord.x + "," +coord.y);
		}
		setChanged();
		notifyObservers();
		draw();
	}
	public void goSouth(){
		if(coord.y+1 < map.getH() && (map.tileValue(coord.y+1, coord.x) == 0 || map.tileValue(coord.y+1, coord.x) == 6)){
			prev = new Point(coord.x, coord.y);
			coord.y++;
			//System.out.println(coord.x + "," +coord.y);
		}
		setChanged();
		notifyObservers();
		draw();
	}
	@Override
	public Point getShipLocation() {
		// TODO Auto-generated method stub
		return coord;
	}
	@Override
	public void fireCannons() {
		// TODO Auto-generated method stub
		
	}
	public Point getPreviousLocation() {
		// TODO Auto-generated method stub
		return prev;
	}
	@Override
	public void undo() {
	
		coord.x = prev.x;
		coord.y = prev.y;
		
	}
	@Override
	public void draw() {
		image.setX(coord.x*scale);
		image.setY(coord.y*scale);
		
	}
	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return coord;
	}
	@Override
	public boolean isDead() {
		if(health <= 0)
			return true;
		return false;
	}
	@Override
	public void takeDamage(int dmg) {
		health -= dmg;
		
	}
}