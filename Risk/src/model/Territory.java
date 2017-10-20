package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Andrew
 * 
 *         This is the territory class which needs to contain an instance of the
 *         player occupying it, the dice contained in it, adjacent territories,
 *         and its continent
 * 
 *         Methods Contained: adjustDice(), setOwner(), getOwner(),
 *         getUniqueID()
 */
public class Territory {
  private Player owner;
  private int numDice;
  public int uniqueID;
  private static int serialNumber = 0;
  private ArrayList<Territory> adjacentTerritories = new ArrayList<Territory>();
  private String name;

  public Territory() {
    name = "";
    uniqueID = serialNumber;
    serialNumber++;
    Globe.globalTerr.add(this);
    numDice = 1;
  }

  public Territory(String name) {
    this.name = name;
    uniqueID = serialNumber;
    serialNumber++;
    Globe.globalTerr.add(this);
    numDice = 1;
  }

  public String getName() {
    return name;
  }

  public int getNumDice() {
    return numDice;
  }

  public int getUniqueID() {
    return uniqueID;
  }

  /**
   * 
   * @param die
   *          This method handles the dice manipulations between and within
   *          territories. So it handles adjusting the dice lost in battle and
   *          also dice gained in fortify or moved in reinforce.
   */
  public void adjustDice(int die) {
    numDice = numDice + die;
  }

  public void setOwner(Player playa) {
    owner = playa;
  }

  public Player getOwner() {
    return owner;
  }

  public List<Territory> getAdjacentTerritories() {
    return adjacentTerritories;
  }

  public void addAdjacentTerritory(Territory territory) {
    adjacentTerritories.add(territory);
  }
}
