package com.example.gulzar.christuniversitybus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Gulzar on 19-02-2016.
 */
public class FireBaseTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        Firebase.setAndroidContext(this);
        //Firebase ref = new Firebase("https://docs-examples.firebaseio.com/android/saving-data/fireblog");
        // Get a reference to our posts
        Firebase ref = new Firebase("https://docs-examples.firebaseio.com/web/saving-data/fireblog/posts");
        // Attach an listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(FireBaseTest.this, snapshot.getValue().toString(), Toast.LENGTH_LONG);
                //System.out.println(snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(FireBaseTest.this, "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG);
                //System.out.println("The read failed: " + firebaseError.getMessage());
            }


        });


    }
}
