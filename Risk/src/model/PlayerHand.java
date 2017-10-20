package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author super Kewl Team
 * 
 *  Assigned to: Steven
 *    
 *         This is class is in charge of keeping track of the utility that comes
 *         with a players hand of cards as well as keeping a reference to that
 *         list of cards.
 * 
 *         methods contained: PlayerHand() turnIn(Card, Card, Card) getTurnIns()
 *         drawFromDeck() getHandFromLosingPlayer(List<Card>) getCurrentDeck()
 * 
 */
public class PlayerHand implements CardPile {
	private static int turnIns = 0;
	private List<Card> hand;
	private static int bonus;
	/**
	 * This constructor takes in no arguments and just sets the hand to a new
	 * ArrayList of Cards
	 */
	public PlayerHand() {
		hand = new ArrayList<Card>();
		// System.out.println("Player Hand Size: " +
		// Board.getDeck().getCurrentDeck().size());
		// hand.add(Board.getDeck().getCurrentDeck();
		// deck.getCurrentDeck().remove(0)
	}

	/**
	 * 
	 * @param one
	 * @param two
	 * @param three
	 * @return int
	 * 
	 *         This method takes care of the turn in procedure for cards, it
	 *         checks if the hand is valid then it returns the number of bonus
	 *         armies attained from that hand.
	 */
	public int turnIn(Player player, Card one, Card two, Card three) {
		if (player.getTerritories().contains(one.cardID)
				|| player.getTerritories().contains(two.cardID)
				|| player.getTerritories().contains(three.cardID)) {
			bonus += 2;
		}
		System.out.println("just to verify:");
		System.out.println(hand);
		System.out.println("Are your cards?");
		// if (hand.contains(one)&&hand.contains(two)&&hand.contains(three)) {
		System.out.println("These are your cards");
		Deck.boardDeck.add(one);
		Deck.boardDeck.add(two);
		Deck.boardDeck.add(three);
		if (Board.getBoard().validateHand(one, two, three)) {
			turnIns++;
			if (turnIns == 1) {
				bonus += 4;
			}
			if (turnIns > 1 && turnIns < 6) {
				bonus += 2;
			}
			if (turnIns == 6) {
				bonus += 3;
			}
			if (turnIns > 6) {
				bonus += 5;
			}
		}

		hand.remove(one);
		hand.remove(two);
		hand.remove(three);
		System.out.println("Your bonus: " + bonus);
		player.addReinforcements(bonus);
		return bonus;
	}
	public int checkHandForTurnIn(Player player){
		int value=0;
		for(int i=0; i<this.getCurrentDeck().size();i++){
			for(int j=0; j<this.getCurrentDeck().size();j++){
				for(int k=0; k<this.getCurrentDeck().size();k++){
					if(i!=j&&i!=k&&j!=k){
						value=turnIn(player, this.getCurrentDeck().get(i), this.getCurrentDeck().get(j), this.getCurrentDeck().get(k));
						return value;
					}
				}
			}
		}
		return value;
	}
	public int getTurnIns() {
		return turnIns;
	}

	/**
	 * 
	 * @return Card
	 * 
	 *         This method is just a call to draw from the board's deck of
	 *         cards.
	 */
	public Card drawFromDeck() {
		Card card = Deck.boardDeck.get(0);
		hand.add(Deck.boardDeck.remove(0));
		return card;
	}

	/**
	 * 
	 * @param player
	 *            This is only used when a player has been eliminated and the
	 *            cards go to the player who defeated him/her, and may have to
	 *            be immediately turned in depending on the rules and situation
	 */
	public void getHandFromLosingPlayer(List<Card> player) {
		hand.addAll(player);
		System.out.println();
		System.out.println(hand);
		System.out.println("did add all work?");
	}
/**
 * @return List<Card>
 * 	This just returns a list of the current instance of the player's hand
 */
	public List<Card> getCurrentDeck() {
		return hand;
	}
	


}
