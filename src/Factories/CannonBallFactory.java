package Factories;

import javafx.scene.layout.Pane;
import Common.Point;
import MapEntity.CannonBall.*;
import MapEntity.Ship.Ship;
import Ocean.OceanMap;

/*
 * Factory class for creating cannonballs and making sure that each ship doesn't make them too quickly */
public class CannonBallFactory{
	
	private Ship ship;
	private Pane root;
	
	public CannonBallFactory(Ship s, Pane p){
		ship = s;
		root = p;
	}
	
	public CannonBall makeCannonBall(String[] types){
		CannonBall cannonBall = null;
		for(int i = 0; i < types.length; i++){
			//base first
			if(i == 0){
				switch(types[i]){
				case "Up-Regular":
					if(OceanMap.getInstance(root).tileValue(ship.getLocation().y-1, ship.getLocation().x) != 2 || OceanMap.getInstance(root).tileValue(ship.getLocation().y-1, ship.getLocation().x) != 4)
						cannonBall = new RegularCannonBall(new Point(ship.getLocation().x, ship.getLocation().y-1), root, 1);
					else
						cannonBall = new Blank();
					break;
				case "Down-Regular":

					if(OceanMap.getInstance(root).tileValue(ship.getLocation().y+1, ship.getLocation().x) != 2 || OceanMap.getInstance(root).tileValue(ship.getLocation().y+1, ship.getLocation().x) != 4)
						cannonBall = new RegularCannonBall(new Point(ship.getLocation().x, ship.getLocation().y+1), root, 2);
					else
						cannonBall = new Blank();
					break;
				case "Swivel":
					if(OceanMap.getInstance(root).tileValue(ship.getLocation().y, ship.getLocation().x-1) != 2 || OceanMap.getInstance(root).tileValue(ship.getLocation().y, ship.getLocation().x-1) != 4)
						cannonBall = new SwivelCannonBall(new Point(ship.getLocation().x-1, ship.getLocation().y), root);
					else
						cannonBall = new Blank();
					break;
				default:
					cannonBall = new Blank();
					break;
				}
			}
			else{
				switch(types[i]){
				case "DamageUp":
					cannonBall = new DamageBonus(cannonBall);
				default:
					break;
				}
			}
		}
		return cannonBall;
	}
}