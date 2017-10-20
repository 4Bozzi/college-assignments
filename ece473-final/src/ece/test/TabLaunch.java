package ece.test;

import java.io.File;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TabHost;

/**
 * This is the main launching activity. It launches the intents for each tab. If
 * the inventory.txt file does not exist, it will launch the origin story
 * dialogs.
 * 
 * @author Paul Frost & FOTB
 * 
 */
public class TabLaunch extends TabActivity {

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Builder secondPage;
    final TabHost tabHost = getTabHost();
    // If inventory.txt does not exist, then it is the first time being played,
    // and this will introduce the game
    File f = new File(Environment.getExternalStorageDirectory() + "/inventory.txt");
    if (!f.exists()) {
      secondPage = new AlertDialog.Builder(this);
      secondPage.setTitle(R.string.app_name);
      secondPage.setIcon(R.drawable.icon);
      secondPage.setMessage(R.string.OriginStory2);
      secondPage.setPositiveButton("OK", null);

      Builder firstPage = new AlertDialog.Builder(this);
      firstPage.setTitle(R.string.app_name);
      firstPage.setIcon(R.drawable.icon);
      firstPage.setMessage(R.string.OriginStory1);
      firstPage.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1) {
          secondPage.show();

        }
      });
      firstPage.show();
    }

    // adds each tab and calls their onCreate functions
    // switching to a tab calls onResume
    tabHost.addTab(tabHost.newTabSpec("map").setIndicator("Map").setContent(new 
    		Intent(this, MapTab.class)));
    tabHost.addTab(tabHost.newTabSpec("inventory").setIndicator("Inventory").setContent(
        new Intent(this, InventoryTab.class)));
    tabHost.addTab(tabHost.newTabSpec("options").setIndicator("Options").
    		setContent(new Intent(this, OptionsTab.class)));

  }

  protected void onStop() {
    super.onStop();
  }

  /**
   * This method was overridden because at one point there was a specific order 
   * in which to destroy some objects
   */
  protected void onDestroy() {
    super.onDestroy();
    this.finish();
  }
}