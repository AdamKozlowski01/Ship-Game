package Factories;

import javafx.scene.layout.Pane;
import MapEntity.Ship.*;

//decides how to make the ships and applies decorators to them
public class ShipFactory {

	private Ship playerShip;
	private Pane root;
	
	public ShipFactory(Pane root){
		this.root = root;
	}
	
	public Ship makeShip(String type){
		Ship ship = null;
		switch(type){
		case "Player":
			playerShip = PlayerShip.getInstance(root);
			ship = playerShip;
			break;
		case "Pirate":
			ship = new PirateShip(playerShip,root);
			break;
		default:
			break;
		}
		
		return ship;
		
	}
}
