package MapEntity.Ship;
import Common.Point;
import MapEntity.MapEntity;


public interface Ship extends MapEntity {

	Point getShipLocation();
	Point getPreviousLocation();
	void goEast();
	void goWest();
	void goNorth();
	void goSouth();
	abstract void fireCannons();
	void undo();
	void draw();
	boolean isDead();
	void takeDamage(int dmg);
}
