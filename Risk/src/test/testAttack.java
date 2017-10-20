package test;

import model.Attack;
import model.Board;
import model.Card;
import model.Player;
import model.Territory;

import org.junit.Test;


public class testAttack {
	@Test
	public void testAttack(){
		Board board=new Board();
		
		Player a =new Player("steven");
		Player b =new Player("notSteven");
		
		Territory t=new Territory();
		Territory r=new Territory();
		
		a.addTerritories(t);
		b.addTerritories(r);
		
		t.setOwner(a);
		r.setOwner(b);
		
		t.adjustDice(10);
		r.adjustDice(10);
		
		Attack attack=new Attack(r, t);
		
		attack.setAttackDice(3);
		attack.setDefendDice(1);
		
		attack.fightRound();
		
		System.out.println("Attacking armies: "+r.getNumDice());
		System.out.println("Defending armies: "+t.getNumDice());
	}
}
