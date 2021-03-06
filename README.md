
# Preview

![Preview](https://downloads.jamesswinton.com/apks/Utils/WallpaperConfigUtil/Wallpaper%20%20Config%20Util.gif)

# Description
Sets device wallpaper based on provided parameters. Parameters can be delivered via UI or Intent. Supports custom background colour, text & text colour & will display text in a playing-card pattern.  
  
# Intent Formats  
Action: **com.zebra.setdevicenumberutil.SET_WALLPAPER**

|  Extra | Type | Value |
|--|--|--|
|device-id | String  | Text to display on wallpaper |
|text-colour | String  | Colour of text displayed on wallpaper |
|background-colour | String  | Wallpaper background colour |
  
# adb example
```  
adb shell am start -a com.zebra.setdevicenumberutil.SET_WALLPAPER -n com.zebra.jamesswinton.setdevicenumberutil/.MainActivity --es device-id 'test12345' --es background-colour '#000000' --es text-colour '#ffffff'
```  
  
The above command will set the wallpaper to be black, with white text reading "test12345" displayed in a play-card like design.

# Pre-compiled
[Download APK](https://downloads.jamesswinton.com/apks/Utils/WallpaperConfigUtil/Wallpaper%20Config%20Util%20%28v1.0%29.apk)

# Stage Now Barcode
![enter image description here](https://downloads.jamesswinton.com/apks/Utils/WallpaperConfigUtil/Install%20Wallpaper%20Config%20Util%20Stage%20Now%20Barcode.PNG)
