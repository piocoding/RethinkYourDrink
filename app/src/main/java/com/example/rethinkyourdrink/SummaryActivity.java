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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SummaryActivity extends AppCompatActivity {

    private final String FILENAME = "drink_data_file";
    int[][] totals = new int[3][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        String data = readSummary();
        TextView dataField = findViewById(R.id.DataField);
        dataField.setText(data);
        setFields();

        Button BtnBack = findViewById(R.id.BtnBack);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public String readSummary(){
        String data = "";
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
                String[] arr = line.split(",");
                data += arr[0] + "   " + arr[1] + "   " + arr[2] + " ml\n";
                int amount = Integer.parseInt(arr[2]);
                int i = arr[0].equals("DAY 1")? 0 : arr[0].equals("DAY 2")? 1 : 2;
                int j = arr[3].equals("Plain Water")? 1 : arr[3].equals("Non-Sweetened")? 2 : 3;
                totals[i][j] += amount;
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }

    public void setFields(){
        TextView Day1Total = findViewById(R.id.TVDay1Amount), Day1Plain = findViewById(R.id.TVDay1Plain),
                Day1NonSweet = findViewById(R.id.TVDay1NonSweet), Day1Sweet = findViewById(R.id.TVDay1Sweet),
                Day2Total = findViewById(R.id.TVDay2Amount), Day2Plain = findViewById(R.id.TVDay2Plain),
                Day2NonSweet = findViewById(R.id.TVDay2NonSweet), Day2Sweet = findViewById(R.id.TVDay2Sweet),
                Day3Total = findViewById(R.id.TVDay3Amount), Day3Plain = findViewById(R.id.TVDay3Plain),
                Day3NonSweet = findViewById(R.id.TVDay3NonSweet), Day3Sweet = findViewById(R.id.TVDay3Sweet);
        TextView[] fields = { Day1Total, Day1Plain, Day1NonSweet, Day1Sweet,
                Day2Total, Day2Plain, Day2NonSweet, Day2Sweet,
                Day3Total, Day3Plain, Day3NonSweet, Day3Sweet};
        int x = 0;
        for (int i = 0; i < 3; i++) {
            totals[i][0] = totals[i][1] + totals[i][2] + totals[i][3];
            for (int j = 0; j < 4; j++) {
                fields[x++].setText(totals[i][j] + " ml");
            }
        }
    }
}