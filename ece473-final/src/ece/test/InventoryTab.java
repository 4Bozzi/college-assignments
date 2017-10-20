package ece.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 
 * @author Kyle Haston
 *
 *  ----- Inventory Tab Content for ECE 473 Beta Release -----
 *  This code is the framework for the inventory tab that will 
 *  allow the user to view info regarding the selected monster. 
 */
 public class InventoryTab extends Activity {
  // this defines how many lines per monster in the file inventory.txt
  int linesPerMonster;
  // this will hold all monster info that we read in from a file
  ArrayList<Monster> monsters;
  static Vector<String> monsterInfo;
  
  static boolean debugflag;

  private LinearLayout holderLayout;
  private TextView tv; // displays description of this tab
  private ListView lv; // listview of all captured monsters
  @SuppressWarnings("unused")
private InputStream is; // points to file on sdcard
  ArrayAdapter<String> aa; // used to populate our list view
  EditText et; // used for renaming monsters
  private SampleView sview;
  
  FileInputStream fileIS; // used to read in file on sdcard with monster info
  static FileWriter fWriter; // used for writing out changes to names etc.

  Vector<String> strs;
  int i; // used for indexing
  int positionInList; //used for renaming and removing monsters
  private Button renameButton; //let's the user rename their monsters
  private Button releaseButton; //let's the user release their monsters
  private Button doneButton; // when they're done changing the name

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);	
    this.setContentView(R.layout.inventoryview);
    // lines per monster in inventory.txt file
    linesPerMonster = 6;
    
	// create a new vector that holds the monsters
	monsters = new ArrayList<Monster>();
	// create a new arraylist that holds lines from the file on the sdcard
	monsterInfo = new Vector<String>();
    
    //link the edit text with our xml description
    et = (EditText) this.findViewById(R.id.editName);
    et.setVisibility(View.GONE);
	
    // set up the buttons

    this.doneButton = (Button) this.findViewById(R.id.DoneButton);
    doneButton.setVisibility(View.GONE);
    doneButton.setOnClickListener(new OnClickListener() {
      public void onClick(View V) {
        Log.d("DONE BUTTON: ", "CLICKED");
        //remove newline chars from name
        String newName = new String("" + et.getText()).replace('\n',' ');
        newName = newName.replace('\r',' ');
        
        // change the name in the list
        aa.remove(monsters.get(positionInList).getName());
        aa.insert(newName, positionInList);

        // change the name in the vector of monsters
        monsters.get(positionInList).setName(newName);
        
        // change the name in the vector of file lines
        Log.d("RENAME: ","changing name: "  + monsterInfo.elementAt(positionInList) + " to " + newName);
        monsterInfo.remove(positionInList*linesPerMonster);
        Log.d("RENAME: ","removed position = " + positionInList*linesPerMonster);
        monsterInfo.insertElementAt("n " + newName, positionInList*linesPerMonster);

        writeInfoToFile();
        
        renameButton.setVisibility(View.GONE);
        releaseButton.setVisibility(View.GONE);
        sview.setVisibility(View.GONE);
        et.setVisibility(View.GONE);
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(et.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        lv.setVisibility(View.VISIBLE);
        doneButton.setVisibility(View.GONE);
      }
    });
    
    // link with xml description
    this.renameButton = (Button) this.findViewById(R.id.RenameButton);
    renameButton.setOnClickListener(new OnClickListener() {
      public void onClick(View V) {
        Log.d("RENAME BUTTON: ", "CLICKED");
        renameButton.setVisibility(View.GONE);
        releaseButton.setVisibility(View.GONE);
        sview.setVisibility(View.GONE);
        doneButton.setVisibility(View.VISIBLE);
        et.setVisibility(View.VISIBLE);
        et.setText(monsters.get(positionInList).getName());
      }
    });
    
    // set up the release button
    this.releaseButton = (Button) this.findViewById(R.id.ReleaseButton); 
    releaseButton.setOnClickListener(new OnClickListener() {
      public void onClick(View V) {
        Log.d("RELEASE BUTTON: ", "CLICKED");
        if(monsters.size() < 2) { //don't let them release their only monster
            lv.setVisibility(View.VISIBLE);
            renameButton.setVisibility(View.GONE);
            releaseButton.setVisibility(View.GONE);
            sview.setVisibility(View.GONE);
            
            Context context = getApplicationContext();
            CharSequence text = "You can't release your last monster!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            
        	return;
        }
        // remove the monster from the list
        aa.remove(monsters.get(positionInList).getName());
        // remove elements related to this monster from vector of monsters
        // and from vector of strings
        Log.d("RELEASE: ","Original size: " + ((Integer) monsters.size()).toString());
        monsters.remove(positionInList);
        Log.d("RELEASE: ","New size: " + ((Integer) monsters.size()).toString());
        for(int j=0 ; j< (linesPerMonster) ; j++) { // for each line per monster
        	monsterInfo.remove((positionInList*linesPerMonster)); // edit monster info in turn
        	Log.d("RELEASE: ","removing line: " + ((positionInList*linesPerMonster)+j));
        }
        // write changes to the file on the sdcard and re-populate the list
        writeInfoToFile();
         
        Log.d("RELEASE: ","Done Writing Modified File");

        lv.setVisibility(View.VISIBLE);
        sview.setVisibility(View.GONE);
        renameButton.setVisibility(View.GONE);
        releaseButton.setVisibility(View.GONE);
      }
    });

    // hide the buttons they click a monster
    renameButton.setVisibility(View.GONE);
    releaseButton.setVisibility(View.GONE);


    // setup the textview that will display intro text
    tv = new TextView(this);
    tv.setTextSize(18);
    tv.setText("Inventory of Captured Monsters");

    // setup the list that will hold the creature names
    lv = (ListView) findViewById(R.id.listEntries);
    lv.addHeaderView(tv);
    lv.setOnItemClickListener(new ItemClickedListener());
    
    holderLayout = (LinearLayout) findViewById(R.id.holderLayout);
    holderLayout.setScrollContainer(true);
    
	sview = new SampleView(this);
	sview.setVisibility(View.GONE);
	sview.setOnClickListener(new OnClickListener() {
	     public void onClick(View V) {
         V.setVisibility(View.GONE);
	     renameButton.setVisibility(View.GONE);
	     releaseButton.setVisibility(View.GONE);
	     lv.setVisibility(View.VISIBLE);
	     }
	   });

	holderLayout.addView(sview, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
	  
    // put this here in case they capture before inventory tab comes to foreground
    //if (monsterInfo.size() < 1) {
    	initList();
    //}
  }
  
  public void onResume() {//onWindowFocusChanged() {
	    super.onResume(); // not calling this throws an exception
		Log.d("ONRESUME: ","inventory tab has come to the foreground");
		//call this here instead of in onCreate() in case they
	    // just captured a new monster 
		//setup the list of monsters
	    initList();
		}
  
  private class ItemClickedListener implements OnItemClickListener {

    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
      Log.d("LIST: ", "ITEM CLICKED: " + position);

      if (position != 0) {
    	// update global var that holds which monster they clicked
      	positionInList = position - 1;
      	
        Log.d("LIST: " , "clicked creature at position: " + position);
    	
    	// show the picture of the monster
        showMonster(monsters.get(positionInList));

        // show the buttons
        renameButton.setVisibility(View.VISIBLE);
        releaseButton.setVisibility(View.VISIBLE);

      }
    }
  }
  
  /**
   * This code sets up the listview of creatures 
   * based on the file that we already read in. 
   */
  public void initList() {
	  // clear monsters etc.
	  monsters.clear();
	  monsterInfo.clear();
	  
    // read the info from the file
	try {
		monsterInfo = readList("/inventory.txt");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
    // now we split up the data (read from the inventory file) into a vector of
    // monsters
    // adding monster info as we go
    Log.d("INITLIST: ", "number of lines in inventory file: " + monsterInfo.size());
    for (i = 0; i < monsterInfo.size(); i++) {
      Log.d("INITLIST: ", "READ FROM VECTOR: " + monsterInfo.elementAt(i));

      // depending on the first letter of this string
      // we will add info to the vector of monsters

      switch (monsterInfo.elementAt(i).charAt(0)) {

      // found new monster name
      case 'n':
        // create temp monster
        Monster tempM = new Monster();
        // set name (from 1st character to end)
        tempM.setName(monsterInfo.elementAt(i).substring(1, monsterInfo.elementAt(i).length()));
        Log.d("INITLIST: ", "name = " + tempM.getName());
        // add to vector of monsters
        monsters.add(tempM);
        break;

      // found new monster pic id
      case 'i':
    	 String temp = new String();
        // set pic name (from 2nd character to end)
        temp = monsterInfo.elementAt(i).substring(2, monsterInfo.elementAt(i).length());
        // set pic name for creature
        monsters.get(i/linesPerMonster).setId(Integer.parseInt(temp)); // used as an integer to index the vector
        Log.d("INITLIST: ", "id = " + temp);
        break;

      // found new monster pc name
      case 'p':
        String tempP = new String();
        // set pic name (from 2nd character to end)
        tempP = monsterInfo.elementAt(i).substring(2, monsterInfo.elementAt(i).length());
        // set date for creature
        monsters.get(i/linesPerMonster).setPic(tempP); // used as an integer to index the vector
        Log.d("INITLIST: ", "pic = " + tempP);
        break;
        
      // found new xoffset
      case 'x':
        String tempX = new String();
        tempX = monsterInfo.elementAt(i).substring(2, monsterInfo.elementAt(i).length());
        // set date for creature
        monsters.get(i/linesPerMonster).setX(Integer.parseInt(tempX)); // used as an integer to index the vector
        Log.d("INITLIST: ", "xoffset = " + tempX);
        break;
        
      // found new yoffset
      case 'y':
        String tempY = new String();
        tempY = monsterInfo.elementAt(i).substring(2, monsterInfo.elementAt(i).length());
        // set date for creature
        monsters.get(i/linesPerMonster).setY(Integer.parseInt(tempY)); // used as an integer to index the vector
        Log.d("INITLIST: ", "yoffset = " + tempY);
        break;

      // found new monster capture date
      case 'd':
        String tempD = new String();
        // set date (from 2nd character to end)
        tempD = monsterInfo.elementAt(i).substring(2, monsterInfo.elementAt(i).length());
        // set date for creature
        monsters.get(i/linesPerMonster).setDate(tempD); // used as an integer to index the vector
        Log.d("INITLIST: ", "date = " + tempD);
        break;

      // disregard all other lines
      default:
        Log.d("INITLIST: ", "Line ignored; didn't start with reserved character.)");
      } // end switch
    } // end for
    Log.d("INITLIST: ", "# Monsters: " + monsters.size());

    // make the list using the names of the monsters
    aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    for (i = 0; i < monsters.size(); i++) {
      aa.add(monsters.get(i).getName());
    }
    lv.setAdapter(aa);

    Log.d("INITLIST: ", "COMPLETE");
  }

  /**
   * This code reads lines from the text file on the sdcard.
   * It returns a vector of strings (each is a line in the file).
   */
  public Vector<String> readList(String filename) throws IOException {
    Vector<String> tempStr = new Vector<String>();

    // load the list of monster names from the file located on the sdcard
    try {
     	File f =new File(Environment.getExternalStorageDirectory() + filename);
    	fileIS = new FileInputStream(f);
    	BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
    	String readString = new String();

    	for (i = 0; (readString = buf.readLine()) != null; i++) {
    		Log.d("READLIST: ", "adding monster info: " + readString);
    		tempStr.addElement(readString);
    	}
    } catch (FileNotFoundException e) {
    	// if the file wasn't found, give them a starting pack of monster
        e.printStackTrace();
        Log.d("READLIST: ", "FILE NOT FOUND. TRYING TO CREATE DEFAULT INVENTORY FILE.");
        Monster tempM1 = new Monster("Brolax", "0", "defaultBack.jpg", "50", "70");
        Monster tempM2 = new Monster("Progre", "1", "defaultBack.jpg", "80", "80");
        Monster tempM3 = new Monster("Xtreme Progre", "2", "defaultBack.jpg", "95", "50");
        addMonster(tempM1);
        addMonster(tempM2);
        addMonster(tempM3);
		tempStr = readList(filename); // file was created on sdcard
		return tempStr;
    } catch (IOException e) {
      Log.d("READLIST: ", "IO EXCEPTION.");
      e.printStackTrace();
    }

    Log.d("READLIST: ", "monster info lines read: " + tempStr.size());

    try {
		fileIS.close();
	} catch (IOException e) {
		Log.d("READLIST: ","Unable to close file.");
		e.printStackTrace();
	}
    Log.d("READLIST: ", "COMPLETE");

    return tempStr;
  }

  /**
   * This function takes all lines in monsterInfo and
   * writes them to the file on the sdcard.
   */
  public static void writeInfoToFile() {
      // write these changes to the file
      try{
  		fWriter = new FileWriter("/sdcard/inventory.txt");
      	//for every monster, write linesPerMonster lines
      	for(int i = 0; i < (monsterInfo.size()) ; i++) {
      		fWriter.write( monsterInfo.elementAt(i) + "\n" );
      		Log.d("WRITEINFOTOFILE: ","wrote this line to new text file: " + monsterInfo.elementAt(i));
      	}
          fWriter.flush();
          fWriter.close();
       }catch(Exception e){
                e.printStackTrace();
                Log.d("WRITEINFOTOFILE: ","Could not write file.");
       }
  }

  /**
   * This function shows the monster and the rename and release buttons.
   * @param tempM
   */
  public void showMonster(Monster tempM){
	  sview.init(tempM);
	  sview.setVisibility(View.VISIBLE); // show the monster
	  lv.setVisibility(View.GONE); // hide the list
	    sview.setOnClickListener(new OnClickListener() {
	        public void onClick(View V) {
	        V.setVisibility(View.GONE);
	        renameButton.setVisibility(View.GONE);
	        releaseButton.setVisibility(View.GONE);
	        lv.setVisibility(View.VISIBLE);
	        }
	      });
  }
  
  /**
   * This function is called by the camera tab when a new monster is captured.
   * Also, this is a handy way to simulate a capture.
   * A monster sent here will be written to the file on the sdcard.
   * This function is used to give the user their starting monsters. 
   */
  static public void addMonster(Monster tempM) {
	  try {
		  monsterInfo.size();
	  }
	  catch (Exception e) {
		  monsterInfo = new Vector<String>();
	  }
	if (monsterInfo.size() < 1) {
	  monsterInfo = new Vector<String>();
	}
	Log.d("addMonster: ","adding a monster");
	monsterInfo.addElement("n " + tempM.getName()); //name
	Log.d("addMonster: ","name: " + tempM.getName());
	monsterInfo.addElement("i " + tempM.getId()); //monster's picture name
	Log.d("addMonster: ","name: " + tempM.getPic());
	monsterInfo.addElement("p " + tempM.getPic()); //background picture name
	Log.d("addMonster: ","name: " + tempM.getPic());
	monsterInfo.addElement("x " + tempM.getX()); //x offset over background picture
	Log.d("addMonster: ","name: " + tempM.getX());
	monsterInfo.addElement("y " + tempM.getY()); //y offset over background picture
	Log.d("addMonster: ","name: " + tempM.getY());
	monsterInfo.addElement(tempM.getDate());
	Log.d("addMonster: ","name: " + tempM.getDate());
	writeInfoToFile();
	Log.d("addMonster: ","DONE");
  }

  /**
   * This class is used to show the monster after the user
   * clicks on it from the listview.
   * 
   * @author Kyle Haston
   *
   */
  private static class SampleView extends View {
	        @SuppressWarnings("unused")
			private Bitmap bmBack;
	        @SuppressWarnings("unused")
			private Bitmap bmImg;
	        Monster myMon;
	        
	        public SampleView(Context context) {
	          super(context);
	          setFocusable(true);
	        }

	        public void init (Monster tempM) {
		          myMon = new Monster(tempM);
	        }
	        
	        @Override protected void onDraw(Canvas canvas) {
		        InputStream is = super.getContext().getResources().openRawResource(myMon.getMonsterDrawable());
		        Rect windowSize = new Rect();
		        this.getWindowVisibleDisplayFrame(windowSize);
		        int w = windowSize.width();
		        int h = windowSize.height();
		    	int picWidth = 2048; // defined default resolution of camera
		    	int picHeight = 1536; // defined default resolution of camera
		    	
		    	int downSample = 1; 
		    	
		    	if(picWidth/w > picHeight/h) {
		    		downSample = picWidth/w;
		    	}
		    	else {
		    		downSample = picHeight/h;
		    	}
		        Log.d("SCALING PIC: ","width of this window: " + windowSize.width());
		        Log.d("SCALING PIC: ","height of this window: " + windowSize.height());
		        Log.d("SCALING PIC: ","downSample: " + downSample);
		        
		          // set up scaling
		          BitmapFactory.Options options = new BitmapFactory.Options();
		          options.inSampleSize = 2; //downSample;
		          Bitmap bmImg = BitmapFactory.decodeStream(is,null, options);
		          
		    	  // set up different scaling for huge background image
		    	  //options.inSampleSize = downSample + (downSample%2); // downsample a bit more in hopes of catching a pwr of 2 (faster)
		          options.inSampleSize = downSample + 4;
		    	  Bitmap bmBack; // declare a bitmap
		    	  // write to the bitmap 1 of 2 ways
		    	    File f =new File(Environment.getExternalStorageDirectory() + "/" + myMon.getPic());
		    	    if(f.exists()) { // valid pic on sdcard
		    		  bmBack = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + myMon.getPic(),options);
		    	    }
		    	    else {
		    		  // use the default background from the /res/drawable/ folder
		    	      Log.d("SAMPLEVIEW: "," file not found on sdcard: " + myMon.getPic() + " Using default background.");
		    		  is =super.getContext().getResources().openRawResource(R.drawable.defaultback);
		    		  bmBack = BitmapFactory.decodeStream(is,null, options);
		    	    }
	            Paint p = new Paint();
	            canvas.drawBitmap(bmBack, 0, 0, p);
	            int xOffset = myMon.getX();
	            canvas.drawBitmap(bmImg, (xOffset * 1/2), myMon.getY(), p);
	        }
  }
}
