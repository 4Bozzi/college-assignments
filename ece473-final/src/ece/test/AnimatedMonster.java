package ece.test;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

/**
 * This is the canvas that is drawn as the monster moves. It can animate over
 * time, but in this (beta) iteration it does not. This portion of code is partially inspired by
 * an example in the Google Android SDK api demo.
 * 
 * @author Paul + FOTB
 * 
 */
public class AnimatedMonster extends Drawable {

  private Animation relocation;
  private Transformation transformation = new Transformation();
  private Drawable pictureOfMonster;

  /**
   * Sets the monsters image on as the drawable, and the translational animation
   * 
   * @param target
   * @param animation
   */
  public AnimatedMonster(Drawable monster, Animation motionAn) {
    pictureOfMonster = monster;
    relocation = motionAn;
  }

  /**
   * This would update where to draw the monster's picture as set over time. The
   * time delay is never used in the function, but keep for future purposes.
   */
  public void draw(Canvas canvas) {
    Drawable pic = pictureOfMonster;
    // if the image does not exist, this will doe nothing
    if (pic != null) {
      int sc = canvas.save();
      Animation anim = relocation;
      // motion over time
      if (anim != null) {
        anim.getTransformation(AnimationUtils.currentAnimationTimeMillis(), transformation);
        canvas.concat(transformation.getMatrix());
      }
      pic.draw(canvas);
      canvas.restoreToCount(sc);
    }
  }

  /**
   * Not used, but required
   */
  public int getOpacity() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Not used, but required
   */
  public void setAlpha(int alpha) {
    // TODO Auto-generated method stub

  }

  /**
   * Not used, but required
   */
  public void setColorFilter(ColorFilter cf) {
    // TODO Auto-generated method stub

  }
}