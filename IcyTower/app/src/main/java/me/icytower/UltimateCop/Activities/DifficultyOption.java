package me.icytower.UltimateCop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.icytower.R;
import me.icytower.UltimateCop.GlobalConstants.Constants;


public class DifficultyOption extends AppCompatActivity {

    private void checkForUserChoise(View view){
        int id = view.getId();
        switch (view.getId()){
            case R.id.easy:
                Constants.DIFICULTY_LEVEL = "EASY";
                break;
            case R.id.normal:
                Constants.DIFICULTY_LEVEL = "NORMAL";
                break;
            case R.id.hard:
                Constants.DIFICULTY_LEVEL = "HARD";
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_option);

    }
    public void initializeNewGame(View view){
        checkForUserChoise(view);
        startActivity(new Intent(getApplication(),NewGame.class));
    }
}
