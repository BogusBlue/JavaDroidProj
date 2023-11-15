package com.example.javadroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("JavaDroid");
    }

    public void Disable (View currentView) {
        currentView.setEnabled(false);
        Button currentButton = (Button) currentView;
        currentButton.setText("Disabled");
        TextView currentText = (TextView) findViewById(R.id.textView); // cast is unnecessary because findViewById returns TextView type View anyways
        currentText.setText("Button Disabled");
    }

    public void ProcessUserText (View currentView) {
        TextInputEditText currentUserInput = findViewById(R.id.userInput);
        String currentUserInputText = currentUserInput.getText().toString();
        TextView currentText = (TextView) findViewById(R.id.textView); // cast is unnecessary because findViewById returns TextView type View anyways
        currentText.setText(currentUserInputText);
        DisplayAlertMessage(currentUserInputText);
    }

    public void DisplayAlertMessage (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void LaunchNewActivity (View currentView) {
        Intent newActivityIntent = new Intent(this, NewActivity.class);
        startActivity(newActivityIntent);
    }
}