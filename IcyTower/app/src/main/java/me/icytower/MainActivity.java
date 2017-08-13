package me.icytower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import me.icytower.Game.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void initializeNewGame (View view) {
        Intent initializeNewGame = new Intent(this, NewGame.class);
        startActivity(initializeNewGame);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

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
