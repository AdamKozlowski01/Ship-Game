/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Twistingsun
 */

import java.util.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

import java.lang.Object;

import AI.PirateAI;
import Common.Point;
import Factories.ShipFactory;
import MapEntity.Ship.*;
import Ocean.OceanMap;


public class OceanExplorer extends Application{
	
	final int dimensions = 50;
	final int scale = 20;
	AnchorPane root;
	Scene scene;
	Ship ship;
	OceanMap oceanMap;
	//<ImageView> islands;
	ArrayList<Ship> pirates;
	ArrayList<Ship> playerAndAllies;
	Thread pirateThread;
	
	boolean gameOver = false;
	boolean gameWon = false;
	boolean playerHasLittleMan = false;
	
	
    public static void main(String[] args){
        
        launch(args);
        
    }

	@Override
	public void start(Stage oceanStage) throws Exception {
		System.out.println("Arrr, welcome to the pirate's islands.\n"
				+ "One of your mates has discovered the Pirate King's hidden treasure.\n"
				+ "You need to grab him and get out of the islands without being caught by pirates!\n"
				+ "Go to the island with your man, pick him up and then get to the right most edge of the map to escape\n"
				+ "If a pirate gets next to you you'll be boarded and raided, with or without the treasure.");
		root = new AnchorPane();
		int height = 1000;
		int width = 1000;
		scene = new Scene(root,width, height);
		oceanMap = OceanMap.getInstance(root);
		pirates = new ArrayList<Ship>();
		playerAndAllies = new ArrayList<Ship>();
		
		ShipFactory warf = new ShipFactory(root);
		ship = warf.makeShip("Player");
		playerAndAllies.add(ship);
		pirates.add(warf.makeShip("Pirate"));
		pirates.add(warf.makeShip("Pirate"));
		pirates.add(warf.makeShip("Pirate"));
		pirates.add(warf.makeShip("Pirate"));
		pirates.add(warf.makeShip("Pirate"));
		pirates.add(warf.makeShip("Pirate"));
		
		
		
		oceanStage.setScene(scene);
		oceanStage.setTitle("Pirate Islands");
		oceanStage.show();
		
		PirateAI pirateAI = new PirateAI(oceanMap, pirates, playerAndAllies);
		pirateThread = new Thread(pirateAI);

		startSailing();
		pirateThread.run();
	}
	

	public boolean lost(){
		Point sp = ship.getLocation();
		for(Ship s : pirates){
			Point pp = s.getLocation();
			if(((sp.x-1 == pp.x || sp.x+1 == pp.x || sp.x == pp.x) && (sp.y-1 == pp.y || sp.y+1 == pp.y || sp.y == pp.y)) || ship.isDead()){
				return true;
			}
		}
		return false;
	}
	
	public void startSailing(){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent ke){
				if(oceanMap.tileValue(ship.getLocation().y-1, ship.getLocation().x) == 4 || oceanMap.tileValue(ship.getLocation().y+1, ship.getLocation().x) == 4 || oceanMap.tileValue(ship.getLocation().y, ship.getLocation().x+1) == 4 || oceanMap.tileValue(ship.getLocation().y, ship.getLocation().x-1) == 4){
					//pick up the little man and change the island
					playerHasLittleMan = true;
					oceanMap.replaceTreasureIsland();
				}
				if(ship.getLocation().x == oceanMap.getW()-1 && playerHasLittleMan){ //and we have the little man
					//win the game
					gameWon = true;
					System.out.println("You won!");
				}
				if(lost()){
					gameOver=true;
					System.out.println("You Lost...");
				}
				pirateThread.run();
				//oceanMap.drawMap();
				if(!gameOver && !gameWon){
					switch(ke.getCode()){
					case RIGHT:
						ship.goEast();
						break;
					case LEFT:
						ship.goWest();
						break;
					case UP:
						ship.goNorth();
						break;
					case DOWN:
						ship.goSouth();
						break;
					case SPACE:
						//ship.fireCannons();
						break;
					case R:
						break;
					default:
						break;
					}
				}
			}
		});
	}
}