package model;
/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Steven, Matt
 * 
 *         This is an example of the decorator pattern. It is a HardAI that runs
 *         on a thread, and is used by the GUI. This Class also implements runnable
 *         
 *         methods contained: run()
 *         
 *         **NOTE: This class does not work properly, while the non treaded counter part works.
 *         There are bugs that have not yet been worked out yet.
 * 
 */
public class ThreadedHardAI extends HardAI implements Runnable {
	private Player player;
	
	/**
	 * This is the constructor for ThreadedHardAI, it calls methods from HardAI to set up the 
	 * threading for this player. 
	 * 
	 * @param player- this is the AI player whose turn it is.
	 */
	public ThreadedHardAI(Player player){
		this.player=player;
		Thread thread=new Thread();
		thread.start();
	}
	
	/**
	 * The run method calls methods from HardAI class, in attempt to act out the stages of a 
	 * turn (runs into a problem in the attack stage, reinforce and fortify should work though).
	 */
	public void run() {
			Object o = new Object();
			while (true) {
				synchronized (o) {
					this.reinforce(player);
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.calcDefaultDice(player.getFromTerritory());
					this.attack(player);
					this.fortify(player);
				}
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
}
