package MapEntity.Ship;
import java.util.Observable;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import Common.Point;
import Ocean.OceanMap;

public class PlayerShip extends Observable implements Ship{
	private static int scale = 20;
	private static PlayerShip instance;
	private ImageView shipImageView;
	private Point coord;
	private Point prev;
	private Cannon cannons;
	private Thread cannonsThread;
	private OceanMap map;
	private int health;
	
	public static PlayerShip getInstance(Pane root){
		if(instance==null){
			instance = new PlayerShip(root);
		}
		return instance;
	}
	
	private PlayerShip(Pane root){
		//this.addObserver(map);
		this.map = OceanMap.getInstance(root);
		coord = new Point(45,45);
		prev = new Point(45,45);
		
		if(map.tileValue(coord.y, coord.x) != 0){
			while(coord.x < map.getW() && map.tileValue(coord.y, coord.x) != 0){
				coord.x--;
				prev.x--;
			}
		}
		map.registerEntity(this);
		//cannons = new Cannon(this, root);
		//cannonsThread = new Thread(cannons);
		Image shipImage = new Image("ship.png",scale,scale,true,true);
		shipImageView = new ImageView(shipImage);
		draw();
		root.getChildren().add(shipImageView);
		health = 10;
	}
	
	public void takeDamage(int dmg){
		health -= dmg;
	}
	
	public int getHealth(){
		return health;
	}
	
	public boolean isDead(){
		if(health <= 0)
			return true;
		return false;
	}
	
	public Point getShipLocation(){
		return coord;
	}
	
	public void undo(){
		coord = prev;
		prev = new Point(coord.x,coord.y);
		System.out.println("Coord: " + coord.x + ","+coord.y);
		System.out.println("Prev: " + prev.x + ","+prev.y);
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
		if(coord.x-1 > -1 && (map.tileValue(coord.y, coord.x-1) == 0 || map.tileValue(coord.y, coord.x-1) == 6)){
			prev = new Point(coord.x, coord.y);
			coord.x--;

			//System.out.println(coord.x + "," +coord.y);
		}
		setChanged();
		notifyObservers();
		draw();
	}
	
	public void goNorth(){
		if(coord.y-1 > -1 && (map.tileValue(coord.y-1, coord.x) == 0 || map.tileValue(coord.y-1, coord.x) == 6)){
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
	public void fireCannons() {
		//cannonsThread.run();
		
	}

	public Point getPreviousLocation() {
		return prev;
	}

	@Override
	public void draw() {
		shipImageView.setX(coord.x * scale);
		shipImageView.setY(coord.y * scale);
		
	}

	@Override
	public Point getLocation() {
		return coord;
	}
}
