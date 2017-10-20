package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Super Kewl Team
 * 
 *  Assigned to: Steven
 *
 *		Globe is used for easy access to each individual territory. When a territory is created it adds itself
 *		to the ArrayList globalTerr and there are getters and setters for globalTerr.
 */

public class Globe {
	
	public static ArrayList<Territory> globalTerr;
	
	/**
	 * Globe() constructs the arraylist that holds all the territories
	 */
	public Globe() {
		globalTerr = new ArrayList<Territory>();
	}
	
	//probably never used from what i can tell
	public void addTerritory(Territory t){
		globalTerr.add(t);
	}
	
	/**
	 * This is a very useful method which allows you to input an INTEGER to get a TERRITORY
	 * which is extremely helpful since we often just refer to a territory by its ID number. Therefore
	 * if at some point all you have is an integer and can't call the methods you usually call on 
	 * territory, you can get the territory from the integer.
	 * 
	 * @param id - an integer representing a territories unique id.
	 * 
	 * @return the territory that has the unique id "id"s
	 */
	public Territory getTerritoryByID(int id) {
		Iterator<Territory> iter = globalTerr.iterator();
		while(iter.hasNext()){
			Territory terr = iter.next();
			
			if(terr.getUniqueID() == id){
				return terr;
			}
		}
		return null;
		
	}
}
