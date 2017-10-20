package model;
import java.util.Timer;
/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Steven, Matt
 * 
 *         This is an example of the decorator pattern. It is a EasyAI that runs
 *         on a thread, and is used by the GUI. This Class also implements
 *         runnable
 * 
 *         methods contained: run()
 *         
 *         **NOTE: This class does not work properly, while the non treaded counter part works.
 *         There are bugs that have not yet been worked out yet.
 * 
 */
public class ThreadedEasyAI extends EasyAI implements Runnable {

	private Player player;
	private Thread thread;

	/**
	 * This is the constructor for ThreadedEasyAI, it calls methods from EasyAI to set up the 
	 * threading for this player. 
	 * 
	 * @param player- this is the AI player whose turn it is.
	 */
	
	public ThreadedEasyAI(Player player) {
		this.player = player;
		thread=new Thread();
		thread.start();
	}

	/**
	 * The run method calls methods from EasyAI class, in attempt to act out the stages of a 
	 * turn (runs into a problem in the attack stage, reinforce and fortify should work though).
	 */
	public void run() {
		while (true) {
				this.reinforce(player);
				this.attack(player);
				this.fortify(player);
				thread.notify();
				try {
					thread.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
