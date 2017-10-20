package ece.test;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * This class (and tab) exist to alter various settings. Currently, this is only
 * the background sound and the debug mode.
 */
public class OptionsTab extends Activity {
	//mediaplayer plays the background sound track
	MediaPlayer mp;
	/**
	 * debugflag exists to allow other classes to detect whether debug mode is 
	 * enabled or not.  MapTab checks this to determine whether or not to display 
	 * the debug encounter launch button.
	 */
	static boolean debugflag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.optionsview );
		
		/*
		 * this button toggles whether or not the background music is enabled.
		 */
		ToggleButton musicToggle = (ToggleButton) findViewById( R.id.toggle1 );
		musicToggle.setText("Off");
		TextView musiclabel = (TextView) findViewById( R.id.TextView01);
		musiclabel.setText("Music Toggle");
		
		mp = MediaPlayer.create(getApplicationContext(), 
				 Uri.parse("android.resource://" + "ece.test" + "/" + R.raw.defaultsong));
		//setLooping(true) allows the audio to restart playing when the end of the file
		//is reached
		mp.setLooping(true);
		musicToggle.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (mp.isPlaying()) {
					//pause allows the start() function to resume play from the last
					//known audio position.
					//stop() would cause the track to restart.
					mp.pause();
				} else {
					mp.start();
				}
			}
		});
		
		/*
		 * debug toggles whether or not the debug encounter launch is available on
		 * the map tab.
		 */
		ToggleButton debug = (ToggleButton) findViewById( R.id.toggle2 );
		debug.setText("Off");
		debug.setChecked(false);
		TextView debuglabel = (TextView) findViewById(R.id.TextView02);
		debuglabel.setText("Debug Mode Toggle");
		
		debug.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//flip the debug bit
				debugflag = !debugflag;
				
			}
		});
		
    }
}