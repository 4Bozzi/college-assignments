/*+----------------------------------------------------------------------
 ||
 ||  Class EmailCollection.java 
 ||
 ||         Author:  Andrew Bozzi
 ||
 ||        Purpose:  This class is here to take care of the collection of emails including
 ||					everything from adding emails, the getters, both sorting algorithms, 2 
 ||					different constructors and the timer.
 ||
 ||  Inherits From: None.
 ||
 ||     Interfaces: None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  EmailCollection() and EmailCollection(EmailCollection)
 ||
 ||  Class Methods:  addEmail(Email email) returns void, getList() returns  ArrayList<Email>,
 ||					sortEmail(String sortType) returns void, sortEmailJava(String sortType) returns void,
 ||					Inner classes: SenderComparator implements Comparator, DateTimeComparator implements Comparator
 ++-----------------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmailCollection {
	private ArrayList<Email> emailCollection;
	
	
    /*---------------------------------------------------------------------
    |  Method EmailCollection
    |
    |  Purpose:  The first constructor of 2. This one just creates a variable to hold the emails.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: After this executes we will have the collection we are going to add the emails to
    |					and sort.
    |
    |  Parameters: None.        
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
	public EmailCollection() {
		emailCollection = new ArrayList<Email>();
	}
	
	
    /*---------------------------------------------------------------------
    |  Method EmailCollection(EmailCollection emailCollection)
    |
    |  Purpose:  This constructor creates a different emailCollection to be sorted
   	|			since we will always be sorting twice, we save some work by not having to
   	|			reload the file.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: Same as other constructor.
    |
    |  Parameters:	None.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
	public EmailCollection(EmailCollection emailCollection) {
			this.emailCollection = new ArrayList<Email>(emailCollection.getList());
	}

	
    /*---------------------------------------------------------------------
    |  Method addEmail(Email)
    |
    |  Purpose:  Adds an Email to the emailCollection ArrayList.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: The emailCollection will have this new Email at the end of it.
    |
    |  Parameters:
    |      email -- this is the Email that we want to add.
    |
    |  Returns:  None.
    *-------------------------------------------------------------------*/
	public void addEmail(Email email) {
		emailCollection.add(email);
	}
	
	
    /*---------------------------------------------------------------------
    |  Method getList()
    |
    |  Purpose:  We need to return the list of emails, or call size on email collection
    			so we need a list getter.
    |
    |  Pre-condition:  None.
    
    |  Post-condition: None.
    |
    |  Parameters:	None.
    |     
    |  Returns:  Returns an ArrayList of Emails.
    *-------------------------------------------------------------------*/
	public ArrayList<Email> getList(){
		return emailCollection;
	}
	
    /*---------------------------------------------------------------------
    |  Method sortEmail
    |
    |  Purpose:  This method is to be called on a list of Emails and puts
    |      		them in sorted order by sender or by date. It will also use
    |			a timer to determine how long it took to complete the sorting.
    |			I used insertion sort for my sort, I figured since the emails would already
    |			be mostly sorted, insertionSort would be extra fast. It does a decent job.
    |
    |  Post-condition: Upon completion the emailCollection should be ready to be printed
    |				into a file called "mailfile-mine".
    |
    |  Parameters:
	|		sortType -- Either Sender or Date
	|		
    |  Returns:  None
    *-------------------------------------------------------------------*/
	public void sortEmail(String sortType) {
        long startTime;
        double seconds;
		int location;
		Email temp;
		
        startTime = startTiming();
		for (int i = 1; i < emailCollection.size(); i++) {
			if (emailCollection.get(i).getDateTime().compareToIgnoreCase(emailCollection.get(i - 1).getDateTime()) <0) {
				temp = emailCollection.get(i);
				location = i;
				do {
										emailCollection.set(location, emailCollection.get(location - 1));
					location--;
				} while ((location > 0) && emailCollection.get(location - 1).getDateTime().compareToIgnoreCase(temp.getDateTime()) >0);
				emailCollection.set(location, temp);
			}
		}
		if(sortType == "sender"){
			for (int i = 1; i < emailCollection.size(); i++) {
				if (emailCollection.get(i).getSender().compareToIgnoreCase(emailCollection.get(i - 1).getSender()) <0) {
					temp = emailCollection.get(i);
					location = i;
					do {
											emailCollection.set(location, emailCollection.get(location - 1));
						location--;
					} while ((location > 0) && emailCollection.get(location - 1).getSender().compareToIgnoreCase(temp.getSender()) >0);
					emailCollection.set(location, temp);
				}
			}
		}
		seconds = stopTiming(startTime);
		System.out.println("My sorting algorithm sorted the mbox file in " + seconds + " miliseconds.");
	}
	
	
    /*---------------------------------------------------------------------
    |  Method sortEmailJava
    |
    |  Purpose:  This is the java sort that sorts the emails.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: A sorted emailCollection ready to be written into "mailfile-java".
    |
    |  Parameters:
	|			sortType -- Either Sender or Date
	|
    |  Returns:  None.
    *-------------------------------------------------------------------*/
	public void sortEmailJava(String sortType){
        long startTime;
        double seconds;
		
        startTime = startTiming();
		Collections.sort(emailCollection, new DateTimeComparator());
		if(sortType == "sender"){
			Collections.sort(emailCollection, new SenderComparator());
		}
		seconds = stopTiming(startTime);
		System.out.println("Java's sorting algorithm sorted the mbox file in " + seconds + " miliseconds.");
	}
	
   
	//These methods should look familiar, they are almost the same as the ones from McCann's example of how to use
	//the timer.
	public static long startTiming ()
    {
        System.gc();
        return (System.currentTimeMillis());
    }

    public static double stopTiming (long startingTime)
    {
        long elapsedTime = System.currentTimeMillis() - startingTime;
        return (elapsedTime);  
    }
    
    
    /*---------------------------------------------------------------------
    |  Class SendComparator
    |
    |  Purpose:  This class was used to aid in doing collections.sort by sender.
    |			It compares our senders, this was the best way I could think to 
    |			Implement Collections.sort
    *-------------------------------------------------------------------*/
    private class SenderComparator implements Comparator{
		public int compare(Object arg0, Object arg1) {
			return ((Email)arg0).getSender().compareToIgnoreCase(((Email)arg1).getSender());
		}
    	
    }
    
    
    /*---------------------------------------------------------------------
    |  Class DateTimeComparator
    |
    |  Purpose:  Same as above but for Date and Time sorting.
    *-------------------------------------------------------------------*/
    private class DateTimeComparator implements Comparator{
		public int compare(Object arg0, Object arg1) {
			return ((Email)arg0).getDateTime().compareToIgnoreCase(((Email)arg1).getDateTime());
		}
    	
    }
    
}
