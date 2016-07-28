package MapEntity.Ship;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.layout.Pane;
import Factories.CannonBallFactory;
import Ocean.OceanMap;

/* sets up the cannonballs to fire in the right directions and sets a time limit on how long between firings */
public class Cannon implements Runnable{

	private Ship owner;
	private CannonBallFactory factory;
	private ExecutorService executor = Executors.newFixedThreadPool(3); // make this shit multithreaded 
	public Cannon(Ship s, Pane root){
		factory = new CannonBallFactory(s, root);
		owner = s;
	}
	
	@Override
	public void run() {
		String[] stuff = new String[1];
		//
		
		stuff[0] = "Up-Regular";
		executor.execute(factory.makeCannonBall(stuff));
		stuff[0] = "Down-Regular";
		executor.execute(factory.makeCannonBall(stuff));
		stuff[0] = "Swivel";
		executor.execute(factory.makeCannonBall(stuff));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
