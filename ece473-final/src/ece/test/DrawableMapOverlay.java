package ece.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * This is the transparent overlay on the map. It contains the images geopoint
 * and the image itself.
 * 
 * @author Andrew
 * 
 */
public class DrawableMapOverlay extends Overlay {

  private GeoPoint geoPoint;
  private final Context context;
  private int drawable;
  // Direction would be used to turn the position icon into a compass
  private int direction = 0;

  /**
   * @param context
   *          the context in which to display the overlay
   * @param geoPoint
   *          the geographical point where the overlay is located
   * @param drawable
   *          the ID of the desired drawable
   */
  public DrawableMapOverlay(Context context, GeoPoint geoPoint, int drawable) {
    this.context = context;
    this.geoPoint = geoPoint;
    this.drawable = drawable;

  }

  /**
   * This is the overloaded draw, it will eventually rotate the image as well.
   * It will update all images on the map view as well. The 'when' parameter and
   * shadow is not used.
   */
  public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
    super.draw(canvas, mapView, shadow);
    // Convert geo coordinates to screen pixels
    Point screenPoint = new Point();
    mapView.getProjection().toPixels(geoPoint, screenPoint);

    // Read the image
    Bitmap markerImage = BitmapFactory.decodeResource(context.getResources(), drawable);

    // Draw it, centered around the given coordinates
    canvas.drawBitmap(markerImage, screenPoint.x - markerImage.getWidth() / 2, screenPoint.y - markerImage.getHeight()
        / 2, null);

    /*
     * canvas.rotate(direction, markerImage.getWidth() / 2, markerImage
     * .getHeight() / 2);
     */

    return true;
  }

  /**
   * Not utilized
   */
  public boolean onTap(GeoPoint p, MapView mapView) {
    return true;
  }

  public void setGeoPoint(GeoPoint newPoint) {
    this.geoPoint = newPoint;
  }

  public void setDirection(int direction) {
    this.direction = direction;

  }
}
