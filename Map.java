/*=============================================================================
 |   Assignment:  Program #2:  Bones Battle
 |       Author:  Andrew Bozzi (abozzi@email.arizona.edu)
 |
 |       Course:  C Sc 345
 |   Instructor:  L. McCann
 |     Due Date:  March 12, 2009
 |
 |  Description:  This is my computer strategy, it checks if there is a gap 
 |				  it can close to increase its largest connected territory
 |                
 | Deficiencies:  It has not been tested.
 *===========================================================================*/
import java.util.ArrayList;
import java.util.List;

/*
 * Class Map has several constants that are set by some other class as arguments.
 * Territory[][] map uses the Territory class to construct a 2d array of territories.
 * playerList uses the Player class to construct an ArrayList of player IDs.
 * The Map constructor creates a new 2d array map and fills it with territories and
 * their ID numbers, then calls constructGraph which should create the Graph with holes and
 * such but does nothing because i didn't finish it. It calls petittionTerritories which sets
 * the owners of each of the territories, it calls distributeDice which determines how many
 * dice each player has total and randomly spreads them out amongst the territories.
 */

/*+----------------------------------------------------------------------
||
||  Class Map
||
||         Author:  Andrew Bozzi
||
||        Purpose:  Construct and maintain the map of bones battle
|+-----------------------------------------------------------------------
||
||      Constants:  ROWS/COLUMNS - the size of the graph, VICTIMS: the number of inactive territories
||          OCCUPIED: the number of territories - number of inactive territories
||			MAXDICE: the maximum number of dice a player can have
||
|+-----------------------------------------------------------------------
||
||   Constructors:   Map: players, rows, columns, victims,
||			 maxDice 
||
++-----------------------------------------------------------------------*/

public class Map {
	int ROWS;
	int COLUMNS;
	int VICTIMS;
	int NUMTERRITORIES = (ROWS * COLUMNS);
	int OCCUPIED = (NUMTERRITORIES - VICTIMS);
	int MAXDICE;
	private Territory[][] map;
	private Graph graph;
	ArrayList<Player> playerList;


