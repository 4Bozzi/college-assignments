/*
Andrew Bozzi, C Sc 345, Program #4: Concordance Construction and 2-3-4 Trees, 
Dr. McCann, 4/30/09
*/
import java.awt.Point;
import java.util.ArrayList;

/*
This is the major Two Four Tree class
it has private classes "TreeNode" and "Data"
*/
public class TwoFourTree
{
	private TreeNode root; //root of our TreeNode

	public TwoFourTree()
	{
		root = new TreeNode(); //assign the root to a new tree node
	}
	
	
    /*---------------------------------------------------------------------
    |  Method insert()
    |
    |  Purpose:  	insert will call the insert method on the root node
					it takes a word and a point (paragraph, line)
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The data will be at the root.
    |
    |  Parameters:
    |      word - the word that goes into this spot
    		point - the location in the tree.
    |
    |  Returns:  none
    *-------------------------------------------------------------------*/

	public void insert(String word, Point point)
	{
		//call insert
		root.insert(new Data(word, point));
	}

    /*---------------------------------------------------------------------
    |  Method printString()
    |
    |  Purpose:  	printString displays the output in the proper format.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: None
    |
    |  Parameters:
    |   None
    |
    |  Returns:  none
    *-------------------------------------------------------------------*/
	public void printString()
	{
		//print the column labels
		System.out.printf("%-20s %s\n", "Word", "Occurrences [form: (Paragraph#, Line#)]");
		System.out.printf("%-20s %s\n", "----", "-----------");
		root.printString();
	}
	
	
	/*+----------------------------------------------------------------------
	 ||
	 ||  Class TreeNode
	 ||
	 ||         Author: 	Andrew Bozzi
	 ||
	 ||        Purpose:  This class defines the functionality of a node in the tree.
	 ||
	 ||  Inherits From:  None
	 ||
	 ||     Interfaces:  None
	 ||
	 |+-----------------------------------------------------------------------
	 ||
	 ||      Constants:  None
	 |+-----------------------------------------------------------------------
	 ||
	 ||   Constructors:  TreeNode()
	 ||
	 ||  Class Methods:  printString() returns void, isLeaf() returns boolean,
	 					isFull() returns boolean, split() returns void, popData returns Data,
	 					insert(Data) returns TreeNode, copyGreaterToLess(Data a, Data b) returns void,
	 					insertData(Data) returns void,
	 ||
	 ||
	 ++-----------------------------------------------------------------------*/

	private class TreeNode
	{
		Data head; //data is stored in a linked list, this is the head of that linked list

		public TreeNode()
		{
			head = null; //no data so set head to null
		}
		

	    /*---------------------------------------------------------------------
	    |  Method printString()
	    |
	    |  Purpose:  	printString is a recursive print method
						it prints left children, then root then right children
	    |
	    |  Pre-condition:  there is a tree with data
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   None
	    |
	    |  Returns:  none
	    *-------------------------------------------------------------------*/
		public void printString()
		{
			TreeNode previousTreeNode = null; //holds the previous node
			TreeNode currentTreeNode = null; //holds current node

			Data currentData = head; //start at the head of our data which is a linked list
			while (currentData != null) //while the currentData is not null
			{
				currentTreeNode = currentData.getLT(); //get the Less Than tree
				if (currentTreeNode != previousTreeNode)
					currentTreeNode.printString(); //print the Less Than tree
				previousTreeNode = currentTreeNode;

				currentData.getWord(); //get the current word
				System.out.printf("%-20s", currentData.getWord()); //print the current word
				ArrayList locations = currentData.getLocations(); //get the word's locations in the file
				for (int i = 0; i < locations.size(); i++) //print all of those
				{
					System.out.printf(" (%s,%s)", ((Point) locations.get(i)).x, ((Point) locations.get(i)).y);
					if ((((i + 1) % 8) == 0) && ((i + 1) != locations.size())) //only print 8 items per line
					{
						System.out.printf("\n%-20s", "\"");
					}
				}
				System.out.println(); //print a newline

				currentTreeNode = currentData.getGT(); //get the Greater Than tree
				if (currentTreeNode != previousTreeNode)
					currentTreeNode.printString(); //print the Greater Than tree
				previousTreeNode = currentTreeNode;
				currentData = currentData.nextData();
			}
		}


