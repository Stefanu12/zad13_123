package com.example.zad13;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String DATA_KEY = "UserInputData";
    private static final String SWITCH_KEY = "SwitchState";
    private static final String COUNTER_KEY = "LaunchCounter";

    private EditText dataInput;
    private Button saveButton;
    private Switch switch1;
    private TextView counterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataInput = findViewById(R.id.dataInput);
        saveButton = findViewById(R.id.saveButton);
        switch1 = findViewById(R.id.SWitch);
        counterView = findViewById(R.id.counter);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int launchCount = prefs.getInt(COUNTER_KEY, 0) + 1;
        prefs.edit().putInt(COUNTER_KEY, launchCount).apply();
        counterView.setText("Liczba uruchomień: " + launchCount);

        String savedData = prefs.getString(DATA_KEY, "");
        dataInput.setText(savedData);

        boolean switchState = prefs.getBoolean(SWITCH_KEY, false);
        switch1.setChecked(switchState);

        Toast.makeText(this, "Dane i przełącznik wczytane!", Toast.LENGTH_SHORT).show();

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(DATA_KEY, dataInput.getText().toString());
            editor.putBoolean(SWITCH_KEY, switch1.isChecked());
            editor.apply();
            Toast.makeText(this, "Dane zapisane!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(SWITCH_KEY, switch1.isChecked());
        editor.apply();
    }
}
