package ece.test;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * This is the view containing a monster over the camerafeed. When the
 * orientation sensor detects a change, the location is updated and the view is
 * invalidated. The view initially sets a random heading for the monster to be
 * in. It adjusts it slightly so that the monster does not show up too close to
 * 359-0 degrees (north) as it causes an issue with the image will more to too
 * far extremes because of the wrap around values. In debug mode, the heading is
 * set at 10 degrees, so it is always in the camera view in the emulator.
 * 
 * @author Paul Frost + FOTB
 * 
 */
public class MonsterView extends View {
  // This is the random starting heading of the monster
  private int monsterLocationX;
  private AnimatedMonster monster;
  private Drawable monsterImage;
  private int monsterX;
  private int monsterY;
  private int width;
  private int height;

  /**
   * This will random the start location of the monster. Eventually the z
   * displacement may be considered, but for now the monster is place in a
   * static y position. The camera (moto cliq) that this is being tested with
   * has approximately a 60 degree view angle and a 480px wide screen. This
   * means that the heading should be multiplied by 8 (because 480px/60deg =
   * 8px/deg) to scale the location of the image.
   * 
   * @param context
   *          The context of the view
   */
  public MonsterView(Context context) {
    super(context);
    setFocusable(true);
    setFocusableInTouchMode(true);

    monsterImage = context.getResources().getDrawable(MapTab.currentMonster.getMonsterDrawable());
    width = monsterImage.getIntrinsicWidth();
    height = monsterImage.getIntrinsicHeight();
    // image bounds
    monsterImage.setBounds(0, 0, width, height);

    // This will create the random starting location of the monster, it is in a
    // degree heading off of north.
    Random random = new Random();
    //limits it to avoid above mentioned wrap-around issue
    int maxDegree = (int) (359 - (width / 8));
    monsterLocationX = (int) (random.nextDouble() * maxDegree);
    //statically set size
    if (OptionsTab.debugflag) {
      monsterLocationX = 10;
    }
    //scale the size
    monsterX = 8 * monsterLocationX;
    //static set
    monsterY = 60;
    //sets up initial location
    Animation an = new TranslateAnimation(8 * monsterLocationX, 8 * monsterLocationX, 60, 60);
    an.setDuration(0);
    an.setRepeatCount(0);
    an.initialize(width, height, 0, 0);
    //make the animation of the monster
    monster = new AnimatedMonster(monsterImage, an);
    an.startNow();
  }

  protected void onDraw(Canvas canvas) {

    monster.draw(canvas);
    invalidate();
  }

  /**
   * This will make a new animation of the monster, and is called by the sensor
   * listener in the CaptureMonsterActivity. It takes in the values and
   * approximates the location of monster.
   * 
   * @param heading
   *          This is the heading of the monster compared to the position you
   *          are facing, it should be in degree offset of magnetic north.
   */
  public void moveMonster(double dheading) {
    int heading = (int) dheading;
    Animation an = new TranslateAnimation(8 * (-monsterLocationX + heading), 8 * (-monsterLocationX + heading), 60, 60);
    an.setDuration(0);
    an.setRepeatCount(0);
    an.initialize(width, height, 0, 0);
    // Updates the monster's x value
    monsterX = 8 * (-monsterLocationX + heading);
    monster = new AnimatedMonster(monsterImage, an);
    an.startNow();
  }

  /**
   * Returns the x location in reference to the screen
   * 
   * @return monster's current X, from the left edge of image
   */
  public int monsterXLeft() {
    return monsterX;
  }

  /**
   * Returns the x location in reference to the screen
   * 
   * @return monster's current X, from the right edge of image
   */
  public int monsterXRight() {
    return monsterX + width;
  }

  /**
   * Returns the y loaction in reference to the screen
   * 
   * @return monster's current Y
   */
  public int monsterY() {
    return monsterY;
  }
}
