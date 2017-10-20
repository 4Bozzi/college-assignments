package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Steven
 * 
 *         This is the Deck Class which is stored in the board and should only
 *         have one instance of itself which will be stored in the board class.
 * 
 *         methods contained: Deck() getCurrentDeck()
 * 
 */
public class Deck implements CardPile {
	public static List<Card> boardDeck = new ArrayList<Card>();

	/**
	 * This is the constructor which takes no args and so just initializes the
	 * instance variables and creates all of the cards to be used in the game
	 */
	public Deck() {
		List<CardType> type = new ArrayList<CardType>(3);
		type.add(CardType.CAVALRY);
		type.add(CardType.CANNON);
		type.add(CardType.INFANTRY);
		int counter = 0;
		boardDeck = new ArrayList<Card>(44);
		while (counter < 43) {
			if (counter == 42) {
				boardDeck.add(new Card(CardType.WILD));
				boardDeck.add(new Card(CardType.WILD));
			} else {
				boardDeck.add(new Card(type.get(counter % 3), counter));
			}
			counter++;
		}
	}
/**
 * @return List<Card>
 * 
 * This returns an instance of the board's deck of cards
 */
	public List<Card> getCurrentDeck() {
		// TODO Auto-generated method stub
		return boardDeck;
	}

}
