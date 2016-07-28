package Ocean;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Common.Point;
import MapEntity.MapEntity;
import MapEntity.CannonBall.CannonBall;
import MapEntity.Ship.*;

public class OceanMap implements Observer{

	private static OceanMap instance;
	private final int mapH = 50, mapW = 50; 
	private final static int scale = 20;
	private int[][] map; //0=water,1=player,2=island,3=pirate,4=nina,5=pinta,6=cannonball
	private Random rngesus;
	private EntityRegistrar registrar;
	private ArrayList<CannonBall> cannonBalls;
	private ArrayList<Ship> ships;
	private ShipMover shipMover;
	private CannonBallMover cbMover;
	private Pane root;
	private Point TreasureIslandPoint;
	
	public static OceanMap getInstance(Pane root){ 
		if(instance == null)
			instance = new OceanMap(root);
		return instance;
	
	}
	public int getH(){return mapH;}
	public int getW(){return mapW;}
	public int[][] getMap(){return map.clone();}
	private OceanMap(Pane root){
		map = new int[mapH][mapW];
		this.root = root;
		rngesus = new Random();
		cannonBalls = new ArrayList<CannonBall>();
		ships = new ArrayList<Ship>();
		shipMover = ShipMover.getInstance(map,cannonBalls);
		cbMover = CannonBallMover.getInstance(map,ships);
		registrar = EntityRegistrar.getInstance(map,cannonBalls);
		generateIslands();
		generateTreasureIsland();
		drawMap();
	}
	
	public void drawMap(){
		for(int x = 0; x < mapH; x++){
			for(int y = 0; y < mapW; y++){
				if(map[x][y] == 0){
					Rectangle rect = new Rectangle(y*scale,x*scale,scale,scale);
					rect.setStroke(Color.BLACK);
					rect.setFill(Color.PALETURQUOISE);
					root.getChildren().add(rect);
				}
				if(map[x][y] == 2){
					Image image = new Image("island.png", scale,scale,true,true);
					ImageView islandView = new ImageView(image);
					islandView.setX(y*scale);
					islandView.setY(x*scale);
					root.getChildren().add(islandView);
				}
				if(map[x][y] == 4){
					Image image = new Image("pirateIsland.png", scale,scale,true,true);
					ImageView islandView = new ImageView(image);
					islandView.setX(y*scale);
					islandView.setY(x*scale);
					root.getChildren().add(islandView);
				}
			}
		}
	}
	
	private void generateTreasureIsland(){
		//int islandDie;
		//int min = 0, max = 5;
		for(int i = 0; i < mapH; i++){
			for(int j = 0; j < mapW; j++){
				if(map[i][j] == 0){
					map[i][j] = 4;
					TreasureIslandPoint = new Point(i,j);
					return;
				}
			}
		}
	}
	private void generateIslands(){
		//System.out.println("inside generateIslands");
		int islandDie;
		int min = 0, max = 5;
		for(int i = 0; i < mapH; i++){
			for(int j = 0; j < mapW; j++){
				islandDie = rngesus.nextInt((max-min)+1);
				if(islandDie == 0){
					//if it's 0 we put an island there.
					map[i][j] = 2;
					continue;
				}
				if(islandDie == 2){
					//increase the chance to create chains
					if(i > 0 && map[i-1][j] == 2){
						map[i][j] = 2;
						continue;
					}
					if(i < mapH-1 && map[i+1][j] == 2){
						map[i][j] = 2;
						continue;
					}
					if(j > 0 && map[i][j-1]==2){
						map[i][j] = 2;
						continue;
					}
					if(j < mapW-1 && map[i][j+1] == 2){
						map[i][j] =2;
						continue;
					}
				}
			}
		}
	}
	
	public void registerEntity(MapEntity e){
		registrar.register(e);
	}
	public void deregisterEntity(MapEntity e){
		registrar.deregister(e);
	}
	public int tileValue(int y, int x){
		if(y < mapH && y > -1 && x < mapW && x > -1)
			return map[y][x];
		else
			return -1;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Ship){
			Ship s = ((Ship)o);
			shipMover.moveShip(s);
		}
		/*if(o instanceof CannonBall){
			CannonBall c = ((CannonBall)o);
			cbMover.moveCannonball(c);
		}*/
	}
	/*public void moveEntityNorth(MapEntity e){
		Point loc = e.getLocation();
		if(e instanceof PlayerShip){
			map[loc.y][loc.x] = 0;
			map[loc.y-1][loc.x] = 1;
		}
	}
	public void moveEntitySouth(MapEntity e){
		Point loc = e.getLocation();
		if(e instanceof PlayerShip){
			map[loc.y][loc.x] = 0;
			map[loc.y+1][loc.x] = 1;
		}
	}
	public void moveEntityWest(MapEntity e){
		Point loc = e.getLocation();
		if(e instanceof PlayerShip){
			map[loc.y][loc.x] = 0;
			map[loc.y][loc.x-1] = 1;
		}
	}
	public void moveEntityEast(MapEntity e){
		Point loc = e.getLocation();
		if(e instanceof PlayerShip){
			map[loc.y][loc.x] = 0;
			map[loc.y][loc.x+1] = 1;
		}
	}*/
	
	public void replaceTreasureIsland(){
		map[TreasureIslandPoint.y][TreasureIslandPoint.x] = 2;
		Image image = new Image("island.png", scale,scale,true,true);
		ImageView islandView = new ImageView(image);
		islandView.setX(TreasureIslandPoint.y*scale);
		islandView.setY(TreasureIslandPoint.x*scale);
		root.getChildren().add(islandView);
	}
}