	public Map(ArrayList<Player> players, int rows, int columns, int victims,
			int maxDice) {

		playerList = players;
		map = new Territory[rows][columns];
		int counter = 0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Territory territory = new Territory(this);
				territory.setIdNum(counter);
				map[r][c] = territory;
				counter++;
			}
		}

		constructGraph(rows, columns, victims);
		partitionTerritories();
		distributeDice();
	}

    /*---------------------------------------------------------------------
    |  Method getters and setters
    |
    |  Purpose:  These are used for getting a reference to the Map, Graph, Territory(at location row, column)
    |      Territory Id number, the owner of a territory, neighbors of a territory, enemy neighbors of a territory
    |
    |  Parameters: Many of these have the parameter (rows, cols) so that we can specify which territory we are 
    |		interested in.
    |
    |  Returns:  A reference to each of the objects they are meant to get.
    *-------------------------------------------------------------------*/

	public Territory[][] getMap() {
		return map;
	}

	/*
	 * Getter getGraph returns the constructed graph.
	 */
	public Graph getGraph() {
		return graph;
	}

	/*
	 * getter getTerritory returns the territory at location (row,column) of the
	 * map of territories.
	 */
	public Territory getTerritory(int row, int column) {
		return map[row][column];
	}

	/*
	 * getTerritoryId returns the ID of the territory based on the getRow() and
	 * getCol() methods in the Territory class. The territory exists in map, the
	 * 2d array of territories.
	 */
	public int getTerritoryId(int row, int column) {
		return (map[row][column].getRow() * map[0].length)
				+ map[row][column].getCol();
	}

    
	/*---------------------------------------------------------------------
    |  Method countTerritories
    |
    |  Purpose:  Count up player's total number of territories
    |
    |  Parameters: Player player, we need to know which player we want to count their territories
    |      
    |
    |  Returns:  An integer representing the number of territories player owns
    *-------------------------------------------------------------------*/
	public int countTerritories(Player player) {
		int counter = 0;
		for (int r = 0; r < map[0].length; r++) {
			for (int c = 0; c < map.length; c++) {
				if (map[r][c].getOwner() == player) {
					counter++;
				}
			}
		}
		return counter;
	}

	/*---------------------------------------------------------------------
    |  Method countDice
    |
    |  Purpose:  Count up player's total number of dice
    |
    |  Parameters: Player player, we need to know which player we want to count their dice
    |      
    |
    |  Returns:  An integer representing the number of dice player has
    *-------------------------------------------------------------------*/
	public int countDice(Player player) {
		int counter = 0;
		for (int r = 0; r < map[0].length; r++) {
			for (int c = 0; c < map.length; c++) {
				if (map[r][c].getOwner() == player) {
					counter = counter + map[r][c].getDice();
				}
			}
		}
		return counter;
	}

	/*
	 * getPropertyOf takes in a player object as an argument, it returns an
	 * arraylist of territories that that player owns.
	 */
	public ArrayList<Territory> getPropertyOf(Player player) {
		List<Territory> playersProperties = new ArrayList<Territory>();
		for (int r = 0; r < map[0].length; r++) {
			for (int c = 0; c < map.length; c++) {
				if (map[r][c].getOwner() == player) {
					playersProperties.add(map[r][c]);
				}
			}
		}
		return (ArrayList<Territory>) playersProperties;
	}

	/*
	 * getNeighbors takes an a territory object and returns an arraylist of
	 * territories, it calls the getAdjacent method from the graph class and
	 * computes their indices in the 2d array map and adds them to an arraylist
	 * neighbors.
	 */
	public ArrayList<Territory> getNeighbors(Territory cell) {
		List<Territory> neighbors = new ArrayList<Territory>();
		if (cell.getRow() != ROWS) {
			if (graph.isInGraph(map[(cell.getRow() + 1)][cell.getCol()].getIdNum())) {
				neighbors.add(map[(cell.getRow() + 1)][cell.getCol()]);
			}
		}
		if (cell.getRow() != 0) {
			if (graph.isInGraph(map[(cell.getRow() - 1)][cell.getCol()]
					.getIdNum())) {
				neighbors.add(map[(cell.getRow() - 1)][cell.getCol()]);
			}
		}
		if (cell.getCol() != COLUMNS) {
			if (graph.isInGraph(map[cell.getRow()][(cell.getCol() + 1)]
					.getIdNum())) {
				neighbors.add(map[cell.getRow()][(cell.getCol() + 1)]);
			}
		}
		if (cell.getCol() != 0) {
			if (graph.isInGraph(map[cell.getRow()][(cell.getCol() - 1)]
					.getIdNum())) {
				neighbors.add(map[cell.getRow()][cell.getCol() - 1]);
			}
		}
		return (ArrayList<Territory>) neighbors;
	}

	/*
	 * getEnemyNeighbors does the same thing as getNeighbors except that it
	 * differentiates between enemy and friendly territories, only adding the
	 * enemies to the list.
	 */
	public ArrayList<Territory> getEnemyNeighbors(Territory cell) {
		List<Territory> neighbors = new ArrayList<Territory>();
		if (cell.getRow() != ROWS) {
			if (graph.isInGraph(map[(cell.getRow() + 1)][cell.getCol()]
					.getIdNum())) {
				if (cell.getOwner() == map[(cell.getRow() + 1)][cell.getCol()]
						.getOwner()) {
					neighbors.add(map[(cell.getRow() + 1)][cell.getCol()]);
				}
			}
		}
		if (cell.getRow() != 0) {
			if (graph.isInGraph(map[(cell.getRow() - 1)][cell.getCol()]
					.getIdNum())) {
				if (cell.getOwner() == map[(cell.getRow() - 1)][cell.getCol()]
						.getOwner()) {
					neighbors.add(map[(cell.getRow() - 1)][cell.getCol()]);
				}
			}
		}
		if (cell.getCol() != COLUMNS) {
			if (graph.isInGraph(map[cell.getRow()][(cell.getCol() + 1)]
					.getIdNum())) {
				if (cell.getOwner() == map[cell.getRow()][(cell.getCol() + 1)]
						.getOwner()) {
					neighbors.add(map[cell.getRow()][(cell.getCol() + 1)]);
				}
			}
		}
		if (cell.getCol() != 0) {
			if (graph.isInGraph(map[cell.getRow()][(cell.getCol() - 1)]
					.getIdNum())) {
				if (cell.getOwner() == map[cell.getRow()][(cell.getCol() - 1)]
						.getOwner()) {
					neighbors.add(map[cell.getRow()][cell.getCol() - 1]);
				}
			}
		}
		return (ArrayList<Territory>) neighbors;
	}

	/*
	 * partitionTerritories is used to set the owners of all of the territories
	 * in the 2d array of territories called map. It does so pseudo randomly by
	 * determining the most even number of territories as possible for each
	 * player and then randomly picks a player to own each territory unless the
	 * player has expended their maximum amount of territories, then it gives
	 * the particular territory to a different player who does have more to use
	 * up until they are all full.
	 */
	private void partitionTerritories() {
		int randomPlayer;
		int max = playerList.size() - 1;
		int min = 0;
		int[] tokens = new int[playerList.size()];
		int extraTokens = NUMTERRITORIES % playerList.size();
		for (int i = 0; i < playerList.size(); i++) {
			if (extraTokens > 0) {
				tokens[i] = (NUMTERRITORIES / playerList.size()) + 1;
				extraTokens--;
			} else {
				tokens[i] = NUMTERRITORIES / playerList.size();
			}
		}
		for (int r = 0; r < map[0].length; r++) {
			for (int c = 0; c < map.length; c++) {
				randomPlayer = (int) (Math.random() * (max - min + 1)) + min;
				if (tokens[randomPlayer] != 0) {
					map[r][c].setOwner(playerList.get(randomPlayer));
					tokens[randomPlayer]--;
				} else {
					c--;
				}
			}
		}

	}

	/*---------------------------------------------------------------------
    |  Method distributeDice
    |
    |  Purpose:  Give dice to each player pseudo randomly based on the number of players and territories
    |
    |  Parameters: None
    |      
    |  Returns:  none
    *-------------------------------------------------------------------*/
	private void distributeDice() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		int randomTerritory;
		int tokens;
		for (int i = 0; i < playerList.size(); i++) {
			territories.clear();
			tokens = countTerritories(playerList.get(i)) * 3;
			for (int r = 0; r < map[0].length; r++) {
				for (int c = 0; c < map.length; c++) {
					if (map[r][c].getOwner() == playerList.get(i)){
						territories.add(map[r][c]);
						map[r][c].setDice(1);
						tokens = tokens - 1;
					}
				}
			}
			while(tokens > 0){
				randomTerritory = (int) (Math.random() * territories.size() );
				if(territories.get(randomTerritory).getDice() < 8){
					territories.get(randomTerritory).setDice(territories.get(randomTerritory).getDice() +1);
					tokens --;
				}
			}
		}
	}
	
	/*---------------------------------------------------------------------
    |  Method countConnected
    |
    |  Purpose:  Count up player's total number of connected territories
    |
    |	*Uses a recursive helper method to do this
    |
    |  Parameters: Player player, we need to know which player we want to count their connected territories
    |      
    |  Returns:  An integer representing the number of connected territories player owns
    | 
    *-------------------------------------------------------------------*/
	public int countConnected(Player guy) {
		int max = 0;
		ArrayList<Territory> guysTerritories = new ArrayList<Territory>();
		
		for (int r = 0; r < map[0].length; r++) {
			for (int c = 0; c < map.length; c++) {
				if (map[r][c].getOwner() == guy){
					guysTerritories.add(map[r][c]);
				}
			}
		}
		while (guysTerritories.size() > 0){
			max = Math.max(recursiveConnected(guysTerritories.get(0), guysTerritories, guy), max);
		}
		return max;
	}
	
	private int recursiveConnected(Territory territory, List<Territory> guysTerritories, Player guy){
		int T = 0;
		int B = 0;
		int R = 0;
		int L = 0;
		guysTerritories.remove(territory);
		if(territory.getRow() != 0){
			if(map[(territory.getRow() - 1)][territory.getCol()].getOwner() == guy){
				T = recursiveConnected(map[(territory.getRow() - 1)][territory.getCol()], guysTerritories, guy);
			}
		}
		if(territory.getRow() != ROWS){
			if(map[(territory.getRow() + 1)][territory.getCol()].getOwner() == guy){
				B = recursiveConnected(map[(territory.getRow() + 1)][territory.getCol()], guysTerritories, guy);
			}
		}
		if(territory.getCol() != 0){
			if(map[territory.getRow()][(territory.getCol() - 1)].getOwner() == guy){
				L = recursiveConnected(map[territory.getRow()][(territory.getCol() - 1)], guysTerritories, guy);
			}
		}
		if(territory.getCol() != COLUMNS){
			if(map[territory.getRow()][(territory.getCol() + 1)].getOwner() == guy){
				R = recursiveConnected(map[territory.getRow()][(territory.getCol() + 1)], guysTerritories, guy);
			}
		}
		return T + B + L + R;
		
	}

    /*---------------------------------------------------------------------
    |  Method constructGraph
    |
    |  Purpose:  assembles the graph and connects the edges, randomly sets some inactive
    |      and makes sure that it is connected
    |
    |  Parameters: rows and cols to created the board, and victims to know how many to set inactive
    |      
    |
    |  Returns:  A constructed graph
    *-------------------------------------------------------------------*/

	public Graph constructGraph(int rows, int cols, int victims) {
		int randomToRemove = 0;
		graph = new Graph(rows*cols);
		while(!graph.connected()){
			for (int i = 0; i < rows*cols; i++) {
				if(i%rows != 0){
					graph.addEdge(i, (i+1));
					graph.addEdge(i, (i-1));
				}
				if(i%cols != 0){
					graph.addEdge(i, (i + rows));
					graph.addEdge(i, (i - rows));
				}
			}
			for(int j = 0; j < victims; j++){
				randomToRemove = (int) (Math.random() * (rows*cols -1)); 
				graph.removeVertex(randomToRemove);
			}
		}
		return graph;
	}
}
