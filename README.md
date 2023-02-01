# partyverse app for Android OS

meow

## Build it yourself

1. Obtain Mapbox tokens using this [guide](https://docs.mapbox.com/android/maps/guides/install/#configure-credentials).
2. Create a file named `gradle.properties` in the ~/.gradle directory and add the following lines:
```
MAPBOX_DOWNLOADS_TOKEN=<your downloads token>
``` 
3. Create a file named `secrets.xml` in the `app/src/main/res/values` directory and add the public token there.
4. Build the app using Android Studio.

## License

This project is licensed under the terms of the MIT license.
You can find a copy of this license in the [LICENSE](LICENSE) file.