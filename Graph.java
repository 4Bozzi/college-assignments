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
 * Class graph constructs a 2d array that will be an adjacency matrix to keep track of 
 * and create/remove the vectors and edges. It is used in many methods in the Map class. 
 */
public class Graph {

	private int[][] adjacencyMatrix;
	
	/*
	 * Constructor Graph takes in the number of vertices as an integer and creates a square
	 * matrix of that length of sides to hold the adjacency relations between the vectors.
	 */
	public Graph(int numVertices) {
		adjacencyMatrix = new int[numVertices][numVertices];
	}
	
	/*
	 * getUnusedVertices i think would better be named getInactiveVertices since that the term
	 * most commonly used (active/inactive), it returns a list of integers that represent
	 * the vectors that are not active in the graph, not active meaning they have not
	 * adjacent vectors.
	 */
	public List<Integer> getUnusedVertices() {
		List<Integer> unusedVertices = new ArrayList<Integer>();
		boolean unused;
		for (int y = 0; y < adjacencyMatrix.length; y++) {
			unused = true;
			for (int x = 0; x < adjacencyMatrix[0].length; x++) {
				if (adjacencyMatrix[x][y] == 1) {
					unused = false;
					break;
				}
			}
			if (unused == true) {
				unusedVertices.add(y);
			}
		}
		return unusedVertices;
	}
	
	/*
	 * isEdge checks to see if there is an edge between the source and destination vectors
	 * and returns true or false.
	 */
	public boolean isEdge(int source, int destination) {
		return adjacencyMatrix[source][destination] == 1;
	}
	
	/*
	 * addEdge creates an edge between the source and the destination regardless of if there
	 * was one there already or not.
	 */
	public void addEdge(int source, int destination) {
		adjacencyMatrix[source][destination] = 1;
		adjacencyMatrix[destination][source] = 1;
	}
	
	/*
	 * removeEdge takes away the edge between the source and destination vectors regardless
	 * of if there wasn't one there already.
	 */
	public void removeEdge(int source, int destination) {
		adjacencyMatrix[source][destination] = 0;
		adjacencyMatrix[destination][source] = 0;
	}
	
	/*
	 * isInGraph returns true if the specified vertex is active in the graph, and false
	 * if it isn't.
	 */
	public boolean isInGraph(int vertex) {
		for (int x = 0; x < adjacencyMatrix[0].length; x++) {
			if (adjacencyMatrix[x][vertex] == 1) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * removeVertex causes the vertex to not have any incident vectors on it, otherwise
	 * causing it to not be active in the grap.
	 */
	public void removeVertex(int vertex) {
		for (int x = 0; x < adjacencyMatrix[0].length; x++) {
			adjacencyMatrix[x][vertex] = 0;
		}
		for (int y = 0; y < adjacencyMatrix[0].length; y++) {
			adjacencyMatrix[vertex][y] = 0;
		}
	}
	
	/*
	 * getAdjacent returns a list of integers that represent the vertices that are adjacent
	 * to the specified vertex.
	 */
	public List<Integer> getAdjacent(int vertex) {
		List<Integer> adjacent = new ArrayList<Integer>();
		for (int x = 0; x < adjacencyMatrix[0].length; x++) {
			if (adjacencyMatrix[x][vertex] == 1) {
				adjacent.add(x);
			}
		}
		return adjacent;
	}
	
	/*
	 * degree takes in a vertex and returns an integer that represents how many edges it has
	 * with other vertices.
	 */
	public int degree(int vertex) {
		return getAdjacent(vertex).size();
	}
	
	/*
	 * connected is a BFS that returns true if all of the vertices were reachable and false if
	 * any of the vertices is left after it finds as many as it can possibly reach.
	 */
	public boolean connected() {
		List<Integer> activeVertices = new ArrayList<Integer>();
		List<Integer> queue = new ArrayList<Integer>();
		for (int i = 0; i < adjacencyMatrix[0].length; i++) {
			if (isInGraph(i)) {
				activeVertices.add(i);
			}
		}
		queue.add(activeVertices.get(0));
		activeVertices.remove(0);
		while (queue.size() != 0) {
			for (int i = 0; i < getAdjacent(queue.get(0)).size(); i++) {
				int vertex = getAdjacent(queue.get(0)).get(i);
				if (activeVertices.contains(vertex)) {
					queue.add(vertex);
					activeVertices.remove((Object) vertex);
				}
			}
			queue.remove(0);
		}
		return activeVertices.size() == 0;
	}

}
