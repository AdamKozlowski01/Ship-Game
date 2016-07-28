package MapEntity.CannonBall;

import MapEntity.MapEntity;

public interface CannonBall extends MapEntity, Runnable{

	int getDamage();

	void die();
}
