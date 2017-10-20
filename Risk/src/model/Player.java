package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Super Kewl Team
 * 
 *  Assigned to: Steven, Andrew
 *  
 *         This is the Player class which holds all of the methods which both
 *         the human and the AI users will call upon to play the game
 * 
 *         Methods contained: fortify(), attack(), and reinforce(),addCard(),
 *         getContinent(), getName(), getType(), getCards(),
 *         getPlayerColor(),getTerritories(),
 */
public class Player {

  private boolean PlayerIsHuman = false;
  private Territory from_territory;
  private Territory to_territory;
  private PlayerHand hand;
  private List<Territory> territories = new ArrayList<Territory>();
  private int attackDefault = 1;
  private int defenseDefault = 1;
  private String name;
  private int reinforcements = 0;
  private AI ai;
  private String[] names = { "Space Andrew", "Space Matt", "Space Paul", "Space Steven", "Lames-Sauce", "Space Pirate", "Space Nazi", "Space Ninja" };

  public Player(String name) {
    hand = new PlayerHand();
   // System.out.print(hand.getCurrentDeck().size());
    this.name = name;
    //System.out.println("Player was made");
    hand = new PlayerHand();
    PlayerIsHuman = true;
  }

  public Player(int number, AI ai) {
    PlayerIsHuman = false;
    ArrayList<String> list = new ArrayList<String>();
    for (int i = 0; i < names.length; i++) {
      list.add(names[i]);
    }
    Collections.shuffle(list);
    this.name = list.get(number + 1); // NEED TO CHANGE
    //System.out.println("A nameless Player was made");
    hand = new PlayerHand();
    this.ai = ai;
  }

  /**
   * The name of the player
   * 
   * @return a String with the player name
   */
  public String getName() {
    return name;
  }

  public boolean isPlayerHuman() {
    return PlayerIsHuman;
  }

  public AI getAI() {
    return ai;
  }

  /*  *//**
   * 
   * @param to
   * @param from
   * @return true if was successful
   * 
   *         This method is used during the first stage of a turn and sets the
   *         player's new armies on the board to fortify his territory.
   */
  /*
   * public boolean fortify() { System.out.println("This is the from Owner: " +
   * from_territory.getOwner()); System.out.println("This is the to Owner: " +
   * to_territory.getOwner()); new Fortify(from_territory, to_territory); return
   * false; }
   */

  public Territory getFromTerritory() {
    return from_territory;
  }

  public Territory getToTerritory() {
    return to_territory;
  }

  public void setFromTerritory(Territory from) {
    from_territory = getTerritories().get(from.getUniqueID());
  }

  public void setToTerritory(Territory to) {
    //System.out.println("The territory List: " + this.getTerritories());
  //  System.out.println("The UniqueID: " + to.getUniqueID());
   // System.out.println("The territory List size: " + this.getTerritories().size());
    to_territory = getTerritories().get(to.getUniqueID());
  }

  public void setAttackTerritory(Territory to) {
    if (!getTerritories().contains(to)) {
      to_territory = to;
    }
  }

  /**
   * 
   * @param attack
   * @param defend
   * @return true if you win
   * 
   *         This method is used during the second stage of a turn and handles
   *         what happens when you attack an unfriendly. It calls Dice for the
   *         rolls. Unfortunately the defender can only sit back and watch/set
   *         its default dice used.
   */
  public boolean attack() {
    new Attack(from_territory, to_territory);
    return false;
  }

  /**
   * 
   * @param one
   * 
   *          This method simply adds a card to the player's deck from the
   *          boards deck
   */
  public void addCard(Card one) {
    hand.getCurrentDeck().add(one);
  }

  public int getNumTerritory() {
    return territories.size();
  }

  public List<Territory> getTerritories() {
    return territories;
  }

  public int getAttackDefault() {
    return attackDefault;
  }

  public int getDefenseDefault() {
    return defenseDefault;
  }

  public Card getCard(int i) {
    return hand.getCurrentDeck().get(i);
  }

  public Card removeCard(int i) {
    return hand.getCurrentDeck().remove(i);
  }

  public void addTerritories(Territory t) {
    territories.add(t);
    t.setOwner(this);
  }

  public void draw() {
    hand.drawFromDeck();
  }

  public PlayerHand playersHand() {
    return hand;
  }

  public void setAttackDefault(int i) {
    attackDefault = i;
  }

  public void setDefenseDefault(int i) {
    defenseDefault = i;
  }

  public void setReinforcements(int armies) {
    reinforcements = armies;
  }

  public void addReinforcements(int armies) {
    reinforcements += armies;
  }

  public int getReinforcements() {
    return reinforcements;
  }

  public void decrementReinforcements() {
    reinforcements--;
  }

  /**
   * 
   * @param OliverCromwell
   * @return the army bonus which is different for each continent
   * 
   *         This method checks if any one player holds every territory in the
   *         continent and then awards a bonus. This bonus should be giving at
   *         the end of each turn, or right before the first stage.
   */
  public int fullContinent() {
    for (int i = 0; i < 6; i++) {
      if (this.getTerritories().containsAll(Board.getContinent(i).getTerritories())) {
        if (Board.getContinent(i).getContinentID() == 0) {
          return 5;
        }
        if (Board.getContinent(i).getContinentID() == 1) {
          return 2;
        }
        if (Board.getContinent(i).getContinentID() == 2) {
          return 5;
        }
        if (Board.getContinent(i).getContinentID() == 3) {
          return 3;
        }
        if (Board.getContinent(i).getContinentID() == 4) {
          return 7;
        }
        if (Board.getContinent(i).getContinentID() == 5) {
          return 2;
        }
      }
    }
    return 0;
  }

}
