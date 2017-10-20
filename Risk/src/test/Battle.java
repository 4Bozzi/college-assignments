/*package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Attack;

*//**
 * 
 *@author Super Kewl Team: Matt Giardina, Paul Frost, Steven Downs, Andrew
 *         Bozzi
 * 
 *         This Class contains the functionality that goes along with a risk
 *         battle. This means that it keeps track of who is battling who, and
 *         the dice roll. It does not keep track of units lost or territories
 *         gained/lost those are updated elsewhere in the code(player).
 * 
 *         Methods Contained: Battle(), and roll()
 *//*
public class Battle {
  private Attack attack;
  private int attackersLost;
  private int defendersLost;

  *//**
   * 
   * @param defender
   * @param attacker
   * 
   *          This is the fun part of risk. This method essentially determines
   *          who wins and who loses. The battle method takes in a attacker and
   *          defender and rolls for each of them and compares the highest rolls
   *          who see who wins the battle.
   *//*
  public Battle(Attack attack) {
    this.attack = attack;
    battle();
  }

  public void battle() {
    System.out.println("The attacker Wants to roll a " + attack.getAttackDefault());
    // This sets up the number of dice the attacker uses
    // attackDefault = attacker.getPlayer().getAttackDefault();//
    // territories
    // should have a
    // get player
    while (attack.getAttackingArmy() < attack.getAttackDefault() + 1) {
      System.out.println("Computing how many die the attacker will roll...");
      attack.adjAttackDefault(-1);
    }
    System.out.println("The attacker is Rolling: " + attack.getAttackDefault());

    // This sets up the number of dice the defender uses
    System.out.println("The defender Wants to roll a " + attack.getDefenseDefault());
    if (attack.getDefendingArmy() == 1) {
      System.out.println("Computing how many die the defender will roll...");
      attack.setDefenseDefault(1);
    }
    System.out.println("The defender is Rolling: " + attack.getDefenseDefault());

    // This sets the attacking dice to a value
    List<Integer> attackerdie = new ArrayList<Integer>(attack.getAttackDefault());
    attackerdie = roll(attack.getAttackDefault());
    Collections.sort(attackerdie);
    if (attackerdie.size() == 3) {
      System.out.println("Attacker's rolls: \n" + attackerdie.get(0) + "\n" + attackerdie.get(1) + "\n" + attackerdie.get(2));
    }
    if (attackerdie.size() == 2) {
      System.out.println("Attacker's rolls: \n" + attackerdie.get(0) + "\n" + attackerdie.get(1));
    }
    if (attackerdie.size() == 1) {
      System.out.println("Attacker's rolls: \n" + attackerdie.get(0));
    }
    // This sets the defending dice to a value
    List<Integer> defenderdie = new ArrayList<Integer>(attack.getDefenseDefault());
    defenderdie = roll(attack.getDefenseDefault());
    Collections.sort(defenderdie);
    if (defenderdie.size() == 2) {
      System.out.println("Defender's rolls: \n" + defenderdie.get(0) + "\n" + defenderdie.get(1));
    }
    if (defenderdie.size() == 1) {
      System.out.println("Defender's rolls: \n" + defenderdie.get(0));
    }
    attack.setTerritoryGained(fight(attackerdie, defenderdie));
    if (attack.getTerritoryGained()) {
      attack.winnings();
    }
  }

  *//**
   * 
   * @param numDice
   * @return and list whose size is the number of die rolled and which contains
   *         the value of the die rolled
   * 
   *         This roll is to be compared in the battle method against another
   *         roll to determine a winner.
   *//*
  public List<Integer> roll(int numDice) {
    List<Integer> dieRoll = new ArrayList<Integer>(numDice);
    for (int i = 0; i < numDice; i++) {
      dieRoll.add((int) (Math.random() * 5) + 1);
    }
    return dieRoll;
  }

  public boolean fight(List<Integer> a, List<Integer> d) {
    int counter = 0;
    while (counter < Math.min(attack.getAttackDefault(), attack.getDefenseDefault())) {
      if (a.get(counter) > d.get(counter)) {
        defendersLost--;
      } else {
        attackersLost--;
      }
      counter++;
    }
    attack.setAttackingArmy(attackersLost);
    attack.setDefendingArmy(defendersLost);
    System.out.println("attacking army Left: " + attack.getAttackingArmy());
    System.out.println("defending army Left: " + attack.getDefendingArmy());
    System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::");

    if (attack.getDefendingArmy() == 0) {
      attack.getDefendingTerritory().setOwner(attack.getAttackingTerritory().getOwner());
      return true;
    } else {
      return false;
    }
  }

  public void resetAttackersLost() {
    attackersLost = 0;
  }

  public void resetDefendersLost() {
    defendersLost = 0;
  }
}
*/