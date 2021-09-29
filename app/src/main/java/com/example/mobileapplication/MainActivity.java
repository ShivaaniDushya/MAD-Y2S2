package com.example.mobileapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;

    ShapeableImageView storePPImg;
    ImageView storeSPImg;
    TextView customerName, storeName, address, startLocation, endLocation, distance, routeID, numberOfShops, itemName, itemBrand, itemDescription, monthlySale, monthlyProfit;
    String topCustomerID, profilePicUri, storePicUri, topItemID;
    MaterialCardView itemCard1, itemCard2, customerCard1, customerCard2, routeCard1, routeCard2;
    MaterialToolbar materialToolbar;

    ImageView searchNearby;
    int PERMISSION_ID = 44;

    String longitude, latitude;
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude = String.valueOf(mLastLocation.getLatitude());
        }
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeHelper.loadLocale(this);
        setContentView(R.layout.activity_main);

        storePPImg = findViewById(R.id.store_owner_pic);
        storeSPImg = findViewById(R.id.store_pic);
        customerName = findViewById(R.id.customer_name_txt);
        storeName = findViewById(R.id.store_name_txt);
        address = findViewById(R.id.address_txt);
        startLocation = findViewById(R.id.start_loc_txt);
        endLocation = findViewById(R.id.end_loc_txt);
        distance = findViewById(R.id.distance_txt);
        routeID = findViewById(R.id.rid_txt);
        numberOfShops = findViewById(R.id.cxcount);
        itemName = findViewById(R.id.item_name_txt);
        itemBrand = findViewById(R.id.item_brand_txt);
        itemDescription = findViewById(R.id.item_description_txt);
        itemCard1 = findViewById(R.id.item_card);
        itemCard2 = findViewById(R.id.item_card_2);
        customerCard1 = findViewById(R.id.customer_card);
        customerCard2 = findViewById(R.id.customer_card_2);
        routeCard1 = findViewById(R.id.route_card);
        routeCard2 = findViewById(R.id.route_card_2);
        monthlySale = findViewById(R.id.monthly_sales);
        monthlyProfit = findViewById(R.id.monthly_profit);

        loadTopCustomer();
        loadTopRoute();
        loadTopItem();
        loadMonthlySale();

        materialToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(materialToolbar);

        //Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.items:
                    startActivity(new Intent(getApplicationContext()
                            , Items.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.customers:
                    startActivity(new Intent(getApplicationContext()
                            , Customers.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.home:
                    return true;

                case R.id.routes:
                    startActivity(new Intent(getApplicationContext()
                            , Routes.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.sales:
                    startActivity(new Intent(getApplicationContext()
                            , Sales.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        });

        searchNearby = findViewById(R.id.add_search);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        searchNearby.setOnClickListener(v -> {
            Uri gmnIntentUri = Uri.parse("geo:" +
                    latitude +
                    "," +
                    longitude +
                    "?q=groceries");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmnIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getApplicationContext()
                    , com.example.mobileapplication.Settings.class));
            overridePendingTransition(0, 0);
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {

                        longitude = String.valueOf(location.getLongitude());
                        latitude = String.valueOf(location.getLatitude());
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    @SuppressLint("SetTextI18n")
    public void loadTopCustomer() {
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor cursor = db.topCustomer();
        if (cursor.getCount() == 0) {
            Log.d("workflow", "No customer");
            customerCard1.setVisibility(View.GONE);
            customerCard2.setVisibility(View.VISIBLE);
        } else {
            routeCard1.setVisibility(View.VISIBLE);
            routeCard2.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                topCustomerID = cursor.getString(0);
                customerName.setText(cursor.getString(1));
                storeName.setText(cursor.getString(2));
                address.setText(cursor.getString(2) + " " + getString(R.string.owned) + " " + cursor.getString(1) + " " + getString(R.string.located) + " " + cursor.getString(3) + " " + getString(R.string.in) + " " + cursor.getString(4) + " " + getString(R.string.hint_cus_city));
                profilePicUri = cursor.getString(5);
                if (profilePicUri != null) {
                    storePPImg.setImageURI(Uri.parse(profilePicUri));
                }
                storePicUri = cursor.getString(6);
                if (storePicUri != null) {
                    storeSPImg.setImageURI(Uri.parse(storePicUri));
                }
            }
        }
    }

    public void loadTopRoute() {
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor cursor = db.topRoute();
        if (cursor.getCount() == 0) {
            Log.d("workflow", "No Route");
            routeCard1.setVisibility(View.GONE);
            routeCard2.setVisibility(View.VISIBLE);
        } else {
            routeCard1.setVisibility(View.VISIBLE);
            routeCard2.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                routeID.setText(cursor.getString(0));
                startLocation.setText(cursor.getString(1));
                endLocation.setText(cursor.getString(2));
                distance.setText(cursor.getString(3));
                numberOfShops.setText(cursor.getString(4));
            }
        }
    }

    public void loadTopItem() {
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor cursor = db.topItem();
        if (cursor.getCount() == 0) {
            Log.d("workflow", "No Route");
            itemCard1.setVisibility(View.GONE);
            itemCard2.setVisibility(View.VISIBLE);
        } else {
            itemCard1.setVisibility(View.VISIBLE);
            itemCard2.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                topItemID = cursor.getString(0);
                itemName.setText(cursor.getString(1));
                itemBrand.setText(cursor.getString(2));
                itemDescription.setText(cursor.getString(3));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void loadMonthlySale() {
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor cursor = db.monthlySale();
        if (cursor.getCount() == 0) {
            monthlySale.setText("0K");
        } else {
            if (cursor.moveToFirst()) {
                monthlySale.setText(cursor.getString(0) + "K");
            }
        }
    }
}
