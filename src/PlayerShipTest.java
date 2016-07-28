import static org.junit.Assert.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import org.junit.Before;
import org.junit.Test;

import Common.Point;
import MapEntity.Ship.PlayerShip;
import MapEntity.Ship.Ship;


public class PlayerShipTest {

	Pane root;
	PlayerShip s;
	@Before
	public void setUp() throws Exception {
		root = new AnchorPane();
	}

	@Test
	public void testGetInstance() {
		s = PlayerShip.getInstance(root);
		assertEquals(s, PlayerShip.getInstance(root));
	}

	@Test
	public void testTakeDamage() {
		s.takeDamage(2);
		assertEquals(s.getHealth(), 8);
	}

	@Test
	public void testGoEast() {
		Point p = s.getLocation();
		s.goEast();
		assertEquals(s.getLocation().x, p.x+1);
	}

	@Test
	public void testGoWest() {
		Point p = s.getLocation();
		s.goWest();
		assertEquals(s.getLocation().y, p.y-1);
	}

	@Test
	public void testGoNorth() {
		Point p = s.getLocation();
		s.goNorth();
		assertEquals(s.getLocation().x, p.x+1);
	}

	@Test
	public void testGoSouth() {
		Point p = s.getLocation();
		s.goSouth();
		assertEquals(s.getLocation().y, p.y+1);
	}


}