	    /*---------------------------------------------------------------------
	    |  Method isLeaf()
	    |
	    |  Purpose:  	isLeaf is used to find out if a TreeNode is a leaf
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   None
	    |
	    |  Returns:  boolean true or false if it is or isnt a leaf
	    *-------------------------------------------------------------------*/
		public boolean isLeaf()
		{
			Data currentData = head; //start at the head of the data
			while (currentData != null)
			{
				//if the data has children return false
				if ((currentData.getLT() != null) || (currentData.getGT() != null))
					return false;
				currentData = currentData.nextData();
			}
			return true; //otherwise return true
		}

	    /*---------------------------------------------------------------------
	    |  Method isFull()
	    |
	    |  Purpose:  	checks if the node is full
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   None
	    |
	    |  Returns:  boolean true or false if it is or isnt full
	    *-------------------------------------------------------------------*/
		public boolean isFull()
		{
			int count = 0;
			Data currentData = head;
			while (currentData != null)
			{
				count++;
				currentData = currentData.nextData();
			}
			return (count > 2); //if the count is > 2 then it is full
		}

	    /*---------------------------------------------------------------------
	    |  Method split()
	    |
	    |  Purpose:  	splits a full node into 3 nodes with room in them
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   None
	    |
	    |  Returns:  None
	    *-------------------------------------------------------------------*/
		public void split()
		{
			/*
			The splitting is performed as follows:
			a single median is chosen from among the leaf's elements and the new element.
			The values less than the median are put in the new left node
			and the values greater than the median are put in the new right node,
			with the median acting as a separation value.
			 */
			TreeNode lNode = new TreeNode(); //new left node
			lNode.insertData(popData()); //insert the two left values to the left node
			//lNode.insertData(popData());
			Data tempData = popData(); //hold the middle value for later
			TreeNode rNode = new TreeNode(); //new right node
			rNode.insertData(popData()); //insert the two right values to the right node
			//rNode.insertData(popData());
			this.insertData(tempData);

			TreeNode holdLT = tempData.getLT(); //hold the current node's Less Than pointer
			TreeNode holdGT = tempData.getGT(); //hold the current node's Greater Than pointer
			tempData.setLT(lNode); //set the Less Than pointer to our new less node
			tempData.setGT(rNode); //set the Greater Than pointer to our new greater node

			if (holdLT != null) //if the previous LT was not null then re-insert the data
			{
				Data fillLeft = holdLT.popData();
				while (fillLeft != null)
				{
					lNode.insert(fillLeft);
					fillLeft = holdLT.popData();
				}
			}

			if (holdGT != null) //if the previous GT was not null then re-insert the data
			{
				Data fillRight = holdGT.popData();
				while (fillRight != null)
				{
					rNode.insert(fillRight);
					fillRight = holdGT.popData();
				}
			}
		}

