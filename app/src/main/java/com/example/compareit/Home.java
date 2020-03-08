package com.example.compareit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.geofire.GeoFire;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, PermissionsListener {

    private MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE2 = 2;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    public String start;
    public String end;

    MarkerOptions markerOptions;


    public NavigationMapRoute navigationMapRoute;

    public LatLng startLatLng;
    public LatLng endLatLng;

    double startLat;
    double endLat;
    double startLng;
    double endLng;

    DatabaseReference ref;
    GeoFire geoFire;

    Button btnCompare;
    Button btnCompare1;
    Button btnReset;

    Button fab_end_search;
    Button fab_start_search;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Mapbox.getInstance(this, "pk.eyJ1Ijoic2hpdjg5NjhzaCIsImEiOiJjazJ4bHp4Y3AwZDRuM21vMnB3YjBsd2EyIn0.0WhEGT_VqjsHi8QMnzc6Yg");
        setContentView(R.layout.activity_home);

        fab_start_search = findViewById(R.id.fab_start_search);
        fab_end_search = findViewById(R.id.fab_end_search);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        btnCompare = findViewById(R.id.btnCompare);
        btnCompare1 = findViewById(R.id.btnCompare1);
        btnReset=findViewById(R.id.btnReset);

        btnReset.setVisibility(View.GONE);
        btnCompare.setVisibility(View.VISIBLE);
        btnCompare1.setVisibility(View.GONE);

        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onProceedClicked();
            }
        });

        btnCompare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Yet to be developed", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab_start_search.setEnabled(true);
                fab_end_search.setEnabled(true);
                fab_start_search.setText("");
                fab_end_search.setText("");

                btnReset.setVisibility(View.GONE);
                btnCompare1.setVisibility(View.GONE);
                btnCompare.setVisibility(View.VISIBLE);

                mapboxMap.clear();
                mapboxMap.removeAnnotations();
                navigationMapRoute.removeRoute();

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        ref = FirebaseDatabase.getInstance().getReference("users");
        geoFire = new GeoFire(ref);

    }

    private void onProceedClicked() {

        if (startLatLng != null && endLatLng != null) {
            getRoute(Point.fromLngLat(startLng, startLat), Point.fromLngLat(endLng, endLat));
            LatLngBounds latLngBounds = new LatLngBounds.Builder()
                    .include(startLatLng) // Northeast
                    .include(endLatLng) // Southwest
                    .build();
            mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200), 5000);
            btnCompare.setVisibility(View.GONE);
            btnCompare1.setVisibility(View.VISIBLE);
            btnReset.setVisibility(View.VISIBLE);

            fab_start_search.setEnabled(false);
            fab_end_search.setEnabled(false);



        } else {
            Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getRoute(Point startLatLng, Point endLatLng) {


        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(startLatLng)
                .destination(endLatLng)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null || response.body().routes().size() == 00) {
                            Toast.makeText(Home.this, "No Routes found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DirectionsRoute currentRoute = response.body().routes().get(0);

                        if (navigationMapRoute != null) {
                            navigationMapRoute.updateRouteArrowVisibilityTo(false);
                            navigationMapRoute.updateRouteArrowVisibilityTo(false);
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);
                        }

                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Toast.makeText(Home.this, "Failed :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }


    // maps//
    //
    //

    //

    //

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                enableLocationComponent(style);

                initSearchFab();
                fab_start_search.setText(start);

                initSearchFab2();
                fab_end_search.setText(end);


// Add the symbol layer icon to map for future use
                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        Home.this.getResources(), R.drawable.map_default_map_marker));


            }
        });


    }

    private void initSearchFab2() {
        fab_end_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : "pk.eyJ1Ijoic2hpdjg5NjhzaCIsImEiOiJjazJ4bHp4Y3AwZDRuM21vMnB3YjBsd2EyIn0.0WhEGT_VqjsHi8QMnzc6Yg")
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(Home.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE2);

            }
        });
    }

    private void initSearchFab() {
        fab_start_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : "pk.eyJ1Ijoic2hpdjg5NjhzaCIsImEiOiJjazJ4bHp4Y3AwZDRuM21vMnB3YjBsd2EyIn0.0WhEGT_VqjsHi8QMnzc6Yg")
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(Home.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {


// Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            start = selectedCarmenFeature.placeName();
            fab_start_search.setText(start);


// Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

// Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);

                    startLat = ((Point) selectedCarmenFeature.geometry()).latitude();
                    startLng = ((Point) selectedCarmenFeature.geometry()).longitude();

                    startLatLng = new LatLng(startLat, startLng);



                    markerOptions=new MarkerOptions()
                            .position(new LatLng(startLat, startLng))
                            .title(start);
                        mapboxMap.addMarker(markerOptions);

                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE2) {

// Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            end = selectedCarmenFeature.placeName();
            fab_end_search.setText(end);
// Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

// Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);


                    endLat = ((Point) selectedCarmenFeature.geometry()).latitude();
                    endLng = ((Point) selectedCarmenFeature.geometry()).longitude();

                    endLatLng = new LatLng(endLat, endLng);


                    markerOptions = new MarkerOptions()
                            .position(new LatLng(endLat,endLng))
                            .title(end);
                    mapboxMap.addMarker(markerOptions);

                }
            }
        }
    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    //for navigation view
    //
    //
    //

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            Home.this.finish();
            return true;
        } else if (id == R.id.show_cabs) {
            Intent intent = new Intent(Home.this, CabsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.reset)
        {

            if(btnReset.isActivated() && btnCompare1.isActivated()) {
               if(mapboxMap!=null) {
                   mapboxMap.clear();
                   mapboxMap.removeAnnotations();
                   if(navigationMapRoute!=null) {
                       navigationMapRoute.removeRoute();
                   }
                   fab_start_search.setEnabled(true);
                   fab_end_search.setEnabled(true);
                   fab_start_search.setText("");
                   fab_end_search.setText("");

                   btnReset.setVisibility(View.GONE);
                   btnCompare1.setVisibility(View.GONE);
                   btnCompare.setVisibility(View.VISIBLE);
               }
               else
               {
                   Toast.makeText(this, "Map already in reset mode", Toast.LENGTH_SHORT).show();
               }
            }
            else
            {
                fab_start_search.setText("");
                fab_end_search.setText("");

                mapboxMap.clear();
                mapboxMap.removeAnnotations();


                fab_start_search.setEnabled(true);
                fab_end_search.setEnabled(true);
                fab_start_search.setText("");
                fab_end_search.setText("");

                btnReset.setVisibility(View.GONE);
                btnCompare1.setVisibility(View.GONE);
                btnCompare.setVisibility(View.VISIBLE);
               if(navigationMapRoute!=null) {
                   navigationMapRoute.removeRoute();
               }
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.show_cabs1) {
            Intent intent = new Intent(Home.this, CabsActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.log_out1) {
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            Home.this.finish();
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
