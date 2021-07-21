package com.zebra.jamesswinton.setdevicenumberutil;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.zebra.jamesswinton.wallpaperconfigutil.databinding.ActivityMainBinding;
import java.io.IOException;
import java.security.InvalidParameterException;

public class MainActivity extends AppCompatActivity {

  // Intents
  private static final String SetWallpaperAction = "com.zebra.setdevicenumberutil.SET_WALLPAPER";

  // Extras
  private static final String DeviceIdExtra = "device-id";
  private static final String TextColourExtra = "text-colour";
  private static final String BackgroundColourExtra = "background-colour";

  // UI
  private ActivityMainBinding mDataBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    mDataBinding.setWallpaper.setOnClickListener(v -> {
      if (verifyForm()) {
        String deviceId = mDataBinding.deviceId.getText().toString();
        String textColour = mDataBinding.textColour.getText().toString();
        String backgroundColour = mDataBinding.backgroundColour.getText().toString();
        try {
          Toast.makeText(this, "Setting wallpaper...", Toast.LENGTH_SHORT).show();
          WallpaperUtil.setWallpaper(this, deviceId, Color.parseColor(backgroundColour), Color.parseColor(textColour));
          Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
          finish();
        } catch (IllegalArgumentException e) {
          Toast.makeText(this, "Problem with intent extras: " + e, Toast.LENGTH_LONG)
              .show();
        } catch (IOException e) {
          Toast.makeText(this, "Could not set wallpaper - full error: " + e,
              Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    Intent launchIntent = getIntent();
    if (launchIntent != null) {
      try {
        parseIntentAndSetWallpaper(launchIntent);
      } catch (IOException e) {
        Toast.makeText(this, "Error applying wallpaper: " + e.getMessage(),
            Toast.LENGTH_LONG).show();
        finish();
      } catch (InvalidParameterException e) {
        Toast.makeText(this, "Invalid Intent Action, use: " + SetWallpaperAction,
            Toast.LENGTH_LONG).show();
        finish();
      } catch (IllegalArgumentException e) {
        Toast.makeText(this, "Invalid HEX code: " + e.getMessage(),
            Toast.LENGTH_LONG).show();
        finish();
      }
    }
  }

  private boolean verifyForm() {
    boolean valid = true;
    if (TextUtils.isEmpty(mDataBinding.deviceId.getText())) {
      valid = false;
      mDataBinding.deviceId.setError("Please enter an ID");
    }

    if (TextUtils.isEmpty(mDataBinding.backgroundColour.getText())) {
      valid = false;
      mDataBinding.backgroundColour.setError("Please enter a Background colour");
    }

    if (TextUtils.isEmpty(mDataBinding.textColour.getText())) {
      valid = false;
      mDataBinding.textColour.setError("Please enter a text colour");
    }

    if (valid) {
      mDataBinding.deviceId.setError(null);
      mDataBinding.textColour.setError(null);
      mDataBinding.backgroundColour.setError(null);
    }

    return valid;
  }

  private void parseIntentAndSetWallpaper(Intent intent) throws IllegalArgumentException, IOException {
    String launchIntentAction = intent.getAction();
    if (launchIntentAction != null && !TextUtils.isEmpty(launchIntentAction)) {
      if (launchIntentAction.equals(SetWallpaperAction)) {
        String deviceIdExtra = intent.getStringExtra(DeviceIdExtra);
        String textColourExtra = intent.getStringExtra(TextColourExtra);
        String backgroundColourExtra = intent.getStringExtra(BackgroundColourExtra);
        if (deviceIdExtra != null && backgroundColourExtra != null && textColourExtra != null) {
          Toast.makeText(this, "Setting wallpaper...", Toast.LENGTH_SHORT).show();
          WallpaperUtil.setWallpaper(this, deviceIdExtra, Color.parseColor(backgroundColourExtra), Color.parseColor(textColourExtra));
          Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
          finish();
        } else {
          throw new IllegalArgumentException("Missing DeviceID or Background colour Extra in Intent");
        }
      } else if (!launchIntentAction.equals("android.intent.action.MAIN")){
        throw new InvalidParameterException("Invalid Launch Intent - use: " + SetWallpaperAction);
      }
    }
  }
}