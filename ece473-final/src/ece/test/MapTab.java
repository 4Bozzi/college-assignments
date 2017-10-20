package ece.test;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapTab extends MapActivity {
	//create a map tab object
	private MapTab mapActivity;
	//create a location manager to provide useful methods from gps
	private LocationManager lm;
	//create a location listener for sending location changed events
	private LocationListener locationListener;

	// This is the monster that will be encountered when an encounter occurs,
	// by default(in case of debug) it is a Brolax.
	static public Monster currentMonster;
	//flag to prevent encounters from popping up every second
	private long lastEncounterTime;
	//mapview for displaying google map
	private MapView mapView;
	//map controller for providing helpful methods for the map
	private MapController mc;
	
	//your current position
	GeoPoint position;
	//debug button for when you are in debug mode and want an encounter
	Button debug;
	//becomes visible when you get an encounter notification
	Button encounter;
	//list over overlays that go on top of the map
	List<Overlay> overlays;
	//the icon that indicates your current location on the map
	DrawableMapOverlay myLocationIcon;
	//encounter zone south of the u of a
	EncounterZone uofaZone;
	//image for the encounter zone uofaZone
	DrawableMapOverlay uofaOverlay;
	//encounterzone at the ece building
	EncounterZone eceZone;
	//image for the eceZone
	DrawableMapOverlay eceOverlay;
	//sensormanager for the compass sensor
	public SensorManager sensorManager;
	//list of sensors for getting the compass
	public List<Sensor> sensorList;

	/** Called when the activity is first created. Initializes all of the variables and places buttons and encounters on the map. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maptabview);

		// A reference to itself
		mapActivity = this;

		// Inst. the default monster
		currentMonster = new Monster();

		//set last encounter a minute back
		lastEncounterTime = System.currentTimeMillis() - 60000;

		// Location manager collects GPS data
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//create a new location listener
		locationListener = new MyLocationListener();
		//start requesting gps updates
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locationListener);
		//sensor manager for the compass 
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		//sensorlist that gets the compass sensor
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

		// If the sensor is available, the text will tell you so, if there is no
		// accelerometer, the text will tell you
		if (!sensorList.isEmpty()) {
			int i = sensorList.get(0).getType();
			if (i == Sensor.TYPE_ORIENTATION) {
				sensorManager.registerListener(orientationChangeListener,
						sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);

			}
		}
		
		//set the mapView to the mapview layout in xml
		mapView = (MapView) findViewById(R.id.mapview);
		//set the controller to the mapView's controller
		mc = mapView.getController();
		//set the default zoom so that the image sizing is all correct
		mc.setZoom(16);

		// This sets up the debug button used for testing.
		debug = new Button(this);
		debug.setOnClickListener(new OnClickListener() {//force an encounter
			public void onClick(View v) {
				// launch camera activity
				Log.d("Camera", ((Boolean) OptionsTab.debugflag).toString());
				Intent intent = new Intent();
				intent.setClass(v.getContext(), CaptureMonsterActivity.class);
				v.getContext().startActivity(intent);
			}
		});
		//debug button text
		debug.setText("Debug");
		addContentView(debug, new LayoutParams(LayoutParams.WRAP_CONTENT, //place the button and make it not visible
				LayoutParams.WRAP_CONTENT));
		debug.setVisibility(View.INVISIBLE);
		
		//set the overlays list to the mapViews list of overlays
		overlays = mapView.getOverlays();
		//initialize the geopoint and set your location icon there, for not until the gps sets it
		GeoPoint point = new GeoPoint(10, 10);
		myLocationIcon = new DrawableMapOverlay(getApplicationContext(), point,
				R.drawable.indicator);
		overlays.add(myLocationIcon);
		//create uofazone at its location and creates an overlay at the same location
		uofaZone = new EncounterZone(32.2268, -110.9475, new Monster());
		point = new GeoPoint((int) (32.2268 * 1E6), (int) (-110.9475 * 1E6));
		uofaOverlay = new DrawableMapOverlay(getApplicationContext(), point,
				R.drawable.zone);
		overlays.add(uofaOverlay);
		//create ecezone at its location and creates an overlay at the same location
		eceZone = new EncounterZone(32.2353, -110.9532, new Monster(1));
		point = new GeoPoint((int) (32.2353 * 1E6), (int) (-110.9532 * 1E6));
		eceOverlay = new DrawableMapOverlay(getApplicationContext(), point,
				R.drawable.zone);
		overlays.add(eceOverlay);
	}

	/*
	 * This listener will notify the cursorCanvas when the compass has changed
	 */
	SensorEventListener orientationChangeListener = new SensorEventListener() {
		//autogenerated function
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}
		
		public void onSensorChanged(SensorEvent event) {
			double heading = ((360 - (event.values[0]))) % 360;
			// myLocationIcon.setDirection((int) heading);
			// mapView.invalidate();

			debug.setText("Debug Compass - " + heading + "\nCause Encounter");//this is not currently used
		}																		
	};

	/**
	 * This function exists to determine whether a route from some point on the
	 * map to some other point on the map is displayed.
	 */
	protected boolean isRouteDisplayed() {
		// This map does not display a route at any time.
		return false;
	}

	private class MyLocationListener implements LocationListener {
		//this method will be triggered and send an even to the phone every time the gps detects a change
		public void onLocationChanged(Location loc) {
			if (loc != null) { //make sure the loc isnt null
				position = new GeoPoint((int) (loc.getLatitude() * 1E6), //update your position to the new position
						(int) (loc.getLongitude() * 1E6));
				mc.animateTo(position); //move the map to the position
				myLocationIcon.setGeoPoint(position);//set the icon to the new position
				mapView.invalidate();//redraw the map

				// Checks if you are in a zone, and if an encounter has occurred within 2 minutes
				long currentTime = System.currentTimeMillis();

					if (currentTime - lastEncounterTime > 120000) {
						currentMonster = uofaZone.getMonster();//sets the monster to the correct one  for the zone
						lastEncounterTime = System.currentTimeMillis(); //update the encounter time so you wont encounter for 2 more minutes

						Builder builder = new AlertDialog.Builder(mapActivity);//Create an alert with some text and an image/button
						builder.setTitle("DONT PANIC!!!");
						builder.setIcon(currentMonster.getMonsterDrawable());
						builder
								.setMessage("You have found one of your party animal bros, "
										+ currentMonster.getName()
										+ ", you should drag him back.");
						// Set up button to start camera
						builder.setPositiveButton("Cap(ture) his ass", //button to go to camera
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										lastEncounterTime = System
												.currentTimeMillis();
										Intent intent = new Intent();
										intent.setClass(mapActivity,
												CaptureMonsterActivity.class);
										mapActivity.startActivity(intent);
									}
								});
						builder.setNegativeButton(//button to go back to what you were doing
								"I can't take the pressure!!!!!",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										lastEncounterTime = System
												.currentTimeMillis();
									}
								});
						builder.show();
					}
			}
		}
		//required method auto generated for the gps
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}
		//required method auto generated for the gps
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}
		//required method auto generated for the gps
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}
	//when we return to the tab set the the debug button visible or not
	public void onResume() {
		super.onResume();
		if (OptionsTab.debugflag) {
			// show the debug button
			debug.setVisibility(View.VISIBLE);
		} else {
			// hide the debug button
			// View.INVISIBLE apparently only works in versions later than 1.5
			debug.setVisibility(View.GONE);
		}
	}
}
