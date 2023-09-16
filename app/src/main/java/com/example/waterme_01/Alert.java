package com.example.waterme_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Alert extends AppCompatActivity {

    private TextView outputSoil;
    private TextView outputPercentage;
    private TextView outputAlert;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        // Get a reference to the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        outputSoil = findViewById(R.id.outputMoisterLevel);
        outputPercentage = findViewById(R.id.outputPercentage);
        outputAlert = findViewById(R.id.outputAlert);
        button = findViewById(R.id.backAlert);
        button2 = findViewById(R.id.btncheckStatus);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseRef = database.getReference("soilMoistureSensor/sensorValue");
                DatabaseReference moisturePercentageRef = database.getReference("soilMoistureSensor/moisturePercentage");
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Integer data = dataSnapshot.getValue(Integer.class);
                        if (data != 0) {
                            outputSoil.setText(String.valueOf(data));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Toast.makeText(Alert.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                moisturePercentageRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Integer data = dataSnapshot.getValue(Integer.class);
                        if (data != 0) {
                            outputPercentage.setText(String.valueOf(data));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Toast.makeText(Alert.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Add code to retrieve and display temperature data if needed
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
