package MapEntity.CannonBall;

import Common.Point;

public class DamageBonus implements CannonBallDecorator {

	private CannonBall base;
	private int damageMult;
	
	public DamageBonus(CannonBall base){
		this.base = base;
		damageMult = 2;
	}
	
	@Override
	public Point getLocation() {
		return base.getLocation();
	}

	@Override
	public int getDamage() {
		return base.getDamage()*damageMult;
	}

	@Override
	public void run() {
		base.run();
		
	}

	@Override
	public Point getPreviousLocation() {
		// TODO Auto-generated method stub
		return base.getPreviousLocation();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		base.draw();
		
	}

	@Override
	public void die() {
		base.die();
		
	}

}