class EntityRegistrar{
	private static EntityRegistrar instance;
	private int[][] map;
	private ArrayList<CannonBall> cannonBalls;
	private ArrayList<Ship> ships;
	
	private EntityRegistrar(int[][]map, ArrayList<CannonBall> cannonBalls){this.map = map;this.cannonBalls = cannonBalls;}
	
	public static EntityRegistrar getInstance(int[][] map, ArrayList<CannonBall> cannonBalls){
		if(instance == null)
			instance = new EntityRegistrar(map,cannonBalls);
		return instance;
	}

	public void deregister(MapEntity e){
		Point p = e.getLocation();
		System.out.println(p.x+","+p.y);
		map[p.y][p.x] = 0;
		if(e instanceof CannonBall)
			cannonBalls.remove(e);
		if(e instanceof Ship){
			ships.remove(e);
		}
	}
	
	public void register(MapEntity e){
		Point p = e.getLocation();
		
		if(map[p.y][p.x] == 0){
			if(e instanceof PlayerShip)
				map[p.y][p.x] = 1;
			if(e instanceof PirateShip)
				map[p.y][p.x] = 3;
			if(e instanceof CannonBall){
				map[p.y][p.x] = 6;
				cannonBalls.add((CannonBall)e);
			}
		}
	}
}

class ShipMover{
	private static ShipMover instance;
	private int[][] map;
	private ArrayList<CannonBall> cannonBalls;
	
	private ShipMover(int[][]map, ArrayList<CannonBall> cannonBalls){this.map = map;this.cannonBalls = cannonBalls;}
	
	public static ShipMover getInstance(int[][] map,ArrayList<CannonBall> cannonBalls){
		if(instance==null){
			instance = new ShipMover(map,cannonBalls);
		}
		return instance;
	}
	
	public void moveShip(Ship s){
		Point pos = s.getLocation();
		Point prev = s.getPreviousLocation();
		
		if(s instanceof PlayerShip){
			map[prev.y][prev.x] = 0;
			if(map[pos.y][pos.x] == 6){
				//take damage
				for(CannonBall c : cannonBalls){
					if(pos.x == c.getLocation().x && pos.y == c.getLocation().y){
						System.out.println("You've been hit! for " + c.getDamage() + " damage.");
						s.takeDamage(c.getDamage());
						c.die();
					}
				}
			}
			map[pos.y][pos.x] = 1;
		}
		if(s instanceof PirateShip){
			map[prev.y][prev.x] = 0;
			if(map[pos.y][pos.x] == 6){
				//take damage
				for(CannonBall c : cannonBalls){
					if(pos.x == c.getLocation().x && pos.y == c.getLocation().y){
						s.takeDamage(c.getDamage());
						c.die();
					}
				}
			}
			map[pos.y][pos.x] = 3;
		}
	}
}

class CannonBallMover{
	private static CannonBallMover instance;
	private int[][] map;
	private ArrayList<Ship> ships;
	
	private CannonBallMover(int[][]map, ArrayList<Ship> ships){this.map = map;this.ships = ships;}
	
	public static CannonBallMover getInstance(int[][] map, ArrayList<Ship> ships){
		if(instance==null){
			instance = new CannonBallMover(map, ships);
		}
		return instance;
	}
	
	public void moveCannonball(CannonBall b){
		Point pos = b.getLocation();
		Point prev = b.getPreviousLocation();
		
		map[prev.y][prev.x] = 0;
		if(map[pos.y][pos.x] != 0){
			for(Ship s: ships){
				Point p = s.getLocation();
				if(p.x == pos.x && p.y == pos.y){
					s.takeDamage(b.getDamage());
					b.die();
				}
			}
		}
		map[pos.y][pos.x] = 6;
	}
}