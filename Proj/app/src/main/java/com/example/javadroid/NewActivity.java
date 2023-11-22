package com.example.javadroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class NewActivity extends AppCompatActivity {

    private String buttonNameBase = "cell";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        setTitle("New");
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    public void DisplayAlertMessage (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void changeCellState (int cellRow, int cellCol) {
        // Check tag of given cell and set alive to dead and vice versa
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        Button checkingButton = findViewById(getResources().getIdentifier(currentCell, "id", getPackageName()));
        if (checkingButton.getTag().equals("alive")) {
            checkingButton.setBackgroundColor(Color.BLACK);
            checkingButton.setTag("dead");
        }
        else {
            checkingButton.setBackgroundColor(Color.GREEN);
            checkingButton.setTag("alive");
        }
    }

    public void cellClicked (View currentView) {
        // Check if alive or dead and operate accordingly
        Button currentButton = (Button) currentView;

        if (currentButton.getTag().equals("alive")) {
            currentButton.setBackgroundColor(Color.BLACK);
            currentButton.setTag("dead");
        }
        else {
            currentButton.setBackgroundColor(Color.GREEN);
            currentButton.setTag("alive");
        }
    }

    public void processStep(View currentView) {
        // First, check I was passed the right view (the table layout)
        if (!findViewById(R.id.GoL_Table).isActivated()) {
            DisplayAlertMessage("ERROR: Table Not Active");
            return;
        }

        // Need to check state of buttons

        // Figure out what the next condition of each(?) button will be

        // proceed to change buttons as necessary

    }

    public boolean checkCenterCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);

        // Check horizontals
        String checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol+1);
        Button checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (checkingButton.getTag().equals("alive")) {
            checkingButton.setBackgroundColor(Color.BLACK);
            checkingButton.setTag("dead");
        }
        else {
            checkingButton.setBackgroundColor(Color.GREEN);
            checkingButton.setTag("alive");
        }


        // Check verticals

        // check diagonals


        return true;
    }
}