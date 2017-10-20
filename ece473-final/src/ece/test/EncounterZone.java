package ece.test;

import android.location.Location;
/**
 * This class exists to hold the specifications of a single encounter zone:
 * latitude and longitude boundaries, and which monster is encountered in 
 * this zone.
 * This class is contained in the MapTab class.
 * @author Andrew
 *
 */
public class EncounterZone {
	double lowerLongitude;
	double upperLongitude;
	double lowerLatitude;
	double upperLatitude;
	Monster monster; 
	
	/**
	 * This creates the encounter zone.  The zones are .0042 latitude wide and
	 * .0042 longitude wide. Also inserts the monster which is to be encountered 
	 * in this zone.  
	 * @param Lat
	 * @param Long
	 * @param monstId
	 */
    public EncounterZone(double Lat, double Long, Monster nuMonster){
    	lowerLongitude = Long - 0.0021;
    	upperLongitude = Long + 0.0021;
    	lowerLatitude = Lat - 0.0021;
    	upperLatitude = Lat + 0.0021;
    	monster = nuMonster;
    }
    
    /**
     * returns whether the location passed into this method is within
     * the bounds of the encounterzone recorded by this instance of the 
     * class
     * @param loc
     * @return
     */
    public boolean encounterZoneCheck(Location loc){
        return((loc.getLongitude() >= this.getLowerLongitude() && 
        		loc.getLongitude() <= this.getUpperLongitude())&&
        		(loc.getLatitude() >= this.getLowerLatitude() && 
        		loc.getLatitude() <= this.getUpperLatitude()));
    }
    
    
    /**
     * getters for the map tab to draw the encounter zone
     * @return
     */
    public double getLowerLongitude(){
    	return lowerLongitude;
    }
    
    public double getUpperLongitude(){
    	return upperLongitude;
    }
    
    public double getLowerLatitude(){
    	return lowerLatitude;
    }
    
    public double getUpperLatitude(){
    	return upperLatitude;
    }
    
    /**
     * getter for the map tab to tell the camera which monster to display
     * in the capture zone
     * @return
     */
    public Monster getMonster(){
    	return monster;
    }
}
