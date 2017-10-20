package model;

/**
 * 
 * @author Super Kewl Team: Matt Giardina, Paul Frost, Steven Downs, Andrew Bozzi
 * 
 *  Assigned to: Steven
 *  
 *         This is the Card class which will each of the 44 cards in a deck will
 *         be based off. of the 44 cards, 42 will take in two parameters one
 *         will be the army type the other the territory. The territory will be
 *         depicted by its serial number which is created each time its
 *         constructor is called. The other 2 cards will be the wild cards and
 *         only have the enum wild to depict them as well as a serial number
 *         zero for the territory.
 *         
 *         Methods contained: getID(), getType()
 * 
 */
public class Card {
	public int cardID;
	public CardType type;
	/**
	 * 
	 * @param type
	 * @param territory
	 * 
	 * This sets a territory to the card
	 */
	public Card(CardType type,int territory ){
		cardID=territory;
		this.type=type;
		
	}
	/**
	 * 
	 * @param type
	 * 
	 * This is sets the card type to wild card 
	 */
	public Card(CardType type){
		cardID=42;
		this.type=type;
		
	}
	public int getID(){
		return cardID; 
	}
	public CardType getType(){
		return type;
	}
}
