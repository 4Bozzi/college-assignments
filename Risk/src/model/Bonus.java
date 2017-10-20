package model;

/**
 * 
 * @author Super Kewl Team: Matt Giardina, Paul Frost, Steven Downs, Andrew Bozzi
 * 
 *  Assigned to: Andrew
 * 
 *         This is the Bonus class that is used to calculate how many bonus
 *         dice(armies) the PlayerPanel class gets during the reinforcement
 *         stage, for a Player to. put in its territories. The number calculated
 *         is based on certain situations relating to territories a player owns.
 * 
 *         Methods Contained: getBonus()
 * 
 */
public class Bonus {
	/**
	 * 
	 * @param lucky
	 * @return the number of armies awarded to the player at the beginning or
	 *         end of each turn based on the number of territories and
	 *         continents held
	 */
	public int getBonus(Player lucky) {
		int armies = 0;
		
		if (lucky.getNumTerritory() < 9){
			armies = armies + 3;
		} else {
		armies =  armies + (int)(lucky.getNumTerritory()/3);
		}	
		armies = armies + lucky.fullContinent();
		return armies;
	}
}
