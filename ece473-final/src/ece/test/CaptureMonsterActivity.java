package ece.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * This is the camera activity in which the player can find and capture the
 * monster. It contains a surface view that is has the camera preview. The view
 * is implemented in the camerafeed class. There exist 2 buttons that will
 * either take a picture or end the activity.
 * 
 * @author Paul Frost & Robert Kubo
 * 
 * 
 */
public class CaptureMonsterActivity extends Activity {
  CaptureMonsterActivity captureActivity;

  // This is for the orientation sensor
  public SensorManager sensorManager;
  public List<Sensor> sensorList;

  // This is where the compass should be facing
  public double compassHeading = 0;
  public double offsetFromNorth = 0;
  // public TextView text;
  public static CameraFeed cameraFeed;
  static boolean debugflag;
  // This is a transparent layer containing the party monster that is in the
  // field of vision
  public static MonsterView monsterView;

  // This is the monster that will be featured, it is taken from a static field
  // in the maptab
  Monster monster;

  Button capture;
  Button escape;

  /**
   * This will instantiates the different views, as well as set some of the
   * variables
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Inst the monster that will be found
    monster = MapTab.currentMonster;

    captureActivity = this;
    // Make full screen
    getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Make the surface view
    cameraFeed = new CameraFeed(this);
    setContentView(cameraFeed);

    // Make the cursor view, add it to the content
    monsterView = new MonsterView(this);
    addContentView(monsterView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    capture = new Button(this);
    capture.setText("Capture");
    addContentView(capture, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
        Gravity.LEFT));

    capture.setOnClickListener(new OnClickListener() {
      /**
       * capture represents taking the image from the camera feed and saving it.
       * If the monster is not in fully, it will say that you missed.
       * 
       */
      public void onClick(View v) {
        String messageTitle = "";
        String messageText = "";
        // Checks if the monster exists within the screen (480 pixels wide)
        if (monsterView.monsterXLeft() >= 0 && monsterView.monsterXRight() <= 480) {
          cameraFeed.cap();
          messageTitle = "SUCCESS, you caught a" + monster.getName() + "!";
          messageText = "You can rename it in your inventory tab!";
        } else {
          messageTitle = "You missed!";
          messageText = "That's too bad, wait a bit and see if you can catch him again";
        }

        Builder builder = new AlertDialog.Builder(captureActivity);
        builder.setTitle(messageTitle);
        builder.setIcon(monster.getMonsterDrawable());
        builder.setMessage(messageText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

          public void onClick(DialogInterface arg0, int arg1) {
            captureActivity.finish();
          }
        });
        builder.show();

      }
    });

    // This button just ends the encounter
    escape = new Button(this);
    escape.setText("Scare Off");
    addContentView(escape, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
        Gravity.RIGHT));
    escape.setOnClickListener(new OnClickListener() {
      /**
       * Escape represents some alternative action to "capturing" the image. For
       * the purposes of this class, it just means we exit.
       */
      public void onClick(View v) {
        // figure out some way to make the monster signal go away
        finish();
      }
    });

    sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
    sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);

    // If the sensor is available, the text will tell you so, if there is no
    // accelerometer, the text will tell you
    if (!sensorList.isEmpty()) {
      int i = sensorList.get(0).getType();
      if (i == Sensor.TYPE_ORIENTATION) {
        sensorManager.registerListener(orientationChangeListener, sensorList.get(0), SensorManager.SENSOR_DELAY_NORMAL);

      }
    }

  }

  /**
   * This will notify the monsterView that the view has changed, and the monster
   * needs to move accordingly
   */
  SensorEventListener orientationChangeListener = new SensorEventListener() {

    public void onAccuracyChanged(Sensor arg0, int arg1) {
      // TODO Auto-generated method stub

    }

    /*
     * The heading is offset by 90 degrees because the phone is in landscape
     * mode.
     */
    public void onSensorChanged(SensorEvent event) {
      double heading = ((360 - (event.values[0]))) % 360;
      //Move the monster
      monsterView.moveMonster(heading);
    }
  };
  //Just a getter for the camera feed
  public CameraFeed getCameraFeed() {
    return cameraFeed;
  }
  /**
   * This should prevent the program from crashing when the keyboard is pulled out.
   */
  public void onConfigurationChanged(Configuration newConfig) {
    // do nothing?
  }

  public void onStop() {
    this.finish();
    super.onStop();
  }

  public void onPause() {
    super.onPause();
    this.finish();
  }
}

/**
 * This is a surface view that contains the camera feed. It contains a callback
 * to take the picture.  
 * 
 */
class CameraFeed extends SurfaceView implements SurfaceHolder.Callback {

  SurfaceHolder holder;
  Camera camera;

  CameraFeed(Context context) {
    super(context);

    holder = getHolder();
    holder.addCallback(this);
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }
  //Initially startes the camera
  public void surfaceCreated(SurfaceHolder holder) {
    try {
      camera = Camera.open();
      camera.setPreviewDisplay(holder);
    } catch (IOException exception) {
    }
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    camera.stopPreview();
    camera = null;
  }

  public void stopCameraPreview() {
    camera.stopPreview();

  }

  public void startCameraPreview() {
    camera.startPreview();
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    Camera.Parameters parameters = camera.getParameters();
    parameters.setPreviewSize(w, h);
    camera.setParameters(parameters);
    camera.startPreview();
  }
  /**
   * This is called with the picture is allowed to be taken
   * 
   */
  void cap() {
    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    camera.startPreview();
  }

  /**
   * shutterCallback contains some behavior (in this case no action) on what to
   * do after the shutter closes. This is necessary for camera.takePicture
   */
  ShutterCallback shutterCallback = new ShutterCallback() {
    public void onShutter() {
      // TODO Do something when the shutter closes.
      // in this case, we don't care
    }
  };

  /**
   * rawCallback details some behavior (in this case no action) to occur when
   * the image raw data is available. This is necessary for camera.takePicture
   */
  PictureCallback rawCallback = new PictureCallback() {
    public void onPictureTaken(byte[] _data, Camera _camera) {
      // TODO Do something with the image RAW data.
      // in this case, we don't care
    }
  };

  /**
   * jpegCallback details some behavior (in this case, to save the image) to
   * occur when a jpeg formatted data of the capture is available. This is
   * automatically used by takePicture
   */
  PictureCallback jpegCallback = new PictureCallback() {
    public void onPictureTaken(byte[] _data, Camera _camera) {
      // here I need to change the _data array into a bitmap
      // this means I need to use a Bitmap Factory call to decodeByteArray
      // the Bitmap Factory call needs the dimensions of the image, so we
      // need to call the dimensions of the camera source of the data
      // Camera.Parameters param = camera.getParameters();
      // Camera.Size size = param.getPreviewSize();
      FileOutputStream outStream = null;
      try {
        // write to sdcard
        String picName = "MP_" + System.currentTimeMillis() + ".jpg";
        String path = String.format("/sdcard/" + picName);
        outStream = new FileOutputStream(path);
        outStream.write(_data);
        outStream.close();
        Log.d("pic name: ", "" + picName);
        // Make a monster to add to the inventory. The monster is taken from a
        // static variable in the mapTab class.
        Monster aMonster = new Monster(MapTab.currentMonster.getName(), MapTab.currentMonster.getId(), picName,
            CaptureMonsterActivity.monsterView.monsterXLeft(), CaptureMonsterActivity.monsterView.monsterY());
        InventoryTab.addMonster(aMonster);
        // Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
      // Log.d(TAG, "onPictureTaken - jpeg");

    }
  };
}
