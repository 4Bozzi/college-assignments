package ece.test;

import java.util.Date;

/**
 * This class exists to hold information about the collected monsters. What
 * their name is, when they were captured, the image that they were captured in,
 * where in that picture they were located, and the ID.
 * 
 * @author Kyle +FOTB
 * 
 */
public class Monster {
  // attributes
  private String name;
  private String date;
  private String pic;
  private int x;
  private int y;
  private int id;

  /**
   * This constructor is used primarily by the inventory tab to make monsters
   * that will be loaded into the inventory file, that is why it is all string
   * based
   * 
   * @param inName
   *          Name of monster
   * @param inId
   *          ID of Monster
   * @param inPic
   *          Captured picture of monster
   * @param inXoffset
   *          Location of monster in captured monster
   * @param inYoffset
   *          Location of monster in captured monster
   */
  public Monster(String inName, String inId, String inPic, String inXoffset, String inYoffset) {
    name = inName;
    id = Integer.parseInt(inId);
    pic = inPic;
    x = Integer.parseInt(inXoffset);
    y = Integer.parseInt(inYoffset);
    Date d = new Date();
    date = ("d " + d.getMonth() + "/" + d.getDate() + "/" + d.getYear());
  }

  /**
   * Another version of the constructor, but this takes integers and is more
   * widely used.
   * 
   * @param inName
   * @param inId
   * @param inPic
   * @param inXoffset
   * @param inYoffset
   */
  public Monster(String inName, int inId, String inPic, int inXoffset, int inYoffset) {
    name = inName;
    id = (inId);
    pic = inPic;
    x = (inXoffset);
    y = (inYoffset);
    Date d = new Date();
    date = ("d " + d.getMonth() + "/" + d.getDate() + "/" + d.getYear());
  }

  /**
   * This will create a monster with all default values just by ID
   * 
   * @param inId
   */
  public Monster(int inId) {
    id = inId;
    name = this.getMonsterName();
    pic = ("" + this.getMonsterDrawable());
    x = 0;
    y = 0;
    Date d = new Date();
    date = ("d " + d.getMonth() + "/" + d.getDate() + "/" + d.getYear());
  }

  /**
   * Default constructor. In this case, it is assumed to be a Brolax at the top
   * left of the screen.
   */
  public Monster() {
    name = "Brolax";
    id = 0;
    pic = ("" + getMonsterDrawable());
    x = 0;
    y = 0;
    Date d = new Date();
    date = ("d " + d.getMonth() + "/" + d.getDate() + "/" + d.getYear());
  }

  /**
   * This essentially clones the monster, only used in rare case where static
   * monster is being cloned.
   * 
   * @param tempM
   */
  public Monster(Monster tempM) {
    name = tempM.getName();
    id = tempM.getId();
    pic = "" + tempM.getPic();
    x = tempM.getX();
    y = tempM.getY();
    date = tempM.getDate();
  }

  // getters and setters
  public String getName() {
    return name;
  }

  /**
   * method for updating the name of the monster
   * 
   * @param inName
   *          - the new name of the monster
   */
  public void setName(String inName) {
    name = inName;
  }

  /*
   * other getters and setters for the monster attributes
   */
  public int getId() {
    return id;
  }

  public void setId(int inId) {
    id = inId;
  }

  public String getPic() {
    return ("" + pic);
  }

  public void setPic(String inPic) {
    pic = inPic;
  }

  public int getX() {
    return x;
  }

  public void setX(int in) {
    x = in;
  }

  public int getY() {
    return y;
  }

  public void setY(int in) {
    y = in;
  }

  public String getDate() {
    return date;
  }

  public void setDate() {
    Date d = new Date();
    date = ("d " + d.getMonth() + "/" + d.getDate() + "/" + d.getYear());
  }

  public void setDate(String inDate) {
    date = inDate;
  }

  /**
   * Returns the correct resource to draw a monster based on their ID
   * 
   */
  public int getMonsterDrawable() {
    int drawable = 0;
    switch (id) {
    case 0:
      drawable = R.drawable.brolax;
      break;
    case 1:
      drawable = R.drawable.progre;
      break;
    case 2:
      drawable = R.drawable.xtremeprogre;
      break;
    default: // monster is invalid
      drawable = R.drawable.brolax; // use brolax in case of error
      break;
    }
    // Log.d("getDrawable: ", "received: " + id + " returning: " + drawable);
    return drawable;
  }
  /**
   * This set default names for the second version of monsters
   * @return
   */
  public String getMonsterName() {
    switch (id) {
    case 0:
      return "Brolax";
    case 1:
      return "Progre";
    case 2:
      return "Xtreme Progre";
    default: // monster is invalid
      return "Brolax"; // use brolax in case of error
    }
  }
}
