
package model;

	import java.util.List;

	/**
	 * 
	 * @author Super Kewl Team
	 * 
	 *  Assigned to: Steven
	 * 
	 *         This class represents a deck cards which will be contained by both
	 *         players and the board. The board's deck will be considered the game
	 *         deck, while the player's deck is considered to be his hand The deck
	 *         will have an add and a remove card method as well as a
	 *         shuffle(depending on implementation). This class will also keep track
	 *         of the cards turned in and will award army bonuses accordingly.
	 * 
	 *         Methods contained: addCard(),getCard() removeCard(), turnIn()
	 * 
	 */

public interface CardPile {
	
		/**
		 * 
		 * @param hand
		 *            must be a valid list
		 * @return an int determined by the variable bonus
		 * 
		 *         This method calculates the army bonus for the returned card set,
		 *         which depends on the number of card sets previously returned in
		 *         the game. This means referencing the static variable numReturned
		 *         and incrementing it each method call.
		 */
		
		public List<Card> getCurrentDeck();

}
