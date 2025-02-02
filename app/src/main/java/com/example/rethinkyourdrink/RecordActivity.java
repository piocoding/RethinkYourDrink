package com.example.rethinkyourdrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private final String FILENAME = "drink_data_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button BtnBack = findViewById(R.id.BtnBack);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button BtnRecord = findViewById(R.id.BtnRecord);
        BtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup dayOptions = findViewById(R.id.RGDay);
                RadioButton day = findViewById(dayOptions.getCheckedRadioButtonId());
                RadioGroup catOptions = findViewById(R.id.RGCategory);
                RadioButton category = findViewById(catOptions.getCheckedRadioButtonId());
                EditText ETName = findViewById(R.id.ETDrinkName);
                EditText ETAmount = findViewById(R.id.ETAmount);

                if( day==null || category==null || ETAmount.getText()==null ) return;

                String name = ETName.getText().toString().replace(",", "").replace("\n","");
                if(name.length() == 0 || category.getText().toString() == "Plain Water") name = category.getText().toString();
                int amount = 0;
                try{
                    amount = Integer.parseInt(ETAmount.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }

                StringBuilder builder = new StringBuilder();
                builder.append(day.getText()).append(",").append(name).append(",").append(amount).append(",").append(category.getText()).append("\n");

                try(FileOutputStream fos = openFileOutput(FILENAME,MODE_APPEND)){
                    fos.write(builder.toString().getBytes());
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }

                dayOptions.clearCheck();
                catOptions.clearCheck();
                ETAmount.setText("");
                ETName.setText("");
            }
        });
    }


}