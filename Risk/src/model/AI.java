package model;
/**
 * 
 * @author Team Super Kewl
 * Assigned to: Steven, Andrew
 * 
 * This is the AI interface which all AIs must follow
 *
 */
public interface AI {
	public void reinforce(Player reinforcer);
	public void calcDefaultDice(Territory t);
	public void attack(Player reinforcer);
	public void fortify(Player reinforcer);
}
