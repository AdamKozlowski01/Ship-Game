import static org.junit.Assert.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import org.junit.Test;

import Factories.ShipFactory;
import MapEntity.Ship.PirateShip;
import MapEntity.Ship.PlayerShip;
import MapEntity.Ship.Ship;


public class ShipFactoryTest {

	Pane root = new AnchorPane();
	ShipFactory sp = new ShipFactory(root);
	
	
	@Test
	public void testMakeShip() {
		Ship s = sp.makeShip("Player");
		assertEquals(PlayerShip.class, s.getClass());
		
		s = sp.makeShip("Pirate");
		assertEquals(PirateShip.class, s.getClass());
	}

}