	    /*---------------------------------------------------------------------
	    |  Method popData()
	    |
	    |  Purpose:  	popData gets removes and returns the head of our data linked list
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   None
	    |
	    |  Returns:  Date, the data that is popped
	    *-------------------------------------------------------------------*/
		public Data popData()
		{
			Data poppedData = head;
			if (head != null)
			{
				head = head.nextData();
				poppedData.setNextData(null);
			}
			return poppedData;
		}

		
	    /*---------------------------------------------------------------------
	    |  Method insert()
	    |
	    |  Purpose:  		insert is a recursive method that inserts the data
							it either returns null or a TreeNode if a split occurred
							if a TreeNode is returned, the parent inserts that node into itself
							then it returns itself or null
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   newData - the data that we are inserting
	    |
	    |  Returns: TreeNode - this returns a treenode because it will sometimes have to recurse
	    *-------------------------------------------------------------------*/
		public TreeNode insert(Data newData)
		{
			
			//parent is full so we need to split and return the split
			if (isFull())
			{
				this.split();
				return this; //if split return myself to my caller to be added
			}
			
			//hit a leaf that is not full so simply add the data
			if (isLeaf() && !isFull())
			{
				//System.out.println("leaf inserting: " + newData.getWord());
				this.insertData(newData);
				return null;
			}

			//hit a leaf that is full so we need to split and return the split leaf
			if (isLeaf() && isFull())
			{
				//System.out.println("full inserting: " + newData.getWord());
				this.insertData(newData);
				this.split();
				return this; //if split return myself to my caller to be added
			}
			TreeNode result = null;
			TreeNode previousTreeNode = null;
			TreeNode currentTreeNode = null;

			Data currentData = head;
			while (currentData != null) //traverse the current data
			{
				/*
				if we find the newData in currentData
				add the location of the newData to the current one 
				 */
				if (currentData.getWord().equals(newData.getWord()))
				{
					currentData.addPoint((Point) newData.getLocations().get(0));
					return null;
				}

				//if the newData is less than the currentData then insert there
				if (newData.getWord().compareTo(currentData.getWord()) < 0)
				{
					currentTreeNode = currentData.getLT();
					if (currentTreeNode != previousTreeNode)
						result = currentTreeNode.insert(newData); //return the result of the insertion
					previousTreeNode = currentTreeNode;
					break;
				}

				//if the newData is greater than the currentData then insert there
				if ((newData.getWord().compareTo(currentData.getWord()) > 0) && (currentData.nextData() == null))
				{
					currentTreeNode = currentData.getGT();
					if (currentTreeNode != previousTreeNode)
						result = currentTreeNode.insert(newData); //return the result of the insertion
					previousTreeNode = currentTreeNode;
					break;
				}
				currentData = currentData.nextData();
			}

			//if the result is a TreeNode then insert it into myself
			if (result != null)
			{
				//parent is not full so simply add the data
				if (!isFull())
				{
					this.insertData(result.popData());
					return null;
				}
			}

			return null;
		}

	    /*---------------------------------------------------------------------
	    |  Method copyGreaterToLess()
	    |
	    |  Purpose:  	copyGreaterToLess will insert all of the
						Greater data from Data a into the Less data of Data b
						since we have 2 pointers on each Data item we end up with
						too many pointers, this method is needed to unify them
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   Data a, b, 2 data that we want the left and right of
	    |
	    |  Returns: None
	    *-------------------------------------------------------------------*/
		public void copyGreaterToLess(Data a, Data b)
		{
			if ((a == null) || (b == null))
				return;

			TreeNode dataGT = a.getGT(); //get a's Greater Than tree
			TreeNode dataLT = b.getLT(); //get b's Less Than tree

			if (dataGT != null)
			{
				Data fill = dataGT.popData(); //pop all of the greater data
				while (fill != null)
				{
					dataLT.insert(fill); //insert it into the less data
					fill = dataGT.popData();
				}
			}

		}

	    /*---------------------------------------------------------------------
	    |  Method insertData()
	    |
	    |  Purpose:  	used by insert method to Insert data when not full
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   Data -  the new data to be inserted
	    |
	    |  Returns: None
	    *-------------------------------------------------------------------*/
		public void insertData(Data newData)
		{
			if (newData == null) //should never happen, but just incase
				return;

			Data currentData = head;
			while (currentData != null) //search for duplicates and add their locations
			{
				if (currentData.getWord().compareTo(newData.getWord()) == 0)
				{
					currentData.addPoint((Point) newData.getLocations().get(0));
					return;
				}
				currentData = currentData.nextData();
			}

			if (head == null)
			{
				head = newData;
				return;
			}

			currentData = head;
			if (currentData.getWord().compareTo(newData.getWord()) > 0)
			{
				//will copy newData's greater to currentData's less;
				newData.setNextData(currentData);
				copyGreaterToLess(newData, currentData);
				head = newData;
				return;
			}

			while ((currentData.nextData() != null) && currentData.nextData().getWord().compareTo(newData.getWord()) < 0)
			{
				currentData = currentData.nextData();
			}
			
			/*
			inserting the new data between currentData and tempData
			will copy currentData's greater to newData's less and
			newData's greater to tempData's less
			 */
			Data tempData = currentData.nextData();
			currentData.setNextData(newData);
			copyGreaterToLess(currentData, newData);
			newData.setNextData(tempData);
			copyGreaterToLess(newData, tempData);
		}
	}

