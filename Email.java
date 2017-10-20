/*+----------------------------------------------------------------------
 ||
 ||  Class Email.java
 ||
 ||         Author:  Andrew Bozzi
 ||
 ||        Purpose:  These are the Email objects that an EmailCollection consists of.
 ||
 ||  Inherits From:  None.
 ||
 ||     Interfaces:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  Email(int emailStart,String sender,String dateTime)
 ||
 ||  Class Methods:  getEmailStart returns void, getEmailEnd returns void, getSender returns void,
 ||					getDateTime returns void, setEmailEnd returns void
 ||
 ++-----------------------------------------------------------------------*/


public class Email {
	private int emailStart;
	private int emailEnd;
	private String sender;
	private String dateTime;
	
	
    /*---------------------------------------------------------------------
    |  Method Email
    |
    |  Purpose:  This method constructs an Email. Emails consist of and integer representing
    |			the line that the email starts on, the sender, and the date/time it was received.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: A new email will exist, and be ready to be added to the EmailCollection.
    |
    |  Parameters:
    |      emailStart -- the starting line of the email. 
    |      sender -- the sender of the email.
    |      dateTime -- the date/time representation.
    |
    |  Returns:  [If this method sends back a value via the return
    |      mechanism, describe the purpose of that value here, otherwise
    |      state 'None.']
    *-------------------------------------------------------------------*/
	public Email(int emailStart,String sender,String dateTime){
		this.emailStart = emailStart;
		this.dateTime = dateTime;
		this.sender = sender;
	}
	
	
    /*---------------------------------------------------------------------
    |  Method getEmailStart, getEmailEnd, getDateTime, getSender, setEmailEnd
    |
    |  Purpose:  The getters and setters. We need to know the Emails start, and end
    |			and the date and time it was recieve, and the sender. setEmailEnd
    |			ended up being necessary to make sure i didnt miss a line.
    |
    |  Pre-condition:  None.
    |
    |  Post-condition: None.
    |
    |  Parameters:
    |     emailEnd -- the last line of the email.
    |
    |  Returns:  Each getter returns an appropriate value.
    *-------------------------------------------------------------------*/
	public int getEmailStart(){
		return emailStart;
	}
	
	public int getEmailEnd(){
		return emailEnd;
	}
	
	public String getDateTime(){
		return dateTime;
	}
	
	public String getSender(){
		return sender;
	}
	
	public void setEmailEnd(int emailEnd){
		this.emailEnd = emailEnd;
	}
}

