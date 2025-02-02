package com.example.rethinkyourdrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "drink_data_file";
    int totalWater=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File DataFile = new File(getFilesDir(), FILENAME);
        if(DataFile.exists()){
            totalWater = readTotalWater();
            TextView TVTotal = findViewById(R.id.TVTotalWaterToday);
            TVTotal.setText(totalWater + " ml");
        }

        Button BtnRecord = findViewById(R.id.BtnRecord);
        BtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(intent);
            }
        });
        Button BtnSummary = findViewById(R.id.BtnSummary);
        BtnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                startActivity(intent);
            }
        });
    }

    public int readTotalWater(){
        int total = 0;
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(isr)){
            String line = reader.readLine();
            while (line!=null){
                int amount = Integer.parseInt(line.split(",")[2]);
                if(line.split(",")[0].equals("DAY 3")) total += amount;
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return total;
    }
}