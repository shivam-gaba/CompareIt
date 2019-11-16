package com.example.compareit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.SessionConfiguration;

public class CabsActivity extends AppCompatActivity {

Button btnShowCabs,btnShowComparePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs);

        btnShowCabs=findViewById(R.id.btnShowCabs);
        btnShowComparePage=findViewById(R.id.btnShowComparePage);

        btnShowCabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComparisonDialog();
            }
        });

        btnShowComparePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CabsActivity.this,ComparisonPage.class);
                startActivity(intent);
            }
        });
    }






    private void showComparisonDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(CabsActivity.this);
        dialog.setTitle("Compared Rates");
        LayoutInflater inflater = LayoutInflater.from(CabsActivity.this);
        View layout_comparison = inflater.inflate(R.layout.layout_comparison, null);
        dialog.setView(layout_comparison);
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.dismiss(); }
        });
        showUberInfo();
        showOlaInfo();
        dialog.show();

    }
    private void showOlaInfo() { }

    private void showUberInfo() {

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("rejZnkP7Oe2T8_fPrOrWk_Kgy-8OMT7a")

                // required for enhanced button features
                .setServerToken("<TOKEN>")
                // required for implicit grant authentication
                .setRedirectUri("<REDIRECT_URI>")
                // optional: set sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();


        UberSdk.initialize(config);

        RideRequestButton requestButton = new RideRequestButton(CabsActivity.this);

        LinearLayout layout=new LinearLayout(this);
        layout.addView(requestButton);

        RideParameters rideParams = new RideParameters.Builder()
                // Optional product_id from /v1/products endpoint (e.g. UberX). If not provided, most cost-efficient product will be used
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                // Required for price estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of dropoff location
                .setDropoffLocation(
                        37.775304, -122.417522, "Uber HQ", "1455 Market Street, San Francisco")
                // Required for pickup estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of pickup location
                .setPickupLocation(37.775304, -122.417522, "Uber HQ", "1455 Market Street, San Francisco")
                .build();
                // set parameters for the RideRequestButton instance
        requestButton.setRideParameters(rideParams);

    }







}