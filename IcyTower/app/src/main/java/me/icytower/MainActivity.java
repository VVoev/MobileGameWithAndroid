package me.icytower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void initializeNewGame (View view) {
        Intent initializeNewGame = new Intent(this, NewGame.class);
        startActivity(initializeNewGame);
    }

    public void loadHighScores(View view) {
        Intent getHighScore = new Intent(this, MyHighscore.class);
        startActivity(getHighScore);
    }

    public void showInstructions (View view){
        Intent showInstructions = new Intent(this,Instructions.class);
        startActivity(showInstructions);
    }
}
