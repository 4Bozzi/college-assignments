package model;

/**
 * 
 * @author Super Kewl Team
 * 
 *  Assigned to: Steven
 * 
 *         This class contains the functionality of the fortify stage as well as
 *         similar actions that may not be taken inside of the fortify stage
 * 
 *         methods contained: Fortify(Territory, Territory) moveOneArmy()
 *         moveArmies(int) moveForceArmies()
 */
public class Fortify {
	private Territory source;
	private Territory desitination;

	/**
	 * 
	 * @param source
	 * @param desitination
	 * 
	 *            This is the constructor which is called by the GUI and takes
	 *            two territories as Args
	 */
	public Fortify(Territory source, Territory desitination) {
		this.source = source;
		this.desitination = desitination;
	}

	/**
	 * This method just moves one army at a time and is used by the human user
	 * during the fortify stage
	 */
	// this will correspond to the button
	public void moveOneArmy() {
		source.adjustDice(-1);
		desitination.adjustDice(1);
	}

	/**
	 * This method just moves one army chunk at a time and is used by the AI
	 * during the fortify stage
	 */
	// this is for the AI
	public void moveArmies(int armies) {
		source.adjustDice(-armies);
		desitination.adjustDice(armies);
	}

	/**
	 * This method moves the required armies into an acquired territory right
	 * after a successful attack
	 */
	// this may be the default that always needs to be moved.
	public void moveForceArmies() {
		int dieRolled = source.getOwner().getAttackDefault();
		int movingArmies = source.getNumDice() - 1;
		if (movingArmies > 0) {
			if (dieRolled < movingArmies) {
				source.adjustDice(-dieRolled);
				desitination.adjustDice(dieRolled);
			} else {
				source.adjustDice(movingArmies);
				desitination.adjustDice(movingArmies);
			}
		}
	}
	public Territory getSource(){
		return source;
	}
	public Territory getDestination(){
		return desitination;
	}
}
