package MapEntity.CannonBall;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import Common.Point;
import Ocean.OceanMap;

public class RegularCannonBall extends Observable implements CannonBall {

	private static final int scale = 10;
	private Point pos;
	private Point prev;
	private int damage;
	private int direction; // 1 = up, 2= down;
	private ImageView cannonImage;
	private int lifeTime;
	private OceanMap map;
	
	public RegularCannonBall(Point pos, Pane root, int direction){
		this.pos = pos;
		prev = new Point(pos.x, pos.y);
		damage = 1;
		map = OceanMap.getInstance(root);
		//this.addObserver(map);
		OceanMap.getInstance(root).registerEntity(this);
		lifeTime = 8;
		this.direction = direction;
		Image image = new Image("Cannonball.png",scale,scale,true,true);
		cannonImage = new ImageView(image);
		draw();
		root.getChildren().add(cannonImage);
	}
	
	public void die(){
		lifeTime = 0;
		map.deregisterEntity(this);
		this.deleteObservers();
		cannonImage.setVisible(false);
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void draw(){ //maybe some trouble here with the map being on a different thread
		cannonImage.setX(pos.x*scale);
		cannonImage.setY(pos.y*scale);
	}
	
	@Override
	public Point getLocation() {
		return pos;
	}

	@Override
	public void run() {
		while(lifeTime > 0){
			if(direction == 1 && pos.y-1 > -1 && (map.tileValue(pos.y-1, pos.x) != 2 || map.tileValue(pos.y-1, pos.x) != 4)){
				prev = new Point(pos.x, pos.y);
				pos.y--;
				lifeTime--;
				setChanged();
				notifyObservers();
				if(lifeTime <= 0)
					die();
			}else if (direction == 2 && pos.y+1 < map.getH() && (map.tileValue(pos.y+1, pos.x) != 2 || map.tileValue(pos.y+1, pos.x) != 4)){
				prev = new Point(pos.x,pos.y);
				pos.y++;
				lifeTime--;
				setChanged();
				notifyObservers();
				if(lifeTime <= 0)
					die();
			}else{
				die();
			}
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Point getPreviousLocation() {
		return prev;
	}


}
