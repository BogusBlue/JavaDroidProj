package com.example.javadroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
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

    public boolean checkCell_Dangerous (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;
        boolean liveCell = false;

        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            liveCell =true;
        }

        // Check horizontals
        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol + 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol - 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        // Check verticals
        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow + 1) + Integer.toString(cellCol);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow - 1) + Integer.toString(cellCol);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        // check diagonals
        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow + 1) + Integer.toString(cellCol + 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow - 1) + Integer.toString(cellCol + 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow + 1) + Integer.toString(cellCol - 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        try {
            checkingCell = buttonNameBase + Integer.toString(cellRow - 1) + Integer.toString(cellCol - 1);
            checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
            if (isCellAlive(checkingButton)) {
                neighborCount++;
            }
        } catch (Exception e) {}

        if (liveCell && neighborCount < 4 && neighborCount > 1) {
            return true;
        } else if (!liveCell && neighborCount == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    public void toggleCellState (int cellRow, int cellCol) {
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

    public void updateCellState (int cellRow, int cellCol, boolean newState) {
        // Check tag of given cell and set alive to dead and vice versa
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        Button checkingButton = findViewById(getResources().getIdentifier(currentCell, "id", getPackageName()));
        if (newState) {
            checkingButton.setBackgroundColor(Color.GREEN);
            checkingButton.setTag("alive");
        }
        else {
            checkingButton.setBackgroundColor(Color.BLACK);
            checkingButton.setTag("dead");
        }
    }

    public boolean isCellAlive (Button checkingButton) {
        if (checkingButton.getTag().equals("alive")) {
            return true;
        }
        else {
            return false;
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
        // First, create array for next iteration states
        String[][] nextIteration = new String[10][10];

        // Need to check state of buttons (in parallel)
        // Figure out what the next condition of each(?) button will be (in parallel)
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 10; col++) {
                if (checkCell_Dangerous(row, col)) {
                    nextIteration[row-1][col-1] = "alive";
                }
                else {
                    nextIteration[row-1][col-1] = "dead";
                }
            }
        }

        // proceed to change buttons as necessary (in parallel)
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 10; col++) {
                if (nextIteration[row-1][col-1].equals("alive")) {
                    updateCellState(row, col, true);
                }
                else if (nextIteration[row-1][col-1].equals("dead")){
                    updateCellState(row, col, false);
                }
            }
        }
    }

    public boolean checkCenterCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;

        // Check horizontals
        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // Check verticals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // check diagonals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        return neighborCount < 4 && neighborCount > 1;
    }

    public boolean checkTopCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;

        // Check horizontals
        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // Check verticals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // check diagonals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        return neighborCount < 4 && neighborCount > 1;
    }

    public boolean checkBottomCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;

        // Check horizontals
        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // Check verticals
        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // check diagonals

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        return neighborCount < 4 && neighborCount > 1;
    }

    public boolean checkLeftCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;

        // Check horizontals
        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // Check verticals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // check diagonals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol+1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        return neighborCount < 4 && neighborCount > 1;
    }

    public boolean checkRightCell (int cellRow, int cellCol) {
        // Check all 8 surrounding cells
        String currentCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol);
        int neighborCount = 0;
        String checkingCell;
        Button checkingButton;

        // Check horizontals

        checkingCell = buttonNameBase + Integer.toString(cellRow) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // Check verticals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        // check diagonals
        checkingCell = buttonNameBase + Integer.toString(cellRow+1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        checkingCell = buttonNameBase + Integer.toString(cellRow-1) + Integer.toString(cellCol-1);
        checkingButton = findViewById(getResources().getIdentifier(checkingCell, "id", getPackageName()));
        if (isCellAlive(checkingButton)) {
            neighborCount++;
        }

        return neighborCount < 4 && neighborCount > 1;
    }
}