package com.example.compareit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CabRatesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public LatLng startLatLng;
    public LatLng endLatLng;

    LinearLayout orientationLayout;

    double startLat;
    double endLat;
    double startLng;
    double endLng;

    public double distance;
    public double duration;
    public double minutes;

    TextView tvDistance;
    TextView tvDuration;

    RecyclerView olaList;
    RecyclerView uberList;

    RecyclerView.Adapter adapterOla;
    RecyclerView.LayoutManager layoutManagerOla;

    RecyclerView.Adapter adapterUber;
    RecyclerView.LayoutManager layoutManagerUber;


    ArrayList<OlaListClass> ola;
    ArrayList<UberListClass> uber;

    public boolean alignment = false;
    Button btnChangeAlignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_cab_rates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("NOTE ...");
        alertDialog.setMessage("Due to some issue, partner cab applications have stopped providing API linking to the developers." +
                "The fares provided in this app are just approximations made using the common algorithm followed by the partner Cab applications");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();


        btnChangeAlignment = findViewById(R.id.btnChangeAlignment);
        tvDistance = findViewById(R.id.tvDistance);
        tvDuration = findViewById(R.id.tvDuration);
        olaList = findViewById(R.id.olaList);
        uberList = findViewById(R.id.uberList);

        orientationLayout = findViewById(R.id.orientationLayout);


        getLatLng();
        getDistanceAndTime();
        getOlaRates();
        getUberRates();

        btnChangeAlignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alignmentChanger();
            }
        });

    }


    @SuppressLint("WrongConstant")
    private void alignmentChanger() {

        alignment = !alignment;
        getOlaRates();
        getUberRates();
        orientationLayout.setOrientation(!alignment ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
    }

    @SuppressLint("WrongConstant")
    private void getUberRates() {

        if (alignment) {
            LinearLayout textBoxVS = findViewById(R.id.textBoxVS);
            textBoxVS.setVisibility(View.GONE);
            TextView tvOla;
            TextView tvUber;

            tvOla = findViewById(R.id.tvOLA);
            tvUber = findViewById(R.id.tvUber);

            tvOla.setVisibility(View.VISIBLE);
            tvUber.setVisibility(View.VISIBLE);

            layoutManagerUber = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            LinearLayout textBoxVS = findViewById(R.id.textBoxVS);
            textBoxVS.setVisibility(View.VISIBLE);
            TextView tvOla;
            TextView tvUber;

            tvOla = findViewById(R.id.tvOLA);
            tvUber = findViewById(R.id.tvUber);

            tvOla.setVisibility(View.GONE);
            tvUber.setVisibility(View.GONE);
            layoutManagerUber = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }

        uberList.setLayoutManager(layoutManagerUber);

        uber = new ArrayList<>();
        uber.add(new UberListClass("uber go", distance, minutes));
        uber.add(new UberListClass("uber hire premier", distance, minutes));
        uber.add(new UberListClass("uber hire go", distance, minutes));
        //uber.add(new UberListClass("uber moto",distance,minutes));
        uber.add(new UberListClass("uber premier", distance, minutes));

        adapterUber = new AdapterUber(this, uber);
        uberList.setAdapter(adapterUber);

    }

    @SuppressLint("WrongConstant")
    private void getOlaRates() {


        if (alignment) {

            layoutManagerOla = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {

            layoutManagerOla = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }


        olaList.setLayoutManager(layoutManagerOla);

        ola = new ArrayList<>();
        //ola.add(new OlaListClass("Bike",distance,minutes));
        //ola.add(new OlaListClass("Auto",distance,minutes));
        ola.add(new OlaListClass("Prime Sedan", distance, minutes));
        ola.add(new OlaListClass("Mini", distance, minutes));
        ola.add(new OlaListClass("Micro", distance, minutes));
        ola.add(new OlaListClass("Prime SUV", distance, minutes));
        ola.add(new OlaListClass("Prime Exec", distance, minutes));
        //ola.add(new OlaListClass("Share",distance,minutes));

        adapterOla = new AdapterOla(this, ola);
        olaList.setAdapter(adapterOla);
    }

    @SuppressLint("SetTextI18n")
    private void getDistanceAndTime() {
        distance = getIntent().getDoubleExtra("distance", 0);
        duration = getIntent().getDoubleExtra("duration", 0);

        minutes = duration / 60;
        distance = distance / 1000;

        TimeZone tz = TimeZone.getTimeZone("UTC");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date((long) (duration * 1000)));

        tvDistance.setText(String.format("%.2f", distance) + " Km");
        tvDuration.setText(time + " Hrs");
    }


    private void getLatLng() {

        Intent intent = getIntent();
        startLat = intent.getDoubleExtra("startLat", 0);
        endLat = intent.getDoubleExtra("endLat", 0);
        startLng = intent.getDoubleExtra("startLng", 0);
        endLng = intent.getDoubleExtra("endLng", 0);

        startLatLng = (LatLng) intent.getSerializableExtra("startLatLng");
        endLatLng = (LatLng) intent.getSerializableExtra("endLatLng");
    }


    //calligraphy


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    // navigation controls

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
        getMenuInflater().inflate(R.menu.cab_rates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logOutCO) {
            startActivity(new Intent(this, MainActivity.class));
            CabRatesActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.homeC) {
            startActivity(new Intent(this, Home.class));
            CabRatesActivity.this.finish();
            return true;
        } else if (id == R.id.bookCabC) {

            return true;

        } else if (id == R.id.logOutC) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Are you sure, you want to logout ?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(CabRatesActivity.this, MainActivity.class);
                    startActivity(intent);
                    CabRatesActivity.this.finish();

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alertDialog.show();
            return true;


        } else if (id == R.id.aboutC) {
            startActivity(new Intent(CabRatesActivity.this, AboutActivity.class));
            return true;
        } else if (id == R.id.privacyPolicyC) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            dialog.setView(inflater.inflate(R.layout.privacy_policy, null));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();

            return true;
        } else if (id == R.id.termsCndC) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            dialog.setView(inflater.inflate(R.layout.terms, null));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
            return true;
        } else if (id == R.id.bookUberC) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Redirect to Uber app ?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.ubercab");

                    if (intent != null) {
                        startActivity(intent);
                    } else {
                        Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab");
                        Intent goToPlayStore = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goToPlayStore);
                    }
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            return true;
        } else if (id == R.id.bookOlaC) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Redirect to Ola app ?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.olacabs.customer");

                    if (intent != null) {
                        startActivity(intent);
                    } else {
                        Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.olacabs.customer");
                        Intent goToPlayStore = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goToPlayStore);
                    }

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
