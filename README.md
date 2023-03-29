# partyverse app for Android OS

meow

## Build it yourself

1. Obtain Mapbox tokens using this [guide](https://docs.mapbox.com/android/maps/guides/install/#configure-credentials).
2. Create a Supabase project and copy URL and public token from the project settings.
3. Create a file named `gradle.properties` in the ~/.gradle directory and add the following lines:
```
MAPBOX_DOWNLOADS_TOKEN=<your downloads token>
``` 
4. Create a file named `secrets.xml` in the `app/src/main/res/values` directory and add the following lines:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mapbox_dark_style_url">[your mapbox style url or the default one (for the dark theme)]</string>
    <string name="mapbox_light_style_url">[your mapbox style url or the default one (for the light theme)]</string>
    <string name="mapbox_access_token">[your mapbox public token]</string>
    <string name="supabase_url">[your supabase url]</string>
    <string name="supabase_public_token">[your supabase public token]</string>
</resources>
```
5. Build the app using Android Studio.

> URL of our dark Mapbox style: `mapbox://styles/otomir23/cld7ah07z000p01ntkdquh9r2`.
> URL of our light Mapbox style: `WIP, use mapbox default`.
> We can provide supabase credentials on request, but you can decompile them anyway so it's not a big deal.

## License

This project is licensed under the terms of the MIT license.
You can find a copy of this license in the [LICENSE](LICENSE) file.
