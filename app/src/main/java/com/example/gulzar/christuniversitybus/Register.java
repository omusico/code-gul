package com.example.gulzar.christuniversitybus;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Register extends FragmentActivity {

    GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    String addd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Getting Google Play availability status
        Button button=(Button)findViewById(R.id.sb);
        final EditText editText=(EditText)findViewById(R.id.addr);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();



            //Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addd = editText.getText().toString();
                    getcoordinatebyname gcb = new getcoordinatebyname();
                    Toast.makeText(Register.this, gcb.getLocationFromAddress(Register.this, addd).toString(), Toast.LENGTH_SHORT).show();
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(gcb.getLocationFromAddress(Register.this, addd));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);
                }
            });

            final ScrollView scrollView=(ScrollView)findViewById(R.id.scrollView1);

            googleMap.setOnMapClickListener(new OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });




            drawMarker();


        }



    }


    private void drawMarker(){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        ArrayList<LatLng> point=new ArrayList<>();

      //  point.add(new LatLng(40.76793169992044, -73.98180484771729));
        point.add(new LatLng(12.880760,77.446938));
        point.add(new LatLng(12.8619456, 77.4354617));



        for(int i=0;i<point.size();i++){
        markerOptions.position(point.get(i));
        googleMap.addMarker(markerOptions);}
    }


}
