/*+----------------------------------------------------------------------
 ||
 ||  Class C Sc 345
 ||
 ||         Author:  Andrew Bozzi
 ||
 ||        Purpose:  To interact with the graph and map classes to control the AI
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  Strategy
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  None
 ||
 ||  Inst. Methods:  setPlayer, args: Player, return: void
 ||                  willAttack, args: Map, return: boolean
 ||                  getAttacker, args: None, return: territory
 ||                  getDefender, args: None, return: territory
 ||
 ++-----------------------------------------------------------------------*/


import java.util.ArrayList;

//Player will be set to keep track of which player is playing, attacker and defender will be set
//in willAttack and returned in getAttacker and getDefender
public class ComputerStrategy implements Strategy {
    Player player; 
    Territory attacker;
    Territory defender;

    //Set the player
    public void setPlayer (Player whom) { 
    	player = whom; 
    }

    //Creates a list of Players territories, one by one gets each of players territories and gets his/her enemy 
    //territories. Checks if there is a territory one step past each of those enemies that it owned by player.
    //If not it just finds an enemy territory that it has more dice then, and attacks it. Otherwise returns false.
    public boolean willAttack (Map board) { 
    	ArrayList<Territory> playersTerritories = board.getPropertyOf(player);
    	
    	for(int i = 0; i < playersTerritories.size(); i++){
    		ArrayList<Territory> enemyNeighbors = board.getEnemyNeighbors(playersTerritories.get(i));
    		for(int j = 0; j < enemyNeighbors.size(); j++){
    			if(playersTerritories.get(i).getRow() != board.ROWS && playersTerritories.get(i).getRow() != (board.ROWS - 1)){
	    			if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()] == board.getMap()[playersTerritories.get(i).getRow() +1][playersTerritories.get(i).getCol()]){
	    				if(board.getMap()[enemyNeighbors.get(j).getRow() + 1][enemyNeighbors.get(j).getCol()].getOwner() == player){
	    					attacker = board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()];
	    					defender = board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()];
	    					return true;
	    				}
	    			}	
	    		}
    			if(playersTerritories.get(i).getRow() != 0 && playersTerritories.get(i).getRow() !=  1){
	    			if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()] == board.getMap()[playersTerritories.get(i).getRow() -1][playersTerritories.get(i).getCol()]){
	    				if(board.getMap()[enemyNeighbors.get(j).getRow() - 1][enemyNeighbors.get(j).getCol()].getOwner() == player){
	    					attacker = board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()];
	    					defender = board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()];
	    					return true;
	    				}
	    			}
    			}	
    			if(playersTerritories.get(i).getCol() != 0 && playersTerritories.get(i).getCol() != 1){	
	    			if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()] == board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol() +1]){
	    				if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol() + 1].getOwner() == player){
	    					attacker = board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()];
	    					defender = board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()];
	    					return true;
	    				}
	    			}
    			}	
	    		if(playersTerritories.get(i).getCol() != board.COLUMNS && playersTerritories.get(i).getCol() != (board.COLUMNS - 1)){	
	    			if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()] == board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol() -1]){
	    				if(board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol() - 1].getOwner() == player){
	    					attacker = board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()];
	    					defender = board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()];
	    					return true;
	    				}
	    			}
	    		}	
	    		if(board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()].getDice() > board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()].getDice()){
	    			attacker = board.getMap()[playersTerritories.get(i).getRow()][playersTerritories.get(i).getCol()];
					defender = board.getMap()[enemyNeighbors.get(j).getRow()][enemyNeighbors.get(j).getCol()];
					return true;
	    		}
    		}
    	}
    	return false; 
    }
    
    //Specifies the attacker
    public Territory getAttacker () { 
    	return attacker; 
    }
    
    //Specifies the defender
    public Territory getDefender () { 
    	return defender; 
    }

}
