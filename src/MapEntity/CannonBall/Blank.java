package MapEntity.CannonBall;

import Common.Point;

public class Blank implements CannonBall {

	public Blank(){}
	
	@Override
	public Point getLocation() {
		return new Point(0,0);
	}

	@Override
	public void run() {

	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public Point getPreviousLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
