package test;

import model.Continent;
import model.Player;
import model.Territory;
import junit.framework.*;

public class Tests extends TestCase {
	public Tests(String name) {
		super(name);
	}
	
	public void testSimpleTest() {
		Territory alaska = new Territory();	
		Territory nwTerritory = new Territory();
		Territory greenland = new Territory();
		Territory alberta = new Territory();
		Territory ontario = new Territory();
		Territory quebec = new Territory();
		Territory westernUS = new Territory();
		Territory easternUS = new Territory();
		Territory centralAmerica = new Territory();
		Territory venezuela = new Territory();
		Player player = new Player("Optimus");
		
		alaska.addAdjacentTerritory(nwTerritory);
		alaska.addAdjacentTerritory(alberta);
		
		nwTerritory.addAdjacentTerritory(alaska);
		nwTerritory.addAdjacentTerritory(greenland);
		nwTerritory.addAdjacentTerritory(alberta);
		nwTerritory.addAdjacentTerritory(ontario);
		
		greenland.addAdjacentTerritory(nwTerritory);
		greenland.addAdjacentTerritory(ontario);
		greenland.addAdjacentTerritory(quebec);
		
		alberta.addAdjacentTerritory(nwTerritory);
		alberta.addAdjacentTerritory(alaska);
		alberta.addAdjacentTerritory(ontario);
		alberta.addAdjacentTerritory(westernUS);
		
		ontario.addAdjacentTerritory(nwTerritory);
		ontario.addAdjacentTerritory(alberta);
		ontario.addAdjacentTerritory(quebec);
		ontario.addAdjacentTerritory(greenland);
		ontario.addAdjacentTerritory(easternUS);
		ontario.addAdjacentTerritory(westernUS);
		
		westernUS.addAdjacentTerritory(alberta);
		westernUS.addAdjacentTerritory(quebec);
		westernUS.addAdjacentTerritory(easternUS);
		westernUS.addAdjacentTerritory(centralAmerica);
		
		easternUS.addAdjacentTerritory(ontario);
		easternUS.addAdjacentTerritory(quebec);
		easternUS.addAdjacentTerritory(westernUS);
		easternUS.addAdjacentTerritory(centralAmerica);
		
		centralAmerica.addAdjacentTerritory(westernUS);
		centralAmerica.addAdjacentTerritory(easternUS);
		centralAmerica.addAdjacentTerritory(venezuela);
		
		venezuela.addAdjacentTerritory(centralAmerica);

		assertEquals(0, alaska.getUniqueID());
		assertEquals(1, nwTerritory.getUniqueID());
		
		Continent nAmerica = new Continent(0);
		
		assertEquals(0, nAmerica.getContinentID());//adds a different instance of each of the territories.
		nAmerica.getTerritories().add(alaska);
		assertEquals(alaska,nAmerica.getTerritories().get(9));
		assertEquals(nwTerritory,nAmerica.getTerritories().get(9).getAdjacentTerritories().get(0));
		alaska.setOwner(player);
		assertEquals(player,nAmerica.getTerritories().get(9).getOwner());
		
	}
}