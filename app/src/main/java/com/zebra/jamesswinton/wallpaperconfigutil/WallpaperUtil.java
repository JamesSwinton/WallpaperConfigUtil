package com.zebra.jamesswinton.wallpaperconfigutil;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.util.Log;
import com.zebra.jamesswinton.wallpaperconfigutil.databinding.LayoutWallpaperBinding;
import java.io.IOException;

public class WallpaperUtil {

  // Padding
  private static final float Y_Padding = 5f;
  private static final float X_Padding = 5f;

  // Scale
  private static final double CornerTextMaxWidthAsPercentOfScreenSize = 0.33;
  private static final double CenterTextMaxWidthAsPercentOfScreenSize = 0.75;


  public static void setWallpaper(Activity activity, String id, int backgroundColour, int textColour) throws IOException {
    // Init Wallpaper Manager
    WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);

    // Verify Wallpaper Manager
    if (wallpaperManager == null) {
      Log.e(activity.getClass().getName(), "Could not get wallpaper manager");
      return;
    }

    // Create Bitmap from View
    Bitmap wallpaperBitmap = drawBitmap(activity, id, backgroundColour, textColour);

    // Set Wallpaper
    wallpaperManager.setBitmap(wallpaperBitmap);
  }

  private static Bitmap drawBitmap(Activity activity, String id, int backgroundColour, int textColour) {
    // Get Screen size
    DisplayMetrics displayMetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
    int width = displayMetrics.widthPixels;
    int height = displayMetrics.heightPixels;

    // Create Bitmap to Hold Image
    Bitmap wallpaperBitmap = Bitmap.createBitmap(displayMetrics, width, height, Config.ARGB_8888);

    // Create Canvas with Bitmap
    Canvas canvas = new Canvas(wallpaperBitmap);

    // Draw Background
    canvas.drawColor(backgroundColour);

    // Init TextPaint for TextView
    Paint textPaint = new Paint();
    textPaint.setTextSize(100);
    textPaint.setColor(textColour);
    textPaint.setStyle(Style.FILL);

    // Validate Text Width
    scaleText(width, id, textPaint);

    // Get Text Dimens
    float textHeight = textPaint.measureText("yY");
    float textWidth = textPaint.measureText(id);

    // Draw Top Left Text
    float textCordX = 0 + X_Padding;
    float textCordY = textHeight + Y_Padding;
    canvas.drawText(id, textCordX, textCordY, textPaint);

    // Draw Top Right Text
    textCordX = (width - textWidth) - X_Padding;
    textCordY = textHeight + Y_Padding;
    canvas.drawText(id, textCordX, textCordY, textPaint);

    // Draw Bottom Left Text
    textCordX = 0 + X_Padding;
    textCordY = height - Y_Padding;
    canvas.drawText(id, textCordX, textCordY, textPaint);

    // Draw Bottom Right Text
    textCordX = (width - textWidth) - X_Padding;
    textCordY = height - Y_Padding;
    canvas.drawText(id, textCordX, textCordY, textPaint);

    // Init TextPaint for Center TextView
    Paint centerTextPaint = new Paint();
    centerTextPaint.setTextSize(250);
    centerTextPaint.setColor(textColour);
    centerTextPaint.setStyle(Style.FILL);

    // Scale
    scaleCenterText(width, id, centerTextPaint);

    // Get Center Text Dimens
    float centerTextHeight = centerTextPaint.measureText("yY");
    float centerTextWidth = centerTextPaint.measureText(id);

    // Draw Center Text
    textCordX = (width - centerTextWidth) / 2;
    textCordY = (height + centerTextHeight) / 2;
    canvas.drawText(id, textCordX, textCordY, centerTextPaint);

    // Return Bitmap
    return wallpaperBitmap;
  }

  private static void scaleText(int viewWidth, String text, Paint paint) {
    // Get Text Dimens
    float textWidth = paint.measureText(text);

    // Halve Size if Required
    if (textWidth > (viewWidth * CornerTextMaxWidthAsPercentOfScreenSize)) {
      float textSize = (float) (paint.getTextSize() * 0.75);
      paint.setTextSize(textSize);
      scaleText(viewWidth, text, paint);
    }
  }

  private static void scaleCenterText(int viewWidth, String text, Paint paint) {
    // Get Text Dimens
    float textWidth = paint.measureText(text);

    // Halve Size if Required
    if (textWidth > (viewWidth * CenterTextMaxWidthAsPercentOfScreenSize)) {
      float textSize = (float) (paint.getTextSize() * 0.75);
      paint.setTextSize(textSize);
      scaleCenterText(viewWidth, text, paint);
    }
  }

}