	/*+----------------------------------------------------------------------
	 ||
	 ||  Class Data 
	 ||
	 ||         Author:  Andrew Bozzi
	 ||
	 ||        Purpose:  	this is the Data class, it has variables for the data
							it also has a pointer to another Data class
							and a pointer to a tree of values less than itself
							and a pointer to a tree of values greater than itself
	 ||
	 ||  Inherits From:  None
	 ||
	 ||     Interfaces:  None
	 ||
	 |+-----------------------------------------------------------------------
	 ||
	 ||      Constants:  None
	 ||
	 |+-----------------------------------------------------------------------
	 ||
	 ||   Constructors:  Data(String, Point)
	 ||
	 ||  Class Methods:  getLocations() returns ArrayList<Point>, addPoint(Point) returns void,
	 					getters and setters.
	 ||
	 ||
	 ++-----------------------------------------------------------------------*/

	private class Data
	{
		private String word; //to hold the word

		private ArrayList<Point> locations; //to hold the list of locations

		//pointer to the next data item
		private Data nextData = null;

		//pointers to TreeNodes that contain data less than and greater than this data
		private TreeNode lThan, gThan;

		//Data constructor which creates new data
		public Data(String word, Point point)
		{
			locations = new ArrayList<Point>();
			this.word = word.toLowerCase();
			this.locations.add(point);
		}

		//get the locations ArrayList
		public ArrayList getLocations()
		{
			return locations;
		}

	    /*---------------------------------------------------------------------
	    |  Method addPoint()
	    |
	    |  Purpose:  	add a location to the locations ArrayList
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   Point -  the new Point to be added
	    |
	    |  Returns: None
	    *-------------------------------------------------------------------*/
		public void addPoint(Point point)
		{
			if (locations.size() == 0) //if no locations just add
				locations.add(point);
			else
			{
				//insert in sorted order
				int i = 0;
				while ((i < locations.size()) && (locations.get(i).x <= point.x))
				{
					if (locations.get(i).x == point.x)
					{
						if (locations.get(i).y >= point.y)
							break;
					}
					i++;
				}
				locations.add(i, point);
			}

		}
	    /*---------------------------------------------------------------------
	    |  Method getters and setters
	    |
	    |  Purpose:  	manipulation and access  of the data
	    |
	    |  Pre-condition:  None
	    |
	    |  Post-condition: None
	    |
	    |  Parameters:
	    |   Corresponding parameters for setters
	    |
	    |  Returns: Corresponding value for getters
	    *-------------------------------------------------------------------*/
		
		
		
		//Get the data's word
		public String getWord()
		{
			return word;
		}

		//set the datas less than treenode
		public void setLT(TreeNode node)
		{
			this.lThan = node;
		}

		//get the datas less than treenode
		public TreeNode getLT()
		{
			return lThan;
		}
		//set the datas greater than treenode
		public void setGT(TreeNode node)
		{
			this.gThan = node;
		}
		//get the datas greater than treenode
		public TreeNode getGT()
		{
			return gThan;
		}

		//set pointer to the next data node
		public void setNextData(Data nextData)
		{
			this.nextData = nextData;
		}

		//get the pointer to the next data node
		public Data nextData()
		{
			return this.nextData;
		}

	}
}