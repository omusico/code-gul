package com.example.gulzar.christuniversitybus;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Register extends FragmentActivity {

    ArrayList<LatLng> point=new ArrayList<>();
    GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    String addd;
    ProgressBar pbbar;
    EditText fn,ln,reg,addr;
    ConnectionClass connectionClass;
    String cord;
    //String route1[];
    ArrayList<ArrayList<String>> route=new ArrayList<ArrayList<String>>();
    ArrayList<String> datanum = new ArrayList<>();

    String s[][];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbar);
        fn=(EditText)findViewById(R.id.fn);
        ln=(EditText)findViewById(R.id.ln);
        reg=(EditText)findViewById(R.id.reg);
        addr=(EditText)findViewById(R.id.addr);
        pbbar.setVisibility(View.GONE);
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
            /**try {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        }
                    }
                });
            }
            catch (Exception e)
            {

            }*/






            drawMarker();


        }

        //Auto Complete Text View
        GetRoute getRoute=new GetRoute();
        getRoute.execute("");
     /**   AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.actv1);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, (String[]) route.get(0).toArray());
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);*/


    }


    private void drawMarker(){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();


      //  point.add(new LatLng(40.76793169992044, -73.98180484771729));
      //  point.add(new LatLng(12.880760, 77.446938));
       // point.add(new LatLng(12.8619456, 77.4354617));



        for(int i=0;i<point.size();i++){
        markerOptions.position(point.get(i));
        googleMap.addMarker(markerOptions);}
    }
    public void Submitbtn(View view) {
        String addd = addr.getText().toString();
        Boolean flag = true;
        getcoordinatebyname gcb = new getcoordinatebyname();
        try {
            cord = gcb.getLocationFromAddress(Register.this, addd).toString();
        } catch (Exception e) {
            flag = false;
        }
        if (flag == true) {
            if (cord != null) {
                Toast.makeText(Register.this, cord, Toast.LENGTH_SHORT).show();
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(gcb.getLocationFromAddress(Register.this, addd));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

                googleMap.moveCamera(center);
                googleMap.animateCamera(zoom);
        }

            }



    //AddPro addPro = new AddPro();
    //addPro.execute("");


    }













    public class AddPro extends AsyncTask<String, String, String> {


        String z = "";
        Boolean isSuccess = false;
        String fnstr=fn.getText().toString();
        String lnstr=ln.getText().toString();
        String addrstr=addr.getText().toString();
        String regstr=reg.getText().toString();

        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(Register.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess==true) {//calling new activity
               // Toast.makeText(Register.this,"DATA PUSHED", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (fnstr.trim().equals("") || lnstr.trim().equals(""))
                z = "Please enter First NAme and Last Name ";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        /*String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                .format(Calendar.getInstance().getTime());*/
                        /*String query = "select * from student_user where username='" + userid + "' and password='" + password + "'";*/
                        String s = "1417185";
                        String query = "insert into student_registration (First_name,last_name,reg_no,reg_pass,route_no) values ('" + fnstr + "','" + lnstr + "','" + regstr + "','" + s + "','" + addrstr + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        z = "Added Successfully";
                        isSuccess = true;
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }


        //Fill list with bus routes



    }

    public class GetRoute extends AsyncTask<String, String, String> {
        String z = "";



        @Override
        protected void onPreExecute() {

            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            String str=new String();
            str="";
            pbbar.setVisibility(View.GONE);
            for(int i=0;i<route.size();i=i+1){
               // route1= (String[]) route.get(i).toArray();
                /*str+=datanum.get(i).toString()+datanum.get(i+1).toString()+datanum.get(i+2).toString()+"\n";
                point.add(new LatLng(Double.parseDouble(datanum.get(i+1)), Double.parseDouble(datanum.get(i+2))));*/
            }
            drawMarker();
            Toast.makeText(Register.this, str, Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    s=new String[2][];
                    String query[] = {"select * from route1","select * from route2"};
                    for (int i =0;i<2;i++)
                    {   datanum.clear();
                        PreparedStatement ps = con.prepareStatement(query[i]);
                        ResultSet rs = ps.executeQuery();

                        //ArrayList<String> data1 = new ArrayList<String>();
                        while (rs.next()) {
                            datanum.add(rs.getString("Bus_Stop"));
                            datanum.add(rs.getString("lat"));
                            datanum.add(rs.getString("long"));
                        }
                        route.add(datanum);
                    }


                    z = "Success";
                }
            } catch (Exception ex) {
                z = "Error retrieving data from table";

            }
            return z;
        }
    }



}


